// Jake Prisby
// 2/12/2021
// Game Of Life with GUI

package GameOfLife; // Add this Class to GameOfLife Package

// Imports
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


// Class ( public ) for the GUI for the Game Of Life
public class LifeGUI implements ActionListener {
   
   // Internal Fields
   JFrame frame; // Frame
   JPanel contentPane; // Content Pane
   JButton[][] cellBtn = new JButton[20][20]; // Array (Two-Dimensional) Button objects that will visually represent the cell objects
   Cell[][] cells = new Cell[20][20]; // Array( Two-Dimensional ) of cell objects
   JButton btnS; // A button that will start the game
      
   // Contstructor
   public LifeGUI() {
      
      this.frame = new JFrame("Game Of Life"); // Call to JFrame Constructor, Title of Frame set to "Game Of Life"
      this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame set to close when exit is clicked
      
      this.contentPane = new JPanel(); // Call to ContentPane Constructor
      this.contentPane.setLayout(new GridLayout(21,20)); // ContentPane is given layout of 21 rows, 20 columns
      this.contentPane.setBackground(Color.black); // Background color of the ContentPane is set to be black
      this.frame.setContentPane(contentPane); // ContentPane of the JFrame is set to be the ContentPane that was constructed
      
      // For loop that will be used to navigate the array of Cell Objects
      for(int r = 0; r < this.cells.length; r++){
         for(int c = 0; c < this.cells[r].length; c++){
         
            this.cells[r][c] = new Cell(); // Call to cell Constructor
            this.cellBtn[r][c] = new JButton(); // Call to JButton Constructor
            this.cellBtn[r][c].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set Border for the Buttons
            this.cellBtn[r][c].addActionListener(this); // Add ActionListener to the JButton Objects
            this.cellBtn[r][c].setActionCommand(r + " " + c); // Add ActionCommand to the JButton
            this.cellBtn[r][c].setBackground(Color.BLACK); // Default Color of Cell is red to represent a dead cell
            this.contentPane.add(this.cellBtn[r][c]); // JButton Object is added to the ContentPane
                     
         }
      }
   
      this.btnS = new JButton("Start"); // Call to Constructor for JButton Object, Text on button will display "Start"
      this.btnS.addActionListener(this); // Add ActionListener to this JButton Object
      this.btnS.setActionCommand("start"); // Add ActionCommand to this JButton Object
      this.contentPane.add(btnS); // Add this JButton to the ContentPane
     
      this.frame.pack(); // Set size of frame based on Objects inside of it
      this.frame.setVisible(true); // JFrame is set to visible
   
   }

   // Swing Timer, Tick every 500 milisecs
   Timer time = new Timer( 500, 
      new ActionListener() {
         public void actionPerformed( ActionEvent evt ) {
            updateGUI(); // Call to method that will update the GUI through the generations of the game
         }
      });

   // ActionPerformed Method 
   public void actionPerformed(ActionEvent event){
      String eventName = event.getActionCommand(); // String that will hold the name of the event
      
      // Navigate the Cell Objects 
      for(int r = 0; r < this.cells.length; r++){
         for(int c = 0; c < this.cells[r].length; c++){  
         
            String btnNum = r + " " + c; // String is set equal to the coordinates of the Cell Object
            // Check if a button representing a cell is being pressed
            if( eventName.equals( btnNum ) ) {     
               if ( this.cells[r][c].getIsAlive() == false ) {        
                  // Here the cell is dead, so make the cell alive   
                  this.cellBtn[r][c].setBackground( Color.WHITE ); // Color of button is set to White to indicate the cell is alive
                  this.cells[r][c].setIsAlive( true ); // Cell to Cell Object modifier to set internal field to true
               } else {
                  // Here the cell is alive, so make the cell dead
                  this.cellBtn[r][c].setBackground( Color.BLACK ); // Color of button is set to Black to indicate the cell is dead
                  this.cells[r][c].setIsAlive( false ); // Call to Cell Object modifier to set internal field to false
               }
            }
         }   
      } 
      
      // Check if the player is hitting the start button
      if( eventName.equals("start") ){
         // Here the Start button was pressed
         time.start(); // Start the Swing Timer
         btnS.setText("Stop"); // Change text on Start Button to say "Stop"
         btnS.setActionCommand("stop"); // Change ActionCommand on Button to be stop
      } else if ( eventName.equals("stop") ) {
         // Here the button was pressed to stop time
         time.stop(); // Stop the Swing Timer
         btnS.setText("Start"); // Change text on the Button to say "Start"
         btnS.setActionCommand("start"); // Change the ActionCommand of the button to be start
      }
   }
   
   // Desc.: The next generation of cells is generated then displayed on the GUI
   private void updateGUI(){
      this.cells = nextGen(this.cells); // cells are set equal to the call to the nextGen method
      // Navigate through the cell objects
      for(int r = 0; r < this.cells.length; r++){
         for(int c = 0; c < this.cells[r].length; c++){
            // Changes that will occur after the player has started the game:
            // Check to see if the Cell is alive
            if(this.cells[r][c].getIsAlive() == true){
               // Here the Cell is Alive, change the appearance to make it appear like an alive cell
               this.cellBtn[r][c].setBackground(Color.WHITE);                             
            }else{
               // Here the Cell is not Alive, change appearance to make it appear like a dead cell
               this.cellBtn[r][c].setBackground(Color.BLACK);
            }
         }    
      }
   }

   // Desc.: Generate the next generation of cells
   // Input: The current generation of cells
   // Output the next generation of cells
   private Cell[][] nextGen(Cell[][] currGen) {
      Cell[][] newGen = new Cell[20][20]; // New Generation of cells Generated
      // Traverse the array
      for( int r = 0; r < newGen.length; r++ ) {
         for( int c = 0; c < newGen[r].length; c++ ) {
            newGen[r][c] = new Cell(); // Initialize Cell
            // Check how many neighbors the cell in the current generation in the same location has       
            // All cells in newGen will default to being dead, so only need to check if the cell in the currGen will remain alive or become alive
            // Check if the cell is currently alive
            if ( currGen[r][c].getIsAlive() == true ) {
               // Check if the cell will remain alive in the next generation
               if ( aliveN(r,c) == 2 || aliveN(r,c) == 3 ) {
                  // Here the cell will has 2 or 3 neighbors
                  newGen[r][c].setIsAlive(true);
               }
            } else {
               // Here the current cell is dead, check how many alive neighbors it has
               if( aliveN(r,c)== 3 ) {
                  // Here the cell has 3 alive neighbors, this cell be become alive
                  newGen[r][c].setIsAlive(true);
               }
            }
         }
      }
      return newGen;
   }
   
   // Desc.: Gives the number of alive neighbors of a given Cell
   // Input: Coordinates of the Cell
   // Output: The number of alive neighbors represented by an Integer
   private int aliveN ( int row, int column ) {
      int amountAlive = 0; // Integer that will store the number of neighbors that are alive, will be returned
      
      // Check how many alive neighbors the cell at [r][c] has, catch exception if the neigboring cell does not exist
      try {
         // Check NW neighbor
         if ( this.cells[row - 1][column - 1].getIsAlive() == true ) {
            // Here the NW neighbors is Alive
            amountAlive++;
         }
      } catch ( IndexOutOfBoundsException e ){}
      try { 
         // Check North Neighbor
         if ( this.cells[row - 1][column].getIsAlive() == true ) {
            // Here the north neighbor is alive
            amountAlive++;
         }
      } catch( IndexOutOfBoundsException e ){}
      try {
         // Check the NW neighbor
         if ( this.cells[row - 1][column + 1].getIsAlive() == true ) {
            // Here the NE neighbor is alive
            amountAlive++;
         }
      } catch ( IndexOutOfBoundsException e ){}
      try {
         // Check the W neighbor
         if( this.cells[row][column + 1].getIsAlive() == true ) {
            // Here the W neighbor is alive
            amountAlive++;
         }
      } catch ( IndexOutOfBoundsException e ){}
      try {
         // Check the E neighbor
         if ( this.cells[row][column - 1].getIsAlive() == true ) {
            // Here the E neighbor is alive
            amountAlive++;
         }
      } catch ( IndexOutOfBoundsException e ){}
      try { 
         // Check the SW neighbor
         if ( this.cells[row + 1][column - 1].getIsAlive() == true ) {
            // Here the SW neighbor is alive
            amountAlive++;
         }
      } catch ( IndexOutOfBoundsException e ){}
      try {
         // Check the S neighbor
         if ( this.cells[row + 1][column].getIsAlive() == true ) {
            // Here the S neighbor is alive
            amountAlive++;
         } 
      } catch ( IndexOutOfBoundsException e){}
      try {
         // Check the SE neighbor
         if ( this.cells[row + 1][column + 1].getIsAlive() == true ) {
            // Here the SE neighbor is alive
            amountAlive++;
         }
      } catch ( IndexOutOfBoundsException e ){}
      return amountAlive; // Return the amount of alive neighbors
   }

}