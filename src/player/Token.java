package player;

/*
 * A token is a lexical item that the parser uses.
 * symbols: 
 * notes:A ,a , chords: [ABC] tuplet: (3ABC Bar: | , Rest: z repeat |: :| [2 [1
 */
public class Token {
    private final Type type;
    private final String value;
    
    public Token(Type t, String val){
        type = t;
        value = val;
    }
    
    public Type getType(){
        return type;
    }
    
    public String getVal(){
        return value;
    }
    
    
    /**
     * All the types of tokens that can be made.
     */
    public static enum Type {
        X_INDEX,
        T_TITLE,
        K_KEY,
        C_COMPOSER,
        M_METER,
        M_METER_CUT,
        L_NOTELENGTH,
        Q_TEMPO,
        NOTE, 
        CHORD_OPEN,
        CHORD_CLOSE,
        TUPLET,
        BAR,
        REST,
        REPEATSTART,
        REPEATEND,
        REPEAT1,
        REPEAT2,
        VOICE,
        MAJORSECTION,
        COMMENT,
        EOF
    }
}