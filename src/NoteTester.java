

import static org.junit.Assert.*;

import org.junit.Test;

import player.Note;

import sound.Pitch;

public class NoteTester {
    /*
     * Note tester will cover each note options including ^,^^,_,__ and= as well as different timing
     * and different octaves.
     * Test will also cover a check if a node has an accidental, and will check for the note's demon
     */
    @Test
    public void allNotes(){
        String a = "D/";
        Note n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('D'));
        assertEquals(n.getDenom(),1);


        a = "C,";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(-1));

        a = "C,,";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(-2));
        assertEquals(n.hasAccidental(),false);

        
        a = "c";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(1));
        assertEquals(n.hasAccidental(),false);
        assertEquals(n.getDenom(),1);


        
        a = "c'";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(2));
        
        a = "c''";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3));
        

        a = "_c''";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(-1));        
        assertEquals(n.hasAccidental(),true);

        
        a = "__c''";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(-2));
        
        a = "__c''3/4";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(-2));
        assertEquals(n.getLength(), 3/(double)4, .000001);
        
        a = "__c''/4";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(-2));
        assertEquals(n.getLength(), 1/(double)4, .000001);
        assertEquals(n.hasAccidental(),true);
        
        a = "^^c''/4";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(2));
        assertEquals(n.getLength(), 1/(double)4, .000001);
        assertEquals(n.hasAccidental(),true);
        assertEquals(n.getDenom(),4);

        
        a = "^c''/12";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(1));
        assertEquals(n.getLength(), 1/(double)12, .000001);
        assertEquals(n.hasAccidental(),true);
        assertEquals(n.getDenom(),12);



        a = "=c''/8";
        n = new Note(a);
        assertEquals(n.getPitch(), new Pitch('C').octaveTranspose(3).accidentalTranspose(0));
        assertEquals(n.getLength(), 1/(double)8, .000001);
        assertEquals(n.hasAccidental(),true);
        assertEquals(n.getDenom(),8);



    }

}
