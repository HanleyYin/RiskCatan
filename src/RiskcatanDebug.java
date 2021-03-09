import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Timer;
import java.util.TimerTask;

public class RiskcatanDebug extends JPanel implements MouseListener{

   private Tile[][] tiles = new Tile[16][10];
   private int mouseX = -1;
   private int mouseY = -1;
   private int mx, my;

   String selectedResource, selectedPlayer, selectedTroops;

   private Timer timer;

   private RiskcatanDebug() {
      JFrame window = new JFrame("CatanBoard");
      window.setBounds(100,100,800,1000);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBackground(Color.WHITE);
      window.getContentPane().add(this);
      window.setVisible(true);
      timer = new Timer();
      TimerTask task = new TimerTask() {
         public void run() {
            repaint();
         }
      };
      timer.scheduleAtFixedRate(task, 1000, 1000/60);

      addMouseListener(this);
      addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseMoved(MouseEvent me)
         {
            mx = me.getX();
            my = me.getY();
         }
      });
   }


   public void paintComponent(Graphics g){
      super.paintComponent(g);
      paintBoard(g);
      drawInfo(g);

   }

   private void paintBoard(Graphics g){
      drawHex(g);
   }

   private void drawHex(Graphics g){
     int xAdj = 0;    
     for(int j = 0; j<14; j++){  
         xAdj = (int)(j*39*1.15);
         for(int i = 0; i<10; i++){
            int adj = i*60;
            if(j%2==1){
               adj+=30;
            }
            if(tiles[j][i] == null){
               tiles[j][i] = new Tile(pickColor(g), null, 0, (int)(xAdj*1.15), adj);
            }
            int[] x = { (int)((0+xAdj)*1.15) , (int)((15+xAdj) * 1.15) , (int)((45+xAdj)*1.15), (int)((60+xAdj)* 1.15),
                    (int)((45+xAdj)* 1.15), (int)((15+xAdj)* 1.15)};
            int[] y = {30+adj,0+adj,0+adj,30+adj,60+adj,60+adj};
            g.setColor(tiles[j][i].getColor());
            //g.drawPolygon(x,y,6); //outines
            g.fillPolygon(x, y, 6); //color
            g.setColor(Color.BLACK);
            g.drawPolygon(x,y,6);

            Font myFont = new Font ("Courier New", Font.BOLD, 12);
            g.setFont(myFont);
            g.drawOval(tiles[j][i].getX(), tiles[j][i].getY(), 3, 3);
            String s = j + ":" + i;
            g.drawString(s ,(int)(tiles[j][i].getX()+25*1.15), tiles[j][i].getY()+30);
         
         }
      }


    }
   private Color pickColor(Graphics g){
      Color[] colors = {new Color(97,192,0), new Color(90,9,12), new Color(217,238,83), new Color(19,36,58), new Color(1,24,7)};
      //                light green,         brick               yellow               ore                    dark green
      //, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE
      //g.setColor(colors[(int)(Math.random()*5)]);
      return( colors[(int)(Math.random()*5)]);
   }

   private void drawSelected(Graphics g, int x, int y){
      g.setColor(Color.GREEN);
      g.drawOval(x-5, y-5, 10, 10);
      g.setColor(Color.RED);
      int[] xcoord = { (int)((0*1.15)+x) , (int)((15*1.15)+x) , (int)((45*1.15)+x), (int)((60*1.15)+x), (int)((45*1.15+x)), (int)((15*1.15)+x)};
      int[] ycoord = {30+y,0+y,0+y,30+y,60+y,60+y};
      g.fillPolygon(xcoord,ycoord,6);
      g.setColor(Color.BLACK);
      g.drawPolygon(xcoord,ycoord,6);

   }

   private void drawInfo(Graphics g) {
      Font myFont = new Font ("Times New Roman", Font.PLAIN, 25);
      g.setFont(myFont);
      g.setColor(Color.BLACK);
      g.drawLine(0, 650,800, 650);
      if(selectedResource == null)
         g.drawString("Resource: Null", 10, 680);
      else
         g.drawString(selectedResource, 10, 680);
      if(selectedPlayer == null)
         g.drawString("Player: Null", 10, 710);
      else
         g.drawString(selectedPlayer, 10, 710);
      if(selectedTroops == null)
         g.drawString("Troops: Null", 10, 740);
      else
         g.drawString(selectedTroops, 10, 740);

      myFont = new Font ("Courier New", Font.PLAIN, 15);
      g.setFont(myFont);
      String s = mx + ", " + my;
      g.setColor(Color.red);
      g.drawString(s, mx, my);

   }

   private Tile getSelectedHexagon(int x, int y)
   {
      int gridHeight = 60;
      int gridWidth = (int)(45*1.15);
      int halfHeight = 30;
      // Find the row and column of the box that the point falls in.
      int row;
      int col = (x / gridWidth);

      boolean colIsOdd = col % 2 == 1;

      // offsets if the column is odd
      if (colIsOdd)
         row = ((y - halfHeight) / gridHeight);
      else
         row = (int)(y / gridHeight);
      System.out.println(row + " " + col);
      // Work out the position of the point relative to the box it is in

      if(col == 0)
         return tiles[col][row];

      double relX = x - (col * gridWidth);
      double relY;

      int b = 30; //used for calculating triangles
      int m = 30/15;

      if (colIsOdd)
         relY = (y - (row * gridHeight)) - halfHeight;
      else
         relY = y - (row * gridHeight);
      // Work out if the point is to the left of either of the hexagon's Left edges
      if(relX <= 15) {
         if (relY < (-m * relX) + b) // top edge
         {
            col--;
            if (!colIsOdd)
               row--;
         } else if (relY > (m * relX) + b) // bot edge
         {
            col--;
            if (colIsOdd)
               row++;
         }
      }

      return tiles[col][row];

   }

   
   public void mouseExited(MouseEvent arg0){} //required MouseListener overrides
   public void mouseEntered(MouseEvent arg0) {}
   public void mousePressed(MouseEvent arg0) {}
   public void mouseReleased(MouseEvent arg0) {}
   
   public void mouseClicked(MouseEvent e){
      mouseX = e.getX();
      mouseY = e.getY();
      System.out.println("X:" + mouseX);
      Tile selectedTile = getSelectedHexagon(mouseX, mouseY);

      selectedResource = "Resource: " + selectedTile.resource();
      selectedPlayer = "Player: " + selectedTile.getPlayerName();
      selectedTroops = "Troops: " + selectedTile.getTroops();

      drawSelected(getGraphics(), selectedTile.getX(), selectedTile.getY()); // draws hexagon at tile position
      //drawSelected(getGraphics(), mouseX, mouseY);   //draws hexagon at exact mouse position

   }

   private int determineTileX(int x){
      return 1;
   }

   public static void main(String[] args){
      JPanel gamePanel = new RiskcatanDebug();

      //while(true){gamePanel.repaint();}
      

      
   }
}