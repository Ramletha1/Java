import java.util.*;

class D6_4_Collections 
{
    ArrayList<Student>  A;

    public void initialize()
    {
	A = new ArrayList<>();
	A.add(new Student("Ellen", 45.0));	
	A.add(new Student("David", 35.0));
	A.add(new Student("Carol", 45.0));	
        A.add(new Student("Annie", 55.0));
	A.add(new Student("Betty", 35.0));
	A.add(new Student("Annie", 25.0));  
    }
    
    public static void main(String[] args)  
    {
        D6_4_Collections mainprog = new D6_4_Collections();
        mainprog.initialize();
        mainprog.testCollections();  
    }
    
    
    public void testCollections()
    {
        System.out.println("Original order = " + A);        

        Student minStudent = Collections.min(A, new SortStudentByName());
        System.out.print("\nMin by name       = " + minStudent);
        
        minStudent = Collections.min( A, new SortStudentByName()
                                             .thenComparing(new SortStudentByScore()) 
                                    );
        System.out.print("\nMin by name-score = " + minStudent); 
        
        Student key = new Student("annie", 0);
        System.out.printf("\nThere are %d Annie's \n", Collections.frequency(A, key));
         
        Collections.rotate(A, -2);
        System.out.println("\nLeft  rotation by 2 = " + A);
        
        Collections.rotate(A, 4);
        System.out.println("\nRight rotation by 4 = " + A);       
        
        Collections.swap(A, 0, 1);
        System.out.println("\nSwaping indices 0,1 = " + A); 
        
        Collections.shuffle(A);
        System.out.println("\nShuffling = " + A);     
    }
}
