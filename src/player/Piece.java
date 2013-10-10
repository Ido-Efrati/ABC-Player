package player;

import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import sound.Pitch;
import sound.SequencePlayer;

/**
 * play the music when called
 */
public class Piece {
    private String meter; //M
    private String tempo; //T
    private String lengthOfNote; //L
    private ArrayList<Integer> denomList=new ArrayList<Integer>(); //array list of all the denominators appeared in the piece
    private boolean dupletPresent=false;
    private boolean tripletPresent=false;
    private boolean quadrupletPresent=false;
    private SequencePlayer player;
    private ArrayList<Voice> musicToPlay;

    /**
     * @param music an ArrayList of voices that makes up the music
     * @param Header the header specifications for the music
     * @param dupletPresent true if duplet is present in the piece
     * @param tripletPresent true if triplet is present in the piece
     * @param quadrupletPresent true if quadruplet is present 
     */
    public Piece(ArrayList<Voice> music, Header h, boolean dupletPresent, boolean tripletPresent, boolean quadrupletPresent, ArrayList<Integer> denomList){ 
        tempo=h.getTempo();
        this.meter= h.getMeter();
        this.denomList=denomList;
        this.dupletPresent=dupletPresent;
        this.tripletPresent=tripletPresent;
        this.quadrupletPresent=quadrupletPresent;
        lengthOfNote=h.getNoteLength();
        musicToPlay=music;
    }
    public void play(){
        makeMusic(musicToPlay);
    }
    /**
     * Calls playMusic to add notes for each voice
     * figures out the tempo, tick per note of the piece
     * @param music an ArrayList of voices to be played
     */
    private void makeMusic(ArrayList<Voice> music){
        int voice=music.size();
        double m_denom=Double.parseDouble(meter.substring(2));
        double length_of_note=1/Double.parseDouble(lengthOfNote.substring(2)); //L 
        double tempo_given=Double.parseDouble(tempo);//Q
        int tickPerQuarter=(int) (tempo_given*length_of_note*m_denom); // Q*L*4*denom(M)
        //tickPerQuarter calculation was based on an answer for a TA (Katrina Panovich)
        int lcmBeat=(int) (calculateLCM(denomList));

        try {
            player = new SequencePlayer(tickPerQuarter, lcmBeat);
            for (int i=0; i<voice; i++){ 
                playMusic(player, music.get(i).getBars(), lcmBeat);
            }
            player.play(); //play music after all notes are added
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

   /**
    * Add notes to player according to their pitch and length
    * @param player the SquencePlayer to add notes to
    * @param piece an arrayList of bars to be added to the Player  
    * @param lcmBeat the least common factor of all the denominators appearing in the piece
    */
    private void playMusic(SequencePlayer player, ArrayList<Bar> piece, int lcmBeat){
        int timer=0; //global timer
        int duration=0; 

        for(Bar bar: piece){ //iterate through each bar
            for(musicExpression note: bar.changeMusicExpression()){ //iterate each musicExpresion in the bar
                if (note.getType().equals("Rest")){
                    timer+=note.getLength()*lcmBeat;
                }
                else if(note.getType().equals("Note")){
                    duration=(int) (note.getLength()*lcmBeat);
                    player.addNote(((Note) note).getPitch().toMidiNote(), timer, duration); //change note to Note class
                    timer+=duration;
                }
                else if(note.getType().equals("Chord")){
                    for(musicExpression playnote: ((Chord) note).getChordNote()){
                        duration=(int) (playnote.getLength()*lcmBeat);
                        player.addNote(((Note) playnote).getPitch().toMidiNote(), timer, duration);
                    }
                    timer+=duration;
                }
                else if(note.getType().equals("Tuplet")){ 
                    double noteLength= note.getLength();  //get the factor of tuplet
                    for(musicExpression playNote: ((Tuplet) note).getTupletNote()){
                        if (playNote.getType().equals("Rest")){
                            timer+=playNote.getLength()*lcmBeat*noteLength;
                        }
                        else if(playNote.getType().equals("Note")){
                            duration=(int) (playNote.getLength()*lcmBeat*noteLength);
                            player.addNote(((Note) playNote).getPitch().toMidiNote(), timer, duration); //change note to Note class
                            timer+=duration;
                        }    
                    }
                }
            }
        }  
    }
    

    /**
     * calculate the LCM given an array of input
     * @param inputList, an ArrayList of integer input to calculate lcm from
     * @return the lcm of the list
     */
    private int calculateLCM(ArrayList<Integer> inputList) { //changed to static for testing
        int result=inputList.get(0).intValue();
        for(int i=1; i<inputList.size(); i++){
            result=lcm(result, inputList.get(i).intValue());
        }
        //if duplet, triplet, quadruplet is present, multiply them to final result since they are not accounted for in Note length.
        if(dupletPresent){ 
            result=result*2;
        }
        if(tripletPresent){
            result=result*3;
        }
        if(quadrupletPresent){
            result=result*4;
        }
        return result;
    }
    /**
     * Greatest common denominator given 2 numbers
     * @param a, b the two numbers to compute gcd from
     * @return gcd of a and b
     */
    private int gcd(int a, int b)
    {
        while (b > 0)
        {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }
    /**
     * Least common factor given 2 numbers
     * @param a, b the two numbers to compute lcm from
     * @return lcm of a and b
     */
    private int lcm(int a, int b)
    {
        return a * (b / gcd(a, b));
    }
    
    public SequencePlayer getPlayer(){
        return player;
}
}