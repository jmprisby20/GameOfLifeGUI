// Jake Prisby
// 2/12/2021
// Game Of Life with GUI

// Imports
import GameOfLife.LifeGUI;
import javax.swing.JFrame;

public class GameOfLife {

   // Main Method
   public static void main ( String[] args ) {
      LifeGUI life = new LifeGUI(); // GUI Object 
      javax.swing.SwingUtilities.invokeLater(
         new Runnable(){
            public void run(){
               runGUI(); // Call to method that runs the GUI
            }
         });
   }
   
   // Method that sets GUI decorations to be handled by the system
   private static void runGUI(){
        JFrame.setDefaultLookAndFeelDecorated(true);
    }   
}