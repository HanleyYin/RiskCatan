import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tile{
   
   public int xPos;
   public int yPos;
   public Player player;
   public Color color;
   public int Troops;
   
   public Tile(Color tcolor, Player playerB, int troops, int x, int y){
      xPos = x;
      yPos = y;
      player = playerB;
      color = tcolor;
      Troops = troops;   
   }
   
   public Color getColor(){
      return color;
   }
   
   public void setPlayer(Player playerX){
      player = playerX; 
   } 
   
   public Player getPlayer(){
      return player;
   }
   
   public String getPlayerName(){
      if(player == null){
         return "unoccupied";
      }
      return player.getName();
   }
   
   public int getX(){
      return xPos;
   }
   
   public int getY(){
      return yPos;
   }
   
   public int getTroops(){
      return Troops;
   }
   
   public void addTroop(int a){
      Troops += a;
   }
  
   public String resource(){
      if(color.equals(new Color(97,192,0))){
         return "wool";
      }
      if(color.equals(new Color(90,9,12))){
         return "brick";
      }
      if(color.equals(new Color(217,238,83))){
         return "wheat";
      }
      if(color.equals(new Color(19,36,58))){
         return "ore";
      }
      if(color.equals(new Color(1,24,7))){
         return "wood";
      }
      return "water";
   }
}  
