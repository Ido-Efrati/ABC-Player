package player;

import java.util.*;

public class Bar {
    private ArrayList<musicExpression> music;
    private Header head;
    private ArrayList<String> keyArray;
    private Dictionary<Character, Integer> d = new Hashtable<Character, Integer>();
    private boolean hasKeyChange = true;
    /**
     * creates a dictionary based on the key value
     * @param expression
     * @param h
     */
    public Bar(ArrayList<musicExpression> expression, Header h) {
        music = expression;
        head = h;
        keyArray = head.getValueOfKey(head.getKey());
        newKeyDict();
    }

    /**
     * the method will create a new dictionary that will help with modifying the
     * notes.
     * 
     */
    private void newKeyDict() {
        if (keyArray.size()==0){
            hasKeyChange = false;
        }else{
            for (int i = 0; i < keyArray.size(); i++) {
                int value = 0;
                if (keyArray.get(i).substring(0, 1).equals("^")) {
                    value = 1;
                } else {
                    value = -1;
                }
                d.put(keyArray.get(i).charAt(1), value);
            }
        }
    }
    /**
     * will change each note to the appropriate one based on the key and previous accidentals
     * @return the array list of the modified music expression
     */
    public ArrayList<musicExpression> changeMusicExpression() {
        if (hasKeyChange){
            for (musicExpression part : music) {
                part.changePitch(d);
            }
        }
        return music;
    }
    
    public String toString(){
        String bar = "";
        for(musicExpression m: music){
            bar+= m.toString();
        }
        return bar;
    }
}
