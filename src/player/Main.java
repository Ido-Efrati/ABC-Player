package player;

/**
 * Main entry point of your application.
 */
public class Main {

	/**
	 * Plays the input file using Java MIDI API and displays
	 * header information to the standard output stream.
	 * 
	 * <p>Your code <b>should not</b> exit the application abnormally using
	 * System.exit()</p>
	 * 
	 * @param file the name and the valid path of the input abc file
	 */
	public static void play(String file) {
	    SimpleReader sr = new SimpleReader();
	    String fileForLexer = sr.FileToString(file);
	    abcLexer lex = new abcLexer(fileForLexer);
	    abcParser parser = new abcParser(lex);
	    Piece piece = new Piece(parser.getVoice(),parser.getHeader(),
	            parser.hasDublet(),parser.hasTriplet(),parser.hasQuad(),parser.getDenoms());
	    piece.play();
	    
	    
	}
}
