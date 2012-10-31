import java.util.*;

class Room{
    private String roomName = "Room";
    private String roomDesc = "It's a room.";
    private String roomDetail = "There's stuff in it.";
    private String goNMessage = "You go North. ";
    private String goEMessage = "You go East. ";
    private String goWMessage = "You go West. ";
    private String goSMessage = "You go South. ";
    private String goNEMessage = "You travel in the north-eastern direction. ";
    private String goNWMessage = "You travel in the north-western direction. ";
    private String goSEMessage = "You travel in the south-eastern direction. ";
    private String goSWMessage = "You travel in the south-western direction. ";
    private String errorN = "You can't go North.";
    private String errorE = "You can't go East.";
    private String errorW = "You can't go West.";
    private String errorS = "You can't go South.";
    private String errorNE = "You can't go Northeast.";
    private String errorNW = "You can't go Northwest.";
    private String errorSE = "You can't go Southeast.";
    private String errorSW = "You can't go Southwest.";
    private int xLocation;
    private int yLocation;
    //private int currentLocation; We used to use this, but probably not anymore.
    public int howLongForToString=3; //Sets the substring length for the method toString
    private boolean north=false;
    private boolean south=false;
    private boolean east=false;
    private boolean west=false;
    private boolean northeast=false;
    private boolean northwest=false;
    private boolean southeast=false;
    private boolean southwest=false;
    private boolean hasBeenVisited=false;
    private boolean interations = true;
    //used to be private LinkedList<Item> items;
    private Inventory items;
    LinkedList<Mover> peopleInRoom;

    public Room(){
        this("Room");
    }

    public Room(String newNameOfRoom){
        roomName=newNameOfRoom;
        xLocation=0;
        yLocation=0;
        errorN="You can't go that way.";
        errorE="You can't go that way.";
        errorW="You can't go that way.";
        errorS="You can't go that way.";
        errorNE="There's no path going that way. Better stick to the road.";
        errorNW="There's no path going that way. Better stick to the road.";
        errorSE="There's no path going that way. Better stick to the road.";
        errorSW="There's no path going that way. Better stick to the road.";
        north=false;
        south=false;
        east=false;
        west=false;
        items = new Inventory();
        peopleInRoom = new LinkedList<Mover>();
    }

    public void setNMessage(String goMessage){
        goNMessage=goMessage;
    }

    public void setEMessage(String goMessage){
        goEMessage=goMessage;
    }

    public void setWMessage(String goMessage){
        goWMessage=goMessage;
    }

    public void setSMessage(String goMessage){
        goSMessage=goMessage;
    }

    public void setRoomName(String name){
        roomName=name;
    }

    public void setDesc(String desc){
        roomDesc=desc;
    }

    public void setDetail(String detail){
        roomDetail=detail;
    }

    public void setNorth(boolean x){
        north=x;
    }

    public void setSouth(boolean x){
        south=x;
    }

    public void setEast(boolean x){
        east=x;
    }

    public void setWest(boolean x){
        west=x;
    }

    public void setNError(String errorMessage){
        errorN=errorMessage;
    }

    public void setSError(String errorMessage){
        errorS=errorMessage;
    }  

    public void setEError(String errorMessage){
        errorE=errorMessage;
    }  

    public void setWError(String errorMessage){
        errorW=errorMessage;
    }

    public String getRoomName(){
        return(roomName);
    }

    public void getDesc(){
        System.out.println(roomDesc+" "+tabulateItems());
    }

    public void getDetail(){
        System.out.println(roomDetail+" "+tabulateItems());
    }

    public String getNMessage(){
        return goNMessage;
    }

    public String getEMessage(){
        return goEMessage;
    }

    public String getWMessage(){
        return goWMessage;
    }

    public String getSMessage(){
        return goSMessage;
    }

    public String getNEMessage(){
        return goNEMessage;
    }

    public String getNWMessage(){
        return goNWMessage;
    }

    public String getSEMessage(){
        return goSEMessage;
    }

    public String getSWMessage(){
        return goSWMessage;
    }

    public void getErrorN(){
        System.out.println(errorN);
    }

    public void getErrorE(){
        System.out.println(errorE);
    }

    public void getErrorW(){
        System.out.println(errorW);
    }

    public void getErrorS(){
        System.out.println(errorS);
    }

    public void getErrorNE(){
        System.out.println(errorNE);
    }

    public void getErrorNW(){
        System.out.println(errorNW);
    }

    public void getErrorSE(){
        System.out.println(errorSE);
    }

    public void getErrorSW(){
        System.out.println(errorSW);
    }

    public boolean getNorth(){
        return(north);
    }

    public boolean getSouth(){
        return(south);
    }

    public boolean getEast(){
        return(east);
    }

    public boolean getWest(){
        return(west);
    }

    public boolean getNortheast(){
        return(northeast);
    }

    public boolean getNorthwest(){
        return(northwest);
    }

    public boolean getSoutheast(){
        return(southeast);
    }

    public boolean getSouthwest(){
        return(southwest);
    }
    
    public LinkedList<Mover> getMovers(){
        return peopleInRoom;
    }
    
    public int getMoversNum(){
        return peopleInRoom.size();
    }

    public void addItem(Item newItem){
        if(newItem!=null){
            items.add(newItem);
        }
    }

    public void removeItem(Item newItem){
        items.remove(newItem);
    }

    public void addMover(Mover newMover){
        if (newMover != null){
            int rollForInitiative = (int)Math.floor(Math.random()*2);
            peopleInRoom.add(newMover);
            if (peopleInRoom.size()==2){
                peopleInRoom.get(rollForInitiative).interact(peopleInRoom.get(1-rollForInitiative));
            }
        }
    }

    public void removeMover(Mover newMover){
        if (newMover != null){
            peopleInRoom.remove(newMover);
        }
    }

    public boolean hasAlien( ) {
        if (peopleInRoom.size()<1){
            return false;
        }else {
            for(int x=0; x<peopleInRoom.size()-1; x++){
                Mover currMover = peopleInRoom.get(x);
                if (currMover.isAlien) {
                    //if (currMover.instanceOf (Class.Alien)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setHasBeenVisited(){
        hasBeenVisited=true;
    }

    public boolean getHasBeenVisited(){
        return hasBeenVisited;
    }

    /**
     * Verify that the room has the specified item
     */
    public Item checkItem(String itemName){
        for(int x=0; x<items.size(); x++){
            if(items.get(x).getName().equals(itemName)){
                return items.get(x);
            }
        }
        return null;
    }

    public Item checkItemType(String itemType){
        for(int x=0; x<items.size(); x++){
            if( items.get(x).getItemType().equals(itemType)){
                return items.get(x);
            }
        }
        return null;
    }

    public String tabulateItems(){
        StringBuffer temp;
        if(items.size()>0){
            temp=new StringBuffer("The room contains: ");
            for(int x=0; x<items.size()-1; x++){
                temp.append(items.get(x).getName()+", ");
            }
            if(items.size()>1){
                temp.append("and ");
            }
            temp.append(items.get(items.size()-1).getName()+".");
        }
        else{
            temp=new StringBuffer("This room contains no items that you can take.");
        }
        return temp.toString();
    }

    /**
     * ?? Incomplete search -- search for particular types of items
     */
    public String searchItemType(){
        StringBuffer temp;
        if(items.size()>0){
            temp=new StringBuffer("The room contains: ");
            for(int x=0; x<items.size()-1; x++){
                temp.append(items.get(x).getName()+", ");
            }
            if(items.size()>1){
                temp.append(temp+"and ");
            }
            temp.append(items.get(items.size()-1).getName()+".");
        }
        else{
            temp=new StringBuffer("This room contains no items that you can take.");
        }
        return temp.toString();
    }

    /**
     * returns specified number of blank spaces
     */
    public String padding(int howManyPads){
        if(howManyPads>0){
            return "";
        }
        else{
            StringBuffer temp=new StringBuffer();
            for(int x=1; x<=howManyPads; ++x){
                temp.append(" ");
            }
            return temp.toString();
        }

    }

    /**
     * Returns shorthand form, with blank padding if necessary
     * to be exactly howLongForToString  characters long.
     */
    public String toString(){
        //         if ((this.getMovers( ).length( )) > 0) {
        //             return 
        //         }
        if(roomName.length()<howLongForToString){
            int howMuchPaddingNeeded = howLongForToString-roomName.length();
            return "["+roomName+padding(howMuchPaddingNeeded)+"]";

        }
        else{
            return "["+roomName.substring(0, howLongForToString)+"]";
        }
    }

}
