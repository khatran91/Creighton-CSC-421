import java.util.List;

/**
 * Generic class that implements a binary search tree, building upon the 
 * existing BinaryTree class.
 *   @param <T> the type of value stored, must be Comparable<T>
 *   @author Kha Tran
 *   @version 8/30/24
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> {
    /** Method to rebalance the tree */
    public void rebalance() {
        List<T> sortedList = this.asList();
        this.root = buildBalancedTree(sortedList, 0, sortedList.size() - 1);
    }

    /**
     * Method to build a balanced tree.
     * @param sortedList
     * @param start
     * @param end
     * @return
     */
    private TreeNode<T> buildBalancedTree(List<T> sortedList, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode<T> node = new TreeNode<>(sortedList.get(mid), null, null);
        node.setLeft(buildBalancedTree(sortedList, start, mid - 1));
        node.setRight(buildBalancedTree(sortedList, mid + 1, end));
        return node;
    }

    /** Method to add a value to the tree */
    @Override
    public void add(T value) {
        super.add(value);
        // Check for imbalance after addition
        if (this.root != null && this.root.getHeight() > (1 + Math.log(this.size()) / Math.log(2))) {
            this.rebalance();
        }
    }

    /** Method to remove a value from the tree */
    @Override
    public boolean remove(T value) {
        boolean removed = super.remove(value);
        // Check for imbalance after removal
        if (this.root != null && this.root.getHeight() > (1 + Math.log(this.size()) / Math.log(2))) {
            this.rebalance();
        }
        return removed;
    }

    /**
     * Overrides the super.contains method to take advantage of binary search.
     *   @param value the value to be searched for
     *   @return true if that value is in the tree, otherwise false
     */
    @Override
    public boolean contains(T value) {
        return this.contains(this.root, value);
    }

    private boolean contains(TreeNode<T> current, T value) {
        if (current == null) {
            return false;
        } else if (value.equals(current.getData())) {
            return true;
        } else if (value.compareTo(current.getData()) < 0) {
            return this.contains(current.getLeft(), value);
        } else {
            return this.contains(current.getRight(), value);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /// FOR TESTING PURPOSES
    ////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        tree.add(7);
        tree.add(2);
        tree.add(12);
        tree.add(1);
        tree.add(5);
        tree.add(6);
        tree.add(99);
        tree.add(88);
        System.out.println(tree);
        
        System.out.println("size = " + tree.size());
        System.out.println(tree.contains(2) + " " + tree.contains(7)
                                            + " " + tree.contains(8));

        tree.remove(99);
        tree.remove(7);
        System.out.println(tree);
        System.out.println("size = " + tree.size());
        System.out.println(tree.contains(2) + " " + tree.contains(7)
                                            + " " + tree.contains(8));
    }
}
