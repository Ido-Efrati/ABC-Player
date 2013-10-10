package player;
import java.util.Dictionary;
public interface musicExpression {
    public double getLength();
    public String getType();
    public void changePitch(Dictionary<Character, Integer> d);

}
