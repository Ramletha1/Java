import java.util.*;

class D6_2_ListObjects 
{
    LinkedList<Student> LL, cloned_LL;
    ArrayDeque<Student> AD;
    
    public void initialize()
    {
	LL = new LinkedList<>();
	LL.add(new Student("Ellen", 45.0));	
	LL.add(new Student("David", 35.0));
	LL.add(new Student("Carol", 45.0));	
        
        AD = new ArrayDeque<Student>(LL);
	
        LL.add(new Student("Annie", 55.0));
	LL.add(new Student("Betty", 35.0));
	LL.add(new Student("Annie", 25.0));  
        
        System.out.println("\n=== Original ===");
        for(Student s : LL) s.print();
        System.out.println(LL);
        //System.out.printf("LL contains all in AD ? %b \n", LL.containsAll(AD));
    }
    
    public static void main(String[] args)  
    {
        D6_2_ListObjects mainprog = new D6_2_ListObjects();
        mainprog.initialize();

        //mainprog.testForGetI();
        //mainprog.testForEach();
        //mainprog.testIterator();
        
        //mainprog.shallowCopy();  mainprog.testCloned();
        //mainprog.deepCopy();     mainprog.testCloned();
    }

    ////////////////////////////////////////////////////////////////////////////
    public void testForGetI()
    {        
	System.out.println("\n=== Iterate by for-get(i) ===");
        System.out.println("\nFront to back");
	for (int i=0; i < LL.size(); i++)     LL.get(i).print(); 
        
        System.out.println("\nBack to front");
	for (int i=LL.size()-1; i >= 0; i--)  LL.get(i).print(); 
        
        System.out.println("\nSkip 1 element");
	for (int i=0; i < LL.size(); i=i+2)   LL.get(i).print(); 

	System.out.println("\nUpdate while iterating");
	for (int i=0; i < LL.size(); i++) 
	{
            if (i==0) 
            {
		LL.remove(LL.size()-1);
		LL.add(new Student("Jones", -100));
                LL.set(2, new Student("Karen", -200));
                LL.get(3).setScore(-300);
            }
            LL.get(i).print();			
	}
    }
    
    public void testForEach()
    {
	System.out.println("\n=== Iterate by for-each ===");
        System.out.println("\nFront to back");
	for (Student s : LL) s.print(); 

        System.out.println("\nBack to front");
        Collections.reverse(LL);
        for (Student s : LL) s.print();  
        
	System.out.println("\nUpdate while iterating");
	int index = 0;
	for (Student s : LL)
	{
            if (index == 0) 
            {
		//LL.remove(LL.size()-1);
		//LL.add(new Student("Jones", -100));
                //LL.set(2, new Student("Karen", -200));
                //LL.get(3).setScore(-300);
            }
            s.print(); index++;
	}
    }
    
    public void testIterator()
    {
        //ListIterator<Student> iter1 = LL.listIterator(); 
        ListIterator<Student> iter1 = LL.listIterator(2);   
        System.out.println("\n=== Move next ===");
        int index = 0;
        while(iter1.hasNext()) 
        {
            if (index == 0) 
            {
		//LL.remove(LL.size()-1);
		//LL.add(new Student("Jones", -100));
                //LL.set(2, new Student("Karen", -200));
                //LL.get(3).setScore(-300);
            }            
            iter1.next().print();                          // include member at starting index
        }  

        //ListIterator<Student> iter2 = LL.listIterator(); 
        ListIterator<Student> iter2 = LL.listIterator(2);        
        System.out.println("\n=== Move previous ===");
        index = 0;
        while(iter2.hasPrevious()) 
        {
            if (index == 0) 
            {
		//LL.remove(LL.size()-1);
		//LL.add(new Student("Jones", -100));
                //LL.set(2, new Student("Karen", -200));
                //LL.get(3).setScore(-300);
            }                        
            iter2.previous().print();                      // exclude member at starting index 
        }                 
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public void shallowCopy()
    {
        System.out.println("\n=== Shallow copy ===");
        //cloned_LL = (LinkedList<Student>) LL.clone();
        cloned_LL = new LinkedList<Student>(LL);
    }
        
    public void deepCopy()
    {
        System.out.println("\n=== Deep copy ===");
        cloned_LL = new LinkedList<Student>();
        for (Student s : LL)
        {
            // create a new Student object & add it to cloned list
            cloned_LL.add( new Student(s.getName(), s.getScore()) );
        }
    }
    
    public void testCloned()
    {
        LL.removeFirst();         
        cloned_LL.removeLast();           
        System.out.println("\nOriginal (-first) = " + LL); 
        System.out.println("Cloned   (-last)  = " + cloned_LL);      
        
        LL.get(1).setScore(-100);
        cloned_LL.get(3).setScore(-300);
        System.out.printf("\nOriginal (index 1) = %s \n", LL); 
        System.out.printf("Cloned   (index 3) = %s \n", cloned_LL);  
        
        Collections.sort(LL, new SortStudentByName());
        Collections.sort(cloned_LL, new SortStudentByScore());
        System.out.println("\nOriginal (sort by name)  = " + LL); 
        System.out.println("Cloned   (sort by socre) = " + cloned_LL);           
    }
}
