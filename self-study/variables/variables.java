class variables{
    static int global_1 = 100;

    public static void main(String[] args){
        int age = 20;

        System.out.println("I am " + age + " years old.");      // println = print and auto \n for new line
        System.out.print("I am " + age + " years old.");        
        System.out.print("I am " + age + " years old.\n\n");        // print = print, will have to do \n maually
        System.out.println("I am " + global_1 + " years old.");
    }
}