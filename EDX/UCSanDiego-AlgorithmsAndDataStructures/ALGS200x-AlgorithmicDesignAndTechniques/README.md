# ALGS200x - Algorithmic Design and Techniques
This module contains all programing assignments for course ALGS200x Algorithmic Design and Techniques

See https://www.edx.org/course/algorithmic-design-and-techniques

## Requirements
* Java 1.8
* Maven 3.3.9

## Build
```shell script
mvn clean package
```

## Test and Run

### PA1.1: Sum of Two Digits
```shell script
# Unit Tests
mvn clean verify -Dtest=SumOfTwoDigitsTest
```

### PA1.2: Maximum Pairwise Product
```shell script
# Unit Tests
mvn clean verify -Dtest=MaximumPairwiseProductTest
```

### PA2.1: Fibonacci Number
```shell script
# Unit Tests
mvn clean verify -Dtest=SmallFibonacciNumberTest
```

### PA2.2: Last Digit of a Large Fibonacci Number
```shell script
# Unit Tests
mvn clean verify -Dtest=LastDigitOfLargeFibonacciNumberTest
```

### PA2.3: Greatest Common Divisor
```shell script
# Unit Tests
mvn clean verify -Dtest=GreatestCommonDivisorTest
```

### PA2.4: Least Common Multiple
```shell script
# Unit Tests
mvn clean verify -Dtest=LeastCommonMultipleTest
```

### PA2.5: Fibonacci Number Again
```shell script
# Unit Tests
mvn clean verify -Dtest=ModOfLargeFibonacciNumberTest
```

### PA2.6: Last Digit of the Sum of Fibonacci Numbers
```shell script
# Unit Tests
mvn clean verify -Dtest=LastDigitOfTheSumOfFibonacciNumbersTest -DtimeLimitTestDuration=10000 -DstressTestDuration=10000
```

### PA2.7: Last Digit of the Sum of Fibonacci Numbers Again
```shell script
# Unit Tests
mvn clean verify -Dtest=LastDigitOfThePartialSumOfFibonacciNumbersTest -DtimeLimitTestDuration=10000 -DstressTestDuration=10000
```
cle
### PA3.1: Money Change
```shell script
# Unit Tests
mvn clean verify -Dtest=MoneyChangeTest -DtimeLimitTestDuration=10000 -DstressTestDuration=10000
```

### PA3.2: Maximum Value of the Loot
```shell script
# Unit Tests
mvn clean verify -Dtest=MaximumValueOfLootTest -DtimeLimitTestDuration=10000 -DstressTestDuration=10000
```
