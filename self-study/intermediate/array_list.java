import java.util.*;

public class array_list {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        numbers.add(5); // index 0
        numbers.add(3); // index 1
        numbers.add(4); // index 2
        numbers.add(1);
        numbers.add(2);

        System.out.println(numbers.toString());
        numbers.sort(Comparator.naturalOrder());

        System.out.println(numbers.toString());
        numbers.sort(Comparator.reverseOrder());

        System.out.println(numbers.toString());
        System.out.println("Index[2] = " + numbers.get(2));

        numbers.remove(2);
        System.out.println(numbers.toString());
        System.out.println("Index[2] = " + numbers.get(2));
    }
}
