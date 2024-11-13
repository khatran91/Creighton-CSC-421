import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Set;

public class KnapsackDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for the knapsack weight limit
        System.out.print("Enter the knapsack weight limit: ");
        int weightLimit = scanner.nextInt();

        // Prompt the user for the filename containing the items
        System.out.print("Enter the items file: ");
        String filename = scanner.next();

        // Create a Knapsack instance with the specified weight limit
        Knapsack knapsack = new Knapsack(weightLimit);

        // Load items from the specified file into the knapsack
        loadItemsFromFile(knapsack, filename);

        // Find the optimal subset of items
        Set<KnapsackItem> optimalSubset = knapsack.findOptimalSubset();

        // Calculate the total value of the optimal subset
        int totalValue = 0;
        System.out.println("Optimal Subset:");
        for (KnapsackItem item : optimalSubset) {
            System.out.println(item);  // Display each item in the optimal subset
            totalValue += item.getValue();  // Accumulate the total value
        }

        // Display the total value of the optimal subset
        System.out.println("Total Value = $" + totalValue);
    }

    // Method to load items from a file and add them to the knapsack
    private static void loadItemsFromFile(Knapsack knapsack, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");  // Split line into parts: value, weight, descriptor
                if (parts.length >= 3) {  // Check that line has at least three parts
                    int value = Integer.parseInt(parts[0]);  // Parse the value
                    int weight = Integer.parseInt(parts[1]);  // Parse the weight
                    String descriptor = parts[2];  // Get the descriptor
                    knapsack.addItem(new KnapsackItem(value, weight, descriptor));  // Add item to knapsack
                } else {
                    System.out.println("Invalid line format: " + line);  // Error if line is not correctly formatted
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading the file: " + e.getMessage());  // Catch and display any errors
        }
    }
}
