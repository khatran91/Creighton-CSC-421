import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Knapsack {
    private List<KnapsackItem> items;
    private int weightLimit;

    // Constructor accepting the weight limit
    public Knapsack(int weightLimit) {
        this.items = new ArrayList<>();
        this.weightLimit = weightLimit;
    }

    // Method to add an item to the knapsack
    public void addItem(KnapsackItem item) {
        items.add(item);
    }

    // Finds the optimal subset of items that fits within the weight limit
    public Set<KnapsackItem> findOptimalSubset() {
        return new HashSet<>(findBestSubset(items, weightLimit));
    }

    // Helper method to find the best subset using generate and test
    private List<KnapsackItem> findBestSubset(List<KnapsackItem> items, int weightLimit) {
        int n = items.size();
        List<KnapsackItem> bestSubset = new ArrayList<>();
        int bestValue = 0;

        // Iterate through all subsets (2^n combinations)
        for (int i = 0; i < (1 << n); i++) {
            List<KnapsackItem> currentSubset = new ArrayList<>();
            int currentWeight = 0;
            int currentValue = 0;

            for (int j = 0; j < n; j++) {
                // Check if the jth item is included in the ith subset
                if ((i & (1 << j)) != 0) {
                    KnapsackItem item = items.get(j);
                    currentWeight += item.getWeight();
                    currentValue += item.getValue();
                    currentSubset.add(item);
                }
            }

            // If the subset weight is within limit and its value is higher, update the best subset
            if (currentWeight <= weightLimit && currentValue > bestValue) {
                bestValue = currentValue;
                bestSubset = currentSubset;
            }
        }
        return bestSubset;
    }
}
