package player;

import java.util.ArrayList;
import java.util.List;

//The class will get all of the relevant bars for each voice and return a list of all of this bars together 
public class Voice {
    private ArrayList<Bar> voice = new ArrayList<Bar>();
    private String name;
    
    /**
     * @param listOfBars all the bars in the voice
     * @param n value of the token voice (includes V:)
     */
    public Voice(ArrayList<Bar> listOfBars,String n){
        voice= listOfBars;
        name=n;
    }
    public ArrayList<Bar> getBars(){
        return voice;
    }
    public String getName(){
        return name;
    }
    public void addBar(Bar bar){
        voice.add(bar);
    }

}
