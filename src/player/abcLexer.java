package player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import player.Token.Type;

/**
 * A lexer takes a string and splits it into tokens that are meaningful to a
 * parser.
 */
public class abcLexer {
    /**
     * Creates the lexer over the passed string.
     * 
     * @param string
     *            The string to tokenize.
     */

    private String myString;
    private final Matcher matcher;
    private int i = 0;

    // Regex matching the next token for all of the valid strings
    private static final Pattern TOKEN_REGEX = Pattern.compile(
    // Header pattern
                    "(X\\:[\\s]*[1-9]+)" // X_INDEX
                    + "|" + "(T\\:[^\\r\\n]*)" // T_TITLE
                    + "|" + "(K\\:[\\s]*[A-G][m]?)" // K_KEY
                    + "|" + "(C\\:[^\\r\\n]*)" // C_COMPOSER
                    + "|" + "(M\\:[\\s]*[1-9]+[\\/][1-9]+)" // M_METER
                    + "|" + "(M\\:[\\s]*C\\|?)" // M_METER_CUT
                    + "|" + "(L\\:[\\s]*[1-9]+[\\/][1-9]+)" // L_NOTELENGTH
                    + "|" + "(Q\\:[\\s]*[1-9]+[0-9]*)" // Q_TEMPO

                    // Body pattern
                    + "|"
                    +"([\\^\\_\\=]{0,2}[a-gA-G][\\']*[\\,]*[1-9]*[\\/]?[1-9]*)"//Note
                    // ' is octave up , is octave down//
                    + "|" + "([\\(][2-4])" // TUPLET
                    + "|" + "([z][1-9]*[\\/]?[1-9]*)" // REST
                    + "|" + "(\\[[1])" // REPEAT1
                    + "|" + "(\\[[2])" // REPEAT2
                    + "|" + "(\\[)"// CHORDS_OPEN
                    + "|" + "(\\][1-9]*[\\/]?[1-9]*)"// CHORDS_CLOSE
                    + "|" + "(\\:\\|)" // REPEATEND
                    + "|" + "(\\|\\:)" // REPEATSTART
                    + "|" + "(\\|\\])" //Major section
                    + "|" + "(\\|)" // BAR 
                    + "|" + "(V\\:[^\\r\\n]*)" // Voice
                    + "|" + "(%\\:[\\s]*[^\\r\\n]*)" // COMMENT
                    
            );
    private static final Type[] TOKEN_TYPES = { 
            Type.X_INDEX, 
            Type.T_TITLE,
            Type.K_KEY, 
            Type.C_COMPOSER,
            Type.M_METER, 
            Type.M_METER_CUT,
            Type.L_NOTELENGTH,
            Type.Q_TEMPO,
            Type.NOTE,
            Type.TUPLET,
            Type.REST,
            Type.REPEAT1,
            Type.REPEAT2,
            Type.CHORD_OPEN,
            Type.CHORD_CLOSE,
            Type.REPEATEND,
            Type.REPEATSTART,
            Type.MAJORSECTION,
            Type.BAR,
            Type.VOICE,
            Type.COMMENT
            };

    /**
     * abcLexer will take a string and return a token.
     * 
     * @param string
     *            The string to create tokens for.
     * @return token to give to Parser
     */
    public abcLexer(String string) {
        myString = string;
        matcher = TOKEN_REGEX.matcher(myString);
    }

    /**
     * to evaluate the next type recognized by the regex at every time, the
     * substring that the matcher looks for starts at the end of the last token
     * it found
     * 
     * @return Token, the token that is found next if it is not an incorrect
     *         input
     */
    public Token next() {
        // when you reach the end of the file
        // We put +1 because the last char is an end of line and in order to actually create EOF
        // token we need to shift this by 1
        if (i+1 >= myString.length()) {
            return new Token(Type.EOF, "");
        }else if (i+2 >= myString.length()&&(int)myString.charAt(i+1)==10){//EOF support for windows
            return new Token(Type.EOF, "");
        }
        //
        // look for next token at starting at the index i
        if (!matcher.find(i)) { // No token found
            throw new RuntimeException("cannot find token "
                    + myString.substring(i) + " is not a valid token");
        }

        // Get the part of the string that the regex matched,
        // and advance our state
        String value = matcher.group(0);
        i = matcher.end();
        
        // check to see what type of token the one found is
        // return a new Token
        for (int i = 1; i <= matcher.groupCount(); i++) {
            if (matcher.group(i) != null) {
                return new Token(TOKEN_TYPES[i - 1], value);
            }
        }

        // if we got here, there's a bug
        throw new AssertionError("shouldn't get here");
    }
}