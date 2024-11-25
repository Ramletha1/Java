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

        System.out.println("Enter N for N*N board (N must be at least 5)");
        while (true) {
            try {
                int N = Integer.parseInt(scanner.nextLine);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
            Board board = new Board(N);
        }
    }
}

class Board {
    private int N;

    public Board(int N) {
        this.N = N;
    }
}