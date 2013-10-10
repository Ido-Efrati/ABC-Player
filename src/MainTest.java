import org.junit.Test;

import player.Piece;
import player.SimpleReader;
import player.abcLexer;
import player.abcParser;


public class MainTest {
@Test
public void Test1(){
    SimpleReader sr = new SimpleReader();
    String fileForLexer = sr.FileToString("sample_abc/piece1.abc");
    abcLexer lex = new abcLexer(fileForLexer);
    abcParser parser = new abcParser(lex);
    parser.parse();
    Piece piece = new Piece(parser.getVoice(),parser.getHeader(),
            parser.hasDublet(),parser.hasTriplet(),parser.hasQuad(),parser.getDenoms());
    piece.play();

}
}
