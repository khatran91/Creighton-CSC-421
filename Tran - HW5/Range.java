public class Range extends Puzzle {

    public Range(String filename) throws java.io.FileNotFoundException {
        super(filename);
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

        // Rule 1: Check if adjacent black tiles conflict
        if (piece.equals("▣")) {
            if ((row > 0 && this.grid[row - 1][col].equals("▣")) ||
                (row < this.numRows - 1 && this.grid[row + 1][col].equals("▣")) ||
                (col > 0 && this.grid[row][col - 1].equals("▣")) ||
                (col < this.numCols - 1 && this.grid[row][col + 1].equals("▣"))) {
                return true;
            }
        }

        // Rule 2: Check if the white tiles with numbers have the correct number of adjacent white tiles
        if (!piece.equals("▣") && Character.isDigit(piece.charAt(0))) {
            int requiredAdjWhiteTiles = Character.getNumericValue(piece.charAt(0));
            int actualAdjWhiteTiles = countAdjacentWhiteTiles(row, col);
            if (actualAdjWhiteTiles != requiredAdjWhiteTiles) {
                return true;
            }
        }

        return false;
    }

    private int countAdjacentWhiteTiles(int row, int col) {
        int count = 0;

        // Count adjacent white tiles in the row
        for (int i = 0; i < this.numCols; i++) {
            if (i != col && this.grid[row][i].equals("▢")) {
                count++;
            }
        }

        // Count adjacent white tiles in the column
        for (int i = 0; i < this.numRows; i++) {
            if (i != row && this.grid[i][col].equals("▢")) {
                count++;
            }
        }

        return count;
    }
}
