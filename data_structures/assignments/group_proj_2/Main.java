// package EGCI221-Project1;
import java.util.*;

class BombPlacement {
    int row;
    int column;

    public BombPlacement(int row, int column) {
        this.row = row;
        this.column = column;
    }

}

class Board {
    private int N;
    private char[][] board;
    private ArrayList<BombPlacement> bombList;

    public Board(int N) { // Constructor
        this.N = N;
        this.board = new char[N][N]; 
        this.bombList = new ArrayList<>();
        assignBoard();
    }

    public void assignBoard() {
        board[0][0] = 'K';
        board[N-1][N-1] = 'C';
    }

    public void addBomb(int row, int column) {
        bombList.add(new BombPlacement(row, column));
    }

    public boolean checkBomb(int row, int column) {    // row 2 col 1
        for (BombPlacement bomb : bombList) {       // Manual input check
            //if (bomb.row == row || bomb.column == column || Math.abs(bomb.row-row) == Math.abs(q.column-column)) return false;
        }
        return true;
    }

    /*public void solve() {
        findSolution(0);                                        // Display total solution
        if (totalSolution>0) System.out.printf("There are %d possible solutions.\n\n", totalSolution);
        else System.out.println("No solution.");
    }

    public void findSolution(int row) {
        if (row == N) {
            totalSolution++;
            if (totalSolution==1) { 
                displayBoard();
                System.out.println("Calculating...");
            }
        }

        if (!manualCheck(row)) findSolution(row+1); // Check Manual to skip row
        for (int col=0; col<N; col++) {
            if (queenCheck(row, col)) {     // Check Queen backtracking
                pushQueen(row, col);
                findSolution(row+1);        // Proceed to next row
                popQueen(row, col);
            }
        }
    }*/

    public void displayBoard() {
        System.out.printf("%-15s", "Cell ID");
        for (int i=1; i<N*N+1; i++) {
            System.out.printf("%5d", i);
            if (i % N == 0) {
                System.out.printf("\n%15s", ' ');
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------  N-Queen NxN-Board  -----------");
        while (true) {      // Loop askInput until user want to exit program
            if (!askInput(scanner)) {
                System.out.println("Exiting the program..");
                break;
            }
            System.out.println("============================================");
        }
        scanner.close();
    }

    public static boolean askInput(Scanner scanner) {
        String input;
        int N;

        while (true) {            // Ask for N size
            System.out.println("Enter N for N*N board (N must be at least 5)");
            try {
                N = Integer.parseInt(scanner.nextLine());
                if (N>=5) break;
            } catch (NumberFormatException e) { }
            System.out.println("\nError. Please input 4 or more only.");
        }

        Board board = new Board(N);     // Create board
        board.displayBoard();           // Print empty board


        while (true) {           // ASk for Manual Input
            System.out.println("Place a bomb or not? (y for yes, n for no)");
            input = scanner.nextLine().toLowerCase();
            if (input.equals("y") || input.equals("n")) break;
            System.out.println("\nError. Please enter valid input.");
        }

        if (input.equals("n")) { // No (Let program do the work)
            //board.solve();
        } else {                 // Yes (Input manually)
            int row, col;
            while (true) {       // Ask row
                System.out.print("Enter row: ");
                try {
                    row = Integer.parseInt(scanner.nextLine())-1;
                    if (row<N && row>=0) break;
                } catch (NumberFormatException e) { }
                System.out.printf("\nError. Please enter no more than %d.\n", N);
            }
            while (true) {      // Ask column
                System.out.print("Enter column: ");
                try {
                    col = Integer.parseInt(scanner.nextLine())-1;
                    if (col<N && col>=0) break;
                } catch (NumberFormatException e) { }
                System.out.printf("\nError. Please enter no more than %d.\n", N);
            }
            //board.manualInput(row, col);
        }

        while (true) {          // Ask continue or exit
            System.out.println("1: Continue  2: Exit");
            try {
                input = scanner.nextLine();
                if (input.equals("1")) return true;
                else if (input.equals("2")) return false;
            } catch (NumberFormatException e) { }
            System.out.println("\nError. Please enter valid input");
        }
    }
}