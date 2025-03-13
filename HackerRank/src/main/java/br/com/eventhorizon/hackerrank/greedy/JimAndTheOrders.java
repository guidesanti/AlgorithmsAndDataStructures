package br.com.eventhorizon.hackerrank.greedy;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

/**
 * Jim's Burgers has a line of hungry customers. Orders vary in the time it takes to prepare them. Determine the order
 * the customers receive their orders. Start by numbering each of the customers from  to , front of the line to the back.
 * You will then be given an order number and a preparation time for each customer.
 * <p>
 * The time of delivery is calculated as the sum of the order number and the preparation time.
 * If two orders are delivered at the same time, assume they are delivered in ascending customer number order.
 */
public class JimAndTheOrders implements PA {

    private int n;

    private int[] orderNumber;

    private int[] preparationTime;

    private int[] customerDeliveryOrder;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read n
        n = scanner.nextInt();

        // Read order number and preparation time
        orderNumber = new int[n];
        preparationTime = new int[n];
        for (int i = 0; i < n; i++) {
            orderNumber[i] = scanner.nextInt();
            preparationTime[i] = scanner.nextInt();
        }
        customerDeliveryOrder = new int[n];
    }

    private void writeOutput() {
        Arrays.stream(customerDeliveryOrder).forEach(v -> System.out.printf("%d ", v));
    }

    @Override
    public void reset() {
        n = 0;
        orderNumber = null;
        preparationTime = null;
        customerDeliveryOrder = null;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        var deliveryTime = new LinkedList<Order>();
        for (int i = 0; i < n; i++) {
            deliveryTime.add(new Order(i + 1, orderNumber[i] + preparationTime[i]));
        }
        deliveryTime.sort(Comparator.comparingInt((Order o) -> o.deliveryTime).thenComparingInt(o -> o.customerNumber));
        for (int i = 0; i < deliveryTime.size(); i++) {
            customerDeliveryOrder[i] = deliveryTime.get(i).customerNumber;
        }
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var queue = new PriorityQueue<Order>();
        for (int i = 0; i < n; i++) {
            queue.add(new Order(i + 1, orderNumber[i] + preparationTime[i]));
        }
        var i = 0;
        while (!queue.isEmpty()) {
            customerDeliveryOrder[i++] = queue.poll().customerNumber;
        }
    }

    private record Order(int customerNumber, int deliveryTime) implements Comparable<Order> {

        @Override
        public int compareTo(Order o) {
            var diff = this.deliveryTime - o.deliveryTime;
            if (diff == 0) {
                diff = this.customerNumber - o.customerNumber;
            }
            return diff;
        }
    }
}
