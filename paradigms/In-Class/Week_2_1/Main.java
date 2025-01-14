import java.util.*;
import java.io.*;

public class Main {
    public static void main (String[] args) {
        try {
            PrintWriter writer = new PrintWriter(new File("hello.txt"));

            writer.printf("Hello\r\n");
            writer.flush();
            writer.close();
            
            System.out.println("Hello printed.");
        } catch (Exception e) { System.err.println(e); }
    }
}