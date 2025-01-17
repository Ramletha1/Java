import java.util.*;
import java.io.*;

public class Main {
    public static void main (String[] args) {
        
        try {
            Scanner readFile = new Scanner(new File("platforms.txt"));

            System.out.printf("%-20s %-20s %-20s %-20s\n" , "Platform", "MAU(thousands)", "YAU(billions)", ">500 millions");
            System.out.printf("=".repeat(78));
            System.out.println();
            while (readFile.hasNext()) {
                String str = readFile.next();
                int num = readFile.nextInt();
                System.out.printf("%-20s %-20d 0.%-20s", str, num*1000, num);
                System.out.println();
            }
        } catch (Exception e) { System.err.println(e); }
    }
}