import java.util.*;
/**
 * Write a description of class Names here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Names
{
    // instance variables - replace the example below with your own
    private int x;
    private String arnold = "Arnold";
    private String bartholomew = "Bartholomew";
    private String charlie = "Charlie";
    private String daniel = "Daniel";
    private String emmanuel = "Emmanuel";
    private String franklin = "Franklin";
    private String gilbert = "Gilbert";
    private String harrison = "Harrison";
    private String ignatius = "Ignatius";
    private String jonathan = "Jonathan";
    private String kurt = "Kurt";
    private String leon = "Leon";
    private String mark = "Mark";
    private String nathaniel = "Nathaniel";
    private String oliver = "Oliver";
    private String pierce = "Pierce";
    private String quincy = "Quincy";
    private String robert = "Robert";
    private String stephen = "Stephen";
    private String terrance = "Terrance";
    private String ulysses = "Ulysses";
    private String victor = "Victor";
    private String william = "William";
    private String xander = "Xander";
    private String yohann = "Yohann";
    private String zachary = "Zachary";
    LinkedList<String> names;

    /**
     * Constructor for objects of class Names
     */
    public Names()
    {
        // initialise instance variables
        names = new LinkedList<String>();
        names.add(arnold);
        names.add(bartholomew);
        names.add(charlie);
        names.add(daniel);
        names.add(emmanuel);
        names.add(franklin);
        names.add(gilbert);
        names.add(harrison);
        names.add(ignatius);
        names.add(jonathan);
        names.add(kurt);
        names.add(leon);
        names.add(mark);
        names.add(nathaniel);
        names.add(oliver);
        names.add(pierce);
        names.add(quincy);
        names.add(robert);
        names.add(stephen);
        names.add(terrance);
        names.add(ulysses);
        names.add(victor);
        names.add(william);
        names.add(xander);
        names.add(yohann);
        names.add(zachary);
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public String assignName(){
        String finalName = null;
        int nameSelect = (int)(Math.random()*names.size());

        finalName = names.get(nameSelect);
        names.remove(names.get(nameSelect));

        return finalName;
    }
}
