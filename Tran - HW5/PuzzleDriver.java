import java.util.Scanner;

/**
 * Driver class for solving grid-based puzzles.
 */
public class PuzzleDriver {
    public static void main(String[] args) throws java.io.FileNotFoundException {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Choose the puzzle type:");
        System.out.println("S - Sudoku");
        System.out.println("3 - 3-in-a-row");
        System.out.println("R - Range"); 
        char puzzleType = input.next().charAt(0);
        System.out.println("Enter the puzzle file name: ");
        String filename = input.next();
        input.close();
        
        Puzzle puzzle = null;
        
        if (puzzleType == 'S') {
            puzzle = new Sudoku(filename);
        } else if (puzzleType == '3') {
            puzzle = new ThreeInARow(filename);
        } else if (puzzleType == 'R') {
            puzzle = new Range(filename); 
        } else {
            System.out.println("Invalid puzzle type.");
            return;
        }

        System.out.println(puzzle);
        
        if (puzzle.solve()) {
            System.out.println("Solution found:\n" + puzzle);
        } else {
            System.out.println("No solution possible");
        }
    }
}

