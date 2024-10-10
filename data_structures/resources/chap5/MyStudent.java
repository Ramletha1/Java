class MyStudent extends Student implements Comparable<MyStudent>
//class MyStudent extends Student implements Comparable
{	
    // additional variable in child class
    private int age;	

    public MyStudent(String n, double sc, int a)  
    {  super(n, sc);  age = a;  }
    
    public int getAge()         { return age; }

    // override method in parent
    public void print() 
    {  
	System.out.printf("%s : %s (%d years old), %.2f \n", 
		           course, getName(), age, getScore());  
    }

    // implement method in Comparable
    public int compareTo(MyStudent other)
    //public int compareTo(Object o)
    {
        //MyStudent other = (MyStudent)o;
	if (this.getScore() < other.getScore())		return -1;
	else if (this.getScore() > other.getScore())	return 1;
	else return 0;
    }
}

