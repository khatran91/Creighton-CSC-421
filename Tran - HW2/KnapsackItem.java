public class KnapsackItem {
    private int value;
    private int weight;
    private String descriptor;

    public KnapsackItem(int value, int weight, String descriptor) {
        this.value = value;
        this.weight = weight;
        this.descriptor = descriptor;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public String getDescriptor() {
        return descriptor;
    }

    @Override
    public String toString() {
        return descriptor;
    }
}
