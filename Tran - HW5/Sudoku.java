public class Sudoku extends Puzzle {

    public Sudoku(String filename) throws java.io.FileNotFoundException {
        super(filename); // Call the constructor of the Puzzle class
    }

    @Override
    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (row == this.numRows) { 
            return true;
        } else if (col == this.numCols) {
            return solve(row + 1, 0);
        } else if (!this.grid[row][col].equals("-")) {
            return solve(row, col + 1);
        } else {
            for (String value : this.values) {
                this.grid[row][col] = value;
                if (!hasConflict(row, col) && solve(row, col + 1)) {
                    return true;
                }
                this.grid[row][col] = "-";
            }
            return false;
        }
    }

    @Override
    public boolean hasConflict(int row, int col) {
    String piece = this.grid[row][col];

    // Check for 3 consecutive same color tiles in rows
    for (int i = 0; i < this.numCols - 2; i++) {
        if (this.grid[row][i].equals(piece) && this.grid[row][i + 1].equals(piece) && this.grid[row][i + 2].equals(piece)) {
            return true; // Conflict: 3 consecutive same color in row
        }
    }

    // Check for 3 consecutive same color tiles in columns
    for (int i = 0; i < this.numRows - 2; i++) {
        if (this.grid[i][col].equals(piece) && this.grid[i + 1][col].equals(piece) && this.grid[i + 2][col].equals(piece)) {
            return true; // Conflict: 3 consecutive same color in column
        }
    }

    // Count black and white tiles in the current row and column
    int blackCountRow = 0, whiteCountRow = 0;
    int blackCountCol = 0, whiteCountCol = 0;

    for (int i = 0; i < this.numCols; i++) {
        if (this.grid[row][i].equals("▣")) {
            blackCountRow++;
        } else if (this.grid[row][i].equals("▢")) {
            whiteCountRow++;
        }
    }

    for (int i = 0; i < this.numRows; i++) {
        if (this.grid[i][col].equals("▣")) {
            blackCountCol++;
        } else if (this.grid[i][col].equals("▢")) {
            whiteCountCol++;
        }
    }

    // Check that there are no more than 3 black or white tiles in the row or column
    if (blackCountRow > 3 || whiteCountRow > 3 || blackCountCol > 3 || whiteCountCol > 3) {
        return true; // Conflict: More than 3 black or white tiles in row or column
    }

    // If all checks pass, there is no conflict
    return false;
    }

}
