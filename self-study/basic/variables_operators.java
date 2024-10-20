public class variables_operators {
    static int global_1 = 100;

    public static void main(String[] args){
        // integer types
        byte aSingleByte = 100; // -128 to 127
        short aSmallNumber = 20000; // -32,768 to 32,767
        int anInteger = 2147483647; // -2147483648 to 2147483647
        long aLargeNumber = 9223372036854775807L; // -9223372036854775808 to 9223372036854775807
        
        // decimal type
        double aDouble = 1.79769313; // 4.9E-324 to 1.7976931348623157E308
        float aFloat = 3.4028F; // 1.4E-45 to 3.4028235E38

        // booleans
        boolean isWeekend = false;
        boolean isWorkday = true;

        // characters
        char copyrightSymbol = '\u00A9';
        char percentSymbol = '%';
        System.out.println("\nPart 1");
        System.out.println("This is the copy right symbol = " + copyrightSymbol);
        System.out.println("This is the percent symbol = " + percentSymbol);

        
        System.out.println("\nPart 2");
        int age = 20;
        System.out.println("I am " + age + " years old.");          // println = print and auto \n for new line
        System.out.print("I am " + age + " years old.");        
        System.out.print("I am " + age + " years old.\n");        // print = print, will have to do \n maually
        System.out.println("I am " + global_1 + " years old.");     // global_1 is declared OUTSIDE this main() function


        // changing variable's type
        System.out.println("\nPart 3");
        int num1 = 5;
        double num2 = num1;  // Possible
        double num3 = 10.5;
        int num4 = (int)num3; // Must include (int) to let system know that you want to convert anyway or else will tell error

        System.out.println("num1 is now = " + num2 + "\nnum3 is now = " + num4);
        System.out.println(num3 + " " + num1);


        // basic mathematics
        System.out.println("\nPart 4");
        num1 = 3;
        num2 = 2;
        System.out.print("");
        System.out.println(num1 + num2);
        System.out.println(num2 - num1);
        System.out.println(num1 * num2);
        System.out.println(6 / num2);
        System.out.println((num1*6)%4);


        // True & False
        System.out.println("\nPart 5");
        num1 = 12;
        num2 = 15;
        System.out.println(num1 == num2);   // 12 == 15 ? F
        System.out.println(num1 != num2);   // 12 != 15 ? T
        System.out.println(num1 > num2);    // 12 > 15 ?  F
        System.out.println(num1 < num2);    // 12 < 15 ?  T
        System.out.println(12 <= 12);       // 12 <= 12 ? T
        System.out.println(12 >= 15);       // 12 >= 15 ? F
        // EXACTLY THE SAME AS:
        // if, else if, for, while in C++ programming.


        // More True & False
        System.out.println("\nPart 6");
        age = 25;
        System.out.println(age >= 18 && age <= 40); // and
        System.out.println(age > 30 && age < 30);   // and
        System.out.println(age > 30 || age < 30);   // or


        // True & False but with 1 and 0 (True and False)
        System.out.println("\nPart 7");
        boolean isStudent = false;
        boolean isLibraryMember = true;
        System.out.println(isStudent || isLibraryMember);   // or
        System.out.println(isStudent && isLibraryMember);   // and
        System.out.println(!isStudent);     // Inverse
        isStudent = !isStudent;
        System.out.println(isStudent && isLibraryMember);
        
        
        // ++x will instandly +1 and print
        // but x++ will print first and +1 later
        System.out.println("\nPart 8");
        int x = 10;
        System.out.println("X = 10");
        System.out.println("++X = " + ++x);
        System.out.println("X = " + x);
        System.out.println("--X = " + --x);
        System.out.println("X = " + x);
        
        System.out.println("---------------");
        x = 20;
        System.out.println("Y = 20");
        System.out.println("Y++ = " + x++);
        System.out.println("Y = " + x);
        System.out.println("Y-- = " + x--);
        System.out.println("Y = " + x);


        // different between String with "new String" and without
        System.out.println("\nPart 9");
        String literalString1 = "abc";
        String literalString2 = "abc";

        String objectString1 = new String("xyz");
        String objectString2 = new String("xyz");
        
        System.out.println(literalString1 == literalString2);   // Check letter/string
        System.out.println(objectString1 == objectString2);     // Check variable name


        System.out.println("\nPart 10");
        System.out.println("Hello World! I am Wongsatorn Suwannarit. I am from USA and I am 69 years old.");
        String name = "Wongsatorn Suwannarit";  // %s
        String country = "USA";     // %s
        age = 69;   // %d
        System.out.println("Hello World! I am " + name + ". I am from " + country + " and I am " + age + " years old.");
        String company = "Toilet Co,";
        System.out.println("I work at " + company + ".");
        System.out.println("--------------------");

        String formattedString = String.format("My name is %s. I am from %s. I am %d years old. I work at %s.", name,country,age,company);
        System.out.println(formattedString);

        double GPA = 3.78;      // %f
        formattedString = String.format(formattedString + " My GPA is %.2f.", GPA);
        System.out.println(formattedString);

        char percentSign = '%';     // %b
        boolean truth = false;
        formattedString = String.format(formattedString + "My attendent %c is 100.\nAm I telling the truth? %b", percentSign,truth);
        System.out.println(formattedString);


        System.out.println("\nPart 11");
        name = "Wongsatorn Suwannarit";
        System.out.println(name);
        System.out.println("Length is " + name.length());
        System.out.println("Is empty? " + name.isEmpty());
        name = "";
        System.out.println("Name removed");
        System.out.println("Is empty? " + name.isEmpty());
        System.out.println("-------------");
        name = "Wongsatorn Suwannarit";
        System.out.println(name.toUpperCase());
        System.out.println(name);
        System.out.println(name.toLowerCase());


        System.out.println("\nPart 12");
        String str1 = new String("abc");
        String str2 = new String("abc");
        String str3 = new String("ABC");

        System.out.println(str1 == str2);       // Check declared name
        System.out.println(str1.equals(str2));  // Check letter
        System.out.println(str1.equals(str3));
        System.out.println(str1.equalsIgnoreCase(str3));


        System.out.println("\nPart 13");
        String string = "The sky is blue.";
        System.out.println(string);
        System.out.println(string.replace("blue","red"));

        string = "That tall guy";
        System.out.println(string);
        String updatedString = string.replace("tall","short");
        System.out.println(updatedString);
        System.out.println(string.contains("You")); // Check letter in that string
    }
}