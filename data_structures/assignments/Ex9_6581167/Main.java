// package Ex9_6581167;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.color.*;



public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        ActorGraph actorGraph = new ActorGraph();
        String filePath = "/workspaces/Java/data_structures/assignments/Ex9_6581167/movies.txt";
        // String filePath = "/src/main/java/Ex9_6581167/movies.txt";

        actorGraph.buildGraph(filePath);
        actorGraph.baconParties();

        while (true) {
            if (!askInput(actorGraph, scanner)) {
                System.out.println("Exiting Program...");
                break;
            }
        }
        scanner.close();
    }

    public static boolean askInput(ActorGraph actorGraph, Scanner scanner) {
        System.out.println("Enter name or surname, or 0 to quit");
        String userInput[] = scanner.nextLine().split(",");
          
        if (userInput[0].contains("0")) return false;

        HashSet<String> chosenActor = new HashSet<>();
        for (int i=0; i<userInput.length; i++) {
            chosenActor.add(userInput[i].trim());
        }

        actorGraph.findActor(chosenActor);      // find different actors with same name and save to resultSet
        return true;
    }
}



class ActorGraph {
    private Graph<String, DefaultEdge> costarGraph;     
    private GreedyColoring<String, DefaultEdge> conflictGraph;   
    private TreeMap<String, LinkedHashSet<String>> workingMap;
    private LinkedHashSet<String> resultSet;

    public static final String BACON = "Kevin Bacon";

    public ActorGraph() { // Constructor
        this.costarGraph = new SimpleGraph<>(DefaultEdge.class);
        // this.conflictGraph = new GreedyColoring<>(DefaultEdge.class);
        this.workingMap = new TreeMap<>();
        this.resultSet = new LinkedHashSet<>();
    }

    public void buildGraph(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));

            while (fileScanner.hasNextLine()) {         // Check every existing line
                String line = fileScanner.nextLine().trim();
                String[] data = line.split(";");        // Split ';' into array
                String movie = data[0];

                // BUILDING workingMap
                HashSet<String> actors = new HashSet<>();
                for (int i=1; i < data.length; i++) {
                    actors.add(data[i]);
                }
                addMovieActor(movie, actors);
                // --------------------

                // BUILDING costarGraph
                for (int i=1; i<data.length; i++) {     // Add vertices and edges at the same time
                    costarGraph.addVertex(data[i]);
                    for (int j=1; j<i; j++) {
                        costarGraph.addEdge(data[i], data[j]);
                    }
                }
                // --------------------
            }
            // BUILDING conflictGraph
            Graph<String, DefaultEdge> tempGraph = new SimpleGraph<>(DefaultEdge.class);
            for (String vertex : costarGraph.vertexSet()) {     // Vertices
                tempGraph.addVertex(vertex);
            }
            for (DefaultEdge edge : costarGraph.edgeSet()) {    // Edges
                String source = costarGraph.getEdgeSource(edge);
                String target = costarGraph.getEdgeTarget(edge);
                tempGraph.addEdge(source, target);
            }
            tempGraph.removeVertex(BACON);

            conflictGraph = new GreedyColoring<>(tempGraph);
            fileScanner.close();
            // --------------------
        }
        catch (FileNotFoundException e){
            System.out.printf("Error: File not found (%s)\n", fileName);
            e.printStackTrace();
        }
    }

    public void addMovieActor(String movie, HashSet<String> actors) {
        for (String actor : actors) {
            if (!workingMap.containsKey(actor)) {
                workingMap.put(actor, new LinkedHashSet<>());
            }
            workingMap.get(actor).add(movie);
        }
    }

    public void findActor(HashSet<String> chosenActor) {     
        for (String input : chosenActor) {      // Find different people with same name
            for (String actor : workingMap.keySet()) {
                if (actor.toLowerCase().contains(input.toLowerCase())) {
                    resultSet.add(actor);
                }
            }
        }
    }

    public void baconDegree() { /* Find Bacon degree of given actor */ }

    public void baconParties() {
        List<Set<String>> colorList = conflictGraph.getColoring().getColorClasses();

        System.out.println("\n\n============================== Bacon parties ==============================");
        System.out.println("By GreedyColoring  >>  total parties = " + colorList.size());
        System.out.println();
        for (int i=0; i<colorList.size();i++) {
            System.out.printf("Parties %d >> guests = %d", i+1, colorList.get(i).size());
            Set<String> colorClass = colorList.get(i);
            int count = 0;
            for (String actor : colorClass) {
                if (count % 6 == 0) System.out.println();   // New line every 6 actors
                System.out.printf("%-20s", actor);
                count++;
            }
            System.out.printf("\n\n");
        }
        System.out.println("============================== Bacon parties ==============================");
        // System.out.println("" + costarGraph.vertexSet());
        // System.out.println("" + costarGraph.edgeSet());
        // System.out.println("" + costarGraph.vertexSet().size());
    }
}

// Total movies = 60
// Total actors = 85