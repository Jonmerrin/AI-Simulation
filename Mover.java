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
    private double aggression=0;
    private double compatibility=0;
    private double violence=0;
    private double cunning=0;
    private int hunger = 0;
    //Motivations
    private double empathy = 0;
    private double sympathy = 0;
    private double selfishness = 0;
    private double desperation = 0;

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
        items =new Inventory(this);
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

        violence = strength-3;
        cunning = intellect-3;
        sympathy = morality-5;

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
    public void setRanName(NamesBoys name){
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

    public Interactions getImpression(String nameOfChar){
        for(int q = 0; q<impressions.size(); q++){
            if(impressions.get(q).getOtherName().equals(nameOfChar)){
                return impressions.get(q);
            }
        }
        return null;
    }

    /**
     * This method moves the characters forward a "turn"
     * This is where I tried to put in a simulated economy of sorts.
     * 
     * Every "turn" the character will gain 30% of what he already had,
     * but will lose the amount of moneys that it costs to subsist.
     * 200 is a fairly arbitrary number, picked because it gives enough leeway for the wealthy to get wealthier, and the poor to get poorer
     * (a sad, but not totally inaccurate observation of the world)
     * 
     * And when hunger = 3, the character will die. I haven't put that in place yet because:
     * a) I don't have a system of repopulation yet (will come when I add females)
     * b) I don't yet have a system for weighing decisions based on desparation. I'll have people be more likely to help if the person is starving than if they just ate.
     * right now I feel that the odds are already stacked against the poor of my world, best not make those odds have consequences until they're balanced.
     * 
     * I have a system in the Interactions class for theft and charity to mix it up a little.
     * 
     */
    public void turn(){
        if((stats[4]*1.3)-200>0){
            stats[4] = (stats[4]*1.3)-200;
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
        stats[4] = x;
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

    public double getAggression(){
        return aggression;
    }

    public double getCompatibility(){
        return compatibility;
    }

    public double getViolence(){
        return violence;
    }

    public double getCunning(){
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

    public Inventory getInventory(){
        return items;
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
            boolean didInteract = false;
            // If the character has never met anyone before, this makes their first impression
            if(impressions.size()==0){
                didInteract = true;
                Interactions action = new Interactions(this , guy);
                Interactions otherAction = new Interactions(guy , this);
                action.compare(this, guy);
                otherAction.compare(guy, this);
                System.out.println("");
                action.interact(this, guy);
                //peopleInRoom.get(1-rollForInitiative).getImpression(peopleInRoom.get(rollForInitiative).getName()).addToStory(peopleInRoom.get(rollForInitiative).getImpression(peopleInRoom.get(1-rollForInitiative).getName()).getLastInteract());
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
                action.addToStory(action.getLastInteract());
                action.addToStory(otherAction.getLastInteract());
                otherAction.addToStory(action.getLastInteract());
                otherAction.addToStory(otherAction.getLastInteract());
                this.addImpression(action);
                guy.addImpression(otherAction);
            }

            //checks to see if the two characters have met before
            if (didInteract==false){
                String encounter = new String();
                String approach = new String();
                for(int x = 0; x < impressions.size(); x++){
                    if (guy.getName().equals(impressions.get(x).getOtherName())){
                        didInteract = true;
                        if(impressions.get(x).getLove()<=-5){
                            encounter = (this.getName() + " re-encountered his enemy " + guy.getName());
                            approach = " his enemy ";
                        }
                        else if(impressions.get(x).getLove() <-1){
                            encounter = (this.getName() + " re-encountered the bothersome " + guy.getName());
                            approach = " the bothersome ";
                        }
                        else if(impressions.get(x).getLove() <= 1){
                            encounter = (this.getName() + " re-encountered " + guy.getName());
                            approach = "";
                        }
                        else if(impressions.get(x).getLove() >1){
                            encounter = (this.getName() + " re-ecountered his acquaintance "+ guy.getName());
                            approach = " his acquaintance ";
                        }
                        else if(impressions.get(x).getLove() >=5){
                            encounter = (this.getName() + " re-encountered his friend "+ guy.getName());
                            approach = " his friend ";
                        }
                        System.out.println(encounter);
                        impressions.get(x).addToStory(encounter+"\n");
                        guy.getImpression(name).addToStory(guy.getName()+" is approached by "+approach+name+".");
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
                        this.getImpressions().get(x).addToStory(this.getImpressions().get(x).getLastInteract()+"\n");
                        this.getImpressions().get(x).addToStory(guy.getImpression(name).getLastInteract()+"\n\n");
                        guy.getImpression(name).addToStory(this.getImpressions().get(x).getLastInteract()+"\n");
                        guy.getImpression(name).addToStory(guy.getImpression(name).getLastInteract()+"\n\n");
                        
                        impressions.get(x).setAggression(impressions.get(x).getAggression()-impressions.get(x).getLove());
                        break;

                    }

                    else if((x==impressions.size()-1)||(impressions.size()==0)){
                        if(didInteract==false){
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
                            action.addToStory(action.getLastInteract());
                            action.addToStory(otherAction.getLastInteract());
                            otherAction.addToStory(action.getLastInteract());
                            otherAction.addToStory(otherAction.getLastInteract());
                        }

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
        buff.append("Name: " + name + " \n \n \n");
        buff.append("Current Location: (" + xLocation + "," + yLocation + ") \n");
        buff.append("world: " + currentWorld.getName() + " \n \n \n");
        // be careful if the world prints a map that holds the Mover(s)
        // which in turn print themselves including their world with mover
        // ad infinitum
        buff.append("Statistics: \n \n");
        buff.append("strength:" + stats[0] + " \n");
        buff.append("intellect:" + stats[1] + " \n");
        buff.append("morality:" + stats[2] + "\n");
        buff.append("health:" + stats[3] + " \n"); //because, why not?
        buff.append("senseOfHumor:" + senseOfHumor + " \n");
        buff.append("silliness:" + silliness + " \n");
        buff.append("stubbornness:" + stubbornness + " \n");
        buff.append("artsiness:" + artsiness + "\n");
        buff.append("speed:" + speed + "\n");
        buff.append("wealth:" + stats[4] + " moneys.\n\n");
        buff.append("Relationships: " + name + " has met " + impressions.size() + " people.\n");
        buff.append("Inventory: " + items);

        return buff.toString( );
    }
}
