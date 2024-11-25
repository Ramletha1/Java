// package EGCI221-Project1;
import java.util.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Main {
    private static final int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1},     // Up / Down
                                                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};    // Left / Right

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
        int N, numInput;
        String strInput;

        System.out.println("Enter N for N*N board (N must be at least 5)");
        while (true) {
            try {
                N = Integer.parseInt(scanner.nextLine());
                if (N < 5) {
                    System.out.println("N must be at least 5");
                    continue;
                }
                break;
            } catch (NumberFormatException e) { System.out.println("Invalid input."); }
        }

        Board board = new Board(N);
        board.printBoardID();

        System.out.println("Enter Knight ID");
        while (true) {
            try {
                numInput = Integer.parseInt(scanner.nextLine());
                if (numInput < 0 || numInput > N*N) {
                    System.out.println("Input must be according to Cell IDs.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) { System.out.println("Invalid input."); }
        }

        return true;
    }
}

class Board {
    private int N;

    public Board(int N) {
        this.N = N;
    }

    public void printBoardID() {
        for (int i=0; i<N; i++) {
            if (i == 0) System.out.printf("%8s", "Cell IDs");
            else System.out.printf("%8s", " ");

            for (int j=0; j<N; j++) {
                System.out.printf("%5d", j+(i*N));
            }
            System.out.println();
        }
    }
}