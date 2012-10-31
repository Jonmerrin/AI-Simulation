import java.awt.*; //provides Point
import java.util.*;
/**
 * Write a description of class World here.
 *
 * @author Jonathan Merrin
 * @version (a version number or a date)
 */
public class World {
    Point worldLocation= new Point(0,0); //holds x and y locations.
    private int worldWidth = 5; //world goes from 0-4, stops at five
    private int worldHeight = 5; //This too should be private maybe.
    private int howManyRooms = 0;
    private Room[][] myWorld = null;
    private Names charNames;
    //private Room blank;
    private String name = "World";
    LinkedList<Mover> people;

    //want another constructor that receives no numbers

    public World() {
        this(5, 5, "World");
    }

    public World(String newName) {
        this(5,5,newName);
    }

    /**
     * Constructor for objects of class World
     */
    public World(int newWorldWidth, int newWorldHeight, String newName) {
        //Room blank = new Room("   ");
        charNames = new Names();
        if(newWorldWidth > 0) {
            worldWidth = newWorldWidth;
        }
        if(newWorldHeight > 0) {
            worldHeight = newWorldHeight;
        }
        //likewise for height
        myWorld = new Room[worldWidth][worldHeight];
        if (newName != null) {
            name = newName;
        }
        people = new LinkedList<Mover>();
        for(int y=0; y<worldHeight; y++){
            for(int x=0; x<worldWidth; x++){
                Room blank = new Room("   ");
                this.setRoom(x,y,blank);
            }
        }
    }

    public void setRoom(int x, int y, Room newRoom) {
        // if we're adding a real room to an empty place, howManyRooms++
        if ((newRoom != null) && (myWorld[x][y] == null)) {
            howManyRooms++;
        } // if we're blanking (nulling) a real room, howManyRooms--
        else  if ((newRoom == null) && (myWorld[x][y] != null)) {
            howManyRooms--; 
        }
        //if(newRoom==null){ ?? }
        if((x < 0) ||(x >= worldWidth)) {
            throw new IllegalArgumentException("Room xLocation " +x + " out of bounds. It must be between 0 and "+(worldWidth - 1) + ".");
        }
        if((y < 0) ||(y >= worldWidth)) {
            throw new IllegalArgumentException("Room yLocation " +y + " out of bounds. It must be between 0 and "+(worldHeight - 1) + ".");
        }

        myWorld[x][y] = newRoom;
    }

    public void setName(String newName){
        if (newName!=null){
            name=newName;
        }

    }

    public int getWidth(){
        return worldWidth;
    }

    public int getHeight(){
        return worldHeight;
    }

    public String getName(){
        return name;
    }

    public int getHowManyRooms( ) {
        return howManyRooms;
    }

    public LinkedList<Mover> getPeople(){
        return people;
    }

    public Room getRoom(int x, int y){

        //if(newRoom==null){ ?? }
        //         if((x<0) || (x>=worldWidth)){
        //             throw new IllegalArgumentException("Room xLocation " +x
        //                 +" out of bounds. It must be between 0 and "+(worldWidth-1)+".");
        //         }
        if((y<0) || (y>=worldWidth)){
            throw new IllegalArgumentException("Room yLocation " +y
                +" out of bounds. It must be between 0 and "+(worldHeight-1)+".");
        }

        return myWorld[x][y];
    }

    public void createMover(){
        Mover guy = new Mover(charNames.assignName(), this);
    }

    public void addMover(Mover guy){
        people.add(guy);
    }

    public void removeMover(Mover guy){
        people.remove(guy);
    }

    /**
     * hmmm, randomly chosen room might be null if we're not filling whole worlds with rooms...
     * if null, maybe we choose again?
     */
    public Room getRandomRoom(){
        if(howManyRooms<1){
            return null;
        }
        double randomNumx = Math.floor(Math.random( )*worldWidth);
        int randomNumy = (int)Math.floor(Math.random( )*worldHeight);
        Room newRoom = this.getRoom((int)randomNumx, randomNumy);

        // potential infinite loop if world has no rooms...
        while ( newRoom == null ) {
            randomNumx = Math.floor(Math.random( )*worldWidth);
            randomNumy = (int)Math.floor(Math.random( )*worldHeight);
            newRoom = this.getRoom((int)randomNumx, randomNumy);
        }
        return newRoom;
    }

    /**
     * Sets location for player in world.
     * Perhaps to be used on non-player characters later, or the x and y might be held by characters later.
     */
    public void setLocation(int x, int y){
        if((x<0) || (x>=worldWidth)){
            throw new IllegalArgumentException("Room xLocation " +x
                +" out of bounds. It must be between 0 and "+(worldWidth-1)+".");
        }
        if((y<0) || (y>=worldWidth)){
            throw new IllegalArgumentException("Room yLocation " +y
                +" out of bounds. It must be between 0 and "+(worldHeight-1)+".");
        }
        worldLocation.setLocation(x,y);
    }

    public void moveAll(World world){
        for(int y=0; y<this.getHeight(); y++){
            for(int x=0; x<this.getWidth(); x++){
                myWorld[x][y].getMovers().clear();
            }
        }
        for(int z=0; z<this.getPeople().size(); z++){
            this.getPeople().get(z).move();

        }
    }

    /**
     * returns the current location of the player as Point (which holds x,y).
     */
    public Point getLocation(){
        return worldLocation;
    }

    public Room getCurrentRoom() {
        return myWorld[worldLocation.x][worldLocation.y];
    }

    public String toString(){
        StringBuffer map = new StringBuffer( );

        for(int y=0; y<worldHeight; y++){
            for(int x=0; x<worldWidth; x++){
                if (this.getRoom(x,y) != null){
                    map.append(this.getRoom(x,y));
                    if (this.getRoom(x,y).getMoversNum()>0){
                        map.delete(map.length()-3, map.length()-3+this.getRoom(x,y).getMoversNum());
                        for(int z=0; z<this.getRoom(x,y).getMoversNum(); z++){
                            map.insert(map.length()-1, this.getRoom(x,y).getMovers().get(z).getName().substring(0,1));
                        }
                    }
                }
                else{
                    map.append("     ");
                }
                map.append("-");

            }
            map.delete(map.length()-1, map.length());
            map.append("\n");
            map.append("\n");
        }
        System.out.println(people.size());
        return map.toString();
    }
}
