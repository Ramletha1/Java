import java.util.*;

class Student
{	
    protected static String course = " ";
    private  String  name;		
    private  double  score;	

    // constructor
    public Student(String n, double sc)     { name = n; score = sc; }

    // setter + getter methods
    public void     setName(String n)       { name = n; }
    public void     setScore(double sc)     { score = sc; }
    public String   getName()               { return name; }
    public double   getScore()              { return score; }

    public void print() 
    {  
        System.out.printf("%s : %s, %.2f \n", course, name, score);  
    }

    
    @Override
    public String toString()
    {  
        String s = String.format("(%s, %.2f)", name, score); 
        return s;
    }
    
    
    public static void setCourse(String c)  { course = c; }
    
    
    // equality rule
    public boolean equals(Object o)
    {
	Student other = (Student)o;
	// equality by name
	return this.getName().equalsIgnoreCase( other.getName() );
	//return this.getName().toLowerCase().equals( other.getName().toLowerCase() );
    }
    
    
    
    // hashcode based on the hashcode of name - optional but recommended	
    public int hashCode()
    {
	return this.getName().toLowerCase().hashCode();
    
        // if equality is based on name and score
        //return this.getName().toLowerCase().hashCode() + Double.hashCode(score);
    } 
    
}

// separate Comparator classes
class SortStudentByName implements Comparator
{
    public int compare(Object o1, Object o2)
    {
	Student s1 = (Student)o1;
	Student s2 = (Student)o2;
	return s1.getName().compareToIgnoreCase(s2.getName());		
	//return s1.getName().toLowerCase().compareTo( s2.getName().toLowerCase() );
    }
}

class SortStudentByScore implements Comparator<Student>
{
    public int compare(Student s1, Student s2)
    {
	if (s1.getScore() < s2.getScore())       return -1;
	else if (s1.getScore() > s2.getScore())  return 1;
	else return 0;
    }
}



