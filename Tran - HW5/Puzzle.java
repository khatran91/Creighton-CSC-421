import java.util.Scanner;
import java.io.File;

public abstract class Puzzle {

    protected String[][] grid;
    protected String[] values; // For puzzles that use a set of valid values (like Sudoku)
    protected int numRows, numCols;
    

    public Puzzle(String filename) throws java.io.FileNotFoundException {
        Scanner infile = new Scanner(new File(filename));
        
        // Read the valid values that can fill the grid
        String valuesLine = infile.nextLine();
        this.values = valuesLine.split(" ");
        
        // Read grid size (rows and columns)
        this.numRows = infile.nextInt();
        this.numCols = infile.nextInt();
        this.grid = new String[numRows][numCols];
        
        // Read the grid content
        infile.nextLine(); // Move to the next line after reading numCols and numRows
        for (int r = 0; r < numRows; r++) {
            String line = infile.nextLine();
            String[] row = line.split(" ");
            for (int c = 0; c < numCols; c++) {
                this.grid[r][c] = row[c];
            }
        }
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public abstract boolean solve();

    public abstract boolean hasConflict(int row, int col);
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                sb.append(grid[r][c]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
