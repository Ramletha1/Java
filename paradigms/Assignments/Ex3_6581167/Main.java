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
    protected int[] arrayOfGames;
    protected int[] arrayOfGoals;
    protected float avgGoals;

    protected int totalGames = 0;
    protected int totalGoals = 0;

    public FootballPlayer(String nm, int by, int[] tga, int[] tgo) {
        super(nm, by);
        this.arrayOfGames = tga;
        this.arrayOfGoals = tgo;
        for (int game : arrayOfGames) this.totalGames += game;
        for (int goal : arrayOfGoals) this.totalGoals += goal;
        this.avgGoals = (float)totalGoals/totalGames;
    }

    @Override
    public void printPersonalData() {
        System.out.printf("%-25s born %-7d age = %-2d\n", getName(), birthyear, age);
    }

    @Override
    public void printStat() {
        System.out.printf("%-25s total games = %-6d total goals = %-3d (%.2f per game)      last season goals = %2d\n", getName(), totalGames, totalGoals, avgGoals, arrayOfGoals[arrayOfGoals.length-1]);
    }
}

class BasketballPlayer extends Player {
    protected int totalGames;
    protected int totalMins;
    protected int totalPts;
    protected float avgMins;
    protected float avgPts;

    public BasketballPlayer(String nm, int by, int tga, int tmi, int tpt) {
        super(nm, by);
        this.totalGames = tga;
        this.totalMins = tmi;
        this.totalPts = tpt;
        this.avgMins = (float)totalMins/totalGames;
        this.avgPts = (float)totalPts/totalGames;
    }

    @Override
    public void printPersonalData() {
        System.out.printf("%-25s born %-7d age = %-2d\n", getName(), birthyear, age);
    }

    @Override
    public void printStat() {
        System.out.printf("%-25s total games = %-6d total mins = %-4d (%.2f per game)     total points = %-5d (%.2f per game)\n", getName(), totalGames, totalMins, avgMins, totalPts, avgPts);
    }
}

public class Main {
    public static void main (String[] args) {
        File inFile = new File("players.txt");
        Player[] allPlayers = new Player[Constant.TOTAL_PLAYER];

        try {
            Scanner readFile = new Scanner(inFile);
            for (int i = 0; i<allPlayers.length; i++) {
                String line = readFile.nextLine();
                String[] col = line.trim().split("\\s*,\\s*");

                String name = col[1];
                int birthyear = Integer.parseInt(col[2]);
                
                if (col[0].equalsIgnoreCase("B")) {
                    // System.out.printf("\nBasketBall");
                    int totalGames = Integer.parseInt(col[3]);
                    int totalMins = Integer.parseInt(col[4]);
                    int totalPts = Integer.parseInt(col[5]);

                    allPlayers[i] = new BasketballPlayer(name, birthyear, totalGames, totalMins, totalPts);

                } else if (col[0].equalsIgnoreCase("F")) {
                    // System.out.printf("\nFootBall  ");
                    int[] totalGames = new int[col.length - 3];
                    int[] totalGoals = new int[col.length - 3];
                    for (int j = 3; j<col.length; j++) {
                        String[] subCol = col[j].split(":");
                        totalGames[j-3] = Integer.parseInt(subCol[0]);
                        totalGoals[j-3] = Integer.parseInt(subCol[1]);
                    }
                    allPlayers[i] = new FootballPlayer(name, birthyear, totalGames, totalGoals);
                }
            }
            readFile.close();
        } catch (Exception e) { System.err.println(e); }


        System.out.println("=== All Player data (by reverse order) ===");
        for (int i = allPlayers.length-1; i>=0; i--) {
            allPlayers[i].printPersonalData();
        } System.out.println();


        System.out.println("=== Football player statistics (by input order) ===");
        for (int i = 0; i<allPlayers.length; i++) {
            if (allPlayers[i] instanceof FootballPlayer) {
                FootballPlayer player = (FootballPlayer) allPlayers[i];
                player.printStat();
            }
        } System.out.println();


        System.out.println("=== Basketball player statistics (by input order) ===");
        for (int i = 0; i<allPlayers.length; i++) {
            if (allPlayers[i] instanceof BasketballPlayer) {
                BasketballPlayer player = (BasketballPlayer) allPlayers[i];
                player.printStat();
            }
        } System.out.println();
    }
}