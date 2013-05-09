import java.util.*; //Provides collections and LinkedLists
/**
 * Basically a LinkedList meant for greatness (superpower stuff) (later).
 * 
 * This is one of the big things I mentioned in the Item class.
 * I have this up and running in my main game, and the framework exists here, I just don't do anything special with it yet.
 * 
 */
public class Inventory
{
private String name = null;
    LinkedList<Item> myList;
    /**
     * Constructor for objects of class Inventory
     */
    public Inventory(Mover myMover)
    {
        myList = new LinkedList<Item>();
        name = myMover.getName();
    }

    public Inventory(Room myRoom)
    {
        myList = new LinkedList<Item>();
        name = myRoom.getRoomName();
    }

    public void add(Item thingy){
        if(thingy!= null){
            myList.add(thingy);
        }
    }

    public void remove(Item majig){
        if(majig!= null){
            myList.remove(majig);
        }
    }

    public int size(){
        return myList.size();
    }

    public Item get(int x){
        return myList.get(x);
    }

    /**
     * Prints out inventory.
     */
    public String toString(){
        StringBuffer invStat = new StringBuffer();
        if(myList.size()>0){
            invStat.append("You have: ");
            for(int x=0; x<myList.size()-1; x++){
                invStat.append("a "+myList.get(x).getName()+" x"+myList.get(x).checkStats()+", ");
                if(myList.size()>1){
                    invStat.append("and ");
                }
            }
            invStat.append("a "+myList.get(myList.size()-1).getName()+" x"+myList.get(myList.size()-1).checkStats()+".");
        }
        else{
            invStat.append(name + " doesn't have any items.");
        }
        return invStat.toString(); //Print or Return?!
    }

}
