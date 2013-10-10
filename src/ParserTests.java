import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import player.Bar;
import player.Header;
import player.SimpleReader;
import player.Voice;
import player.abcLexer;
import player.abcParser;
import player.musicExpression;

/**
 * testing incrementally more and more
 * first test if header is parsed correctly, then a simple piece, then a larger piece
 * check all the throws exceptions actually throw an expression
 * check if correctly implementing other methods inside parser
 * @author Yuxin Jing
 *
 */
public class ParserTests {
    @Test
    public void parseHeader(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        
        Header h = p.parseHeader();
        String X_Index = h.getIndex();
        String T_Title = h.getTitle();
        String K_Key = h.getKey();
        String C_Composer = h.getComposer();
        String Q_Tempo = h.getTempo();
        String L_NoteLength = h.getNoteLength();
        String M_Meter = h.getMeter();
        
        assertEquals(X_Index,"1");
        assertEquals(T_Title,"Piece No.1");
        assertEquals(K_Key,"C");
        assertEquals(C_Composer,"Unknown");
        assertEquals(Q_Tempo,"140");
        assertEquals(L_NoteLength,"1/4");
        assertEquals(M_Meter,"4/4");
    }
    @Test
    public void basics2(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        p.parse();
        ArrayList<Voice> oneVoice = p.getVoice();
        assertEquals(oneVoice.size(), 1);
        ArrayList<Bar> bars = oneVoice.get(0).getBars();
        assertEquals(bars.size(), 4);
        String[] expectedOutput = { "CCC3/4D/4E",
                                    "E3/4D/4E3/4F/4G2",
                                    "(3c/2c/2c/2(3G/2G/2G/2(3E/2E/2E/2(3C/2C/2C/2",
                                    "G3/4F/4E3/4d/4C2"};
        int i = 0;
        for (Bar b: bars){
            String barString = b.toString();
            assertEquals(expectedOutput[i], barString);
            i++;
        }
    }
    
    @Test
    public void basics3(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = sr.FileToString("sample_abc/paddy.abc");
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        p.parse();
    }
    
    @Test
    public void basics4(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = sr.FileToString("sample_abc/paddy.abc");
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        p.parse();
    }
    
    @Test(expected=RuntimeException.class)
    public void invalidTuple(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = "(5eee";
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        p.parseMajorSection();
    }
    @Test(expected=RuntimeException.class)
    public void invalidChord(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = "[]";
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        p.parseMajorSection();
    }
    
    @Test
    public void checkDenominatorAndTuples(){
        SimpleReader sr = new SimpleReader();
        String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
        abcLexer lex = new abcLexer(fileForLexer);
        abcParser p = new abcParser(lex);
        p.parse();
        ArrayList<Integer> a = p.getDenoms();
        System.out.println(a);
        Integer[] expectedOutput = {1, 4, 2};
        assertArrayEquals(expectedOutput, a.toArray(new Integer[a.size()]));
        assertEquals(p.hasDublet(), false);
        assertEquals(p.hasTriplet(), true);
        assertEquals(p.hasQuad(), false);
    }

}