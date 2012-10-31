/**
 * 
 * This is a code using a lot of bits from my main game that I had to comment out to make it compile.
 * 
 * This was my first method of creating random characters,
 * and my current method of making and destributing random items throughout the grid.
 * 
 * Only used in my main game so far, but will be really helpful when I add items.
 */

public class MagicMachine

{

int howManyKeysIveMade = 0;
int howManyBoxesIveMade = 0;
int howManyPadsIveMade = 0;
    /**
     * Constructor for objects of class MagicMachine
     */
    public MagicMachine()
    {
        // initialise instance variables
    }

    //Since there's no argument, it can make anything.
//     public Item create(){
//         Item newItem = null;
//         double randomNum = Math.random( );  // will return [0.0-1.0)
//         if(randomNum <=.25){
//             newItem = new Key();
//             howManyKeysIveMade = howManyKeysIveMade+1;
//         }
//         else if(randomNum <= .5){
//             newItem = new Box();
//             howManyBoxesIveMade = howManyBoxesIveMade+1;
//         }
//         else if(randomNum <=.75){
//             newItem = new PadOfPaper();
//             howManyPadsIveMade++;
//         }
//         else{
//         }
//         return newItem;
//     }
// 
//     public Item create(String itemType){
//         Item newItem = null;
//         if(itemType.equals("key")){
//             newItem = new Key();
//             howManyKeysIveMade = howManyKeysIveMade+1;
//         }
//         else if(itemType.equals("box")){
//             newItem = new Box();
//             howManyBoxesIveMade++;
//         }
//         else if(itemType.equals("pad")){
//             newItem = new PadOfPaper();
//             howManyPadsIveMade++;
//         }
//         return newItem;
//     }
    public Mover createMover(World aWorld){
        Mover guy= new Mover("random");
        return guy;
    }
        
}
