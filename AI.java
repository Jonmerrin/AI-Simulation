import java.io.*;
import java.util.*;


class AI{

    public static void main(String[] args){
        boolean run = true;
        World theWorld = new World(10,10,"Earth");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int characterNum = (int)Math.ceil(Math.random()*20);
        while (characterNum<10){
            characterNum = (int)Math.ceil(Math.random()*20);
        }
        for(int x=0; x<characterNum; x++){
            theWorld.createMover();
        }
        System.out.println(theWorld);

        while(run){

            String input = null;
            try{
                input=br.readLine();
            }
            catch(IOException ioe){
                System.out.println("I don't know what you're saying. You lose. Automatically. Forever.");
                System.exit(1);
            }
            input=input.toLowerCase();

            if(input.equals(null)){

                System.out.println(theWorld);
            }
            if(input.equals("map")){
                System.out.println(theWorld);
            }
            if(input.equals("move")){
                for(int y=0; y<theWorld.getHeight(); y++){
                    for(int x=0; x<theWorld.getWidth(); x++){
                        theWorld.getRoom(x,y).getMovers().clear();
                    }
                }
                for(int z=0; z<theWorld.getPeople().size(); z++){
                    theWorld.getPeople().get(z).move();

                }
                System.out.println(theWorld);
            }
            if(input.equals("num")){
                System.out.println(theWorld.getPeople().size());
            }
            if(input.equals("room num")){
                for(int y=0; y<theWorld.getHeight(); y++){
                    for(int x=0; x<theWorld.getWidth(); x++){
                        if(theWorld.getRoom(x,y).getMoversNum()>0){
                            System.out.println("Room ("+x+","+y+") contains "+ theWorld.getRoom(x,y).getMoversNum() + " people.");
                        }
                    }
                }
            }
            if(input.equals("attendence")){
                for(int x=0; x<theWorld.getPeople().size(); x++){
                    System.out.println(theWorld.getPeople().get(x).getName());
                }
            }

        }
    }
}