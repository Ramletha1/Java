import java.util.*;

class D5_1_ArrayOfObjects
{
    public static void main(String[] args) 
    {
        
	// (1) Individual objects
	Student s1 = new Student("Annie", 55.5);
	Student s2 = new Student("Betty", 35.0);
	Student s3 = new Student("Carol", 45.5);
        Student s4 = null;
	Student.setCourse("EGCO 221");
	s1.setScore(25.5);
        System.out.println("\n=== Student objects ===");
	s1.print();  s2.print();  s3.print();
        System.out.println();
        
        // Try this with & without toString
        // Try this with & without initializing s4 to null
        System.out.println("Explicit toString = " + s1.toString());       
        System.out.println("Implicit toString = " + s1);   
        System.out.println("Null student = " + s4);

        // (2) Arrays        
        Student [] st = { new Student("Annie", 55.5), 
                          new Student("Betty", 35.5), 
                          new Student("Carol", 45.5)};
        Student.setCourse("EGCO 221");                                 
        
        MyStudent [] mst = new MyStudent[3];
        MyStudent.setCourse("EGCO 213");                        
        mst[0] = new MyStudent("Justin", 44.5, 20);
        mst[1] = new MyStudent("Keaton", 77.5, 19);
        mst[2] = new MyStudent("Leonel", 66.5, 21);
        
        // Notice courses of both Student and MyStudent objects
        // Getting content of array by toString doesn't work
        System.out.println("\n=== Student array ===");
        for (int i=0; i < st.length; i++)   st[i].print();      // for i
        //System.out.println(st);

        System.out.println("\n=== MyStudent array ===");        // for each
        for (MyStudent m : mst) m.print();
        //System.out.println(mst);
        System.out.print("\n\n");

                
        /*
        // (3) Equality and hashcode
        //     (3.1) Try this with and without equals
        //     (3.2) Try this with and without hashcode
        Student key = new Student("Annie", -1);
        System.out.printf("st[0] = %s, key = %s \n", st[0], key);
        System.out.printf("st[0] == key ? %b \n", st[0].equals(key));
        System.out.printf("Hashcode of st[0] = %d \n", st[0].hashCode());
        System.out.printf("Hashcode of key   = %d \n\n", key.hashCode());
        */
        

        /*
	// (4) MyStudent implements Comparable, Student doesn't        
        System.out.println("\n=== Sort MyStudent by score (using Comparable)");
        Arrays.sort(mst);
        for (int i=0; i < mst.length; i++)  mst[i].print();
        */

        
        /*
	System.out.println("\n=== Sort Student by score (using Comparator)");
        Arrays.sort(st, new SortStudentByScore());
	for (int i=0; i < st.length; i++)  st[i].print();
		
	System.out.println("\n=== Sort Student by name (using Comparator)");
        Arrays.sort(st, new SortStudentByName());
	for (Student s : st)  s.print();

        // Use Comparator's sorting instead of its own natural order
        // Student pointer can point to MyStudent object
	System.out.println("\n=== Sort MyStudent by name (using Comparator)");
        Arrays.sort(mst, new SortStudentByName());
	for (MyStudent m : mst)  m.print();
        */

        
        /*
        // (5) Use anonymous inner class
        System.out.println("\n=== Sort MyStudent by age (using anonymous Comparator)");
        Arrays.sort(mst, new Comparator<MyStudent>() {
            public int compare(MyStudent s1, MyStudent s2)
            {
                if (s1.getAge() < s2.getAge())          return -1;
                else if (s1.getAge() > s2.getAge())     return 1;
                else return 0;

                // return s1.getAge() - s2.getAge();
            }
        });
	for (MyStudent m : mst)  m.print();   
        */
        
        
        /*
	// (6) Use Lambda
        System.out.println("\n=== Sort MyStudent by name (using Lambda Comparator)");
        Arrays.sort(mst, (MyStudent m1, MyStudent m2) -> m1.getName().compareToIgnoreCase(m2.getName())
                    );
	for (MyStudent m : mst)  m.print();
        */
    }
}

