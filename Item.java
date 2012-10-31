
/**
 * This class doesn't really do anything for the AI program yet.
 * 
 * I have big plans for it though.
 * 
 * 
 */

class Item{
    private String itemDesc = "It's a thingamajig. You know... a whatchamacallit.";
    private String itemName = "unnamed item";
    private String itemType = "item";
    private String using; //Message for when you use an item
    private int uses; // how many times you can use it: countsdown when used.
    private String notGetable = "You can't get that.";
    private boolean getable = true;
    private boolean permanent = false; //forever use
    private boolean fixed = false; //can't be picked up
    private String fixederror = "You can't pick that up. It's firmly bolted to something."; //to be displayed when item is fixed
    public Item itemCreate;

    public Item(){
        this(/*name:*/"unnamed", /*type*/"item");
    }

    /**
     * our favorite constructor, others just jump to here.
     */
    public Item( String newName ) {
        this(newName, "item");
    }

    /**
     * our favorite constructor, others just jump to here.
     */
    public Item( String newName, String newType ) {
        if (newName != null) {
            itemName = newName;
        }
        if (newType != null) {
            itemType = newType;
        }

        uses=1;
        permanent=false;
        using="You use "+itemName;
    }

    public void itemTemplate(){
    }

    public String getName(){
        return(itemName);
    }

    public String getDesc(){
        return(itemDesc);
    }

    public String getItemType(){
        return(itemType);
    }

    public int checkStats(){
        return(uses);
    }

    public void setItemCreate(Item makeItem){
        itemCreate=makeItem;
    }

    public void setName(String name){
        itemName=name;
    }

    public void setDesc(String desc){
        itemDesc=desc;
    }

    public void setUses(int x){
        uses=x;
    }

    public void setUsing(String useMessage){
        using=useMessage;
    }

    public void setPermanent(boolean trueFalse){
        permanent=trueFalse;
    }

    public void setGetable(boolean yesNo){
        getable=yesNo;
    }

    public void setNotGetable(String failMessage){
        notGetable=failMessage;
    }

    public void setItemType(String newType){
        itemType=newType;
    }

    public String use(){
        if(getable){
            if(uses==0){
                using="You can't use that anymore";
                return using;
            }
            if(permanent){
                using="You use "+itemName+".";
                return using;
            }
            else{
                uses=uses-1;
                if(uses==1){
                    using="You use "+itemName+". You have "+uses+" "+itemName+" left.";
                }
                else{
                    using="You use "+itemName+". You have "+uses+" "+itemName+"s left.";
                }
            }
        }
        else{
            return notGetable;
        }

        return using;
    }

    public String use(Room thisRoom){
        return this.use();
    }

    public boolean equals(Item newItem){
        return itemName.equals(newItem.getName());
    }
}