// package OOP;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalDate;
import java.time.Period;

class User {
    String name;
    int age;
    LocalDate birthDay;

    public String getName() { return name; }
    public int getAge() { return age; }
    public User(String name, LocalDate birthDay) {  // Constructor
        this.name = name;
        this.birthDay = birthDay;
        this.age = Period.between(this.birthDay, LocalDate.now()).getYears();
    }
    public String toString() { return "Name: " + name + "\nAge: " + age + "\nDate of Birth: " + birthDay; }
}

public class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<User> nameList = new ArrayList<>();
        int input = 0;
        
        while (input !=5) {
            System.out.println("-----------------------------");
            System.out.println("1: Input/Store data.\n2: Display all existing data.\n3: Sort by..\n4: Calculate total age\n5: Exit");
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
                    // nameList.sort(Comparator.comparing(User::age));
                    for (User tmpNameList : nameList) { System.out.println(tmpNameList); }
                    break;

                
                case 3:
                    System.out.println("1: Name\n2: Age");
                    input = Integer.parseInt(scanner.nextLine());
                    switch (input) {
                        case 1: nameList.sort(Comparator.comparing(User::getName)); break;
                        case 2: nameList.sort(Comparator.comparing(User::getAge)); break;
                        default: System.out.println("Invalid input"); break;
                    }
                    System.out.println("1: Ascending\n2: Decending");
                    input = Integer.parseInt(scanner.nextLine());
                    switch (input) {
                        case 1: break;
                        case 2: Collections.reverse(nameList); break;
                        default: break;
                    }
                    for (User tmpNameList : nameList) { System.out.println(tmpNameList); }
                    break;

                
                case 4:
                    int totalAge = 0;
                    for (User tmpNameList:nameList) { totalAge+=tmpNameList.getAge(); }
                    System.out.printf("Total age is %d\n", totalAge);
                    break;


                default: break;
            }
        }
        scanner.close();
    }
}
