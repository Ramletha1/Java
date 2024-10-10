import java.util.*;

class D6_3_PriorityQueue 
{
    public static void main(String[] args) 
    {
        //Exception!! if Student doesn't implement Comparable
	//PriorityQueue<Student> PQ = new PriorityQueue<>();
        
	PriorityQueue<Student> PQ = new PriorityQueue<>(new SortStudentByScore()
                                                            .thenComparing(new SortStudentByName())
                                                       );
        
	PQ.add( new Student("Ellen", 45.0) );	
	PQ.add( new Student("David", 35.0) );
	PQ.add( new Student("Carol", 45.0) );	
	PQ.add( new Student("Annie", 55.0) );
	PQ.add( new Student("Betty", 35.0) );
	PQ.add( new Student("Annie", 25.0) );

	System.out.println("Peek heap = " + PQ.peek());
	System.out.printf("Size initial = %d \n", PQ.size());

	System.out.println("\n\n=== Content by implicit toString ===");
        System.out.println(PQ);        
        
	System.out.println("\n\n=== Content by for-each ===");
	for (Student s : PQ) s.print();

        
        /*
        // equality based on method equals
        Scanner scan = new Scanner(System.in);
	System.out.println("\nEnter Student name to search : "); String name  = scan.next();
        System.out.println("Enter Student score : ");            double score = scan.nextInt();
	Student key = new Student(name, score);
	if (PQ.contains(key)) System.out.println(key + " exist");
        else                  System.out.println(key + " not exist");
        */

        
	System.out.println("\n\n=== Content by poll ===");
	while (PQ.size() > 0) PQ.poll().print();
	System.out.printf("\nSize after poll = %d \n", PQ.size());
    }
}
