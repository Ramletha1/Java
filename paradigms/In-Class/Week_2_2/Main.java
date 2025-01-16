import java.util.*;

public class Main {
    public static void main (String[] args) {

        System.out.println("Hello World!");

        int[][] array1 = new int[3][3];

        for (int i = 0; i < array1.length; i++) {
            System.out.printf("{ ");
            for (int j = 0; j < array1[i].length; j++) {
                array1[i][j] = (i+1)*(j+1);
                System.out.printf("%-2d ", array1[i][j]);
            }
            System.out.println("}");
        }
    }
}