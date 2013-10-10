package player;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import sound.Pitch;
/**
 * Tuplet Class takes in tuplet, process it to get the relevant information on the tuplet
 */
public class Tuplet implements musicExpression {
    private double tupletLength=1;
    private String length;
    private ArrayList<musicExpression> tuplet = new ArrayList<musicExpression>();

    /**
     * @param inputList a list all notes in the tuplet
     * @param length
     */
    public Tuplet(ArrayList<musicExpression> inputList, String length) {
        tuplet=inputList;
        tupletLength= makeLength(length.substring(1));
        this.length = length.substring(1);
    }

    /**
     * parse the length and return the length each note in the tuplet should be scaled by
     * @param length the number that indicate the length the tuplet should be played
     * @return the scaling factor for the tuplet
     */
    private double makeLength(String length) {
        if(length.equals("2")){ //case (2; Duplet: 2 notes in the time of 3 notes
            return 3/2.0;
        }
        else if(length.equals("3")){ //case (3; Triplet: 3 notes in the time of 2 notes
            return 2/3.0;
        }
        else if(length.equals("4")){ //case (4; Quadruplet: 4 notes in the time of 3 notes
            return 3/4.0;
        }
        else{
            throw new RuntimeException();
        }
    }
    /**
     * @return a list of all musicExpressions
     */
    public List<musicExpression> getTupletNote(){
        return tuplet;
    }
    /**
     * @return a tuplet length; Note: return the noteLength for a quarter, not each individual note
     */
    @Override
    public double getLength() {
        return tupletLength;
    }
    @Override
    public String getType() {
        return "Tuplet";
    }
    public String toString(){
        String n = "(" + length;
        for(musicExpression note: tuplet){
            n+=note.toString();
        }   
        return n;
    }
    public void changePitch(Dictionary<Character, Integer> d){
        for(musicExpression note: tuplet){
            note.changePitch(d);
        }       
    }   

}