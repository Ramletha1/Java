// package EGCI221-Project1;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            if (!askInput(scanner)) {
                System.out.println("Exiting the program...");
                break;
            }
        }
        scanner.close();
    }

    public static boolean askInput(Scanner scanner) {
        String[][] board
    }
}

class BoardPlacement {
    private int row, col, moves;
    private String[][] board;

    private static final int[][] knightMoves = {{ 2,-1},{ 2, 1},    // Up
                                                {-2,-1},{-2, 1},    // Down
                                                { 1,-2},{-1,-2},    // Left
                                                { 1, 2},{-1, 2}};   // Right

    public Board(int row, int col, int moves) {
        this.row = row;
        this.col = col;
        this.moves = moves;
        this.board = new board[row][col];
    }
}