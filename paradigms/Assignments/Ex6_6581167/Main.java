// This code is written in VSCode.

// package Ex6_6581167;

import java.util.*;
import java.io.*;

import java.security.spec.ECFieldF2m;
class OneCard {
    private int score;
    private int suit;
    private int rank;

    public OneCard(int score) { this.score = score; }
}

class CardThread extends Thread {
    private PrintWriter write;
    private ArrayList<OneCard> randomCards;

    public void run() {
        // Create PrintWriter object to write result to a separate file
        // Execute steps 1-3 in loop:
        // 1. Draw 4 cards from the same deck. The cards must not duplicate.
        // 2. Print round number and these 4 cards to output file.
        // 3. If all cards are from the same suit or have equal rank, stop the loop.
        // Otherwise, clear randomCards & continue to the next round.
        // After the loop, print #rounds to the screeen
        for (int i = 0; i<3; i++) {
            File outFile = new File("T" + i);

            try {
                write = new PrintWriter(outFile);
            } catch (Exception e) {}
            System.out.println(outFile.getName());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner ask = new Scanner(System.in);
        

        CardThread a = new CardThread();
        a.run();
        ask.close();
    }
}
