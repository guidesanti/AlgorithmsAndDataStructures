package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PAv2;

import java.util.*;

public class P1405 implements PAv2 {

  private static final int MOD = 1000;

  private static final int MAX_NUMBER_OF_LINES = 100;

  private static final Map<String, OpCode> OP_CODES = new HashMap<>();

  private final Map<String, Register> registers = new HashMap<>();

  private final Register R0 = new Register("R0");

  private final Register R1 = new Register("R1");

  private final Register R2 = new Register("R2");

  private final Register R3 = new Register("R3");

  private final Register R4 = new Register("R4");

  private final Register R5 = new Register("R5");

  private final Register R6 = new Register("R6");

  private final Register R7 = new Register("R7");

  private final Register R8 = new Register("R8");

  private final Register R9 = new Register("R9");

  private final Register PC = new Register("PC");

  static {
    for (OpCode opCode : OpCode.values()) {
      OP_CODES.put(opCode.name, opCode);
    }
  }

  private FastScanner scanner;

  private int numberOfLines;

  private int inputParameter;

  private Operation[] program;

  private void init() {
    registers.put(R0.name, R0);
    registers.put(R1.name, R1);
    registers.put(R2.name, R2);
    registers.put(R3.name, R3);
    registers.put(R4.name, R4);
    registers.put(R5.name, R5);
    registers.put(R6.name, R6);
    registers.put(R7.name, R7);
    registers.put(R8.name, R8);
    registers.put(R9.name, R9);
    registers.put(PC.name, PC);
    scanner = new FastScanner(System.in);
    numberOfLines = 0;
    inputParameter = 0;
    program = new Operation[MAX_NUMBER_OF_LINES];
    for (int i = 0; i < program.length; i++) {
      program[i] = new Operation();
    }
  }

  @Override
  public void finalSolution() {
    init();
    finalSolutionImpl();
  }

  private void finalSolutionImpl() {
    while (true) {
      // Read L and N
      numberOfLines = scanner.nextInt();
      inputParameter = scanner.nextInt();;

      // Check stop condition
      if (numberOfLines == 0 && inputParameter == 0) {
        break;
      }

      // Read the operations
      readOperations();

      // Run the program
      String output = runProgram();

      // Write the result
      if (output == null) {
        System.out.println("*");
      } else {
        System.out.println(output);
      }
    }
  }

  private static class Context {

    int r0;

    int r1;

    int r2;

    int r3;

    int r4;

    int r5;

    int r6;

    int r7;

    int r8;

    int r9;

    int pc;
  }

  private static class Operation  {

    OpCode opCode;

    Register op1Register;

    int op1Value;

    Register op2Register;

    int op2Value;

    Integer ifNotGoto;

    public Operation() {
      reset();
    }

    void reset() {
      this.opCode = OpCode.NOP;
      this.op1Register = null;
      this.op1Value = -1;
      this.op2Register = null;
      this.op2Value = -1;
      this.ifNotGoto = null;
    }

    @Override
    public String toString() {
      return ""
          + opCode
          + (op1Register != null ? " " + op1Register : op1Value >= 0 ? " " + op1Value : "")
          + (op2Register != null ? "," + op2Register : op2Value >= 0 ? "," + op2Value : "")
          + (ifNotGoto != null ? " (ifFalseGoto = " + ifNotGoto + ")" : "");
    }
  }

  private static class Register {

    final String name;

    int value;

    Register(String name) {
      this.name = name;
    }
  }

  private enum OpCode {

    MOV("MOV", 0),
    ADD("ADD", 1),
    SUB("SUB", 2),
    MUL("MUL", 3),
    DIV("DIV", 4),
    MOD("MOD", 5),
    IFEQ("IFEQ", 6),
    IFNEQ("IFNEQ", 7),
    IFG("IFG", 8),
    IFL("IFL", 9),
    IFGE("IFGE", 10),
    IFLE("IFLE", 11),
    ENDIF("ENDIF", 12),
    CALL("CALL", 13),
    RET("RET", 14),
    NOP("NOP", 15);

    final String name;

    final int code;

    OpCode(String name, int code) {
      this.name = name;
      this.code = code;
    }
  }

  private void readOperations() {
    Stack<Operation> conditionalOperations = new Stack<>();
    for (int i = 0; i < numberOfLines; i++)
    {
      Operation operation = program[i];
      operation.reset();

      // Read the operation code
      String opCodeName = scanner.next();
      operation.opCode = OP_CODES.get(opCodeName);

      // Read the operators
      if (operation.opCode.code <= OpCode.IFLE.code) {
        String[] operators = scanner.next().split(",");

        // Operator 1
        String op1 = operators[0];
        if (op1.startsWith("R")) {
          operation.op1Register = registers.get(op1);
        } else {
          operation.op1Value = Integer.parseInt(op1);
        }

        // Operator 2
        String op2 = operators[1];
        if (op2.startsWith("R")) {
          operation.op2Register = registers.get(op2);
        } else {
          operation.op2Value = Integer.parseInt(op2);
        }

        // Handle conditional operations
        if (operation.opCode.code >= OpCode.IFEQ.code) {
          conditionalOperations.push(operation);
        }
      } else if (operation.opCode == OpCode.CALL || operation.opCode == OpCode.RET) {
        String op1 = scanner.next();
        if (op1.startsWith("R")) {
          operation.op1Register = registers.get(op1);
        } else {
          operation.op1Value = Integer.parseInt(op1);
        }
      } else if (operation.opCode == OpCode.ENDIF) {
        Operation ifOperation = conditionalOperations.pop();
        ifOperation.ifNotGoto = i;
      }
    }
    assert conditionalOperations.isEmpty();
  }

  private String runProgram() {
    String output = null;
    Set<Integer> inputSet = new HashSet<>();
    Stack<Integer> inputStack = new Stack<>();
    inputSet.add(inputParameter);
    inputStack.add(inputParameter);
    Map<Integer, Integer> inputOutputCache = new HashMap<>();
    Stack<Context> stack = new Stack<>();
    resetRegisters();
    R0.value = inputParameter;
    while (PC.value < numberOfLines) {
      Operation operation = program[PC.value];
      int op1Value = operation.op1Register != null ? operation.op1Register.value : operation.op1Value;
      int op2Value = operation.op2Register != null ? operation.op2Register.value : operation.op2Value;
      int aux;
      switch (operation.opCode)
      {
        case MOV:
          operation.op1Register.value = op2Value;
          break;

        case ADD:
          operation.op1Register.value = (op1Value + op2Value) % MOD;
          break;

        case SUB:
          aux = op1Value - op2Value;
          if (aux < 0) {
					  aux = (MOD + aux);
          }
          operation.op1Register.value = aux;
        break;

        case MUL:
          operation.op1Register.value = (op1Value * op2Value) % MOD;
          break;

        case DIV:
          if (op2Value > 0) {
            operation.op1Register.value = op1Value / op2Value;
          } else {
            operation.op1Register.value = 0;
          }
          break;

        case MOD:
          if (op2Value > 0) {
            operation.op1Register.value = op1Value % op2Value;
          } else {
            operation.op1Register.value = 0;
          }
          break;

        case IFEQ:
          if (op1Value != op2Value) {
            PC.value = operation.ifNotGoto;
          }
          break;

        case IFNEQ:
          if (op1Value == op2Value) {
            PC.value = operation.ifNotGoto;
          }
        break;

        case IFG:
          if (op1Value <= op2Value) {
            PC.value = operation.ifNotGoto;
          }
          break;

        case IFL:
          if (op1Value >= op2Value) {
            PC.value = operation.ifNotGoto;
          }
          break;

        case IFGE:
          if (op1Value < op2Value) {
            PC.value = operation.ifNotGoto;
          }
          break;

        case IFLE:
          if (op1Value > op2Value) {
            PC.value = operation.ifNotGoto;
          }
          break;

        case ENDIF:
          // Do nothing
          break;

        case CALL:
          if (inputSet.contains(op1Value)) {
            output = "*";
            PC.value = numberOfLines;
            break;
          }
          Integer out = inputOutputCache.get(op1Value);
          if (out != null) {
            R9.value = out;
          } else {
            inputSet.add(op1Value);
            inputStack.push(op1Value);
            saveContext(stack);
            resetRegisters();
            R0.value = op1Value;
            PC.value = -1;
          }
          break;

        case RET:
          if (stack.isEmpty()) {
            output = "" + op1Value;
            PC.value = numberOfLines;
          } else {
            restoreContext(stack);
            int input = inputStack.pop();
            inputOutputCache.put(input, op1Value);
            inputSet.remove(input);
            R9.value = op1Value;
          }
          break;

        case NOP:
          // Do nothing
          break;

        default:
          break;
      }

      PC.value++;
    }

    return output;
  }

  private void resetRegisters() {
    R0.value = 0;
    R1.value = 0;
    R2.value = 0;
    R3.value = 0;
    R4.value = 0;
    R5.value = 0;
    R6.value = 0;
    R7.value = 0;
    R8.value = 0;
    R9.value = 0;
    PC.value = 0;
  }

  private void saveContext(Stack<Context> stack) {
    Context context = new Context();
    context.r0 = R0.value;
    context.r1 = R1.value;
    context.r2 = R2.value;
    context.r3 = R3.value;
    context.r4 = R4.value;
    context.r5 = R5.value;
    context.r6 = R6.value;
    context.r7 = R7.value;
    context.r8 = R8.value;
    context.r9 = R9.value;
    context.pc = PC.value;
    stack.push(context);
  }

  private void restoreContext(Stack<Context> stack) {
    Context context = stack.pop();
    R0.value = context.r0;
    R1.value = context.r1;
    R2.value = context.r2;
    R3.value = context.r3;
    R4.value = context.r4;
    R5.value = context.r5;
    R6.value = context.r6;
    R7.value = context.r7;
    R8.value = context.r8;
    R9.value = context.r9;
    PC.value = context.pc;
  }
}
