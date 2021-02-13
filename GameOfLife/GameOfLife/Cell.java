// Jake Prisby
// 2/12/2021
// Game Of Life with GUI

package GameOfLife; // Add Cell Class to the GameOfLife package

// Class ( package-private ) for the cell object
class Cell {

    // Internal Field
    private boolean isAlive; // Boolean that determines if a cells is either dead or alive

    // Constructor ( package-private ) 
    Cell() {
      // Init Internal Field 
      this.isAlive = false;
    }
    
    // Accessor
    public boolean getIsAlive() { return this.isAlive; }
    
    // Modifier
    public void setIsAlive( boolean b ) { this.isAlive = b; }

}