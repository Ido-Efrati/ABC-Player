import static org.junit.Assert.*;
import org.junit.Test;
import player.Note;
import player.Rest;
import sound.Pitch;
/*
 * Testing for Rest class.
 * test for z with and without a timing behind it
 * check for exception if denom of timing is 0
 */
public class RestTest {
    @Test
    public void basics(){
        String a = "z";
        Rest n = new Rest(a);
        assertEquals(n.getLength(), 1.0, 0.001);
    }
    
    @Test
    public void z2(){
        String a = "z2";
        Rest n = new Rest(a);
        assertEquals(n.getLength(), 2.0, 0.001);
    }
    
    @Test
    public void zHalf(){
        String a = "z/2";
        Rest n = new Rest(a);
        assertEquals(n.getLength(), 0.5, 0.001);
    }
    
    @Test
    public void zQuarter(){
        String a = "z/4";
        Rest n = new Rest(a);
        assertEquals(n.getLength(), 0.25, 0.001);
    }
    
    @Test
    public void zThreeQuarter(){
        String a = "z3/4";
        Rest n = new Rest(a);
        assertEquals(n.getLength(), 0.75, 0.001);
    }
    
    @Test(expected=RuntimeException.class)
    public void zdenomZero(){
        String a = "z/0";
        Rest n = new Rest(a);
    }
        
}