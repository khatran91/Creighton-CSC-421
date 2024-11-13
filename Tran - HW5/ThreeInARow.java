public class ThreeInARow extends Puzzle {

    public ThreeInARow(String filename) throws java.io.FileNotFoundException {
        super(filename); // Call the constructor of the Puzzle class
    }

    @Override
    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (row == this.numRows) {
            return true; // Solved if we've filled all rows
        } else if (col == this.numCols) {
            return solve(row + 1, 0); // Move to the next row
        } else if (!this.grid[row][col].equals("-")) {
            return solve(row, col + 1); // Skip already filled cells
        } else {
            // Try filling with both black (â–£) and white (â–¢) tiles
            for (String value : this.values) {
                this.grid[row][col] = value;
                if (!hasConflict(row, col) && solve(row, col + 1)) {
                    return true; // Return true if the solution is found
                }
                this.grid[row][col] = "-"; // Backtrack if the solution is not valid
            }
            return false; // No valid solution
        }
    }

    @Override
    public boolean hasConflict(int row, int col) {
        String piece = this.grid[row][col];

        // Check for 3 or more consecutive black or white tiles in rows
        for (int i = 0; i < this.numCols - 2; i++) {
            if (this.grid[row][i].equals(piece) && this.grid[row][i + 1].equals(piece) && this.grid[row][i + 2].equals(piece)) {
                return true; // Conflict: 3 consecutive same color in row
            }
        }

        // Check for 3 or more consecutive black or white tiles in columns
        for (int i = 0; i < this.numRows - 2; i++) {
            if (this.grid[i][col].equals(piece) && this.grid[i + 1][col].equals(piece) && this.grid[i + 2][col].equals(piece)) {
                return true; // Conflict: 3 consecutive same color in column
            }
        }

        // Check if the row and column have the same number of black and white tiles
        int blackCountRow = 0, whiteCountRow = 0, blackCountCol = 0, whiteCountCol = 0;
        for (int i = 0; i < this.numCols; i++) {
            if (this.grid[row][i].equals("â–£")) {
                blackCountRow++;
            } else if (this.grid[row][i].equals("â–¢")) {
                whiteCountRow++;
            }
        }

        for (int i = 0; i < this.numRows; i++) {
            if (this.grid[i][col].equals("â–£")) {
                blackCountCol++;
            } else if (this.grid[i][col].equals("â–¢")) {
                whiteCountCol++;
            }
        }

        // Ensure the same number of black and white tiles in each row and column
        if (blackCountRow > this.numCols / 2 || whiteCountRow > this.numCols / 2) {
            return true; // Conflict: More than half of the tiles are black or white in the row
        }

        if (blackCountCol > this.numRows / 2 || whiteCountCol > this.numRows / 2) {
            return true; // Conflict: More than half of the tiles are black or white in the column
        }

        return false; // No conflict found
    }
}
