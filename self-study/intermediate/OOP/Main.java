// package OOP;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.Period;

class User {
    String name;
    LocalDate birthDay;

    public int age() { return Period.between(this.birthDay, LocalDate.now()).getYears(); }  // Convert day to age (year)
    public User(String name, LocalDate birthDay) {  // Constructor
        this.name = name;
        this.birthDay = birthDay;
    }
    public String toString() { return "Name: " + name + "\nDate of Birth: " + birthDay; }
}

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> nameList = new ArrayList<>();
        int input = 0;
        
        while (input !=4) {
            System.out.println("-----------------------------");
            System.out.println("1: Input/Store data.\n2: Display all existing data.\n3: Calculate total age\n4: Exit");
            System.out.println("-----------------------------");
            input = Integer.parseInt(scanner.nextLine());
            switch (input) {
                case 1:
                    System.out.printf("Name: ");
                    String name = scanner.nextLine();
                    System.out.printf("Date of Birth (DD/MM/YY): ");
                    String arrayBirthDay[] = (scanner.nextLine()).split("/");

                    if (arrayBirthDay[2].length() == 2) { arrayBirthDay[2] = String.format("20%s",arrayBirthDay[2]); }
                    LocalDate birthDay = LocalDate.parse(String.format("%s-%s-%s",arrayBirthDay[2],arrayBirthDay[1],arrayBirthDay[0]));

                    nameList.add(new User(name, birthDay));
                    break;


                case 2:
                    System.out.println("Printing list..");
                    for (User tmpNameList : nameList) { System.out.println(tmpNameList); }
                    break;

                
                case 3:
                    int totalAge = 0;
                    for (User tmpNameList:nameList) { totalAge+=tmpNameList.age(); }
                    System.out.printf("Total age is %d\n", totalAge);
                    break;


                default: break;
            }
        }
        scanner.close();
    }
}
