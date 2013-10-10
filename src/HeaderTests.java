import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import player.*;


public class HeaderTests {
    //Testing will cover all fields in the header and will check for both required fields, missing fields and voices
    // In addition we will test an invaild header
    @Test
    public void validHeaderTest(){
    //test for a complete header with some missing values
    Header h;
    SimpleReader sr = new SimpleReader();
    String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
    abcLexer lex = new abcLexer(fileForLexer);
    abcParser parser = new abcParser(lex);
    h=parser.parseHeader();
    assertEquals(h.getIndex(),"1");
    assertEquals(h.getTitle(),"PieceNo.1");
    assertEquals(h.getComposer(),"Unknown");//uses a default value
    assertEquals(h.getIndex(),"1");
    assertEquals(h.getMeter(),"4/4");
    assertEquals(h.getNoteLength(),"1/4");
    assertEquals(h.getTempo(),"140");
    assertEquals(h.getKey(),"C");
    }
    
    @Test
    public void voicesAndvarMeterHeaderTest(){
    //test for a for a cut in the meter and voices in the header 
    Header h;
    SimpleReader sr = new SimpleReader();
    String fileForLexer = sr.FileToString("sample_abc/invention.abc");
    abcLexer lex = new abcLexer(fileForLexer);
    abcParser parser = new abcParser(lex);
    h=parser.parseHeader();
    assertEquals(h.getMeter(),"4/4");// this will test for M:C
    ArrayList<String> voice = new ArrayList<String>();
    voice.add("V:1");
    voice.add("V:2");
    assertEquals(h.getVoice(),voice);
    }
    
    @Test(expected=RuntimeException.class)
    public void missingPartOfHeader(){
    //test for a for a cut in the meter and voices in the header 
    Header h;
    SimpleReader sr = new SimpleReader();
    String fileForLexer = sr.FileToString("sample_abc/invalid_for_testing.abc");
    abcLexer lex = new abcLexer(fileForLexer);
    abcParser parser = new abcParser(lex);
    h=parser.parseHeader();}

}
