import java.util.*;

/**
 * Interaction belongs to a character. Shows his feelings about another character.
 * Will also do an action or a reaction.
 * 
 */
public class Interactions
{
    // All of the attributes that will be used to describe interpersonal relationships that I have gotten around to putting in.
    // The reason I don't have any female characters yet is because I'd have to start work on lust and flirtation and rejection
    // and the multitudes of other things describing what could happen in a relationship between a boy and a girl (including reproduction and the friend zone)
    // But that's next on my list.
    private double love = 0;
    private int aggression = 0;
    private int compatibility = 0;
    private int violence = 0;
    private int cunning = 0;
    private double finalAgg = 0;
    private double finalCom = 0;
    private double finalCun = 0;
    private double finalVio = 0;
    private String interaction;
    private String otherName;
    private int option;
    private boolean positive;
    private double aggressionProb;
    private double compatibilityProb;
    private double violenceProb;
    private double cunningProb;

    /**
     * Constructor for Interactions
     * 
     * Just sets up the defaults.
     */
    public Interactions(Mover person, Mover secondPerson)
    {
        // initialise instance variables
        otherName = secondPerson.getName();
        aggression = person.getAggression();
        compatibility = person.getCompatibility();
        violence = person.getViolence();
        cunning = person.getCunning();
        finalAgg = 0;
        finalCom = 0;
        finalCun = 0;
        finalVio = 0;
        option = 0;
        positive = true;
        aggressionProb = 0;
        compatibilityProb = 0;
        violenceProb = 0;
        cunningProb = 0;
    }

    /**
     * this resets the aggression after it's gone through a compare()
     * (which is why it's the final aggression rather than the original)
     * 
     * This is used for updating based on emotional reactions to actions.
     */
    public void setAggression(double x){
        finalAgg=x;
    }

    //gets the aggression after a compare
    public double getAggression(){
        return finalAgg;
    }

    //The following set and get all of the attributes held by this class:
    public void setCompatibility(int x){
        compatibility=x;
    }

    public void setCunning(int x){
        cunning=x;
    }

    public void setViolence(int x){
        violence=x;
    }

    //gets the name of the character whom this is an impression of.
    //useful for finding out if two characters have met before.
    public String getOtherName(){
        return otherName;
    }

    //This is purely the result of actions and reactions.
    //These contribute to the title given (enemy, nuisance, nothing, acquaintance, friend)
    public void setLove(double x){
        love = x;
    }

    public double getLove(){
        return love;
    }

    //Positive is meant to show if the reaction was positive or negative.
    //This is used to change the other character's love stat after they have already finished interacting.
    public boolean getPositive(){
        return positive;
    }

    //Resets the probabilities of aggression and compatibility after a change in feeling between the two characters has occurred.
    public void resetProbs(){
        aggressionProb = finalAgg/(finalAgg+finalCom);
        compatibilityProb = finalCom/(finalAgg+finalCom);
    }

    /**
     * The interaction between two characters. Figured it would be simpler to hold it all in its own class.
     * 
     * This compares one character's stats against the other to form a series of probabilities that will be used to form a likely course of action.
     */
    public void compare(Mover guy, Mover otherGuy){
        double dAggression = 0;
        double dCompatibility = 0;
        double dViolence = 0;
        double dCunning = 0;
        otherName = otherGuy.getName();

        //NEW CONDENSED VERSION:
        for(int x=0; x<2; x++){
            dCompatibility = dCompatibility + (2-Math.abs(guy.getStat(x)-guy.getStat(x)));
            if (x==0){
                dViolence = dViolence + (guy.getStrength()-otherGuy.getStrength());
            }
            if (x==1){
                dCunning = dCunning +(guy.getIntellect()-otherGuy.getIntellect());
            }
        }
        //COMPARING MORALITY
        //VILLAIN -- Should stay compared to everything? Could account for manipulation.
        //STRENGTH -- Constant dCompatibility of +1
        if (guy.getMorality()<=3 && otherGuy.getStrength()<=3){
            dViolence = dViolence+1;
            dAggression = dAggression+1;
            dCunning = dCunning+1;
            dCompatibility = dCompatibility+1;
        }
        else if (guy.getMorality()<=3 && otherGuy.getStrength()<=6){
            dViolence = dViolence+1;
            dAggression = dAggression+1;
            dCunning = dCunning+2;
        }
        else if (guy.getMorality()<=3 && otherGuy.getStrength()<=9){
            dAggression = dAggression+1;
            dCunning = dCunning+3;
        }
        else if (guy.getMorality()<=3 && otherGuy.getStrength()>9){
            dViolence = dViolence-1;
            dAggression = dAggression+1;
            dCunning = dCunning+4;
        }
        //INTELLECT

        if (guy.getMorality()<=3 && otherGuy.getIntellect()<=3){
            dViolence = dViolence+1;
            dAggression = dAggression+1;
            dCunning = dCunning+2;
            dCompatibility = dCompatibility+3;
        }
        else if (guy.getMorality()<=3 && otherGuy.getIntellect()<=6){
            dViolence = dViolence+1;
            dAggression = dAggression+2;
            dCunning = dCunning+2;
            dCompatibility = dCompatibility+2;
        }
        else if (guy.getMorality()<=3 && otherGuy.getIntellect()<=9){
            dViolence = dViolence+1;
            dAggression = dAggression+3;
            dCunning = dCunning+1;
            dCompatibility = dCompatibility+2;
        }
        else if (guy.getMorality()<=3 && otherGuy.getIntellect()>9){
            dViolence = dViolence+1;
            dAggression = dAggression+4;
            dCunning = dCunning+1;
            dCompatibility = dCompatibility+1;
        }
        //MORALITY

        if (guy.getMorality()<=3 && otherGuy.getMorality()<=3){
            dAggression = dAggression+2;
            dCompatibility = dCompatibility+2;
        }
        else if (guy.getMorality()<=3 && otherGuy.getMorality()<=6){
            dCompatibility = dCompatibility+1;
        }
        else if (guy.getMorality()<=3 && otherGuy.getMorality()<=9){
            dAggression = dAggression+3;
            dCompatibility = dCompatibility-1;
        }
        else if (guy.getMorality()<=3 && otherGuy.getMorality()>9){
            dAggression = dAggression+4;
            dCompatibility = dCompatibility-2;
        }

        //SAMARITAN...THINK ABOUT MORE
        //STRENGTH...NOT DONE YET
        if (guy.getMorality()<=6 && guy.getMorality()>3 && otherGuy.getMorality()<=3){
            dViolence = dViolence+1;
            dCompatibility = dCompatibility+1;
        }
        else if (guy.getMorality()<=6 && guy.getMorality()>3 && otherGuy.getMorality()<=6){
            dCompatibility = dCompatibility+3;
        }
        else if (guy.getMorality()<=6 && guy.getMorality()>3 && otherGuy.getMorality()<=9){
            dCunning = dCunning+1;
            dCompatibility = dCompatibility+1;
        }
        else if (guy.getMorality()<=6 && guy.getMorality()>3 && otherGuy.getMorality()>9){
            dAggression = dAggression+1;
            dCunning = dCunning+2;
            dCompatibility = dCompatibility-2;
        }

        if (guy.getMorality()<=9 && guy.getMorality()>6 && otherGuy.getMorality()<=3){
            dAggression = dAggression+3;
            dCompatibility = dCompatibility-1;
        }
        else if (guy.getMorality()<=9 && guy.getMorality()>6 && otherGuy.getMorality()<=6){
            dCunning = dCunning+1;
            dCompatibility = dCompatibility+1;
        }
        else if (guy.getMorality()<=9 && guy.getMorality()>6 && otherGuy.getMorality()<=9){
            dCunning = dCunning+2;
            dCompatibility = dCompatibility+3;
        }
        else if (guy.getMorality()<=9 && guy.getMorality()>6 && otherGuy.getMorality()>9){
            dCunning = dCunning+2;
            dAggression = dAggression-1;
            dCompatibility = dCompatibility+2;
        }

        //ANGEL
        //MORALITY
        if (guy.getMorality()>9 && otherGuy.getMorality()<=3){
            dAggression = dAggression+2;
            dCompatibility = dCompatibility-2;
        }
        //Hold off on this one...
        //         else if (guy.getMorality()>9 && otherGuy.getMorality()<=6){
        //
        //         }
        else if (guy.getMorality()>9 && otherGuy.getMorality()<=9){
            dCompatibility = dCompatibility+2;
        }
        else if (guy.getMorality()>9 && otherGuy.getMorality()>9){
            dAggression = dAggression-2;
            dCunning = dCunning+2;
            dCompatibility = dCompatibility+3;
        }
        finalAgg = dAggression+aggression;
        finalCom = dCompatibility+compatibility;
        finalCun = dCunning+cunning;
        finalVio = dViolence+violence;

        System.out.println(guy.getName() + " met " + otherGuy.getName()+".");

        //For testing purposes, I used to print all the probabilities.
        //         System.out.println("Agression: " + finalAgg);
        //         System.out.println("Compatibility: " + finalCom);
        //         System.out.println("Cunning: " + finalCun);
        //         System.out.println("Violence: " + finalVio);
        //         System.out.println("Probability of Compatibility: " + 100*finalCom/(finalCom+finalAgg) + "%");
        //         System.out.println("Probability of Aggression: " + 100*finalAgg/(finalCom+finalAgg) + "%");
        //         System.out.println("\n");

        aggressionProb = finalAgg/(finalCom+finalAgg);
        compatibilityProb = finalCom/(finalCom+finalAgg);
        violenceProb = finalVio/(finalVio+finalCun);
        cunningProb = finalCun/(finalVio+finalCun);
    }

    //Returns which course of action was chosen.
    public int getOption(){
        return option;
    }

    //sets which course of action was chosen.
    public void setOption(int x){
        option = x;
    }

    //returns the final violence stat. I was going to make one for each of them, but didn't encounter the need to, so I put it off until after handing it in.
    public double getFinalVio(){
        return finalVio;
    }

    /**
     * The interaction method.
     * 
     * This method takes the probabilities established in compare(Mover,Mover) and selects a likely course of action using a couple of Math.random()'s.
     * 
     * You'll notice that the 6 options only say what they can do rather than actually do them.
     * This is because we can't be sure that their action will succeed until we see the reaction.
     * For this reason, all statements that actually change things are held in the react method.
     */
    public void interact(Mover guy, Mover otherGuy){
        if(finalAgg<0){
            aggressionProb = 0;
        }
        else{
            aggressionProb = finalAgg/(finalCom+finalAgg);
        }
        if(finalCom<0){
            compatibilityProb = 0;
        }
        else{
            compatibilityProb = finalCom/(finalCom+finalAgg);
        }
        if (finalVio<0){
            violenceProb = 0;
        }
        else{
            violenceProb = finalVio/(finalVio+finalCun);
        }
        if(finalCun<0){
            cunningProb = 0;
        }
        else{
            cunningProb = finalCun/(finalVio+finalCun);
        }
        double friendOrFoe = Math.random();
        double mindOrMatter = Math.random();

        if (friendOrFoe <= aggressionProb){
            //             System.out.println("EVIL!!!");

            if(violenceProb==0 && cunningProb==0){
                System.out.println("INACTION!!!");
                System.out.println(guy.getName() + " does nothing to " + otherGuy.getName() + ", but he doesn't seem to very much like him.");
                option = 0;
            }
            else if(mindOrMatter<=violenceProb){
                System.out.println(guy.getName() + " starts an argument with " + otherGuy.getName());
                option = 1;
            }
            else if(guy.getMorality()<5 && otherGuy.getWealth()>guy.getWealth()*2){
                System.out.println(guy.getName() + " tries to steal from " + otherGuy.getName());
                option = 2;
            }
            else{
                System.out.println(guy.getName() + " makes a witty insult about " + otherGuy.getName());
                option = 3;
            }

        }
        else{
            //             System.out.println("FRIENDSHIP!!!");
            if(mindOrMatter <= cunningProb){
                System.out.println(guy.getName() + " starts a casual conversation with " + otherGuy.getName());
                option = 4;
            }
            else if(guy.getMorality()>6 && guy.getWealth()>=otherGuy.getWealth()*2){
                System.out.println(guy.getName() + " tries gives charity to " + otherGuy.getName());
                option = 5;
            }
            else{
                System.out.println(guy.getName() + " gets into a hearty debate with " + otherGuy.getName());
                option = 6;
            }
        }

    }

    /**
     * The reaction method.
     * 
     * This method sees which of the six available options the interaction class chose.
     * Then based on their own attributes, it picks how the character might respond.
     * 
     * This also uses the probabilities generated in compare(Mover,Mover)
     * 
     * another reiteration of what positive does:
     * If positive is true, the other guy will get +1 love
     * If positive is false, the other guy will get -1 love
     * 
     * Some of the reactions involve lowering the other player's health.
     * When the player's health hits 0, they'll die, but I still don't want to put that in until I have a method of repopulation.
     * I'd rather not make a last-man-standing type situation.
     * 
     */
    public void react(Mover guy, Mover otherGuy){
        double response = Math.random();
        double response2 = Math.random();
        if(option == 0){
            System.out.println(guy.getName()+" also keeps to himself.");
            positive = true;
        }
        if(option == 1){
            if (response<=aggressionProb){
                System.out.println(guy.getName() + " argues back against " + otherGuy.getName());
                positive = false;
                love = love-1;
            }
            else {
                System.out.println(guy.getName() + " ignores " + otherGuy.getName());
                positive = false;
                love = love-1;
            }
        }
        if(option == 2){
            if(guy.getStrength()>otherGuy.getStrength()){
                System.out.println(guy.getName() + " defends himself against " + otherGuy.getName());
                otherGuy.setHP(otherGuy.getHealth()-2);
                positive = false;
                love = love-1;
            }
            else{
                int moneyTaken = (int)Math.ceil(Math.random()*5000);
                guy.setWealth(guy.getWealth()-moneyTaken);
                otherGuy.setWealth(otherGuy.getWealth()+moneyTaken);
                positive = true;
                if(response <= violenceProb){
                    System.out.println(guy.getName() + " attempts to defend himself against " + otherGuy.getName() + ", but fails.");
                    guy.setHP(guy.getHealth()-2);
                    love = love-3;
                }
                else{
                    System.out.println(guy.getName() + " gave up his money to " + otherGuy.getName());
                    love = love-2;
                }
            }
        }
        if(option == 3){
            if(response<=aggressionProb){
                if(response2<=violenceProb){
                    System.out.println(guy.getName() + " punches " + otherGuy.getName() + " in the face and storms off.");
                    guy.move();
                    otherGuy.setHP(otherGuy.getHealth()-.5);
                    positive = false;
                }
                else{
                    if(guy.getIntellect()>otherGuy.getIntellect()){
                        System.out.println(guy.getName() + " replies to " + otherGuy.getName() + " with an even wittier retort.");
                        love = love+1;
                        positive = false;
                    }
                    else if(guy.getIntellect()>5){
                        System.out.println(guy.getName() + " replies to " + otherGuy.getName() + " in kind.");
                        love = love-1;
                        positive = false;
                    }
                    else{
                        System.out.println(guy.getName() + " tries to form a clever response, but fails.");
                        love = love-2;
                        positive = true;
                    }
                }
            }
            else{
                if(guy.getIntellect()>otherGuy.getIntellect()){
                    System.out.println(guy.getName() + " light-heartedly retorts with another witty comment.");
                    positive = false;
                    love = love+1;
                }
                else{
                    System.out.println(guy.getName() + " ignores " + otherGuy.getName());
                    positive = false;
                    love=love-1;
                }
            }
        }
        if(option == 4){
            if(response<=aggressionProb){
                if(response2<=violenceProb){
                    System.out.println(guy.getName() + " rudely ignores " + otherGuy.getName());
                    positive = false;
                }
                else{
                    System.out.println(guy.getName() + " responds rudely to " + otherGuy.getName());
                    positive = false;
                }
            }
            else{
                System.out.println(guy.getName() + " responds positively in conversation with " + otherGuy.getName());
                love = love+1;
                positive = true;
            }
        }
        if(option == 5){
            if(response<=aggressionProb){
                if(response2<=violenceProb){
                    System.out.println(guy.getName() + " refuses " + otherGuy.getName()+"'s charity, and throws away his moneys.");
                    int howMuchMoney = (int)Math.ceil(Math.random()*2000);
                    guy.setWealth(guy.getWealth()-howMuchMoney);
                    positive = false;
                    love=love-1;
                }
                else{
                    System.out.println(guy.getName() + " refuses " + otherGuy.getName()+"'s charity, and insults him personally for giving it.");
                    positive = false;
                    love = love-1;
                }

            }
            else{
                if(response2<=cunningProb){
                    System.out.println(guy.getName() + " accepts the charity graciously.");
                    positive = true;
                }
                else{
                    System.out.println(guy.getName() + " accepts the charity begrudgingly");
                    positive = false;

                }
            }
        }
        if(option == 6){
            if(response<=aggressionProb){
                System.out.println(guy.getName() + " irritably insults " + otherGuy.getName());
                positive = false;
                love = love-1;
            }
            else{
                System.out.println(guy.getName() + " enjoys the debate with " + otherGuy.getName());
                positive = true;
                love = love+1;
                double whoWins = Math.random();
                if(whoWins<=otherGuy.getIntellect()/(otherGuy.getIntellect()+guy.getIntellect())){
                    System.out.println(guy.getName() + " won the debate! Good for him.");
                }
                else{
                    System.out.println(otherGuy.getName() + " won the debate! Good for him.");
                }

            }

        }
    }
}

