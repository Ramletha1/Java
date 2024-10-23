import java.util.Arrays;;

public class arrays {
    public static void main(String[] args) {
        int numbers[] = {1,2,3,4,5};
        int DupedNumbers[] = Arrays.copyOf(numbers, numbers.length); // = numbers; <- this is references

        System.out.println(numbers == DupedNumbers);                // Don't do this one
        System.out.println(Arrays.equals(numbers, DupedNumbers));
        
        Arrays.fill(numbers, 0);
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(DupedNumbers));
    }
}
