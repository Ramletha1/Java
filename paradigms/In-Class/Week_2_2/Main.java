import java.util.*;

public class Main {
    public static void main (String[] args) {

        System.out.println("Hello World!\n");

        int[][] array1 = new int[3][3];
        int[][] array2 = new int[3][3];

        int count = 0;
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1[i].length; j++) {
                count++;
                array1[i][j] = count;
                array2[j][i] = count;
            }
        }


        for (int i = 0; i < array1.length; i++) {
            System.out.printf("{ ");
            for (int j = 0; j < array1[i].length; j++) {
                System.out.printf("%-2d ", array1[i][j]);
            }
            System.out.println("}");
        }
        System.out.println();


        for (int i = 0; i < array1.length; i++) {
            System.out.printf("{ ");
            for (int j = 0; j < array1[i].length; j++) {
                System.out.printf("%-2d ", array2[i][j]);
            }
            System.out.println("}");
        }
        System.out.println();
    }
}