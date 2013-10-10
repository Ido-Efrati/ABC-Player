package player;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import sound.Pitch;


public class Chord implements musicExpression {
    private double chordLength=1;
    private ArrayList<musicExpression> chord = new ArrayList<musicExpression>();
    int denom=1;

    public Chord(ArrayList<musicExpression> chord2, String length) {
        chord= chord2;
        chordLength=1; // chord will not have a length only notes will have ones
    }
    public int getDenom(){
        denom*=((Note) chord.get(0)).getDenom();
        return denom;
    }
    @Override
    /**
     * return a the chord length
     */
    public double getLength() {
        return chordLength;
    }
   

    /**
     * @param c a list of all the notes in the chord
     * @return a list of all the notes' pitches
     */
    private List<Pitch> createPitchList(ArrayList<musicExpression> chord2){
        ArrayList<Pitch> chordPitch = new ArrayList<Pitch>();
        for(musicExpression note : chord2){
            if(note.getType().equals("Note")){
            chordPitch.add(((Note) note).getPitch());}
            
        }
        return chordPitch;
    }
   
    /**
     * @param c a list of all the notes in the chord
     * @return a list of all the note's chars.
     */
    private ArrayList<Character> createNotesList(ArrayList<musicExpression> chord2){
        ArrayList<Character> chordNotes = new ArrayList<Character>();
        for(musicExpression note : chord2){
            chordNotes.add(((Note) note).getNote());
        }
        return chordNotes;
    }

    /*
     * return a list of all pitches
     */
    public List<Pitch> getPitchList() {
        return createPitchList(chord);
    }
   
    
    public List<Character> getNoteInChordList(){
        return createNotesList(chord);
    }
    
    public String toString(){
        String c = "[";
        for (musicExpression m: chord){
            c += m.toString();
        }
        c+="]";
        return c;
    }
    
    public ArrayList<musicExpression> getChordNote(){
        return chord;
    }
    
    @Override
    public String getType() {
        return "Chord";
    }
    @Override
    /**
     * fix the the pitch of each note in the chord
     */
    public void changePitch(Dictionary<Character, Integer> d){
        for(musicExpression note: chord){
            note.changePitch(d);
        }       
    }   
}
