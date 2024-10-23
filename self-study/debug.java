// package Java.self-study;
import java.util.Scanner;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.Period;

public class debug {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Name: ");
        String name = scanner.nextLine();
        System.out.printf("Date of Birth (DD/MM/YY): ");
        String arrayBirthDay[] = (scanner.nextLine()).split("/");
        int i;
        String birthDay;
        for (String birthDay:arrayBirthDay) {

            System.out.println(birthDay); 
        }
    }
}