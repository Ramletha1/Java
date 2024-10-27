import java.util.*;

public class array_dequeue {
    public static void main(String[] args) {
        ArrayList<String> numList = new ArrayList<>();

        int arrayInt[] = {5,8,1,3,10,15};

        numList.add("Blue");
        numList.add("Blu");
        numList.add("Cock");
        numList.add("Ass");
        numList.add("Kampang");

         // row 1
        Collections.sort(numList);
        System.out.println(numList);

        Collections.sort(numList, Comparator.reverseOrder());
        System.out.println(numList); // row 2

        Collections.sort(numList, Comparator.naturalOrder());
        // numList.sort(Comparator.naturalOrder());
        System.out.println(numList); // row 3

        //Collections.sort(numList, Comparator.naturalOrder().thenComparing(String::length));
        //System.out.println(numList); // row 4

        for (int a:arrayInt) System.out.printf("%d ", a); System.out.println("");

        Arrays.sort(arrayInt);
        for (int a:arrayInt) System.out.printf("%d ", a); System.out.println("");


        System.out.println("");
        Deque<String> deque = new ArrayDeque<>();

        // Using as a stack (LIFO)
        deque.add("First");
        deque.push("Second");
        deque.push("Third");
        System.out.println(deque);
        System.out.println("Stack pop: " + deque.pop());

        // Using as a queue (FIFO)
        deque.addLast("One");
        deque.addLast("Two");
        deque.addLast("Three");
        System.out.println(deque);
        System.out.println("Queue poll: " + deque.poll());

        // Using as a deque
        deque.addFirst("Start");
        deque.addLast("End");
        System.out.println("Deque: " + deque);




        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.add("Apple");
        pq.add("Banana");
        pq.add("Cherry");

        System.out.println("PriorityQueue (natural order): ");
        while (!pq.isEmpty()) {
            System.out.println(pq.poll()); // Output in alphabetical order
        }

        // PriorityQueue with custom comparator for reverse order (descending alphabetical)
        PriorityQueue<String> pqCustom = new PriorityQueue<>((a, b) -> b.compareTo(a));
        pqCustom.add("Apple");
        pqCustom.add("Banana");
        pqCustom.add("Cherry");

        System.out.println("PriorityQueue (reverse order): ");
        while (!pqCustom.isEmpty()) {
            System.out.println(pqCustom.poll()); // Output in reverse alphabetical order
        }
    } 
}