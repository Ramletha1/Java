// package EGCI221-Project2;

// By Wongsatorn Suwannarit

import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.BFSShortestPath;;

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

public class Main {
// ==============================================================================================================================
    public static final int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1},     // Up / Down
                                               {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};    // Left / Right
    static int N, knightPos, castlePos;
// ==============================================================================================================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Cell> board = new HashMap<>();
        
        while (true) {
            askInput(board, scanner); 
            printBoard(board);
            BFS(board);

            System.out.println("New game? (y - continue, other - exit)");
            if (scanner.nextLine().contains("y")) continue;
            else {
                System.out.println("Exiting the program...");
                break;
            }
        }
        scanner.close();
    }
// ==============================================================================================================================
    public static void askInput(Map<Integer, Cell> board, Scanner scanner) {
        int numInput;
        String[] strInput;

        // Ask 1 : Choose Board size
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

        // Initialize for Empty Board
        for (int cellID=0; cellID<N*N; cellID++) board.put(cellID, new Cell(cellID/N, cellID%N, ' '));
        printBoard(board);

        // Ask 2 : Put Knight
        System.out.println("Enter Knight ID");
        while (true) {
            try {
                numInput = Integer.parseInt(scanner.nextLine());
                if (numInput < 0 || numInput > (N*N)-1) {
                    System.out.println("Input must be according to Cell IDs.");
                    continue;
                } else {
                    if (board.get(numInput).type != ' ') {
                        System.out.println("This cell is already occupied.");
                        continue;
                    }
                }
                break;
            } catch (NumberFormatException e) { System.out.println("Invalid input."); }
        }
        knightPos = numInput;   // Store Knight initial position inside global variable
        setCell(board, knightPos, 'K'); // Add Knight to board

        // Ask 3 : Put Castle
        System.out.println("Enter Castle ID");
        while (true) {
            try {
                numInput = Integer.parseInt(scanner.nextLine());
                if (numInput < 0 || numInput > (N*N)-1) {
                    System.out.println("Input can only be from given Cell IDs.");
                    continue;
                } else {
                    if (board.get(numInput).type != ' ') {
                        System.out.println("This cell is already occupied.");
                        continue;
                    }
                }
                break;
            } catch (NumberFormatException e) { System.out.println("Invalid input."); }
        }
        castlePos = numInput;   // Store Castle initial position inside global variable
        setCell(board, castlePos, 'C'); // Add Castle to board

        // Ask 4 : Put Bomb
        System.out.println("Enter bomb IDs separated by comma (Invalid IDs will be ignored)");
        strInput = scanner.nextLine().split(",");
        for (String bombInput : strInput) {
            try {
                int bombID = Integer.parseInt(bombInput);
                if (bombID < 0 || bombID > (N*N)-1) continue;   // Ignore Invalid number input
                setCell(board, bombID, 'b');    // Add BOMB to Board
            } catch (NumberFormatException e) { /* Ignore Error Input */ }
        }
    }
// ==============================================================================================================================
    public static void setCell(Map<Integer, Cell> board, int cellID, char type) {
        if (board.get(cellID).type == ' ') board.get(cellID).type = type;
    }
// ==============================================================================================================================
    public static void printBoard(Map<Integer, Cell> board) {   // Printing at start
        System.out.printf("%8s", "Cell IDs");
        for (int cellID=0; cellID<N*N; cellID++) {
            System.out.printf("%5d: %2s", cellID, board.get(cellID).type);
            if (cellID%N == N-1) System.out.printf("\n%8s", "");
        }
        System.out.println();
    }
// ==============================================================================================================================
    public static void printPath(GraphPath<Integer, DefaultEdge> path, HashSet<Integer> bombCell) {
        int moveCount = 0;
        for (int move : path.getVertexList()) {
            if (moveCount == 0) System.out.printf("Initially, Knight at [%d]\n", knightPos, "");
            else System.out.printf("Move %d --> Jump to [%d]\n", moveCount, move, "");
            for (int cellID=0; cellID<N*N; cellID++) {
                System.out.printf("%5d: ", cellID);
                String type =   (bombCell.contains(cellID)) ? "b"
                              : (cellID == move) ? "K*"
                              : (cellID == castlePos) ? "C*"
                              : "";
                System.out.printf("%2s", type);
                if (cellID%N == N-1) System.out.printf("\n", "");
            }
            if (moveCount++ == 0) System.out.printf("\nBest route to Castle = %d moves.\n", path.getLength());
            System.out.println();
        }
    }
// ==============================================================================================================================
    public static void BFS(Map<Integer, Cell> board) {  // *** BREADTH-FIRST SEARCH ***
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        HashSet<Integer> bombCell = new HashSet<>();    // To store Bomb cellID

        for (Map.Entry<Integer, Cell> entry : board.entrySet()) {
            if (entry.getValue().type != 'b') {     // Add valid CellID as vertex
                graph.addVertex(entry.getKey());
                // System.out.println("Adding [" + entry.getKey() + "]: " + entry.getValue());
            }
            else bombCell.add(entry.getKey());      // Store bomb data for later use
        }

        for (Integer cellID : graph.vertexSet()) {  // Add valid moves as edges
            Cell cell = board.get(cellID);
            for (int[] move : knightMoves) {    // Check KnightMove rule
                int nextRow = cell.row + move[0];
                int nextCol = cell.col + move[1];

                if (isValidMove(board, nextRow, nextCol)) { // Function check for boundary and Bomb
                    int nextCell = (nextRow * N) + nextCol;
                    graph.addEdge(cellID, nextCell);
                }
            }
        }
        BFSShortestPath<Integer, DefaultEdge> bfs = new BFSShortestPath<>(graph);   // Use build-in algorithm
        GraphPath<Integer, DefaultEdge> path = bfs.getPath(knightPos, castlePos);   // Finding all possible path bfs.getPath(SOURCE, TARGET);

        if (path == null) System.out.println("No path found.");
        else printPath(path, bombCell);
    }
// ==============================================================================================================================
    public static boolean isValidMove(Map<Integer, Cell> board, int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) return false;   // Check boundary
        int cellID = (row * N) + col;
        return board.get(cellID).type != 'b';   // B O M B ! ! !
    }
// ==============================================================================================================================
}

/*

int CellID = (row * N) + col;   // Row & Column to CellID

int row = CellID/N;     // CellID to Row
int col = CellID%N;     // CellID to Column

*/