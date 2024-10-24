package Ex4_6581167; // Wongsatorn Suwannarit

import java.util.*;

class Customer {
    private static int runningID = 1; // for running customer ID
    private int ID;
    private int order; // order amount (random value 1-20)

    public Customer() {
        Random rand = new Random();
        order = rand.nextInt(1,21);
        ID = runningID;
        runningID++;
    }

    public int getID() {
        return ID;
    }

    public int getOrder() {
        return order;
    }
    public void print() {
        System.out.printf(" [Customer %2d order %2d lots]\n",ID,order);
    }
}

class Shop {
    private int itemsToRefill;  // items to refill in odd day
    private int maxDays;        // max day for simulation
    private PriorityQueue<Customer> orderQueue = new PriorityQueue<>(new SortByCustomerOrder().reversed().thenComparing(new SortByCustomerID()));
    private ArrayDeque<Customer> billingQueue = new ArrayDeque<>();
    public Shop(int item,int day) {
        itemsToRefill = item;
        maxDays = day;
    }
    public static int remain = 0;
    public int getItem() {
        return itemsToRefill;
    }
    public int getMaxDays() {
        return maxDays;
    }
    public boolean checkDay(int i) {
        if (i%2 == 1){
            return false;
        }
        else {
            return true;
        }
    }

    public void simulation() {
        System.out.println("\n");
        System.out.println("=== Day 0 : customer arrival ===");

        for (int ini = 0; ini < 5;ini++) {
            Customer customer = new Customer();
            customer.print();
            orderQueue.add(customer);
        }
        System.out.println("\n");
        System.out.println("=== Simulation ===");
        for (int i=1;i <= maxDays;i++){
            if (!checkDay(i)) {
                remain = remain + itemsToRefill;
                Customer customer = new Customer();
                System.out.printf("Day %2d\n",i);
                System.out.printf("Refilling   >> Remaining items = %2d lots\n",remain);
                System.out.printf("New arrival >>");
                customer.print();
                orderQueue.add(customer);

                for (int j=1;j <= 2;j++) {
                    if (!orderQueue.isEmpty()) {
                        Customer currentCustomerO = orderQueue.poll();

                        if (currentCustomerO.getOrder() <= remain) {
                            remain -= currentCustomerO.getOrder();
                            System.out.printf("Packing %d   >> [Customer %2d order %2d lots]  success  Remaining items = %2d lots\n", j, currentCustomerO.getID(), currentCustomerO.getOrder(), remain);
                            billingQueue.add(currentCustomerO);
                        }
                        else {
                            System.out.printf("Packing %d   >> [Customer %2d order %2d lots] failure\n", j, currentCustomerO.getID(), currentCustomerO.getOrder());
                            orderQueue.add(currentCustomerO);
                        }
                    }
                    else {
                        System.out.println("No more customers to pack");
                        break;
                    }
                }
                System.out.println();
            }

            else if (checkDay(i)){
                Customer customerE = new Customer();
                System.out.printf("Day %2d\n",i);
                System.out.printf("New arrival >>");
                customerE.print();
                orderQueue.add(customerE);

                for (int j=1;j<=2;j++) {
                    if (!orderQueue.isEmpty()){
                        Customer currentCustomerE = orderQueue.poll();

                        if (currentCustomerE.getOrder() <= remain) {
                            remain -= currentCustomerE.getOrder();
                            System.out.printf("Packing %d   >> [Customer %2d order %2d lots]  success  Remaining items = %2d lots]\n", j, currentCustomerE.getID(), currentCustomerE.getOrder(), remain);
                            billingQueue.add(currentCustomerE);
                        } else {
                            System.out.printf("Packing %d   >> [Customer %2d order %2d lots]  failure\n",j,currentCustomerE.getID(),currentCustomerE.getOrder());
                            orderQueue.add(currentCustomerE);
                        }
                    } else {
                        System.out.println("No more customers to pack.");
                        break;
                    }
                }
                while (!billingQueue.isEmpty()) {
                    Customer bill = billingQueue.removeFirst();
                    System.out.printf("billing     >> Customer %d\n", bill.getID());
                }
                System.out.println("");
            }
        }
        System.out.println("\n=== Remaining customers in order queue ===");
        for (Customer remain : orderQueue){
            remain.print();
        }

        System.out.println("\n=== Remaining customers in billing queue (latest to earliest) ===");
        while (!billingQueue.isEmpty()) {
            billingQueue.getLast().print();
            billingQueue.removeLast();
        }
    }
}

class SortByCustomerOrder implements Comparator<Customer> {
    public int compare (Customer c1, Customer c2){
        return Integer.compare(c1.getOrder(),c2.getOrder());
    }
}

class SortByCustomerID implements Comparator<Customer> {
    public int compare (Customer c1, Customer c2){
        return Integer.compare(c1.getID(),c2.getID());
    }
}

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int item, day;
        System.out.println("Enter refill items");
        item = scanner.nextInt();
        System.out.println("Enter max days");
        day = scanner.nextInt();
        Shop shop = new Shop(item,day);
        shop.simulation();
        scanner.close();
    }
}