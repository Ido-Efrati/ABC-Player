package player;

import java.util.Dictionary;
import java.util.List;

import sound.Pitch;
/*
 * Rest Class takes in a string for rest in the form of "z timing" and get the relevant variables (length, denom) for the rest
 */
public class Rest implements musicExpression {
    private double restLength = 1;
    private String length;
    int denom=1;
    public Rest(String input){
        restLength=getLenght(input.substring(1));
    }
    /*
     * Given the length of the rest as a string and return it as a double
     * @param noteLength the length of the rest 
     * @return the length of the rest as a double
     */
    public double getLenght(String noteLength){
        length = noteLength;
        double legnthValue=1;
        //if there is a number or "/" then we override the default value of 1
        if (noteLength.length()>0){
            if (noteLength.charAt(0) == '/'){ // if the first character is a "/" then the it is 1/(next number)
                if (noteLength.length()>1){
                    denom = Integer.parseInt(noteLength.substring(1));
                    if(denom==0){ //throw an error if demon is zero;
                        throw new RuntimeException("denominator should not be zero");
                    }
                    legnthValue = 1/(double)denom;
                }               
            }else{ // must be either a whole number or a fraction
                String[] parseNoteLength = noteLength.split("/");
                if (parseNoteLength.length==2){
                    denom = Integer.parseInt(parseNoteLength[1]);
                    if(denom==0){
                        throw new RuntimeException("denominator should not be zero");
                    }
                    legnthValue = Integer.parseInt(parseNoteLength[0])/(double)denom;
                }else{
                    legnthValue = Integer.parseInt(parseNoteLength[0]);
                }
            }
        }
        return legnthValue;  
    }
    public int getDenom(){
        return denom;
    }
    
    @Override
    public double getLength(){
        return restLength;
    }

    @Override
    public String getType() {
        return "Rest";
    }
    @Override
    public void changePitch(Dictionary<Character, Integer> d) {
        //Do nothings
    }
    
    public String toString(){
        return "z"+length;
    }
}