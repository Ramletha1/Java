import java.util.*;
import java.io.*;

public class Main {
    public static void main (String[] args) {
        File inFile = new File("test.txt");
        Scanner userInput = new Scanner(System.in);

        try (Scanner reaFile = new Scanner(inFile);) {
            System.out.println("World");
        } catch (FileNotFoundException e) {}
    }
}