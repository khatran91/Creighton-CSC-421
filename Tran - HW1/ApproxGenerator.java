import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that reads in and stores the contents of a text file.
 *   @author Kha Tran
 *   @version 8/20/24
 */
public class ApproxGenerator {
    private String cleanText;
    private Random randy;

    /**
     * Constructs a FileProcessor object that reads in text from a file. The
     * text is processed to remove non-letters and adjacent spaces, and letters
     * are converted to uppercase.
     *   @param fileName the file containing the text
     */
    public ApproxGenerator(String fileName) throws Exception {
        this.cleanText = "";
        this.randy = new Random();

        Scanner infile = new Scanner(new File(fileName));
        while (infile.hasNextLine()) {
            String nextLine = infile.nextLine().toUpperCase();
            for (int i = 0; i < nextLine.length(); i++) {
                char ch = nextLine.charAt(i);
                if (Character.isLetter(ch) || ch == ' ') {
                    this.cleanText += ch;
                }
            }
            this.cleanText += " ";
        }
        this.cleanText = this.cleanText.trim().replaceAll("\\s+", " ");
        infile.close();       
    }

    /**
     * Generates a string of the specified order and length, matching the 
     * statistical distribution of letters and spaces as the specified file.
     *   @param order the order for the approximation
     *   @param numChars the length of the generated string
     *   @return the generated string
     */
    public String generate(int order, int numChars) {
        if (order < 1) {
            throw new IllegalArgumentException("Order must be >= 1");
        }

        //Map to store k-character sequence and its next possible characters
        Map<String, List<Character>> sequences = new HashMap<>();

        //Iterate the map with sequence
        for (int i = 0; i <= this.cleanText.length() - order; i++) {
            String sequence = this.cleanText.substring(i, i + order);
            char nextChar = i + order < this.cleanText.length() ? this.cleanText.charAt(i + order) : ' ';
            sequences.computeIfAbsent(sequence, k -> new ArrayList<>()).add(nextChar);
        }

        //Generate new text
        StringBuilder newText = new StringBuilder();
        String currentSequence = getRandomKey(sequences);
        
        while (newText.length() < numChars) {
            //Get a list of possible next characters for currentSequence
            List<Character> nextCharacters = sequences.get(currentSequence);
            
            //If null, pick a new random sequence
            if (nextCharacters == null || nextCharacters.isEmpty()) {
                currentSequence = getRandomKey(sequences);
                continue;
            }
             //Choose a random next character from the list
             char nextChar = nextCharacters.get(this.randy.nextInt(nextCharacters.size()));
             newText.append(nextChar);
 
             //Update the current sequence to the last (order) characters of the new text
             currentSequence = currentSequence.substring(1) + nextChar;
        }
        return newText.toString();
    }
    //Helper method to get randomKey from Map
    private String getRandomKey(Map<String, List<Character>> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        return keys.get(this.randy.nextInt(keys.size()));
    }
}