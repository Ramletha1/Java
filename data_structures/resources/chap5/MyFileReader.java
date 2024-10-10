import java.util.*;
import java.io.*;

public class MyFileReader 
{
    private String            path, fileName;
    private Scanner           keyboardScan;
    private ArrayList<String> allLines;
    
    public MyFileReader(String p, String fn)
    {
        path     = p;
        fileName = fn;
        keyboardScan = new Scanner(System.in);
        allLines = new ArrayList<String>();
    }
    
    public void readFile()
    {        
        boolean opensuccess = false;
        while (!opensuccess)
        {
            try (
                Scanner fileScan = new Scanner(new File(path + fileName));
            ){
                opensuccess = true;                
                while(fileScan.hasNextLine())  
                { 
                    allLines.add( fileScan.nextLine() );
                }
            }
            catch (FileNotFoundException e) 
            {
                System.out.print(e + " --> ");
                System.out.println("New file name = ");
                fileName = keyboardScan.next();
            }
        }        
    }
    
    public ArrayList<String> getLines()  
    {
        return allLines;
    }
}
