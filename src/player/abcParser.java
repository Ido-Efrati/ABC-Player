package player;
import java.util.ArrayList;
import player.Token.Type;

public class abcParser {
    private abcLexer lex;
    private Token tok;
    private Header header;
    private ArrayList<Voice> voices = new ArrayList<Voice>();
    private Voice oneVoice;
    private boolean hasVoices = false;
    private boolean hasDublet = false;
    private boolean hasTriplet = false;
    private boolean hasQuad = false;
    private ArrayList<Integer> denoms = new ArrayList<Integer>();

    /**
     * Creates a new parser over the lexer.  This parser will use the passed
     * lexer to get tokens--which it will then parse.
     * @param lexer The lexer.
     */
    
    public abcParser(abcLexer lexer) {
        lex = lexer;
    }
    //First parse the header to figure out if we have voices or not
    //afterward parse the body based on the voices
    /**
     * will parse both the header and the body
     */
    public void parse(){
       header = parseHeader();
       parseVoice();
    }
    /**
     * a get method for the header
     * @return the header
     */
    public Header getHeader(){
        return header;
    }
    /**
     * a get method for the voices
     * @return array list of voices
     */
    public ArrayList<Voice> getVoice(){
        return voices;
    }
    
    //set of helper functions to figure out timing base on the type of tuplets
    public boolean hasDublet(){
        return hasDublet;
    }
    public boolean hasTriplet(){
        return hasTriplet;
    }
    public boolean hasQuad(){
        return hasQuad;
    }
    /**
     * get the denominators of the notes
     * @return array list of denominators 
     */
    
    public ArrayList<Integer> getDenoms(){
        return denoms;
    }
    
    /**
     * a method to parse just the header of the piece
     * @return the header of the piece
     */
    public Header parseHeader(){
        ArrayList<Token> header = new ArrayList<Token>();
        tok = lex.next();
        header.add(tok);
        //keep parsing head till we parse the key
        while (!tok.getType().equals(Type.K_KEY)){
            tok = lex.next();
            header.add(tok);
        }        
        return new Header(header);
    }
    /**
     * create a list of all of the voices and will start parsing the body afterward
     */
    public void parseVoice(){
        if (header.getVoice().size()==0){ //if we only have one voice
        }else{ // multiple voices
            hasVoices = true;
            for(String voice : header.getVoice()){
                Voice a = new Voice(new ArrayList<Bar>(),voice);
                voices.add(a);
            }
        }
        parseBody();
    }
    /**
     * adds bars to the appropriate voice 
     */
    public void parseBody(){
        if (hasVoices){
            tok = lex.next();
            while (!tok.getType().equals(Token.Type.EOF)){
                for(Voice v: voices){
                    if (tok.getVal().equals(v.getName())){
                        ArrayList<Bar> majorBars = parseMajorSection();
                        for (Bar b : majorBars){
                            v.addBar(b);
                        }
                        if (tok.getType().equals(Token.Type.MAJORSECTION)){
                            tok = lex.next();
                        }
                    }
                }
            }
        }else{
            //In case we don't have any voices create just one voice
            ArrayList<Bar> allBars = new ArrayList<Bar>();
            while (!tok.getType().equals(Token.Type.EOF)){
                ArrayList<Bar> majorBars = parseMajorSection();
                for (Bar b: majorBars){
                    allBars.add(b);
                }
                if (tok.getType().equals(Token.Type.MAJORSECTION)){
                    tok = lex.next();
                }
            }
            oneVoice = new Voice(allBars, "oneVoice");
            voices.add(oneVoice);
        }
    }
    /**
     * parses each major section or the entire piece if there are no major sections
     * @return an array list of bars to store in the voice
     */
    public ArrayList<Bar> parseMajorSection() {
        int repeatStart = 0;
        int repeatEnd = 0;
        boolean repeatNums = false;
        boolean repeat = false;
        ArrayList<Bar> myMajorSection = new ArrayList<Bar>();
        
        tok = lex.next();

        while (!tok.getType().equals(Token.Type.MAJORSECTION)&&(!tok.getType().equals(Token.Type.EOF))
                &&!tok.getType().equals(Token.Type.VOICE)) {
            ArrayList<musicExpression> bar = new ArrayList<musicExpression>();
            while (!repeat&&!tok.getType().equals(Token.Type.BAR)&&!tok.getType().equals(Token.Type.EOF)
                    &&!tok.getType().equals(Token.Type.MAJORSECTION)) {
                if (tok.getType().equals(Token.Type.NOTE) || tok.getType().equals(Token.Type.REST)) {
                    bar.add(parseBaseCase());
                }else if (tok.getType().equals(Token.Type.CHORD_OPEN)) {
                    tok = lex.next();
                    ArrayList<musicExpression> chord = new ArrayList<musicExpression>();
                    while (!tok.getType().equals(Token.Type.CHORD_CLOSE)) {
                        chord.add(parseBaseCase());
                    }
                    Chord c = new Chord(chord, tok.getVal());
                    bar.add(c);
                    tok = lex.next();
                    int i = c.getDenom();
                    if (!denoms.contains(i)){
                        denoms.add(i);
                    }
                } else if (tok.getType().equals(Token.Type.TUPLET)) {
                    String length = tok.getVal();
                    ArrayList<musicExpression> tuplet = new ArrayList<musicExpression>();
                    int i = Integer.parseInt(length.substring(1));
                    if (i==2){
                        hasDublet = true;
                    }else if (i==3){
                        hasTriplet = true;
                    }else if (i == 4){
                        hasQuad = true;
                    }else{
                        throw new RuntimeException("invalid number for tuplet");
                    }
                    tok = lex.next();
                    for (int j = 0; j < i; j++) {
                        tuplet.add(parseBaseCase());
                    }
                    Tuplet t = new Tuplet(tuplet, length);
                    bar.add(t);
                }
                else if(tok.getType().equals(Token.Type.REPEATSTART)){
                    //reset the starting location of the repeat 
                    repeatStart = myMajorSection.size()-1;
                    tok = lex.next();
                }
                else if(tok.getType().equals(Token.Type.REPEATEND)){
                    repeat = true;
                    //when the repeat is over add the existing bar
                    myMajorSection.add(new Bar(bar, header));                   
                    if (repeatNums){
                        //duplicate the bar that was just added
                        for (int i= repeatStart; i<=repeatEnd; i++){
                            Bar b = myMajorSection.get(i);
                            //add existing bar again to the major section
                            //should not be a problem of mutation because 
                            //our bar won't do anything if we try to mutate again later on
                            myMajorSection.add(b);
                        }
                        tok = lex.next();
                        repeatNums = false;
                    }
                    else{
                        // set the end of the repeat- this will allow to skip over a section
                        // if we have repeat 1 and repeat 2
                        repeatEnd =  myMajorSection.size()-1;
                        for (int i= repeatStart; i<=repeatEnd; i++){
                            myMajorSection.add(myMajorSection.get(i));
                        }
                        tok = lex.next();
                    }
                    repeatStart = myMajorSection.size()-1;
                }
                else if(tok.getType().equals(Token.Type.REPEAT1)){
                    repeatEnd = myMajorSection.size()-1;
                    repeatNums = true;
                    tok = lex.next();
                }else if(tok.getType().equals(Token.Type.REPEAT2)){
                    tok = lex.next();
                }
            }
            if (repeat){
                repeat = false;
            }else{            
                myMajorSection.add(new Bar(bar, header));
                if (!tok.getType().equals(Token.Type.MAJORSECTION)){
                    tok = lex.next();
                }
            }
        }
        return myMajorSection;
    }
    
    /**
     * Will parse either a note or a rest
     * @return a note or a rest that will go to the appropriate location
     */
    public musicExpression parseBaseCase() {
        if (tok.getType().equals(Token.Type.NOTE)) {
           Note note = new Note(tok.getVal());
           int i = note.getDenom();
           if (!denoms.contains(i)){
               denoms.add(i);
           }
           tok= lex.next();
           return note;
        }
        
        if (tok.getType().equals(Token.Type.REST)) {
            Rest rest =new Rest(tok.getVal());
            tok= lex.next();
            int i = rest.getDenom();
            if (!denoms.contains(i)){
                denoms.add(i);
            }
            return rest;
        }  
        throw new RuntimeException("This is an Exception if we did not match any type of tokens");
    }
}