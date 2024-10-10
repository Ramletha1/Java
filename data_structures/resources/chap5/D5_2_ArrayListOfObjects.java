import java.util.*;

class D5_2_ArrayListOfObjects 
{
    public static void main(String[] args)  
    {
	Scanner scan = new Scanner(System.in);

	ArrayList<MyStudent> A = new ArrayList<MyStudent>();
        System.out.println("Before adding members = " + A);
	A.add( new MyStudent("Justin", 44.5, 20) );
	A.add( new MyStudent("Keaton", 77.5, 19) );
	A.add( new MyStudent("Leonel", 66.5, 21) );
	System.out.println("\n=== Original content ===");
	for (MyStudent m : A)  m.print();
        System.out.println();

        // Try this with and without toString in class Student
        System.out.println("Explicit toString = " + A.toString());   
        System.out.println("Implicit toString = " + A);
        System.out.println();
        
        /*
        MyStudent old = A.set(0, new MyStudent("Nicole", 88.5, 25));
        System.out.printf("\nOld student = %s \n", old);
        System.out.printf("New student = %s \n", A.get(0)); 
        */

        
	MyStudent key1 = new MyStudent("keaton", 0, 0);
        MyStudent key2 = new MyStudent("annie", 0, 0);

	// equals is defined in Student & inherited to MyStudent
	// equality of is based on name, we don't care about score
	System.out.printf("Contain %s ?  %b (index = %d) \n", 
                          key1, A.contains(key1), A.indexOf(key1));
        
	System.out.printf("Contain %s ?  %b (index = %d) \n", 
                          key2, A.contains(key2), A.indexOf(key2)); 
              
	//A.remove(key1);
	//System.out.printf("\nAfter removing %s = %s \n", key1, A);
        
        System.out.println("\n=== Sort MyStudent by score (using Comparable)");
        Collections.sort(A);
        System.out.println(A);
        
        System.out.println("\n=== Sort MyStudent by name (using Comparator)");
	Collections.sort(A, new SortStudentByName());
	System.out.println(A);
    }
}
