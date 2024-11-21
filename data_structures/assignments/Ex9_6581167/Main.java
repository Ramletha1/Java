// package Ex8_6581167;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ActorGraph {

    private TreeMap<String, LinkedHashSet<String>> workingMap;  // Store the data from file
    //private Graph<String, DefaultEdge> costarGraph;
    //private Graph<String, DefaultEdge> conflictGraph;

    public ActorGraph() { // Constructor
        workingMap = new TreeMap<>();
        //costarGraph = new Graph<>();
        //conflictGraph = new Graph<>();
    }

    public void addMovieActor(String movie, HashSet<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
            }
            workingMap.get(actor).add(movie);
        }
    }

    public static final String BACON = "Kevin Bacon";
    public void baconDegree() { /* Find Bacon degree of given actor */ }
    public void baconParties() { /* Arrange parties for Bacon */ }
    // You may use Map to keep data from input file as in Exercise 8
}
 

public class Main {
    public static void main(String args[]) {
        ActorGraph actorGraph = new ActorGraph();

        try {
            Scanner fileScanner = new Scanner(new File("/workspaces/Java/data_structures/assignments/Ex9_6581167/movies.txt"));
            // Scanner fileScanner = new Scanner(new File("/src/main/java/Ex8_6581167/movies.txt"));
            while (fileScanner.hasNextLine()) {         // Check every existing line
                String line = fileScanner.nextLine();
                String[] data = line.split(";");
                
                String movie = data[0].trim();
                HashSet<String> actors = new HashSet<>();
                for (int i=1; i < data.length; i++) {   // Check every ';' of that line
                    actors.add(data[i].trim());
                }
                actorGraph.addMovieActor(movie, actors);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        Scanner scanner = new Scanner(System.in);
        while (true) {      // Loop ask for input
            if (!askInput(actorGraph, scanner)) {
                System.out.println("Exiting Program...");
                break;
            }
        }
        return;
    }


    public static boolean askInput(ActorGraph actorGraph, Scanner scanner) {
        System.out.println("Eter name or surname, or 0 to quit.");
        String actorInput[] = scanner.nextLine().split(",");
        if (actorInput.length == 1) {
            System.out.println("Length is 1.");
            try {
                actorInput = Integer.parseInt(actorInput);
            } catch (NumberFormatException e) {}
        }
        return true;
    }
}