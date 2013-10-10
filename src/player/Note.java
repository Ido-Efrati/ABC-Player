package player;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import sound.Pitch;

public class Note implements musicExpression{
    private Pitch myPitch;
    private char myNote;
    private int myOctive, myAccidental; 
    private boolean hasAccidental = false;
    private double length = 1;
    private int denom=1;
    private String lengthToPrint;
    
    public Note(String note){
        myPitch = parseNote(note);
    }
    /**
     * there is a dictionary with all the accidentals and the octave changes that gives
     *  each character a value
     * @param note String of all the possible note values
     * @return the Pitch with the correct adjustments
     */
    private Pitch parseNote(String note){
        Pitch myPitch;
        char key = 'C'; 
        String noteLength = "";
        int accidental = 0;
        int octive = 0;
        char[] acceptableVals = {'_', '=', '^', ',', '\''};
        Dictionary d = new Hashtable();
        d.put('_', -1);
        d.put('=', 0);
        d.put('^', 1);
        d.put(',', -1);
        d.put('\'', 1);
        
        // iterate over all the individual characters in the string
        for(int i=0; i<note.length(); i++){
            char a = note.charAt(i);
            if (a=='_'|| a=='='|| a=='^'){
                hasAccidental = true;
                accidental+=(Integer)d.get(a);
            }else if (a==','|| a=='\''){ 
                octive += (Integer)d.get(a);
            }else if (a>='a' && a<='g'){   
                key = Character.toUpperCase(a); // Pitch needs capital characters
                myNote = key;
                octive +=1;
            }else if (a>='A' && a<='G'){
                key = a;
                myNote = key;
            }else{ // every other character must deal with the note length
                noteLength+=a;
            }
        }
        
        lengthToPrint = noteLength;
        length= setLength(noteLength);
        myAccidental = accidental;
        myOctive = octive;

        myPitch= new Pitch(key).octaveTranspose(octive).accidentalTranspose(accidental);
        return myPitch;
    }
    /**
     * passes in noteLength, the part of the string after the note and any octave shifts
     * sets the length of the note, the default value is 1 
     * @param noteLength
     * @return length of the note
     */
    private double setLength(String noteLength){
        double legnthValue=1;
        //if there is a number or "/" then we override the default value of 1
        if (noteLength.length()>0){
            if (noteLength.charAt(0) == '/'){ // if the first character is a "/" then the it is 1/(next number)
                if (noteLength.length()>1){
                    if (noteLength.length()>1){
                        denom = Integer.parseInt(noteLength.substring(1));
                        legnthValue = 1/(double)denom;
                    }  
                }      
            }else{ // must be either a whole number or a fraction
                String[] parseNoteLength = noteLength.split("/");
                if (parseNoteLength.length==2){
                    denom = Integer.parseInt(parseNoteLength[1]);
                    legnthValue = Integer.parseInt(parseNoteLength[0])/(double)denom;
                }else{
                    legnthValue = Integer.parseInt(parseNoteLength[0]);
                }
            }
        }
        return legnthValue;
    }
    
    public List<Pitch> getPitchList(){
        List<Pitch> pitch =  new ArrayList<Pitch>();
        pitch.add(new Pitch(myNote).octaveTranspose(myOctive).accidentalTranspose(myAccidental));
        return pitch;
    }
    
    public char getNote(){
        return myNote;
    }
    
    public List<Character> getNoteList(){
        List<Character> noteList =  new ArrayList<Character>();
        noteList.add(myNote);
        return noteList;
    }
    
    public Pitch getPitch(){
        return new Pitch(myNote).octaveTranspose(myOctive).accidentalTranspose(myAccidental);
    }
    
    public double getLength(){
        return length;
    }
    
    public int getDenom(){
        return denom;
    }
    /**
     * it is given the dictionary. if the note has already been modified at the constructor
     * ignore the original value of the dictionary and reset the value to affect future notes
     * else it changes the note according to the value in the dictionary 
     * @param d the dictionary of the bar and notes to change
     */
    public void changePitch(Dictionary<Character, Integer> d) {
        if (!hasAccidental() && d.get(getNote()) != null) {
            myPitch.accidentalTranspose(d.get(getNote()));
            myAccidental +=d.get(getNote());
            hasAccidental = true;
        }
        if (hasAccidental()) {
            d.put(myNote, myAccidental);
        }
    }

    @Override
    public String getType() {
        return "Note";
    }

    public String toString() {
        return getPitch().toString() + lengthToPrint;
    }

    public boolean hasAccidental() {
        return hasAccidental;
    }

}
