import java.awt.*; //provides Points
import java.util.*;

/**
 * Movers are the characters that everything revolves around.
 * 
 * Everything that is done in the program at some point will either have something done by a mover or done to a mover.
 * Or both.
 * 
 * The class contains more attributes than I use later on.
 * I had planned for there to be at least 10 attributes describing the personality,
 * and more emphasis put on mood and physical condition (hunger and remaining health)
 * I toned it down to one from three major components of a personality so that I could have something to send in with my application
 * (Strength for physical prowess, Intellect for mental ability, morality for behavioral patterns)
 * but I plan on factoring the rest in soon.
 * 
 * This already proved incredibly complicated for me, though, so I'm glad I did tone it down a bit to start.
 * Otherwise it would have been much more overwhelming.
 */
public class Mover{
    Point currentLocation = new Point(0,0);
    private int xLocation = 0;
    private int yLocation = 0;
    protected World currentWorld;
    protected double strength=1;
    protected double intellect=2;
    protected double health=10.7; //because, why not?
    protected double senseOfHumor=3; //unnecessary for now
    protected double silliness=5.0; //unnecessary for now
    protected double stubbornness = 2.3; //unnecessary for now
    protected double artsiness = .01; //unnecessary for now
    protected double speed = 1.0; //unnecessary for now
    protected double wealth = 1.0; 
    protected double morality = 4;
    private int statsNum = 5;
    protected double[] stats;
    LinkedList<Interactions> impressions;
    boolean isAlien = false; //more future fun with little green men
    private int aggression=0;
    private int compatibility=0;
    private int violence=0;
    private int cunning=0;
    private int hunger = 0;
    //curiosity?
    protected Inventory items;
    private String name="Alf"; //alien jokes.
    /**
     * Constructor for objects of class Mover
     */
    public Mover()
    {
        this("Alf", null);
    }

    /**
     * Constructor for objects of class Alien
     */
    public Mover(String newName){
        this(newName, null);

    }

    /**
     * The constructor names a character, places it in a world, and assigns all the random attributes.
     */
    public Mover(String newName, World newWorld){
        if(newName!=null){
            name=newName;
        }
        if(newWorld!=null){
            currentWorld=newWorld;
        }
        items =new Inventory();
        stats = new double[statsNum];

        impressions = new LinkedList<Interactions>();

        for(int x=0; x<=2; x++){
            stats[x]=Math.random()*10;
        }
        stats[4] = Math.random()*9000;
        //            currentLocation.setLocation(Math.floor(Math.random()*newWorld.getWidth()), Math.floor(Math.random()*newWorld.getHeight()));
        xLocation = (int)Math.floor(Math.random()*newWorld.getWidth());
        yLocation = (int)Math.floor(Math.random()*newWorld.getHeight());

        newWorld.addMover(this);
        newWorld.getRoom(xLocation,yLocation).addMover(this);
        //        newWorld.getRoom(currentLocation.getX(), currentLocation.getY()).addMover(this);

        //Setting base personality
        if(strength<=3){
            violence=violence-1;
        }
        else if(strength<=6){
            violence=violence+1;

        }
        else if(strength<=9){
            violence=violence+2;
        }
        else{
            violence=violence+3;
        }

        if(intellect<=3){
            cunning=cunning-1;
        }
        else if(intellect<=6){
        }
        else if(intellect<=9){
            cunning=cunning+1;
        }
        else{
            cunning=cunning+3;
        }

        if(morality<=3){
            aggression=aggression+2;
            violence=violence+1;
            cunning=cunning+1;
        }
        else if(morality<=6){
            aggression = aggression+1;
            violence = violence+1;
            cunning=cunning+1;
        }
        else if(morality<=9){
            aggression=aggression-1;
            violence=violence-1;
            cunning=cunning+1;
            compatibility=compatibility+1;
        }
        else{
            aggression=aggression-3;
            cunning=cunning+2;
            violence=violence-1;
            compatibility=compatibility+2;
        }

    }

    //Changes the name. Not really used here, but couldn't hurt to have.
    public void setName(String newName){
        if (newName!=null){
            name=newName;
        }

    }

    //picks a new random name. Also not used, but could still be helpful.
    public void setRanName(Names name){
        name.assignName();
    }

    //sets a new location for drag and drop fun!
    public void setLocation(int x, int y){
        xLocation = x;
        yLocation = y;
    }
    
    //returns the character's position on the x axis
    public int getXLocation(){
        return xLocation;
    }
    
    //returns the character's position on the y axis
    public int getYLocation(){
        return yLocation;
    }

    /**
     * This method moves the characters forward a "turn"
     * This is where I tried to put in a simulated economy of sorts.
     * 
     * Every "turn" the character will gain 30% of what he already had,
     * but will lose the amount of moneys that it costs to subsist.
     * 300 is a fairly arbitrary number, picked because it gives enough leeway for the wealthy to get wealthier, and the poor to get poorer
     * (a sad, but not totally inaccurate observation of the world)
     * 
     * And when hunger = 3, the character will die. I haven't put that in place yet because:
     * a) I don't have a system of repopulation yet (will come when I add females)
     * b) I don't yet have a system for weighing decisions based on desparation. I'll have people be more likely to help if the person is starving than if they just ate.
     * right now I feel that the odds are already stacked against the poor of my world, best not make those odds have consequences until they're balanced.
     * Maybe I'll work with an economy major to smooth this out =) 
     *
     * I have a system in the Interactions class for theft and charity to mix it up a little.
     * 
     */
    public void turn(){
        if((stats[4]*1.3)-300>0){
            stats[4] = (stats[4]*1.3)-300;
            hunger = 0;
        }
        else{
            stats[4] = stats[4]*1.3;
            hunger = hunger+1;
            if(hunger <= 2){
                System.out.println(this.getName()+" is hungry.");
            }
            else{
                System.out.println(this.getName()+" is starving.");
            }
        }
    }

    /**
     * 
     * This method either moves the characters to an adjacent place on the grid, or keeps them in the same place.
     * 
     * Selects direction (dir) randomly, but rerolls the selection if the room is overcrowded or non-existent.
     * 
     * 
     * I recognize that Math.random() isn't perfectly random, but it's all I know at the moment, and so all I use.
     */
    public void move(){
        double dir = Math.random();

        if (dir <= .11){
            if(yLocation>0){
                if(currentWorld.getRoom(xLocation,yLocation-1).getMoversNum()<2){
                    yLocation = yLocation-1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }

                else{
                    this.move();
                }
            }
            else{
                this.move();

            }
        }

        else if (dir <= .22){
            if(yLocation>0 && xLocation<currentWorld.getWidth()-1){
                if(currentWorld.getRoom(xLocation+1,yLocation-1).getMoversNum()<2){
                    yLocation = yLocation-1;
                    xLocation = xLocation+1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }

        }
        else if (dir <= .33){
            if(xLocation<currentWorld.getWidth()-1){
                if(currentWorld.getRoom(xLocation+1,yLocation).getMoversNum()<2){
                    xLocation = xLocation+1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }

        }
        else if (dir <= .44){
            if(xLocation<currentWorld.getWidth()-1 && yLocation<currentWorld.getHeight()-1){
                if(currentWorld.getRoom(xLocation+1,yLocation+1).getMoversNum()<2){
                    xLocation = xLocation+1;
                    yLocation = yLocation+1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }
        }
        else if (dir <= .55){
            if(yLocation<currentWorld.getHeight()-1){
                if(currentWorld.getRoom(xLocation,yLocation+1).getMoversNum()<2){
                    yLocation = yLocation+1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }
        }
        else if (dir <= .66){
            if(yLocation<currentWorld.getHeight()-1 && xLocation>0){
                if(currentWorld.getRoom(xLocation-1,yLocation+1).getMoversNum()<2){
                    yLocation = yLocation+1;
                    xLocation = xLocation-1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }
        }
        else if (dir <= .77){
            if(xLocation>0){
                if(currentWorld.getRoom(xLocation-1,yLocation).getMoversNum()<2){
                    xLocation = xLocation-1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }
        }
        else if (dir <= .88){
            if(xLocation>0 && yLocation>0){
                if(currentWorld.getRoom(xLocation-1,yLocation-1).getMoversNum()<2){
                    xLocation = xLocation-1;
                    yLocation = yLocation-1;
                    currentWorld.getRoom(xLocation,yLocation).addMover(this);
                }
                else{
                    this.move();
                }
            }
            else{
                this.move();
            }
        }
        else{
            System.out.println(this.getName()+" stays stationary");
            currentWorld.getRoom(xLocation,yLocation).addMover(this);
        }

    }

    //the following few let me reset and get the character's stats.

    public void setStr(int x){
        stats[0] = x;
    }

    public void setInt(int x){
        stats[1] = x;
    }

    public void setHP(double x){
        health = x;
    }

    /**
     * Not used for now
     */
    public void setSOH(double x){
        senseOfHumor = x;
    }

    /**
     * Not used for now
     */
    public void setSilly(double x){
        silliness = x;
    }

    /**
     * Not used for now
     */
    public void setStub(double x){
        stubbornness = x;
    }

    /**
     * Not used for now
     */
    public void setArt(double x){
        artsiness = x;
    }

    /**
     * Not used for now
     */
    public void setSpeed(double x){
        speed = x;
    }

    public void setWealth(double x){
        wealth = x;
    }

    public void setMorality(int x){
        stats[2] = x;
    }

    //reset everything
    public void setStats(int a, int b, double c, double d, double e, double f, double g, double h, double i, int j){
        this.setStr(a);
        this.setInt(b);
        this.setHP(c);
        this.setSOH(d);//unnecessary for now
        this.setSilly(e);//unnecessary for now
        this.setStub(f);//unnecessary for now
        this.setArt(g);//unnecessary for now
        this.setSpeed(h);//unnecessary for now
        this.setWealth(i);
        this.setMorality(j);
    }

    /**
     * This adds a new interaction to the list Impressions.
     * Interactions are stored there, and by extension so are one character's feelings for another.
     * 
     * This allows for characters to "remember" one another, and build a relationship over time.
     * They can become friends or enemies right now, and change how aggressive they are towards one another,
     * but I plan on adding a lot to how a relationship impacts both the characters' moods and their actions towards each other with growing familiarity.
     */

    public void addImpression(Interactions meeting){
        impressions.add(meeting);
    }

    //returns the character's name
    public String getName(){
        return name;
    }

    //The following return the character's "personality," meaning how they are likely to act.

    public int getAggression(){
        return aggression;
    }

    public int getCompatibility(){
        return compatibility;
    }

    public int getViolence(){
        return violence;
    }

    public int getCunning(){
        return cunning;
    }

    //Allows the characters to jump from one world to the other. 
    //Not used in the AI, but possibly later, if I choose to make multiple worlds at once.
    public void setWorld(World aWorld){
        //thought about null protection
        currentWorld=aWorld;
    }

    //returns the world that the character is currently based on
    public World getWorld(){
        return currentWorld;
    }

    //returns the number of stats I'm currently using for each character. This is helpful for reference while debugging.
    public int getStatsNum(){
        return statsNum;
    }

    //The following bunch return general statistics about a character
    public double getStrength(){
        return stats[0];
    }

    public double getIntellect(){
        return stats[1];
    }

    public double getMorality(){
        return stats[2];
    }

    public double getHealth(){
        return stats[3];
    }

    public double getHealthPercent(){
        return stats[3]/health;
    }

    public double getWealth(){
        return stats[4];
    }

    public double getStat(int x){
        return stats[x];
    }

    //Returns the list of impressions one character has of other characters
    public LinkedList<Interactions> getImpressions(){
        return impressions;
    }

    //gets the character's current location. Helpful for making sure the move command is functional
    public Point getCurrentLocation(){
        Point place = new Point(xLocation,yLocation);
        return place;
    }

    /**
     * This is the first interaction method involved in an interaction.
     * The real one is contained within the class Interactions, but this sets up what goes through that class and where the class gets stored.
     * 
     * The method checks to see if the character has met the other character before.
     * If they have, they interact based on their last impression of them, then the impression updates.
     * If they have not, they create a new impression that they save for next time, and interact based on that.
     * 
     * There is also a reaction in here. 
     * The reaction is the response to one of the 6 (a disappointingly small number compared to what I originally, and still have, planned) options of what one character can do to another.
     * The reaction dictates how one character responds (multiple possible reactions per action) and how the two characters feel differently about each other in the end.
     * 
     */
    public void interact(Mover guy){
        //checks null
        if(guy!=null && this!=null){
            //If the character has never met anyone before, this makes their first impression
            if(impressions.size()==0){
                Interactions action = new Interactions(this , guy);
                Interactions otherAction = new Interactions(guy , this);
                action.compare(this, guy);
                otherAction.compare(guy, this);
                System.out.println("");
                action.interact(this, guy);
                otherAction.setOption(action.getOption());
                otherAction.react(guy, this);
                if(otherAction.getPositive()){
                    action.setLove(action.getLove()+1);
                }
                else{
                    action.setLove(action.getLove()-1);
                }
                action.setAggression(action.getAggression()-action.getLove());
                otherAction.setAggression(otherAction.getAggression()-otherAction.getLove());
                this.addImpression(action);
                guy.addImpression(otherAction);
            }

            else{
                //checks to see if the two characters have met before
                for(int x = 0; x < impressions.size(); x++){
                    if (guy.getName().equals(impressions.get(x).getOtherName())){
                        if(impressions.get(x).getLove()<=-5){
                            System.out.println(this.getName() + " re-encountered his enemy " + guy.getName());
                        }
                        else if(impressions.get(x).getLove() <-1){
                            System.out.println(this.getName() + " re-encountered the bothersome " + guy.getName());
                        }
                        else if(impressions.get(x).getLove() <= 1){
                            System.out.println(this.getName() + " re-encountered " + guy.getName());
                        }
                        else if(impressions.get(x).getLove() >1){
                            System.out.println(this.getName() + " re-ecountered his acquaintance "+ guy.getName());
                        }
                        else if(impressions.get(x).getLove() >=5){
                            System.out.println(this.getName() + " re-encountered his friend "+ guy.getName());
                        }
                        System.out.println("");
                        impressions.get(x).resetProbs();
                        impressions.get(x).interact(this, guy);
                        for(int y=0; y< guy.getImpressions().size(); y++){
                            if(guy.getImpressions().get(y).getOtherName().equals(this.getName())){
                                guy.getImpressions().get(y).setOption(impressions.get(x).getOption());
                                guy.getImpressions().get(y).resetProbs();
                                guy.getImpressions().get(y).react(guy,this);
                                guy.getImpressions().get(y).setAggression(guy.getImpressions().get(y).getAggression()-guy.getImpressions().get(y).getLove());
                            }
                        }
                        impressions.get(x).setAggression(impressions.get(x).getAggression()-impressions.get(x).getLove());
                        break;

                    }
                    else if(x==impressions.size()-1){
                        Interactions action = new Interactions(this , guy);
                        Interactions otherAction = new Interactions(guy , this);
                        action.compare(this, guy);
                        otherAction.compare(guy, this);
                        System.out.println("");
                        action.interact(this, guy);
                        otherAction.setOption(action.getOption());
                        otherAction.react(guy, this);
                        if(otherAction.getPositive()){
                            action.setLove(action.getLove()+1);
                        }
                        else{
                            action.setLove(action.getLove()-1);
                        }
                        action.setAggression(action.getAggression()-action.getLove());
                        otherAction.setAggression(otherAction.getAggression()-otherAction.getLove());
                        this.addImpression(action);
                        guy.addImpression(otherAction);

                    }
                }
            }
            //if not, the two characters share their first impressions here.

        }
    }

    /**
     * The toString will basically be like a baseball card showing off the character.
     * All it's missing is a picture.
     */
    public String toString(){
        StringBuffer buff = new StringBuffer( );
        buff.append("Name:" + name + " ");
        buff.append("Current Location:" + currentLocation + " ");
        buff.append("world:" + currentWorld + " ");
        // be careful if the world prints a map that holds the Mover(s)
        // which in turn print themselves including their world with mover
        // ad infinitum
        buff.append("strength:" + strength + " ");
        buff.append("intellect:" + intellect + " ");
        buff.append("health:" + health + " "); //because, why not?
        buff.append("senseOfHumor:" + senseOfHumor + " ");
        buff.append("silliness:" + silliness + " ");
        buff.append("stubbornness:" + stubbornness + " ");
        buff.append("artsiness:" + artsiness + " ");
        buff.append("speed:" + speed + " ");
        buff.append("wealth:" + wealth + " ");
        buff.append("Items:" + items +".");

        return buff.toString( );
    }
}
