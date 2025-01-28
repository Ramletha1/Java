import java.util.*;

// Abstract class
abstract class Shape {
    protected float area;

    protected abstract void draw(); // Abstract method (no implementation)
    protected abstract void calculate();

    public void displayAreaSize() {
        calculate();
        System.out.println("Area = " + area);
    }
}


// Subclass
class Circle extends Shape {
    public static final double pie = 3.14;
    protected float radius;

    public Circle(float r) {
        this.radius = r;
    }

    @Override
    protected void draw() {
        System.out.println("Drawing a Circle.");
    }

    @Override
    protected void calculate() {
        this.area = (float)pie*(radius*radius);
    }
}


// Subclass
class Rectangle extends Shape {
    protected float length, width;

    public Rectangle(float l, float w) {
        this.length = l;
        this.width = w;
    }

    @Override
    protected void draw() {
        System.out.println("Drawing a Rectangle.");
    }

    @Override
    protected void calculate() {
        this.area = length*width;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Circle or Rectangle? (C : R) ");
        while (true) {
            String option = userInput.next();
            if (option.equalsIgnoreCase("C")) {
                System.out.println("Radius: ");
                float radius;
                while (true) {
                    try {
                        radius = userInput.nextFloat();
                        break;
                    } catch (InputMismatchException e) { userInput.nextLine(); }
                }
                Shape circle = new Circle(radius);        // Upcasting    
                circle.draw(); circle.displayAreaSize();  
                break;    
            } else if (option.equalsIgnoreCase("R")) {
                System.out.println("Length: ");
                float length, width;
                while (true) {
                    try {
                        length = userInput.nextFloat();
                        break;
                    } catch (InputMismatchException e) { userInput.nextLine(); }
                }
                System.out.println("Width: ");
                while (true) {
                    try {
                        width = userInput.nextFloat();
                        break;
                    } catch (InputMismatchException e) { userInput.nextLine(); }
                }
                Shape rectangle = new Rectangle(length, width);          // Upcasting
                rectangle.draw(); rectangle.displayAreaSize();
                break;
            } else { continue; }
        }
    }
}
