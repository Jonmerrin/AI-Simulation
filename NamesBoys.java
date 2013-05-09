import java.util.*;
/**
 * Names is a class that contains a list of 26 names, each one starting with a different letter of the alphabet.
 * I did this so that when the characters print the first letter of their names on the grid, they'd be distinguishable from one another.
 * 
 * Right now only male names, because of reasons spoken about in other classes
 * (male/female interactions would've taken too long to get this in on time)
 * But that's next on my list, and I already have a list of 26 names at the ready.
 * 
 */
public class NamesBoys
{
//List of names:
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
//LinkedList of names:
    LinkedList<String> names;

    /**
     * Constructor for objects of class Names
     */
    public NamesBoys()
    {
        // putting all the names in the LinkedList()
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
     * Picks a random name, returns it, and takes it off the list of names to ensure that there are no repeats.
     */
    public String assignName(){
        String finalName = null;
        int nameSelect = (int)(Math.random()*names.size());

        finalName = names.get(nameSelect);
        names.remove(names.get(nameSelect));

        return finalName;
    }
    
    public void add(String newName){
        names.add(newName);
    }
}
