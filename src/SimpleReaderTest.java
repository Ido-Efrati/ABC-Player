import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import org.junit.Test;
import player.SimpleReader;


public class SimpleReaderTest {
    /*
     * Testing includes: an invalid file path and a valid file path
     */
    @Test
    public void validFilePath(){
    //Check the new string that was just created
    SimpleReader sr = new SimpleReader();
    String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
    String toCompere="X:1\n"
+"T:Piece No.1\n"+
"M:4/4\n"+
"L:1/4\n"+
"Q:140\n"+
"K:C\n"+
"C C C3/4 D/4 E | E3/4 D/4 E3/4 F/4 G2 | (3c/2c/2c/2 (3G/2G/2G/2 (3E/2E/2E/2 (3C/2C/2C/2 | G3/4 F/4 E3/4 d/4 C\n";
    assertEquals(toCompere, fileForLexer);
    }
    
  @Test
  //Test an invalid file path
  public void invalidFilePath() throws FileNotFoundException{
      SimpleReader sr = new SimpleReader();
          String fileForLexer = sr.FileToString("No/suchFile.abc");
      }

}
