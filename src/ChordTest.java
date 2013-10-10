import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Test;
import player.*;
import sound.Pitch;

/*
 * Testing the Tuplet Class by passing in duplet, triplet and quadruplet with
 * varying values of pitch and note length. 
 * getDenom(), getLength(), getNoteInChordList(), getPitchList() and getType() methods are tested.
 * Test for exception if the string passed in is not valid
 */

public class ChordTest {
    @Test
    public void Twochord(){
        Note a= new Note("D");
        Note b= new Note("^F");
        ArrayList<musicExpression> exp= new ArrayList<musicExpression>();
        exp.add(a);
        exp.add(b);
        Chord chord= new Chord(exp, "");
        assertEquals(chord.getLength(), 1, 0.0001);
        assertEquals(chord.getDenom(), 1);
        ArrayList<Character> result = new ArrayList<Character>();
        result.add('D');
        result.add('F');
        assertEquals(chord.getNoteInChordList(), result);
        assertEquals(chord.getType(), "Chord");
        Pitch d=new Pitch('D');
        Pitch f=new Pitch('F').accidentalTranspose(1);
        ArrayList<Pitch> pitches= new ArrayList<Pitch>();
        pitches.add(d);
        pitches.add(f);
        assertEquals(chord.getPitchList(), pitches);
    }
    
    @Test
    public void Threechord(){
        Note a= new Note("D");
        Note b= new Note("^F/2");
        Note c= new Note("F");
        ArrayList<musicExpression> exp= new ArrayList<musicExpression>();
        exp.add(a);
        exp.add(b);
        exp.add(c);
        Chord chord= new Chord(exp, "");

        ArrayList<Character> result = new ArrayList<Character>();
        result.add('D');
        result.add('F');
        result.add('F');
        assertEquals(chord.getNoteInChordList(), result);
        assertEquals(chord.getType(), "Chord");
        
        Dictionary d = new Hashtable();
        d.put('f', -1);
        chord.changePitch(d);

        Pitch e=new Pitch('D');
        Pitch f=new Pitch('F').accidentalTranspose(1);
        Pitch g=new Pitch('F').accidentalTranspose(1);
        ArrayList<Pitch> pitches= new ArrayList<Pitch>();
        pitches.add(e);
        pitches.add(f);
        pitches.add(g);
        
        assertEquals(chord.getPitchList(), pitches);
    }
}