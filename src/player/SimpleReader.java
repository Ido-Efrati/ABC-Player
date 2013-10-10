package player;
import java.io.*;

public class SimpleReader {
    private static BufferedReader b;
    /**
     * @param takes a valid ABCfile path
     * @return a string represntation of the abc file
     */
    public static String FileToString(String ABCfile) {
        String outValue = "";
        try {
            String eol = System.getProperty( "line.separator" );
            FileReader fin = new FileReader(ABCfile);
            b = new BufferedReader(fin);
            String currentLine; 
            while ((currentLine = b.readLine()) != null) {
                outValue= outValue + currentLine + eol;}
        }
        catch (FileNotFoundException ef) {
            System.out.println("File not found");}
        catch (IOException ei) {
            System.out.println("IO Exception"); }
        return outValue;
    }  
}

