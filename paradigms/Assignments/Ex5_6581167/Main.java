// package Ex5_6581167;                                                                                         // NetBeans

import java.util.*;
import java.io.*;


class InvalidYearException extends Exception {
    public InvalidYearException(String message) {
        super(message);
    }
}

class InvalidNumberException extends Exception {
    public InvalidNumberException(String message) {
        super(message);
    }
}



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

    public static void yearCheck(int year) throws InvalidYearException {
        if (year < 0 || year > 2024) throw new InvalidYearException("For year: \"" + year + "\"");
    }


    public static void positiveNumberCheck(int[] numbers) throws InvalidNumberException {
        for (int i=0; i<numbers.length; i++) {
            if (numbers[i] < 0) throw new InvalidNumberException("For input: \"" + numbers[i] + "\"");
        }
    }


    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        File inFile = new File("companies.txt");                                                                // VSCode
        // File inFile = new File("src/main/Java/Ex5_6581167/companies.txt");                                   // NetBeans
        List<Company> companies = new ArrayList<>();

        while (true) {
            try (Scanner readFile = new Scanner(inFile)) {
                System.out.println();   // Empty line, space
                readFile.nextLine();    // Skip first line on input file

                while (readFile.hasNext()) {
                    String line = readFile.nextLine();

                    try {
                        String[] col = line.trim().split("\\s*,\\s*");

                        /*                    
                        String name = col[0];
                        int year = Integer.parseInt(col[1]);
                        int marketValue = Integer.parseInt(col[2]);
                        int profit = Integer.parseInt(col[3]);
                        int sales = Integer.parseInt(col[4]);
                        */

                        String name = col[0];
                        int year = Integer.parseInt(col[1]);
                        int marketValue = (int) Double.parseDouble(col[2]);
                        int profit = (int) Double.parseDouble(col[3]);
                        int sales = (int) Double.parseDouble(col[4]);

                        yearCheck(year);
                        int[] numbers = {marketValue, profit, sales};
                        positiveNumberCheck(numbers);

                        companies.add(new Company(name, year, marketValue, profit, sales));
                    } catch (Exception e) {
                        System.err.println(e);
                        System.out.println(line + "\n");
                    }
                }
                readFile.close();
                Collections.sort(companies);
                break;
            } catch (FileNotFoundException e) {
                System.err.println(e);
                System.out.println("New file name =");
                inFile = new File(userInput.nextLine().trim());                                                 // VSCode
                // inFile = new File("src/main/Java/Ex5_6581167/" + userInput.nextLine().trim());               // NetBeans
            }
        }

        int threshold;
        System.out.println("Enter year threshold = ");
        while (true) {
            try {
                threshold = userInput.nextInt();
                break;
            } catch (InputMismatchException e) { 
                userInput.nextLine();
                System.err.println(e + ": Invalid Input");
            }
        }

        System.out.printf("Company established since %-7d Market Value ($Bn.)    Profit ($Bn.)    Sales ($Bn.)\n", threshold);
        System.out.println("=".repeat(87));
        for (Company company : companies) {
            // System.out.println(company);
            company.printInformation();
        }

        System.out.println("\nExiting the program...");
        userInput.close();
    }
}