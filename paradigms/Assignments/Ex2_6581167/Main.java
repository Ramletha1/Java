// By Wongsatorn Suwannarit 6581167
// This code is written in Codespace & VSCode

import java.util.*;
import java.io.*;

public class Main {
    public static void main (String[] args) {
        File inFile = new File("platforms.txt");
        File outFile = new File("output.txt");
        // File inFile = new File("src/main/Java/Ex2_6581167/platforms.txt");
        // File outFile = new File("src/main/Java/Ex2_6581167/output.txt");
        int threshold;


        // Ask for MAU Threshold
        Scanner input = new Scanner(System.in);
        System.out.println("=".repeat(70));
        System.out.println("Read platform data from " + inFile.getAbsolutePath());
        // System.out.println("Read platform data from " + inFile.getPath());
        System.out.println("Enter MAU threshold in millions =");
        while (true) {
            try {
                threshold = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException e) { System.out.println("Please enter valid input."); }
        }
        input.close();
        System.out.println("Write output " + outFile.getAbsolutePath());
        // System.out.println("Write output " + outFile.getPath());
        System.out.println("=".repeat(70));


        // Calculate and overwrite into output.txt
        try {
            Scanner readFile = new Scanner(inFile);
            PrintWriter write = new PrintWriter(outFile);

            write.printf("%-15s %-20s %-17s >%d millions\r\n" , "Platform", "MAU(thousands)", "YAU(billions)", threshold);
            write.printf("=".repeat(70));
            write.printf("\r\n");
            while (readFile.hasNext()) {
                String platform = readFile.next();
                int num = readFile.nextInt();
                int MAU = num*1000;
                float YAU = (float)num/1000;
                String moreThan = "no";
                if (num > threshold) moreThan = "yes";
                write.printf("%-15s %,11d %18.3f %15s\r\n", platform, MAU, YAU, moreThan);
            }
            write.flush();
            write.close();
            readFile.close();
        } catch (Exception e) { System.err.println(e); }
    }
}