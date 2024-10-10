import java.util.*;

class Student implements Comparable<Student>
{	
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
        System.out.printf("%s, %.2f \n", name, score);  
    }    
    
    @Override
    public String toString()
    {  
        String s = String.format("(%s, %.2f)", name, score); 
        return s;
    }    
        
    // equality rule
    public boolean equals(Object o)
    {
	Student other = (Student)o;
	return this.getName().equalsIgnoreCase( other.getName() );
    }
    
    // hashcode based on the hashcode of name - optional but recommended	
    public int hashCode()
    {
	return this.getName().toLowerCase().hashCode();
    } 
    
    public int compareTo(Student other)
    {
        return name.compareToIgnoreCase(other.name);
        //return Double.compare(score, other.score);            
    }
}

// separate Comparator , 
class SortStudentByName implements Comparator<Student>
{
    public int compare(Student s1, Student s2)
    {
	return s1.getName().compareToIgnoreCase(s2.getName());		
    }
}

class SortStudentByScore implements Comparator<Student>
{
    public int compare(Student s1, Student s2)
    {
	//if (s1.getScore() < s2.getScore())       return -1;
	//else if (s1.getScore() > s2.getScore())  return 1;
	//else return 0;
        
        // score is double, but return value must be int
        // Be careful with type casting when diff < 1
        //return (int)(s1.getScore() - s2.getScore());
        
        return Double.compare(s1.getScore(), s2.getScore());        
    }
}