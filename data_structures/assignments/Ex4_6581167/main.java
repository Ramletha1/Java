package Ex4_6581167;

import java.util.*;

class Customer {
    private static int runningID = 1; // for running customer ID
    private int ID;
    private int order; // order amount (random value 1-20)

    public Customer() { // constructor
        Random rand = new Random();
        order = rand.nextInt(1,21);
        ID = runningID;
        runningID++;
    }
}

class Shop {
    private int itemsToRefill; // items to refill in odd day
    private int maxDays; // max day for simulation
    private PriorityQueue<Customer> orderQueue;
    private ArrayDeque<Customer> billingQueue;

    public void simulation() { /* implement simulation */ }
}

public class main(String[] args) {
    // Get refill items & max days for simulation from user
}