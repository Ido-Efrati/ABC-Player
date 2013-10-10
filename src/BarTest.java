import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import player.Bar;
import player.Header;
import player.Note;
import player.SimpleReader;
import player.abcLexer;
import player.abcParser;
import player.musicExpression;


public class BarTest {
    @Test
    public void barTest(){
    //Test that changeMusicExpression returns a valid bar with all of the notes
    Header h;
    SimpleReader sr = new SimpleReader();
    String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
    abcLexer lex = new abcLexer(fileForLexer);
    abcParser parser = new abcParser(lex);
    h=parser.parseHeader();
    ArrayList<musicExpression> expression = new ArrayList<musicExpression>();
    Note a = new Note("A");
    Note b = new Note("F");
    Note C = new Note("G");
    expression.add(a);
    expression.add(b);
    expression.add(C);
    Bar bar = new Bar(expression,h);
    assertEquals(expression,bar.changeMusicExpression());
    }
}
