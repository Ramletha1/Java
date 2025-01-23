import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {

    class Parent {

        public String name = "Hello";

        public Parent() {
            System.out.println("My name is parent!");
        }
    }

    class Child extends Parent {

        public int childCount = 0;
        public String childName;

        public Child(String name) {
            childName = name;
        }

        public void print() {
            System.out.println("Child!");
            System.out.println("Raised by " + name);
        }
    }

    public static void main(String[] args) {
        Child child = new Child("hello");
        child.print();
    }
}