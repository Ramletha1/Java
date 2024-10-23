// https://youtu.be/H62Jfv1DJlU?si=QDxCN1X4UOZjCa-7

import java.util.HashMap;

public class hashmap {
    public static void main(String[] args) {
        
        HashMap<String, Integer> empIds = new HashMap</*String, Integer*/>();   // empIds = Employee IDs (name, id)

        empIds.put("John", 12345);
        empIds.put("Carl", 54321);
        empIds.put("Jerry", 8675309);

        System.out.println(empIds);

        System.out.println(empIds.get("Carl"));

        System.out.println(empIds.containsKey("Jerry"));
        System.out.println(empIds.containsKey("George"));

        System.out.println(empIds.containsValue(8675309));
        System.out.println(empIds.containsValue(6));

        empIds.put("John", 98765);      // create new data, but will replace if already exists
        System.out.println(empIds);

        empIds.replace("Kramer", 777);  // change/update the existing data, do nothing if doesnt exists
        System.out.println(empIds);

        empIds.putIfAbsent("Steve", 222); // create new data if doesnt exist, do nothing if already exists
        System.out.println(empIds);

        empIds.remove("Steve");
        System.out.println(empIds);
    }
}