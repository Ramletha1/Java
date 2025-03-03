// package Ex7_6581167;    

import java.util.*;
import java.util.concurrent.*;

class InvalidNumberException extends Exception {
    public InvalidNumberException(String message) {
        super(message);
    }
}


class BankThread extends Thread {
    private Account             sharedAccount;
    private Exchanger<Account>  exchanger;
    private CyclicBarrier       barrier;
    private int                 rounds;
    private boolean             modeD;          // true = deposit, false = withdraw
    private boolean             signal = false; // false = wait, true = another simulation

    public BankThread(String n, Account sa, boolean m) { 
        super(n);
        this.sharedAccount = sa;
        this.modeD = m;
    }

    public void setRound(int r)                     { this.rounds = r; }
    public void setBarrier(CyclicBarrier ba)        { this.barrier = ba; }
    public void setExchanger(Exchanger<Account> ex) { this.exchanger = ex; }
    public void accountExchange() throws InterruptedException { this.sharedAccount = exchanger.exchange(sharedAccount); }

    public boolean isDeposit()                      { return this.modeD; }
    public void setSignal(boolean s)                { this.signal = s; }   
    public synchronized void threadNotify()         { notify(); }
    
    public synchronized void run() {
        while (true) {
            for (int i = 0; i <= rounds; i++) {
                try {
                    Thread.sleep(5);       // Support up to 100 rounds
                    barrier.await();
                    if (modeD) {
                        if (i == 0 && sharedAccount.getBalance() != 0) {
                            accountExchange();
                            System.out.printf("%-4s >> exchange account\n", Thread.currentThread().getName());
                        } else sharedAccount.deposit(i);
                    } else {
                        if (i == 0) continue;
                        sharedAccount.withdraw(i);
                    }
                } catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
            } this.signal = false;

            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) { e.printStackTrace(); }
                finally { if (!this.signal) break; }
            }
        }
    }
}


class Account {
    private String  name;
    private int     balance;

    private Semaphore               semaphore = new Semaphore(2, true);
    private static final Random          rand = new Random();
    
    public Account(String id, int b) { name = id; balance = b; }
    public String getName()          { return name; }
    public int    getBalance()       { return balance; }
    
    public void deposit(int round) {
        String thisThread = String.format("%-4s >>", Thread.currentThread().getName());
        try {
            semaphore.acquire();
            if (round == 0) {
                System.out.printf("%s manage %4s %s (balance = %d)\n", thisThread, "", this.name, this.balance);
            }
            else {
                int money = rand.nextInt(1,100);
                balance += money;
                System.out.printf("%s round %-5d %s %+,d balance = %d\n", thisThread, round, this.name, money, balance);
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
        finally { semaphore.release(); }
    }
    
    public void withdraw(int round) {
        String thisThread = String.format("%-4s >>", Thread.currentThread().getName());
        try {
            semaphore.acquire();
            if (balance == 0) {
                System.out.printf("%s round %-5d %s cannot withdraw.\n", thisThread, round, this.name);
            } else {
                int money = (rand.nextInt(1, (this.balance)/2));
                balance -= money;
                System.out.printf("%s round %-5d %s -%,d balance = %d\n", thisThread, round, this.name, money, balance);
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
        finally { semaphore.release(); }
    }
}


public class Main {
    public static void main(String[] args) {
        Main mainApp = new Main();
        mainApp.runSimulation();
    }

    public void runSimulation() {
        Scanner                 keyboardScan = new Scanner(System.in);
        Account[]               accounts  = {new Account("account A", 0), new Account(".".repeat(35) + "account B", 0)};   
        ArrayList<BankThread>   allThreads = new ArrayList<>();
        Exchanger<Account>      exchanger  = new Exchanger<>();
        CyclicBarrier           barrier    = new CyclicBarrier(allThreads.size() + 1);
        String                  thisThread = String.format("%-4s >>", Thread.currentThread().getName());

        // Create threads
        for (int i = 0; i < accounts.length; i++) {
            // Deposit threads
            BankThread depositThread = new BankThread("D" + (i+1), accounts[i], true);
            depositThread.setBarrier(barrier);
            depositThread.setExchanger(exchanger);
            // Withdraw threads
            BankThread withdrawThread = new BankThread("W" + (i+1), accounts[i], false);
            withdrawThread.setBarrier(barrier);
            withdrawThread.setExchanger(null);
            // Stores all threads inside ArrayList
            allThreads.addAll(Arrays.asList(depositThread, withdrawThread));
        }

        int round = 0;
        boolean again = false;
        // Loop until user quits
        while (round != -1) {
            // Ask user input for #
            threadSleep(200);
            while (true) {
                System.out.printf("\n\n%s Enter #rounds for a new simulation (-1 to quit)\n", thisThread);
                try {
                    round = Integer.parseInt(keyboardScan.nextLine());
                    if (round == -1 || round > 0) break;
                    else throw new InvalidNumberException("");
                } catch (NumberFormatException e) { System.out.println("Invalid format!"); }
                  catch (InvalidNumberException e) { System.err.println("Invalid number!"); }
            }

            // Quit simulation & Final Balance Report
            if (round == -1) break;
            // 1st simulation > Skip exchange deposit accounts
            if (!again) {
                again = true;
                for (BankThread thread : allThreads) {
                    thread.setRound(round);
                    thread.start();
                }
            }
            // 2nd simulation onwards > Exchange deposit accounts
            else if (again) {
                for (BankThread thread : allThreads) {
                    thread.setRound(round);
                    thread.setSignal(true);
                }
                for (BankThread thread : allThreads) allThreads.forEach(BankThread::threadNotify);
            }

            threadSleep(7 * round + 20);
        }
        keyboardScan.close();

        // Wait for all threads to finish
        for (BankThread thread : allThreads) {
            thread.threadNotify();
            try {
                thread.join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        // Report final balances of all accounts
        for (Account account : accounts)
            System.out.printf("%s final balance %4s %s = %d\n", thisThread, "", account.getName(), account.getBalance());
    }

    public void threadSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
        
        // Create BankThread D1 and D2 for deposit task
        //   - In the first simulation, D1 manages account A, D2 manages accout B
        //   - Pass Exchanger & CyclicBarrier objects
        
        // Create BankThread W1 and W2 for withdraw task
        //   - In all simulations, W1 manages only account A, W2 manages only account B
        //   - Pass CyclicBarrier object (Exchanger can be set to null)

        
        // Start all BankThreads

        
        // Loop for banking simulation. In each simulation:
        //  (1) Main thread gets #rounds from user and pass it to BankThread.
        //  (2) Main thread waits until all BankThread completes #rounds of deposit/withdraw
        
        
        // If user dosn't want to run a new simulation:
        //   - Wait until all BankThreads return
        //   - Main thread reports final balances of all accounts