// package Ex3_6581167;

import java.util.*;
import java.io.*;

class Player {
    public static final int CURRENT_YEAR = 2025;
    private String name;
    protected int birthyear, age;

    public Player(String nm, int by) {
        name = nm; birthyear = by;
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
        int[] totalPlayer = new int[12];

        try {
            Scanner readFile = new Scanner(inFile);
            for (int i=0; i<12; i++) {
                String line = readFile.nextLine();
                String[] col = line.trim().split(",");
                
                if (col[0] == "B") {
                    System.out.println("Hello");
                }
                for (String j : col) {
                    System.out.println(j.trim());
                }
            }
            readFile.close();
        } catch (Exception e) { System.err.println(e); }
    }
}