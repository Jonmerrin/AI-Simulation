import java.io.*;
import java.util.*;

/**
 * The Main class.
 */
class AI{
    public static void main(String[] args){
        //boolean makes sure game runs continuously
        boolean run = true;

        //Creates the grid on which the characters move
        World theWorld = new World(10,10,"Earth");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //selects a random number of movers. Movers are the class that are the "characters"
        int characterNum = (int)Math.ceil(Math.random()*20);

        //makes sure that there are no less than 10 movers at a time. It would be pretty boring to just have one guy walking around on his own.
        while (characterNum<10){
            characterNum = (int)Math.ceil(Math.random()*20);
        }

        //Populates the grid with movers. Their respective locations are randomly selected in their constructors.
        for(int x=0; x<characterNum; x++){
            theWorld.createMover();
        }
        //Printing the world prints out a grid that shows where all of the movers are.
        System.out.println(theWorld);

        //Start of it all.
        while(run){

            //Makes sure the input is readable
            String input = null;
            String input2 = null;
            String miscString = null;
            try{
                input=br.readLine();
            }
            catch(IOException ioe){
                System.out.println("I don't know what you're saying. You lose. Automatically. Forever.");
                System.exit(1);
            }
            input=input.toLowerCase();

            //allows the player to "hit enter to continue"
            if(input.length()==0){
                //clears the map of all existing movers' locations
                for(int y=0; y<theWorld.getHeight(); y++){
                    for(int x=0; x<theWorld.getWidth(); x++){
                        theWorld.getRoom(x,y).getMovers().clear();
                    }
                }
                //moves all the movers forward a step and places them back on the map.
                for(int z=0; z<theWorld.getPeople().size(); z++){
                    theWorld.getPeople().get(z).move();
                    theWorld.getPeople().get(z).turn();

                }
                System.out.println(theWorld);
            }
            //shows the map
            if(input.equals("map")){
                System.out.println(theWorld);
            }
            // I used "move" before I got the "hit enter to continue" option working. 
            //I'm still not sure which I like better, so I didn't delete this one yet.

            //             if(input.equals("move")){
            //                 for(int y=0; y<theWorld.getHeight(); y++){
            //                     for(int x=0; x<theWorld.getWidth(); x++){
            //                         theWorld.getRoom(x,y).getMovers().clear();
            //                     }
            //                 }
            //                 for(int z=0; z<theWorld.getPeople().size(); z++){
            //                     theWorld.getPeople().get(z).move();
            // 
            //                 }
            //                 System.out.println(theWorld);
            //             }

            //shows how many people are in the world. I used it for bug checking purposes in case people disappeared.
            if(input.equals("num")){
                System.out.println(theWorld.getPeople().size());
            }

            //shows which room all the movers are in.
            if(input.equals("room num")){
                for(int y=0; y<theWorld.getHeight(); y++){
                    for(int x=0; x<theWorld.getWidth(); x++){
                        if(theWorld.getRoom(x,y).getMoversNum()>0){
                            System.out.println("Room ("+x+","+y+") contains "+ theWorld.getRoom(x,y).getMoversNum() + " people.");
                        }
                    }
                }
            }

            //shows a list of all the movers
            if(input.equals("attendance")){
                for(int x=0; x<theWorld.getPeople().size(); x++){
                    System.out.println(theWorld.getPeople().get(x).getName());
                }
            }

            if(input.equals("add")){
                theWorld.createMover();
            }

            if(input.startsWith("remove ")){
                if(input.length()>6){
                    input=input.substring(input.indexOf(" ")+1);
                    input = input.substring(0,1).toUpperCase()+input.substring(1);
                    for(int x=0; x<theWorld.getPeople().size(); x++){
                        if(input.equals(theWorld.getPeople().get(x).getName()) || input.equals(theWorld.getPeople().get(x).getName().substring(0,1))){
                            theWorld.getRoom(theWorld.getPeople().get(x).getXLocation(),theWorld.getPeople().get(x).getYLocation()).removeMover(theWorld.getPeople().get(x));
                            theWorld.removeMover(theWorld.getPeople().get(x));
                        }
                    }
                    System.out.println(theWorld);
                }
            }

            if(input.startsWith("view ")){
                if(input.length()>5){
                    input=input.substring(5);
                    if(input.startsWith("relationships ")){
                        if(input.length()>14){
                            input=input.substring(input.indexOf(" ")+1);
                            input=input.substring(0,1).toUpperCase()+input.substring(1);
                            for(int x=0;x<theWorld.getPeople().size();x++){
                                if(theWorld.getPeople().get(x).getName().equals(input)){
                                    if(theWorld.getPeople().get(x).getImpressions().size()==0){
                                        System.out.println(theWorld.getPeople().get(x).getName() + " has yet to meet anyone.");
                                    }
                                    else{
                                        for(int y=0;y<theWorld.getPeople().get(x).getImpressions().size();y++){

                                            System.out.println(theWorld.getPeople().get(x).getImpressions().get(y).getRelationship());
                                        }
                                    }
                                }
                            }

                        }
                    }
                    //Doesn't work yet
                    else if(input.startsWith("inv")){
                        if(input.length()>4){
                            input = input.substring(input.indexOf(" ")+1);
                            input=input.substring(0,1).toUpperCase()+input.substring(1);
                            for(int x=0;x<theWorld.getPeople().size();x++){
                                if(theWorld.getPeople().get(x).getName().equals(input)){
                                    if(theWorld.getPeople().get(x).getInventory().size()==0){
                                        System.out.println(theWorld.getPeople().get(x).getName() + " isn't carrying anything.");
                                    }
                                    else{
                                        System.out.println("He's got something!");
                                    }
                                }
                            }
                        }
                    }
                    else if(input.startsWith("profile ")){
                        if(input.length()>8){
                            input = input.substring(input.indexOf(" ")+1);
                            input = input.substring(0,1).toUpperCase()+input.substring(1);
                            for(int x=0; x<theWorld.getPeople().size(); x++){
                                if(theWorld.getPeople().get(x).getName().equals(input)){
                                    System.out.println(theWorld.getPeople().get(x));
                                }
                            }
                        }
                    }
                    else if(input.startsWith("story ")){
                        if(input.length()>6){
                            input = input.substring(input.indexOf(" ")+1);
                            input2 = input.substring(input.indexOf(" ")+1);
                            input = input.substring(0,input.indexOf(" "));
                            input = input.substring(0,1).toUpperCase()+input.substring(1);
                            input2 = input2.substring(0,1).toUpperCase()+input2.substring(1);
                            //System.out.println(input+"\n"+input2);
                            for(int x=0; x<theWorld.getPeople().size(); x++){
                                if(theWorld.getPeople().get(x).getName().equals(input)){
                                    for(int y = 0; y<theWorld.getPeople().get(x).getImpressions().size(); y++){
                                        if(theWorld.getPeople().get(x).getImpressions().get(y).getOtherName().equals(input2)){
                                            System.out.println(theWorld.getPeople().get(x).getImpressions().get(y).getStory());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("");
            System.out.println("Type a command, or hit enter to continue");
        }
    }
}