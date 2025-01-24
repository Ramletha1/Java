// package Ex3_6581167;

import java.util.*;
import java.io.*;

class Constant {
    public static final int TOTAL_PLAYER = 12;
}

class Player {
    public static final int CURRENT_YEAR = 2025;
    private String name;
    protected int birthyear, age;

    public Player(String nm, int by) {
        name = nm;
        birthyear = by;
    }

    public String getName() {
        return name;
    }
    
    public void printPersonalData() {
        /*override this in child class */
    }

    public void printStat() {
        /* override this in child class */
    }
}

/* class FB_Player {
    public FB_Player(String nm, int by) {
        Player.
    }
}

class BB_Player {

} */

public class Main {
    public static void main (String[] args) {
        File inFile = new File("players.txt");
        Player[] allPlayers = new Player[Constant.TOTAL_PLAYER];

        try {
            Scanner readFile = new Scanner(inFile);
            for (int i=0; i<12; i++) {
                String line = readFile.nextLine();
                String[] col = line.trim().split(",");
                
                if (col[0].contains("B")) {
                    System.out.printf("BasketBall");
                } else {
                    System.out.printf("FootBall  ");
                }

                for (int j = 1; j<col.length; j++) {
                    System.out.printf(" %-25s", col[j].trim());
                } System.out.println();
            }
            readFile.close();
        } catch (Exception e) { System.err.println(e); }

        for (Player player : allPlayers) {
            System.out.println(player.age);
        }
    }
}