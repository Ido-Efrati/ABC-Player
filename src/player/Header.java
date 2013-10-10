package player;
import java.util.*;
import player.Token.Type;
/* The parser will give the header class an array list and the header will evaluate the array list
 and will set the required values. Those values will be accessible to the piece that will use it
 before playing the abc file.
 */ 
public class Header {
    private String X_Index = null;
    private String T_Title = null;
    private String K_Key = null;
    private String C_Composer = "Unknown";
    private String Q_Tempo = "100";
    private String L_NoteLength = "1/8";
    private String M_Meter = "4/4";
    private ArrayList<String> voiceList = new ArrayList<String>();
    private ArrayList<Token> header = new ArrayList<Token>();
    private Dictionary<String, ArrayList<String>> d = new Hashtable<String, ArrayList<String>>();


    public Header(ArrayList<Token> headerArray) {
        setKeyDictionary();
        header = headerArray;
        SetValues(header);
        isValidHeader();
    }
    // a private setter method to override default values and to check if X,T and K were given
    // This is a private method to prevent mutations 
    /**
     * Get the ArrayList in the constructor  
     * @param header
     * Set all of the mandatory values, and override default ones
     */
    private void SetValues(ArrayList<Token> header) {
        for (Token tok : header) {
            if (tok.getType().equals(Type.X_INDEX)) {
                X_Index = tok.getVal().replaceAll(" ", "").substring(2);
            }
            else if (tok.getType().equals(Type.T_TITLE)) {
                T_Title = tok.getVal().substring(2);
            }
            else if (tok.getType().equals(Type.K_KEY)) {
                K_Key = tok.getVal().replaceAll(" ", "").substring(2);
            }
            else if (tok.getType().equals(Type.C_COMPOSER)) {
                C_Composer = tok.getVal().substring(2);
            }
            else if (tok.getType().equals(Type.Q_TEMPO)) {
                Q_Tempo = tok.getVal().replaceAll(" ", "").substring(2);
            }
            else if (tok.getType().equals(Type.L_NOTELENGTH)) {
                L_NoteLength = tok.getVal().replaceAll(" ", "").substring(2);
            }
            else if (tok.getType().equals(Type.M_METER)) {
                M_Meter = tok.getVal().replaceAll(" ", "").substring(2);
            }
            else if(tok.getType().equals(Type.M_METER_CUT)){
                String cutValue= tok.getVal().replaceAll(" ", "").substring(2);
                if (cutValue.equals("C")){
                    M_Meter="4/4";
                    
                }
                else if(cutValue.equals("C|")){
                    M_Meter="2/2";
                }
                else{
                    throw new RuntimeException("invalid cut value");
                }
            }
            else if (tok.getType().equals(Type.VOICE)){
                voiceList.add(tok.getVal());
            }}}
            private void isValidHeader(){
            if (X_Index == null || T_Title == null || K_Key == null) {
                throw new RuntimeException(
                        "Missing one of the mandatory header's fields");
            }
    }
    /**
     * creat a hash table for each key and the appropriate note it should change
     * this would be use in changing the notes
     */
    private void setKeyDictionary(){
        d.put("A", new ArrayList<String>(Arrays.asList("^C","^F","^G")));
        d.put("Am", new ArrayList<String>());
        d.put("B", new ArrayList<String>(Arrays.asList("^C","^D","^F","^G","^A")));
        d.put("bm", new ArrayList<String>(Arrays.asList("^F","^C")));
        d.put("C", new ArrayList<String>());
        d.put("Cm", new ArrayList<String>(Arrays.asList("_E","_A","_B")));
        d.put("D", new ArrayList<String>(Arrays.asList("^F","^C")));
        d.put("Dm", new ArrayList<String>(Arrays.asList("_B")));
        d.put("E", new ArrayList<String>(Arrays.asList("^F","^G","^C","^D")));
        d.put("Em", new ArrayList<String>(Arrays.asList("^F")));
        d.put("F", new ArrayList<String>(Arrays.asList("_B")));
        d.put("Fm", new ArrayList<String>(Arrays.asList("_A","_B","_D","_E")));
        d.put("G", new ArrayList<String>(Arrays.asList("^F")));
        d.put("Gm", new ArrayList<String>(Arrays.asList("_B,_E")));
    }

    // Getters for all of the fields
    /**
     * getter for the piece index
     * 
     * @return String
     */
    public String getIndex() {
        return X_Index;
    }

    /**
     * getter for the piece title
     * 
     * @return String
     */
    public String getTitle() {
        return T_Title;
    }

    /**
     * getter for the piece key
     * 
     * @return String
     */
    public String getKey() {
        return K_Key;
    }

    /**
     * getter for the piece Composer
     * 
     * @return String
     */
    public String getComposer() {
        return C_Composer;
    }

    /**
     * getter for the piece Tempo
     * 
     * @return String
     */
    public String getTempo() {
        return Q_Tempo;
    }

    /**
     * getter for the piece Note Length
     * 
     * @return String
     */
    public String getNoteLength() {
        return L_NoteLength;
    }

    /**
     * getter for the piece Meter
     * 
     * @return String
     */
    public String getMeter() {
        return M_Meter;
    }
    /**
     * getter for the voices
     * @return a list of all the voices
     */
    public ArrayList<String> getVoice(){
        return voiceList;
    }
    
    public ArrayList<String> getValueOfKey(String key){
        return d.get(key);
    }

}
