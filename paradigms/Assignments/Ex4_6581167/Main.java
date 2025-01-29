// Wongsatorn Suwannarit 6581167
// This code is written in VSCode & Codespaces

// package Ex4_6581167;

import java.util.*;
import java.io.*;

class Company implements Comparable<Company> {
    private String name;
    private int year, marketValue, profit, sales;

    public Company(String n, int y, int mv, int p, int s) {
        this.name = n;
        this.year = y;
        this.marketValue = mv;
        this.profit = p;
        this.sales = s;
    }

    public void printInformation() {
        System.out.printf("%-20s (%4d) %,18d %16d %15d\n", name, year, marketValue, profit, sales);
    }

    @Override
    public int compareTo(Company other) {
        // Company other = (Company) obj;
        if (this.marketValue > other.marketValue)       return -1;
        else if (this.marketValue < other.marketValue)  return 1;
        else 
            if (this.profit > other.profit)             return -1;
            else if (this.profit < other.profit)        return 1;
            else
                if (this.sales > other.sales)           return -1;
                else if (this.sales < other.sales)      return 1;
                else                                    return this.name.compareToIgnoreCase(other.name);       
    }

    @Override
    public String toString() {
        return name + ":" + year + ":" + marketValue + ":" + profit + ":" + sales;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        File inFile = new File("companies.txt");                                // VSCode
        // File inFile = new File("src/main/Java/Ex4_6581167/companies.txt");   // NetBeans
        

        while (true) {
            int threshold;
            List<Company> companies = new ArrayList<>();
            
            System.out.println("Enter year threshold = ");
            while (true) {
                try {
                    threshold = userInput.nextInt();
                    break;
                } catch (InputMismatchException e) { 
                    userInput.nextLine();
                    System.out.println("Invalid input.");
                }
            }
            System.out.printf("Company established since %-7d Market Value ($Bn.)    Profit ($Bn.)    Sales ($Bn.)\n", threshold);
            System.out.println("=".repeat(87));

            try {
                Scanner readFile = new Scanner(inFile);

                readFile.nextLine();    // Skip first line
                while (readFile.hasNext()) {
                    String line = readFile.nextLine();
                    String[] col = line.trim().split("\\s*,\\s*");

                    String name = col[0];
                    int year = Integer.parseInt(col[1]);
                    int marketValue = Integer.parseInt(col[2]);
                    int profit = Integer.parseInt(col[3]);
                    int sales = Integer.parseInt(col[4]);

                    if (year >= threshold) {
                        companies.add(new Company(name, year, marketValue, profit, sales));
                    }
                }
                readFile.close();
                Collections.sort(companies);
            } catch (Exception e) { System.err.println(e); }

            for (Company company : companies) {
                // System.out.println(company);
                company.printInformation();
            }

            System.out.println("\n\n\nEnter y or Y to continue, else to quit =");
            if (userInput.next().equalsIgnoreCase("y")) continue;
            else break;
        }
        userInput.close();
        System.out.println("Exiting the program...");
    }
}

// Terminal (When using package)
// $ java -cp .. Ex4_6581167/Main