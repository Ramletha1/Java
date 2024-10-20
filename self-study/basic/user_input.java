import java.util.Scanner;

public class user_input {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("What is your name? ");
        String name = scanner.nextLine();

        System.out.printf("Hello %s. How are old are you? ", name);
        int age = Integer.parseInt(scanner.nextLine());
        // scanner.nextLine();     // To prevent program skipping line <enter> from previous nextInt

        System.out.printf("%d years old? ZAMMN. What coding language do you prefer? ", age);
        String language = scanner.nextLine();

        System.out.printf("Molly, %s is a good language.", language);

        scanner.close();
    }
}