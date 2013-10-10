import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import player.*;

/*
 * Testing the Tuplet Class by passing in duplet, triplet and quadruplet with
 * varying values of pitch and note length. 
 * getLength(), getTupletNote() and getType() methods are tested.
 * Test for exception if the string passed in is not valid
 */

public class TupletTest {
    @Test
    public void duplet(){
        Note a= new Note("D");
        Note b= new Note("^F");
        ArrayList<musicExpression> exp= new ArrayList<musicExpression>();
        exp.add(a);
        exp.add(b);
        Tuplet dup= new Tuplet(exp, "(2");
        assertEquals(dup.getLength(), 1.50, 0.000001);
        assertEquals(dup.getTupletNote(), exp);
        assertEquals(dup.getType(), "Tuplet");
    }
    
    @Test
    public void triplet(){
        Note a= new Note("D");
        Note b= new Note("^F/3");
        Note c= new Note("_A");
        ArrayList<musicExpression> exp= new ArrayList<musicExpression>();
        exp.add(a);
        exp.add(b);
        exp.add(c);
        Tuplet trip= new Tuplet(exp, "(3");
        assertEquals(trip.getLength(), 2/3.0, 0.000001);
        assertEquals(trip.getTupletNote(), exp);
        assertEquals(trip.getType(), "Tuplet");
    }
    
    @Test
    public void quadriplet(){
        Note a= new Note("D");
        Note b= new Note("^F");
        Note c= new Note("_A");
        Rest d= new Rest("z");
        ArrayList<musicExpression> exp= new ArrayList<musicExpression>();
        exp.add(a);
        exp.add(b);
        exp.add(c);
        exp.add(d);
        Tuplet quadruplet= new Tuplet(exp, "(4");
        assertEquals(quadruplet.getLength(), 3/4.0, 0.000001);
        assertEquals(quadruplet.getTupletNote(), exp);
        assertEquals(quadruplet.getType(), "Tuplet");
    }
    
    @Test(expected=RuntimeException.class)
    public void exceptionTest(){
        Note a= new Note("D");
        Note b= new Note("^F");
        Note c= new Note("_A");
        Rest d= new Rest("z/2");
        Note e= new Note("c/2");
        ArrayList<musicExpression> exp= new ArrayList<musicExpression>();
        exp.add(a);
        exp.add(b);
        exp.add(c);
        exp.add(d);
        exp.add(e);
        Tuplet quadruplet= new Tuplet(exp, "(5");
        assertEquals(quadruplet.getLength(), 3/4.0, 0.000001);
    }
}
