import java.util.*;

class D6_1_DequePrimitive
{
    public static void main(String[] args) 
    {
	// ArrayDeque
	ArrayDeque<Double> AD1 = new ArrayDeque<Double>();
	AD1.add(100.5);  AD1.add(200.5);  AD1.add(300.5);  AD1.add(400.5); 
        
        ArrayDeque<Double> AD2 = new ArrayDeque<Double>(AD1);
        //ArrayDeque<Double> AD2 = (ArrayDeque<Double>) AD1.clone();
        AD1.add(500.5);
        AD2.add(600.5);

        // (1.1) Use ArrayDeque name
        //       - Primitive data : no need to do anything
        //       - Object : class must override method toString
        System.out.println("\n===== Access AD1 by implicit toString =====");
        System.out.println(AD1);
        
        // (1.2) Use for-each to access each member - no get(i) for ArrayDeque
        System.out.println("\n===== Access AD1 by for-each =====");
        for (double data : AD1)
            System.out.printf("%.2f  ", data);
        System.out.printf("\nEmpty ? %b \n", AD1.isEmpty());
        
        
	// (2) Use polling to simulate queue & stack
	Double data;
	System.out.println("\n===== Queue of AD1 (front to back) =====");
	while ( (data = AD1.pollFirst()) != null ) 
            System.out.printf("%.2f  ", data);
	System.out.printf("\nEmpty ? %b \n", AD1.isEmpty());

	System.out.println("\n===== Stack of AD2 (back to front) =====");
	while ( (data = AD2.pollLast()) != null ) 
            System.out.printf("%.2f  ", data);
	System.out.printf("\nEmpty ? %b \n", AD2.isEmpty());	
    }
}

