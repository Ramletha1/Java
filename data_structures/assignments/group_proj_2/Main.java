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
        String[] strInput;

        // Ask 1
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

        // Ask 2
        System.out.println("Enter Knight ID");
        while (true) {
            try {
                numInput = Integer.parseInt(scanner.nextLine());
                if (numInput < 0 || numInput > (N*N)-1) {
                    System.out.println("Input must be according to Cell IDs.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) { System.out.println("Invalid input."); }
        }
        int knightPos = numInput;

        // Ask 3
        System.out.println("Enter Castle ID");
        while (true) {
            try {
                numInput = Integer.parseInt(scanner.nextLine());
                if (numInput < 0 || numInput > (N*N)-1) {
                    System.out.println("Input must be according to Cell IDs.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) { System.out.println("Invalid input."); }
        }
        int castlePos = numInput;

        // Ask 4
        System.out.println("Enter bomb IDs separated by comma (Invalid IDs will be ignored)");
        HashSet<Integer> bombCell = new HashSet<>();
        strInput = scanner.nextLine().split(",");
        for (String bomb : strInput) {
            try {
                bombCell.add(Integer.parseInt(bomb.trim()));
            } catch (NumberFormatException e) { /* Ignore Invalid Input */ }
        }


        Map<String, Cell> board = new HashMap<>();
        for (int row=0; row<N; row++) {     // Initialize
            for (int col=0; col<N; col++) {
                String position = row + "," + col;
                board.put(position, new Cell(row, col, ' '));   // Empty Cell
            }
        }
        
        for (int bomb : bombCell) {
            int bombRow = bomb/N;
            int bombCol = bomb%N;
            // System.out.printf("\n\nBombCell:%d row:%d col:%d", bomb, bombRow, bombCol); // Check
        }
        return true;
    }

    public static boolean setCell(Map<String, Cell> board, int row, int col, char type) {
        String position = row + "," + col;
        if (board.get(position).type == ' ') {
            board.get(position).type = type;
            return true;
        } else return false;
    }
}


class Cell { // Cells
    int row, col;
    char type;

    public Cell(int row, int col, char type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type + "";
    }
}



/*
for (int i=0; i<N; i++) {
    if (i == 0) System.out.printf("%8s", "Cell IDs");
    else System.out.printf("%8s", " ");

    for (int j=0; j<N; j++) {
        System.out.printf("%5d", j+(i*N));
    }
    System.out.println();
}
*/