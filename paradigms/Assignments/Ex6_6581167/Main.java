// This code is written in VSCode.

// package Ex6_6581167;

import java.util.*;
import java.io.*;

class CONSTANT {
    public static final int THREAD_LIMIT = 10;      // User input limit for total threads
    public static final int DRAW = 4;               // Total cards draw per round
    public static final int CARD = 51;
}

class OneCard {
    private int score;
    private int suit;
    private int rank;

    public OneCard() {
        Random rand = new Random();
        this.score = rand.nextInt(CONSTANT.CARD);
        this.suit = this.score / 13;
        this.rank = this.score % 13;
    }

    public int getScore() {
        return this.score;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getRank() {
        return this.rank;
    }
}

class CardThread extends Thread {
    private int threadNumber;
    private int round;
    private PrintWriter write;
    private HashMap<OneCard, Integer> randomCards;
//  private HashMap< KEY   , VALUE  > randomCards;

    public CardThread(int thread) {
        this.threadNumber = thread;
        this.round = 1;
        this.randomCards = new HashMap<>();
    }

    public void run() {
        try (PrintWriter write = new PrintWriter(new File("T" + this.threadNumber))) {
            while (true) {
                for (int i=0; i<CONSTANT.DRAW; ) {
                    OneCard temp = new OneCard();
                    if (randomCards.containsValue(temp.getScore())) continue;
                    else { 
                        randomCards.put(temp, temp.getScore());
                        i++;
                    }
                }

                ArrayList<String> cardsInHand = new ArrayList<>();
                String previous = "";
                boolean suitCheck = false;
                boolean match = true;
                for (OneCard card : randomCards.keySet()) {
                    String tempRank;
                    switch (card.getRank()) {
                        case 0 : tempRank = "A"; break;
                        case 10: tempRank = "J"; break;
                        case 11: tempRank = "Q"; break;
                        case 12: tempRank = "K"; break;
                        default: tempRank = String.valueOf(card.getRank());
                    }
                    String tempSuit;
                    switch (card.getSuit()) {
                        case 0 : tempSuit = "Clubs";    break;
                        case 1 : tempSuit = "Diamonds"; break;
                        case 2 : tempSuit = "Hearts";   break;
                        case 3 : tempSuit = "Spades";   break;
                        default: tempSuit = "ERROR";    break;
                    }
                    cardsInHand.add(String.format("%2s of %-8s ", tempRank, tempSuit    ));
                
                    if (!previous.equals(tempSuit) && suitCheck) match = false;
                    suitCheck = true;
                    previous = tempSuit ;
                
                    }
                write.printf("Round %-5d %s\n", this.round, cardsInHand);
                write.flush();

                if (match == true) {
                        System.out.println("Thread T" + this.threadNumber + " finishes in " + this.round + " rounds");
                        break;
                }

                randomCards.clear();
                this.round++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        if (CONSTANT.DRAW>CONSTANT.CARD || CONSTANT.DRAW<2) {
            System.out.println("\nTotal Draw must not be more than Total Card and not less than 2.");
            System.out.printf("Total Draw: %d , Total Card: %d\n", CONSTANT.DRAW, CONSTANT.CARD);
            System.out.println("Terminating Program...\n");
            System.exit(1);
        }

        int totalThreads = 0;
        Scanner ask = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Number of threads =");
                totalThreads = Integer.parseInt(ask.nextLine());
                if (totalThreads>0 && totalThreads<CONSTANT.THREAD_LIMIT) break;
                else System.out.println("Input must be more than 0 and less than " + CONSTANT.THREAD_LIMIT);
            } catch (Exception e) {
                System.err.println(e);
                System.out.println("Invalid Input.");
                continue;
            }
        }
        ask.close();

        CardThread[] threads = new CardThread[totalThreads];
        for (int i=0; i<totalThreads; i++) {
            threads[i] = new CardThread(i);
            threads[i].start();
        }
    }
}
