// package Project_2;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

interface Tasks {
    void run();
}

class SharedBuffer {
    private int day;
    private int maxDay;
    private int order;
    private int agencyThread;
    private int agencyCompleted = 0;

    public SharedBuffer(int maxDay, int agencyThread) {
        this.maxDay = maxDay;
        day = 0;
        order = 1;
        this.agencyThread = agencyThread;
    }

    public int getDay() { return day; }
    public int getMaxDay() { return maxDay; }
    public int getOrder() { return order; }

    public synchronized void setOrder(int order) {
        this.order = order;
        notifyAll();
    }

    public boolean checkCompletion() {
        agencyCompleted++;
        if (agencyCompleted == agencyThread) {
            agencyCompleted = 0;
            return true;
        }
        return false;
    }
}

class AgencyThread extends Thread implements Tasks {
    private final Object lock;
    private int maxArrival;
    private ArrayList<Tour> tours;
    private int remainingCustomer = 0;
    private Random rand = new Random();
    private CyclicBarrier barrier;
    private SharedBuffer sharedBuffer;
    private Semaphore semaphore = new Semaphore(1, true);

    public AgencyThread(String name, int maxArrival, ArrayList<Tour> tours, CyclicBarrier barrier, SharedBuffer sharedBuffer, Object lock) {
        super(name);
        this.maxArrival = maxArrival;
        this.tours = tours;
        this.barrier = barrier;
        this.sharedBuffer = sharedBuffer;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < sharedBuffer.getMaxDay(); i++) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                semaphore.acquire();
                barrier.await();
                int arrivingCustomers = rand.nextInt(maxArrival + 1);
                remainingCustomer += arrivingCustomers;
                System.out.printf("%17s  >> new arrival = %2d      remaining customers = %d \n", this.getName(), arrivingCustomers, remainingCustomer);
                Tour selectedTour = tours.get(rand.nextInt(tours.size()));
                int sendCustomer = 0;

                barrier.await();
                if (selectedTour.checkEmpty() < remainingCustomer) {
                    sendCustomer = selectedTour.checkEmpty();
                    remainingCustomer -= sendCustomer;
                } else if (selectedTour.checkEmpty() >= remainingCustomer) {
                    sendCustomer = remainingCustomer;
                    remainingCustomer = 0;
                }
                selectedTour.addCustomer(sendCustomer);
                System.out.printf("%17s  >> send %2d customers to %-7s   seat taken = %2d \n", this.getName(), sendCustomer, selectedTour.getName(), selectedTour.getCurrentCustomers());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}

class Tour {
    private String name;
    private int maxCustomer;
    private int currentCustomer = 0;
    private int totalCustomers = 0;

    public Tour(String name, int maxCustomer) {
        this.name = name;
        this.maxCustomer = maxCustomer;
    }

    public String getName() { return name; }
    public int getTotalCustomers() { return totalCustomers; }
    public int getCurrentCustomers() { return currentCustomer; }

    public int checkEmpty() { return maxCustomer - currentCustomer; }

    public void resetCurrentCustomer() { currentCustomer = 0; }

    public void addCustomer(int customer) {
        totalCustomers += customer;
        currentCustomer += customer;
    }
}

class OperatorThread extends Thread implements Tasks {
    private final Object lock;

    private Random random = new Random();
    private Tour tour;
    private ArrayList<Place> places;
    private CyclicBarrier barrier;
    private SharedBuffer sharedBuffer;
    private Semaphore semaphore = new Semaphore(1, true);

    public OperatorThread(String name, Tour tour, ArrayList<Place> places, CyclicBarrier barrier, SharedBuffer sharedBuffer, Object lock) {
        super(name);
        this.tour = tour;
        this.places = places;
        this.barrier = barrier;
        this.sharedBuffer = sharedBuffer;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < sharedBuffer.getMaxDay(); i++) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                barrier.await();
                semaphore.acquire();
                int ranNum = random.nextInt(places.size());
                places.get(ranNum).addVisitor(tour.getCurrentCustomers());
                if (tour.getCurrentCustomers() <= 0) {
                    System.out.printf("%17s  >> no customer\n", this.getName());
                } else {
                    System.out.printf("%17s  >> take %d to %s   visitor count = %3d \n", this.getName(),
                            tour.getCurrentCustomers(),
                            places.get(ranNum).getName(), places.get(ranNum).getVisitor());
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
              finally {
                semaphore.release();
            }
            tour.resetCurrentCustomer();
        }
    }

    public String getTourName() { return tour.getName(); }
}

class Place {
    private int visitor;
    private String name;

    public Place(String name) {
        this.name = name;
        visitor = 0;
    }
    public String getName()             { return name; }
    public int getVisitor()             { return visitor; }
    public void addVisitor(int visitor) { this.visitor += visitor; }
}

public class Main {
    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.runSimulation();
    }

    public void runSimulation() {
        File configFile = readFile();
        int days = 0, agencyCount = 0, agencyArrival = 0, tourCount = 0, tourCapacity = 0, placeCount = 0;

        ArrayList<AgencyThread>     agencyList = new ArrayList<AgencyThread>();
        ArrayList<OperatorThread> operatorList = new ArrayList<OperatorThread>();
        ArrayList<Tour>                  tours = new ArrayList<Tour>();
        ArrayList<Place>                places = new ArrayList<Place>();
        ArrayList<String>       agencyNameList = new ArrayList<String>();
        ArrayList<String>     operatorNameList = new ArrayList<String>();
        ArrayList<String>        placeNameList = new ArrayList<String>();

        Object agencyLock = new Object();
        Object operatorLock = new Object();

        try (Scanner readFile = new Scanner(configFile)) {
            String line[] = readFile.nextLine().trim().split("\\s*,\\s*");
            days = Integer.parseInt(line[1]);

            line = readFile.nextLine().trim().split("\\s*,\\s*");
            agencyCount = Integer.parseInt(line[1]);
            agencyArrival = Integer.parseInt(line[2]);

            line = readFile.nextLine().trim().split("\\s*,\\s*");
            tourCount = Integer.parseInt(line[1]);
            tourCapacity = Integer.parseInt(line[2]);

            line = readFile.nextLine().trim().split("\\s*,\\s*");
            placeCount = Integer.parseInt(line[1]);
        } catch (FileNotFoundException e) { System.out.println(e); }

        CyclicBarrier agencyBarrier = new CyclicBarrier(agencyCount);
        CyclicBarrier operatorBarrier = new CyclicBarrier(tourCount);
        SharedBuffer sharedBuffer = new SharedBuffer(days, agencyCount);

        for (int i = 0; i < agencyCount; i++) {
            agencyNameList.add("AgencyThread_" + i);
            agencyList.add(new AgencyThread(agencyNameList.get(i), agencyArrival, tours, agencyBarrier, sharedBuffer, agencyLock));
        }

        for (int i = 0; i < tourCount; i++) {
            operatorNameList.add("OperatorThread_" + i);
            tours.add(new Tour("Tour_" + i, tourCapacity));
            operatorList.add(new OperatorThread(operatorNameList.get(i), tours.get(i), places, operatorBarrier, sharedBuffer, operatorLock));
        }

        for (int i = 0; i < placeCount; i++) {
            placeNameList.add("Place_" + i);
            places.add(new Place(placeNameList.get(i)));
        }

        System.out.printf("%17s  >>  %s\n", Thread.currentThread().getName(), "=".repeat(21) + " Parameters " + "=".repeat(21));
        System.out.printf("%17s  >>  %-19s = %d\n", Thread.currentThread().getName(), "Days of simulation", days);
        System.out.printf("%17s  >>  %-19s = %d\n", Thread.currentThread().getName(), "Max Arrival", agencyArrival);
        System.out.printf("%17s  >>  %-19s = %s\n", Thread.currentThread().getName(), "AgencyThreads", agencyNameList);
        System.out.printf("%17s  >>  %-19s = %d\n", Thread.currentThread().getName(), "Tour Capacity", tourCapacity);
        System.out.printf("%17s  >>  %-19s = %s\n", Thread.currentThread().getName(), "OperatorThreads", operatorNameList);
        System.out.printf("%17s  >>  %-19s = %s\n", Thread.currentThread().getName(), "Places", placeNameList);
        System.out.printf("%17s  >>  \n", Thread.currentThread().getName());
        System.out.printf("%17s  >>  %s\n", Thread.currentThread().getName(), "=".repeat(60));

        for (AgencyThread agency : agencyList) { agency.start(); }
        for (OperatorThread operator : operatorList) { operator.start(); }

        HashSet<Tasks> allThreads = new HashSet<>();
        allThreads.addAll(agencyList);
        allThreads.addAll(operatorList);

        for (int i = 0; i < days; i++) {
            System.out.printf("%17s  >>  %s\n", Thread.currentThread().getName(), "Day " + (i + 1));
            System.out.printf("%17s  >>  \n", Thread.currentThread().getName());

            while (sharedBuffer.getOrder() != 0) {
                threadSleep(20);
                boolean allWaiting = true;
                for (Tasks thread : allThreads) {
                    if (sharedBuffer.getOrder() == 1 && thread instanceof AgencyThread) {
                        if (((Thread) thread).getState() != Thread.State.WAITING) {
                            allWaiting = false;
                            break;
                        }
                    } else if (sharedBuffer.getOrder() == 2 && thread instanceof OperatorThread) {
                        if (((Thread) thread).getState() != Thread.State.WAITING) {
                            allWaiting = false;
                            break;
                        }
                    }
                }
                if (sharedBuffer.getOrder() == 1 && allWaiting) {
                    synchronized (agencyLock) {
                        agencyLock.notifyAll();
                        sharedBuffer.setOrder(2);
                    }
                    threadSleep(10);
                    continue;
                } else if (sharedBuffer.getOrder() == 2 && allWaiting) {
                    synchronized (operatorLock) {
                        operatorLock.notifyAll();
                        sharedBuffer.setOrder(0);
                    }
                    threadSleep(10);
                    continue;
                }
            }
            sharedBuffer.setOrder(1);
            System.out.printf("%17s  >>  \n%17s  >> %s\n", Thread.currentThread().getName(), Thread.currentThread().getName(), "=".repeat(60));
        }
    }

    public static File readFile() {
        // String path = "src/main/java/Project_2/";
        String path = "";
        String fileName = "config.txt";
        File file;
        while (true) {
            file = new File(path + fileName);
            try (Scanner readFile = new Scanner(file);){
                break;
            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.println("New file name = ");
                Scanner input = new Scanner(System.in);
                fileName = input.nextLine();
            }
        }
        return file;
    }

    public void threadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}