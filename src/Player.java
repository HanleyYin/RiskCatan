public class Player{
   public String name;
   
   public Player(String playerName){
      name = playerName;
   }
   
   public String getName(){
      if(name.equals(null)){
         return "unoccupied";
      }
      return name;
   }
}