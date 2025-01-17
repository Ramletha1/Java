// Wongastorn Suwannarit 6581167

import java.util.*;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sHour, sMinute;
        int eHour, eMinute;

        System.out.println("=== Start time ===");
        sHour = readHour(scanner);
        sMinute = readMinute(scanner);
        System.out.println();

        System.out.println("=== End time ===");
        eHour = readHour(scanner);
        eMinute = readMinute(scanner);
        System.out.println();

        System.out.printf("Start time = %02d:%02d\n", sHour, sMinute);
        System.out.printf("End time = %02d:%02d", eHour, eMinute);
        if (sHour > eHour || sHour == eHour && sMinute > eMinute) {
            System.out.println(" (Tomorrow)");
        } else { System.out.println(" (Today)"); }

        int resHour = Math.abs(sHour - eHour);
        int resMin = Math.abs(sMinute - eMinute);
        if (sHour > eHour) resHour = 24 - resHour;
        if (sMinute > eMinute) {
            if (resHour == 0) resHour = 23;
            else resHour-=1;
            resMin = 60 - resMin;
        }
        System.out.printf("Duration = %d hours, %d minutes\n", resHour, resMin);
        System.out.println("-".repeat(40));
    }

    public static int readHour (Scanner scanner) {
        int Hour;
        while (true) {
            System.out.println("Enter hour digit (0-23)");
            try {
                Hour = Integer.parseInt(scanner.nextLine());
                if (Hour >= 0 && Hour < 24) break;
            } catch (NumberFormatException e) {}
        }
        return Hour;
    }

    public static int readMinute (Scanner scanner) {
        int Minute;
        while (true) {
            System.out.println("Enter minute digit (0-59)");
            try {
                Minute = Integer.parseInt(scanner.nextLine());
                if (Minute >= 0 && Minute < 60) break;
            } catch (NumberFormatException e) {}
        }
        return Minute;
    }
}