// package Ex7_6581167;    

import java.util.*;
import java.util.concurrent.*;

import java.util.concurrent.BrokenBarrierException;

class InvalidNumberException extends Exception {
    public InvalidNumberException(String message) {
        super(message);
    }


class BankThread extends Thread {
    private Account             sharedAccount;
    private Exchanger<Account>  exchanger;
    private CyclicBarrier       barrier;
    private int                 rounds;
    private boolean             modeD;          // true = deposit, false = withdraw

    public BankThread(String n, Account sa, boolean m) { 
        super(n);
        this.sharedAccount = sa;
        this.modeD = m;
    }

    public void setRound(int r)                     { this.rounds = r; }
    public void setBarrier(CyclicBarrier ba)        { this.barrier = ba; }
    public void setExchanger(Exchanger<Account> ex) { this.exchanger = ex; }
    public boolean isDeposit()                      { return this.modeD; }
    public void accountExchange() throws InterruptedException { this.sharedAccount = exchanger.exchange(sharedAccount); }
    
    public void run() {
        for (int i = 0; i <= rounds; i++) {
            try {
                barrier.await();
                if (modeD) {
                    sharedAccount.deposit(i);
                } else {
                    if (i == 0) continue;
                    sharedAccount.withdraw(i);
                }
            } catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
        }
        // Loop for banking simulation. In each simulation:        
        //  (1) Wait until main thread gets #rounds from user and pass it to BankThread.
        
        //  (2) If this is the first simulation, skip this step.
        //      Otherwise, depositing threads exchange accounts with each other.
        //      Withdrawing threads that don't exchange accounts must wait until this is done.
        
        //  (3) Each thread identifies account it is managing in this simulation
        
        //  (4) Depositing threads deposit to sharedAccount for #rounds.
        //      Withdrawing threads withdraws from sharedAccount for #rounds
        
        // Break from loop & return if user doesn't want to run a new simulation
    }
}


class Account {
    private String  name;
    private int     balance;

    private Semaphore semaphore;
    private static final Random rand = new Random();
    
    public Account(String id, int b) { name = id; balance = b; }
    public String getName()          { return name; }
    public int    getBalance()       { return balance; }
    
    public void deposit(int round) {
        String thisThread = String.format("%-4s >>", Thread.currentThread().getName());
        if (round == 0) {
            if (balance != 0) {
                try {
                    ((BankThread) Thread.currentThread()).accountExchange();
                    System.out.printf("%s exchange account", thisThread);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
            System.out.printf("%s manage %4s %s (balance = %d)\n", thisThread, "", this.name, this.balance);
        }
        else {
            int money = rand.nextInt(1,100);
            balance += money;
            System.out.printf("%s round %-5d %s %+,d balance = %d\n", thisThread, round, this.name, money, balance);
        }
        // Random money (1 to 100) to deposit; update the balance
        // Report thread activity (see example output)
    }
    
    public void withdraw(int round) {
        String thisThread = String.format("%-4s >>", Thread.currentThread().getName());
        if (balance == 0) {
            System.out.printf("%s round %-5d %s cannot withdraw.\n", thisThread, round, this.name);
        } else {
            int money = (rand.nextInt(1, (this.balance)/2));
            balance -= money;
            System.out.printf("%s round %-5d %s -%,d balance = %d\n", thisThread, round, this.name, money, balance);
        }
        // Random money (1 to balance/2) to withdraw; update the balance
        // Report thread activity (see example output)
        //   - But if balance is already 0, report that withdrawal fails
    }

    public void setSemaphores(int accountCount) {
        this.semaphore = new Semaphore(accountCount, true);
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
        CyclicBarrier           barrier    = new CyclicBarrier(accounts.size() * 2 + 1);
        String                  thisThread = String.format("%-4s >>", Thread.currentThread().getName());

        for (Account account : accounts)    // Set semaphore permits to # of accounts
            account.setSemaphores(accounts.length);

        int round = 0;
        boolean again = false;
        // Loop until user quits
        while (round != -1) {
            // Ask user input for #rounds
            while (true) {
                System.out.printf("\n\n%s Enter #rounds for a new simulation (-1 to quit)\n", thisThread);
                try {
                    round = Integer.parseInt(keyboardScan.nextLine());
                    if (round == -1 || round > 0) break;
                    else throw new InvalidNumberException("");
                } catch (NumberFormatException e) { System.out.println("Invalid format!"); }
                  catch (InvalidNumberException e) { System.err.println("Invalid number!"); }
            }

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
            
            // Quit simulation & Final Balance Report
            if (round == -1) break;
            // 2nd simulation onwards > Exchange deposit accounts
            if (again) {
                for (BankThread thread : allThreads) {
                    // Check for deposit threads and swap account
                    if (thread.isDeposit()) {
                        try {
                            thread.accountExchange();
                        } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
            // 1st simulation > Skip exchange
            } else again = true;

            // Start threads
            for (BankThread thread : allThreads) {
                thread.setRound(round);
                thread.start();
            }

            // Wait for all threads to finish
            for (BankThread thread : allThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
            allThreads.clear();
        }
        keyboardScan.close();

        // Report final balances of all accounts
        for (Account account : accounts) {
            System.out.printf("%s final balance %4s %s = %d\n", thisThread, "", account.getName(), account.getBalance());
        }
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