import java.awt.*; //provides Point
import java.util.*;
/**
 * World creates the grid of rooms on which all the characters exist.
 *
 */
public class World {
    //I was experimenting with points as a way to hold position. Most things are not done using them.
    Point worldLocation= new Point(0,0); //holds x and y locations.
    private int worldWidth = 5; //world goes from 0-4, stops at five
    private int worldHeight = 5; 
    private int howManyRooms = 0; //keeps track of how many rooms there are at any given time.
    private Room[][] myWorld = null; //builds the grid of null rooms.
    private Names charNames; //makes a Names class in the world. This will allow me to assign random names to my characters.
    //private Room blank;
    private String name = "World";
    LinkedList<Mover> people;
    //list of people in the world

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

        //makes sure the world has functional parameters
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

        //fills the grid with non-null rooms.
        for(int y=0; y<worldHeight; y++){
            for(int x=0; x<worldWidth; x++){
                Room blank = new Room("   ");
                this.setRoom(x,y,blank);
            }
        }
    }

    /**
     * This is just a function to set a null room to be equal to a non null room.
     * I used the opportunity to try using Illegal Argument Exceptions to catch bugs, 
     * which did turn out to be really helpful in the long run when I had a glitch setting up the worlds, 
     * but I didn't feel comfortable enough making them to use them more frequently.
     */
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

    /**
     * I thought worlds should have an identifying feature, like a name. 
     * In my main game, I have the player move through multiple worlds, so this is helpful.
     */
    public void setName(String newName){
        if (newName!=null){
            name=newName;
        }

    }

    //returns the width of the grid
    public int getWidth(){
        return worldWidth;
    }

    //returns the height of the grid
    public int getHeight(){
        return worldHeight;
    }

    //returns the world's name
    public String getName(){
        return name;
    }

    //returns how many rooms there are in the grid
    public int getHowManyRooms( ) {
        return howManyRooms;
    }

    //returns the list of movers on the grid
    public LinkedList<Mover> getPeople(){
        return people;
    }

    /**
     * This method picks out and returns a single room on a grid.
     * 
     * More experimentation with Illegal Argument Exceptions.
     * I think this is the only class I use it in.
     */
    public Room getRoom(int x, int y){

        //if(newRoom==null){ ?? }
        if((x<0) || (x>=worldWidth)){
            throw new IllegalArgumentException("Room xLocation " +x
                +" out of bounds. It must be between 0 and "+(worldWidth-1)+".");
        }
        if((y<0) || (y>=worldWidth)){
            throw new IllegalArgumentException("Room yLocation " +y
                +" out of bounds. It must be between 0 and "+(worldHeight-1)+".");
        }

        return myWorld[x][y];
    }

    /**
     * creates a new mover with random name and attributes.
     * 
     */
    public void createMover(){
        Mover guy = new Mover(charNames.assignName(), this);
    }

    //adds a mover to the list of people.
    public void addMover(Mover guy){
        people.add(guy);
    }

    //removes a mover from the list of people. Used in case of deaths.
    public void removeMover(Mover guy){
        people.remove(guy);
    }

    /**
     * This method picks out and returns one random room on the grid
     * This is one of the ideas I played with for putting a character in a random room.
     * I ended up just randomly generating x and y coordinates, but I still think this will be useful for placing other things (probably items) randomly.
     */
    public Room getRandomRoom(){
        if(howManyRooms<1){
            return null;
        }
        double randomNumx = Math.floor(Math.random( )*worldWidth);
        int randomNumy = (int)Math.floor(Math.random( )*worldHeight);
        Room newRoom = this.getRoom((int)randomNumx, randomNumy);

        // This is the first time I learned how to do (int) to force the class to change to int.
        // That's why I use it in two different ways. I'm trying to see what feels best.
        // For the rest of the program, I use it only when declaring the variable.
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
     * 
     * Currently not used, though I toyed with the idea of allowing the player to drag and drop the AIs.
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

    /**
     * Makes all of the movers in the world move to an adjacent space on the grid, or else stay where they are.
     * By doing this, it also idirectly causes interactions. (see: Room.addMover())
     */
    public void moveAll(World world){

        //clears all the locations held by the movers.
        //until they move, they're technically not anywhere.
        for(int y=0; y<this.getHeight(); y++){
            for(int x=0; x<this.getWidth(); x++){
                myWorld[x][y].getMovers().clear();
            }
        }
        //moves all the movers
        for(int z=0; z<this.getPeople().size(); z++){
            this.getPeople().get(z).move();

        }
    }

    /**
     * 
     * Converts the world into a readable form.
     * This is what ends up being the map.
     * 
     */
    public String toString(){
        StringBuffer map = new StringBuffer( );

        //goes through all the rooms and appends -[   ]-
        //if there are any people in the room, it cuts out a space and adds the first letter of their names.
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
            //this just cuts off the extra dash at the end of a line.
            map.delete(map.length()-1, map.length());
            map.append("\n");
            map.append("\n");
        }
        System.out.println(people.size());
        return map.toString();
    }
}
