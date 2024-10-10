import java.util.*;

class Teacher 
{
    private String name;
    private int    age, employed, courses;
    
    public Teacher(String n, int a, int e, int c)
    { name = n; age = a; employed = e; courses = c; }
    
    public String getName()        { return name; }
    public int    getAge()         { return age; }
    public int    getEmployed()    { return employed; }
    public int    getCourses()     { return courses; }
  
    public void   print()
    {
        System.out.printf("%s (age = %d), %d yrs employed, %d courses \n",
                           name, age, employed, courses);
    }
}

////////////////////////////////////////////////////////////////////////////////
//
// Implement individual rules to sort Teachers in increasing order of each variable
//
////////////////////////////////////////////////////////////////////////////////
class SortTeacherByName implements Comparator<Teacher>
{
    public int compare(Teacher t1, Teacher t2)      { return t1.getName().compareToIgnoreCase(t2.getName()); } 
}

class SortTeacherByAge implements Comparator<Teacher>
{
    public int compare(Teacher t1, Teacher t2)      { return Integer.compare(t1.getAge(), t2.getAge()); }
}

class SortTeacherByEmployed implements Comparator<Teacher>
{
    public int compare(Teacher t1, Teacher t2)      { return Integer.compare(t1.getEmployed(), t2.getEmployed()); }
}

class SortTeacherByCourses implements Comparator<Teacher>
{
    public int compare(Teacher t1, Teacher t2)      { return Integer.compare(t1.getCourses(), t2.getCourses()); }
}

////////////////////////////////////////////////////////////////////////////////
public class D5_3_ComparatorChain 
{
    String path     = "src/main/java/examples_ch5/";
    String fileName = "teachers.txt";
    
    ArrayList<Teacher> A;
    
    public void initialize()
    {
        MyFileReader myreader = new MyFileReader(path, fileName);
        myreader.readFile();
        ArrayList<String> allLines = myreader.getLines();
        
        A = new ArrayList<Teacher>();
        for(String line : allLines)
        {
            String []items  = line.split(",");
            String name     = items[0].trim();
            int    age      = Integer.parseInt( items[1].trim() );
            int    employed = Integer.parseInt( items[2].trim() );
            int    courses  = Integer.parseInt( items[3].trim() );
            
            A.add( new Teacher(name, age, employed, courses) );
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        D5_3_ComparatorChain mainprog = new D5_3_ComparatorChain();
        mainprog.initialize();

        //mainprog.sortEasy();
        //mainprog.sortWithChain();
        //mainprog.checkReverse();
    }
    
    public void sortEasy()
    {
        System.out.println("=== Sort Teacher by increasing age");
        Collections.sort(A, new SortTeacherByAge());
        for (Teacher t : A) t.print();
        
        System.out.println("\n\n=== Sort Teacher by decreasing age");
        //Comparator<Teacher> reverseSortAge = new SortTeacherByAge().reversed();
        //Collections.sort(A, reverseSortAge);
        Collections.sort(A, new SortTeacherByAge().reversed());
        for (Teacher t : A) t.print();        
    }
    
    public void sortWithChain()
    {
        System.out.println("\n\n=== Sort Teacher by increasing age, increasing employed, increasing courses, name");
        Collections.sort(A, new SortTeacherByAge()
                                .thenComparing( new SortTeacherByEmployed() )
                                .thenComparing( new SortTeacherByCourses()  )
                                .thenComparing( new SortTeacherByName()     ) 
                        );
        for (Teacher t : A) t.print(); 
        
        System.out.println("\n\n=== Sort Teacher by increasing age, decreasing employed, decreasing courses, name");
        Collections.sort(A, new SortTeacherByAge()
                                .thenComparing( new SortTeacherByEmployed().reversed() )
                                .thenComparing( new SortTeacherByCourses().reversed()  )
                                .thenComparing( new SortTeacherByName()                ) 
                        );
        for (Teacher t : A) t.print();            
    }
    
    public void checkReverse()
    {
        System.out.println("\n\n=== Checking reverse 1");
        Collections.sort(A, new SortTeacherByAge()
                                .thenComparing( new SortTeacherByName().reversed() )   //reverse only name sorting
                        );
        for (Teacher t : A) t.print();
        
        System.out.println("\n\n=== Checking reverse 2");
        Collections.sort(A, new SortTeacherByAge()
                                .thenComparing( new SortTeacherByName() ).reversed()   //reverse both sorting
                        );
        for (Teacher t : A) t.print();            
    }
}
