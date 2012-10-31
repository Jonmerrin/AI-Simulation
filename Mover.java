import java.awt.*; //provides Points
import java.util.*;

public class Mover extends Item
{
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
    boolean isAlien = false;
    private int aggression=0;
    private int compatibility=0;
    private int violence=0;
    private int cunning=0;
    private int hunger = 0;
    //curiosity?
    protected Inventory items;
    private String name="Alf";
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

    public void setName(String newName){
        if (newName!=null){
            name=newName;
        }

    }

    public void setRanName(Names name){
        name.assignName();
    }

    public void setLocation(int x, int y){
        xLocation = x;
        yLocation = y;
    }

    public void turn(){
        if((stats[4]*1.3)-500>0){
            stats[4] = (stats[4]*1.3)-500;
        }
        else{
            stats[4] = stats[4]*1.3;
        }
    }

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
            System.out.println(this.getName()+" stays Stationary");
            currentWorld.getRoom(xLocation,yLocation).addMover(this);
        }

    }

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

    public void addImpression(Interactions meeting){
        impressions.add(meeting);
    }

    public String getName(){
        return name;
    }

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

    public void setWorld(World aWorld){
        //thought about null protection
        currentWorld=aWorld;
    }

    public World getWorld(){
        return currentWorld;
    }

    public int getStatsNum(){
        return statsNum;
    }

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

    public LinkedList<Interactions> getImpressions(){
        return impressions;
    }

    public Point getCurrentLocation(){
        Point place = new Point(xLocation,yLocation);
        return place;
    }

    public void interact(Mover guy){
        if(guy!=null && this!=null){
            if(impressions.size()==0){
                Interactions action = new Interactions(this , guy);
                Interactions otherAction = new Interactions(guy , this);
                action.compare(this, guy);
                otherAction.compare(guy, this);
                System.out.println("\n");
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
                            if(guy.getImpressions().get(y).equals(name)){
                                guy.getImpressions().get(y).setOption(impressions.get(x).getOption());
                                guy.getImpressions().get(y).resetProbs();
                                guy.getImpressions().get(y).react(guy,this);
                                guy.getImpressions().get(y).setAggression(guy.getImpressions().get(y).getAggression()-guy.getImpressions().get(y).getLove());
                            }
                        }
                        impressions.get(x).setAggression(impressions.get(x).getAggression()-impressions.get(x).getLove());

                    }

                    else{
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
        }
    }

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
