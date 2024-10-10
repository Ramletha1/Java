package Ex3_6581167;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Language {
    private String name;
    private int releaseYear;
    private double stackQuestions; // Stackoverflow questions in millions
    private double githubRepos; // Github repositories in millions
    private double githubTopStars; // Github top-repo stars in thousands

    public Language (String name, int releaseYear, double stackQuestions, double githubRepos, double githubTopStars){   // Constructor
        this.name = name;
        this.releaseYear = releaseYear;
        this.stackQuestions = stackQuestions;
        this.githubRepos = githubRepos;
        this.githubTopStars = githubTopStars;
    }

    public String getName(){
        return name;
    }

    public int getYear(){
        return releaseYear;
    }

    public double getStackQuestions() {
        return stackQuestions;
    }

    public double getGithubRepos(){
        return githubRepos;
    }

    public double getGithubTopStars(){
        return githubTopStars;
    }

    public String toString() {
        return String.format("%-18s %-22d %-21.1f %-19.1f %-16.1f", name, releaseYear, stackQuestions, githubRepos, githubTopStars);
    }

    public static void printHead() {
        System.out.println("Language       Release Year   Stackoverflow Questions(M)   Github Repos(M)   Github Top Stars(K)");
        System.out.println("================================================================================================");
    }
}

class SortLanguageByName implements Comparator<Language> {
    public int compare(Language l1, Language l2){
        return l1.getName().compareTo(l2.getName());
    }
}

class SortLanguageByYear implements Comparator<Language> {
    public int compare(Language l1, Language l2){
        if (l1.getYear() != l2.getYear()){
            return Integer.compare(l1.getYear(), l2.getYear());
        }
        if (Double.compare(l1.getGithubRepos(), l2.getGithubRepos()) != 0) {
            return Double.compare(l2.getGithubRepos(), l1.getGithubRepos());
        }
        if (Double.compare(l1.getStackQuestions(), l2.getStackQuestions()) != 0) {
            return Double.compare(l2.getStackQuestions(), l1.getStackQuestions());
        }
        if (Double.compare(l1.getGithubTopStars(), l2.getGithubTopStars()) != 0) {
            return Double.compare(l2.getGithubTopStars(), l1.getGithubTopStars());
        }
        return l1.getName().compareTo(l2.getName());
    }
}

class SortLanguageByStackoverflow implements Comparator<Language> {
    public int compare(Language l1, Language l2) {
        if (Double.compare(l2.getStackQuestions(), l1.getStackQuestions()) != 0) {
            return Double.compare(l2.getStackQuestions(), l1.getStackQuestions());
        }
        if (Double.compare(l1.getGithubRepos(), l2.getGithubRepos()) != 0) {
            return Double.compare(l1.getGithubRepos(), l2.getGithubRepos());
        }
        if (Double.compare(l1.getGithubTopStars(), l2.getGithubTopStars()) != 0) {
            return Double.compare(l1.getGithubTopStars(), l2.getGithubTopStars());
        }
        return l1.getName().compareTo(l2.getName());
    }
}

class SortLanguageByGithubRepos implements Comparator<Language> {
    public int compare(Language l1, Language l2) {
        if (Double.compare(l2.getGithubRepos(), l1.getGithubRepos()) != 0) {
            return Double.compare(l2.getGithubRepos(), l1.getGithubRepos());
        }
        if (Double.compare(l1.getGithubTopStars(), l2.getGithubTopStars()) != 0) {
            return Double.compare(l1.getGithubTopStars(), l2.getGithubTopStars());
        }
        return l1.getName().compareTo(l2.getName());
    }
}

class SortLanguageByGithubStars implements Comparator<Language> {
    public int compare(Language l1, Language l2) {
        if (Double.compare(l2.getGithubTopStars(), l1.getGithubTopStars()) != 0) {
            return Double.compare(l2.getGithubTopStars(), l1.getGithubTopStars());
        }
        return l1.getName().compareTo(l2.getName());
    }
}

public class main {
    public static void main(String[] args){
        ArrayList<Language> languages = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String userInput;

        try {
            Scanner fileScanner = new Scanner(new File("/workspaces/Java/data_structures/Ex3_6581167/languages.txt"));
            //Scanner fileScanner = new Scanner(new File("src/main/java/Ex3_6581167/languages.txt"));
            fileScanner.nextLine();
            while (fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                String[] input = line.split(",");

                try {
                    String name = input[0].trim();
                    int yearRelease = Integer.parseInt(input[1].trim());
                    double stackQuestion = Double.parseDouble(input[2].trim());
                    double githubRepo = Double.parseDouble(input[3].trim());
                    double githubTop = Double.parseDouble(input[4].trim());
                    languages.add(new Language(name, yearRelease, stackQuestion, githubRepo, githubTop));

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Sort by >> n = name\n" +
                               "           y = year\n" +
                               "           q = Stackoverflow question\n" +
                               "           r = Github repos\n" +
                               "           s = Github stats\n" +
                               "      others = quit");
            userInput = scanner.next();

            switch (userInput.toLowerCase()) {
                case "n":   Collections.sort(languages, new SortLanguageByName());
                    break;
                case "y":   Collections.sort(languages, new SortLanguageByYear());
                    break;
                case "q":   Collections.sort(languages, new SortLanguageByStackoverflow());
                    break;
                case "r":   Collections.sort(languages, new SortLanguageByGithubRepos());
                    break;
                case "s":   Collections.sort(languages, new SortLanguageByGithubStars());
                    break;
                default:
                    System.out.println("End of program.");
                    scanner.close();
                    System.exit(0);
                    break;
            }
            Language.printHead();
            for (Language lan : languages) {
                System.out.println(lan);
            }

            while (true) {
                System.out.println("Do you want to sort again? (yes:no)");
                userInput = scanner.next();

                if (userInput.equals("yes")) {
                    break;
                } else if (userInput.equals("no")) {
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                } else {
                    System.out.println("Invalid input. Please type 'yes' or 'no'.");
                }
            }
        }
    }
}

// directory -> /java/Ex3_6581167 : java -cp .. Ex3_6581167.main

// directory -> /java : java Ex3_6581167.main