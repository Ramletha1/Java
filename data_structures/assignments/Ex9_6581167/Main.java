// package Ex9_6581167;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.color.*;



public class Main {
    public static void main(String args[]) {
        ActorGraph actorGraph = new ActorGraph();
        String filePath = "/workspaces/Java/data_structures/assignments/Ex9_6581167/movies.txt";
        // String filePath = "/src/main/java/Ex9_6581167/movies.txt";

        actorGraph.buildGraph(filePath);
    }
}



class ActorGraph {
    private Graph<String, DefaultEdge> costarGraph;
    private Graph<String, DefaultEdge> conflictGraph;

    public static final String BACON = "Kevin Bacon";

    public ActorGraph() { // Constructor
        this.costarGraph = new SimpleGraph<>(DefaultEdge.class);
        this.conflictGraph = new SimpleGraph<>(DefaultEdge.class);
    }

    public void buildGraph(String fileName) {
        costarGraph.addVertex(BACON);

        try {
            Scanner fileScanner = new Scanner(new File(fileName));

            while (fileScanner.hasNextLine()) {         // Check every existing line
                String line = fileScanner.nextLine();
                String[] data = line.split(";");        // Split ';' into array
                String movie = data[0].trim();

                for (int i=1; i<data.length; i++) {     // Add vertices and edges at the same time
                    costarGraph.addVertex(data[i]);
                    for (int j=1; j<i; j++) {
                        costarGraph.addEdge(data[i], data[j]);
                    }
                }
                // for (int i=1; i<data.length; i++) costarGraph.addVertex(data[i]);    // Add vertices
                // for (int i=1; i<data.length; i++) for (int j=i+1; j<data.length; j++) costarGraph.addEdge(data[i], data[j]); // Then edges
            }
            fileScanner.close();
            costarGraph.removeVertex("Kevin Bacon");

            // System.out.println("" + costarGraph.vertexSet());
            // System.out.println("" + costarGraph.edgeSet());
            // System.out.println("" + costarGraph.vertexSet().size());

            GreedyColoring<String, DefaultEdge> greedyColoring = new GreedyColoring<>(costarGraph);
            List<Set<String>> colorList = greedyColoring.getColoring().getColorClasses();
            
            System.out.println("============================== Bacom parties ==============================");
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
        }
        catch (FileNotFoundException e){
            System.out.printf("Error: File not found (%s)\n", fileName);
            e.printStackTrace();
        }
    }

    public void baconDegree() { /* Find Bacon degree of given actor */ }
    public void baconParties() { /* Arrange parties for Bacon */ }
    // You may use Map to keep data from input file as in Exercise 8
}



// Total movies = 60
// Total actors = 85