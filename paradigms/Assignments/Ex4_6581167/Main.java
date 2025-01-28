import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        int threshold;
        
        System.out.println("Enter year threshold = ");
        while (true) {
            try {
                threshold = userInput.nextInt();
                break;
            } catch (InputMismatchException e) { userInput.nextLine(); }
        }
        System.out.printf("Company established since %-7d Market Value ($Bn.)    Profit ($Bn.)    Sales ($Bn.)\n", threshold);
        System.out.println("=".repeat(87));

        // .................

        userInput.close();
    }
}