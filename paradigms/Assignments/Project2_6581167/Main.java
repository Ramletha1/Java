// package operation;

import java.util.*;
import java.io.*;

class OperatorThread extends Thread {
    private Random random = new Random();
    private int totalCustomers;
    private int currentCustomers;
    private int MaxCustomer;
    private ArrayList<Place> places;

    public OperatorThread(String name, int MaxCustomer, ArrayList<Place> places) {
        super(name);
        this.MaxCustomer = MaxCustomer;
        this.places = places;
    }

    @Override
    public void run() {
        int ranNum = random.nextInt(places.size());
        places.get(ranNum).addVisitor(currentCustomers);
        totalCustomers += currentCustomers;
        if (currentCustomers <= 0) {
            System.out.printf("%10s >> no customer\n", this.getName());
        } else {
            System.out.printf("%10s >> take %d to %s   visitor count = %3d \n", this.getName(), currentCustomers,
                    places.get(ranNum).getName(), places.get(ranNum).getVisitor());
        }
        currentCustomers = 0;
    }

    public int checkEmpty() {
        return MaxCustomer - currentCustomers;
    }

    public void addCustomer(int customer) {
        if (checkEmpty() < customer) {
            System.out.println("No more customer can be added");
            return;
        }
        currentCustomers += customer;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }
}

class AgencyThread extends Thread {

    private int maxArrival;
    private List<Tour> tours;
    private int totalCustomers = 0;
    private Random rand = new Random();
    private Object dayLock;

    public AgencyThread(Sting name, int maxArrival, List<Tour> tours, Object dayLock) {
        Super(name);
        this.agencyId = agencyId;
        this.maxArrival = maxArrival;
        this.tours = tours;
        this.dayLock = dayLock;
    }

    @Override
    public void run() {
        for (int day = 1; day <= Main.config.days; day++) {
            synchronized (dayLock) {
                try {
                    dayLock.wait();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }

            int arrivingCustomers = rand.nextInt(maxArrival + 1);
            totalCustomers += arrivingCustomers;
            System.out.println("Day " + day + ": Agency " + this.getName() + " received " + arrivingCustomers + " customers.");


            int remainingCustomers = arrivingCustomers;
            while (remainingCustomers > 0) {
                Tour selectedTour = tours.get(rand.nextInt(tours.size()));
                int allocated = selectedTour.assignCustomers(remainingCustomers);
                remainingCustomers -= allocated;
            }

            System.out.println("Agency " + this.getName() + ": Total customers so far " + totalCustomers + ".");
        }
    }
}


class Place {
    private int visitor;
    private String name;

    public Place(String name) {
        this.name = name;
        visitor = 0;
    }

    public void addVisitor(int visitor) {
        this.visitor += visitor;
    }

    public String getName() {
        return name;
    }

    public int getVisitor() {
        return visitor;
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("Place 1"));
        places.add(new Place("Place 2"));
        places.add(new Place("Place 3"));

        OperatorThread operator1 = new OperatorThread("Operator1", 50, places);
        OperatorThread operator2 = new OperatorThread("Operator2", 50, places);
        OperatorThread operator3 = new OperatorThread("Operator3", 50, places);

        operator1.addCustomer(10);
        operator2.addCustomer(20);
        operator3.addCustomer(30);

        operator1.start();
        operator2.start();
        operator3.start();
    }
}