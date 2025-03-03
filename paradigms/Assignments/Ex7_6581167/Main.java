// package Ex7_6581167;    

import java.util.*;
import java.util.concurrent.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
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

    public BankThread(String n, Account sa, boolean m) { 
        super(n);
        this.sharedAccount = sa;
        this.modeD = m;
    }

    public void setRound(int r)                     { this.rounds = r; }
    public void setBarrier(CyclicBarrier ba)        { this.barrier = ba; }
    public void setExchanger(Exchanger<Account> ex) { this.exchanger = ex; }
    
    public void run() {
        try {
            barrier.await();
            if (modeD) {
                sharedAccount.deposit(rounds);
            } else {
                sharedAccount.withdraw(rounds);
            }
        } catch (InterruptedException | BrokenBarrierException e) { e.printStackTrace(); }
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
        String thisThread = String.format("%-4s >> ", Thread.currentThread().getName());
        System.out.println(thisThread + "manage     " + this.name + " (Balance = " + this.balance + ")");

        int money = rand.nextInt(1,100);
        balance += money;
        // Random money (1 to 100) to deposit; update the balance
        // Report thread activity (see example output)
    }
    
    public void withdraw(int round) {
        if (balance == 0) {
            String thisThread = String.format("%-4s >> ", Thread.currentThread().getName());
            System.out.println(thisThread + "withdrawal fails (Balance = 0)");
            return;
        }
        int money = rand.nextInt(1, (this.balance)/2);
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
        CyclicBarrier           barrier    = new CyclicBarrier(allThreads.size()+1);
        String                  thisThread = String.format("%-4s >> ", Thread.currentThread().getName());

        for (Account account : accounts)
            account.setSemaphores(accounts.length);

        for (int i = 0; i < accounts.length; i++) {
            // Deposit threads
            BankThread depositThread = new BankThread("D" + i, accounts[i], true);
            depositThread.setBarrier(barrier);
            depositThread.setExchanger(exchanger);
            // Withdraw threads
            BankThread withdrawThread = new BankThread("W" + i, accounts[i], false);
            withdrawThread.setBarrier(barrier);
            withdrawThread.setExchanger(null);
            allThreads.addAll(Arrays.asList(depositThread, withdrawThread));
        }

        int round = 0;
        boolean exchange = false;
        while (round != -1) {
            while (true) {
                System.out.println(thisThread + "Enter #rounds for a new simulation (-1 to quit)");
                try {
                    round = Integer.parseInt(keyboardScan.nextLine());
                    if (round == -1 || round > 0) break;
                    else throw new InvalidNumberException("");
                } catch (NumberFormatException e) { System.out.println("Invalid format!"); }
                  catch (InvalidNumberException e) { System.err.println("Invalid number!"); }
            }
            if (round == -1) break;         // Quit simulation & Final Balance Report

            if (exchange) {            // 2nd simulation onwards > Exchange deposit accounts
                accounts[0] = exchanger.exchange(accounts[1]);
                accounts[1] = exchanger.exchange(accounts[0]);
            } else exchange = true;    // 1st simulation

            for (BankThread thread : allThreads) {
                thread.setRound(round);
                thread.start();
            }
        }
        keyboardScan.close();

        for (Account account : accounts)
            System.out.println(thisThread + "final balance   " + account.getName() + " = " + account.getBalance());

        
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
    }
}
