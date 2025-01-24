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
        this.name = nm;
        this.birthyear = by;
        this.age = CURRENT_YEAR - by;
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

class FootballPlayer extends Player {
    public FootballPlayer(String nm, int by) {
        super(nm, by);
    }

    @Override
    public void printPersonalData() {
        System.out.printf("%-25s born %-7d age = %-2d\n", getName(), birthyear, age);
    }

    @Override
    public void printStat() {
.
    }
}

class BasketballPlayer extends Player {
    public BasketballPlayer(String nm, int by) {
        super(nm, by);
    }

    @Override
    public void printPersonalData() {
        System.out.printf("%-25s born %-7d age = %-2d\n", getName(), birthyear, age);
    }

    @Override
    public void printStat() {
        
    }
}

public class Main {
    public static void main (String[] args) {
        File inFile = new File("players.txt");
        Player[] players = new Player[Constant.TOTAL_PLAYER];

        try {
            Scanner readFile = new Scanner(inFile);
            for (int i=0; i<players.length; i++) {
                String line = readFile.nextLine();
                String[] col = line.trim().split("\\s*,\\s*");
                
                if (col[0].equalsIgnoreCase("B")) {
                    //System.out.printf("\nBasketBall");
                    players[i] = new BasketballPlayer(col[1],Integer.parseInt(col[2]));
                } else if (col[0].equalsIgnoreCase("F")) {
                    //System.out.printf("\nFootBall  ");
                    players[i] = new FootballPlayer(col[1],Integer.parseInt(col[2]));
                }
            }
            readFile.close();
        } catch (Exception e) { System.err.println(e); }


        System.out.println("=== All Player data (by reverse order) ===");
        for (int i = players.length-1; i>=0; i--) {
            players[i].printPersonalData();
        }

        System.out.println("=== Football player statistics (by input order)");
    }
}