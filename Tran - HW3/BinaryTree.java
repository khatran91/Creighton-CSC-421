import java.util.ArrayList;
import java.util.List;

/**
 * Generic class that implements a binary tree structure.
 *   @author Kha Tran
 *   @version 8/30/24
 */
public class BinaryTree<T> {    
    protected TreeNode<T> root;

    /**
     * Constructs an empty binary tree.
     */
    public BinaryTree() {
        this.root = null;
    }

    /**
     * Adds a value to the binary tree (at a random  location).
     *   @param value the value to be added
     */
    public void add(T value) {
        this.root = this.add(this.root, value);
    }
    private TreeNode<T> add(TreeNode<T> current, T value) {
        if (current == null) {
            current = new TreeNode<T>(value, null, null);
        }
        else if (Math.random() < 0.5) {
            current.setLeft(this.add(current.getLeft(), value)); 
        }
        else {
            current.setRight(this.add(current.getRight(), value));
        }
        return current;
    }

    /**
     * Determines the size of the binary tree.
     *   @return the size (number of nodes in the tree)
     */
    public int size() {
        return (this.root == null) ? 0 : this.root.getSize();
    }
    // private int size(TreeNode<T> current) {
    //     if (current == null) {
    //         return 0;
    //     }
    //     else {
    //         return this.size(current.getLeft()) +
    //                this.size(current.getRight()) + 1;
    //     }
    // }

    /**
     * Determines the size of the binary tree.
     *   @return the size (number of nodes in the tree)
     */
    public int height() {
        return (this.root == null) ? 0 : this.root.getHeight();
    }
    // private int height(TreeNode<T> current) {
    //     if (current == null) {
    //         return 0;
    //     }
    //     else {
    //         return 1 + Math.max(this.height(current.getLeft()),
    //                             this.height(current.getRight()));
    //     }
    // }
    /**
     * Determines whether the tree contains a particular value.
     *   @param value the value to be searched for
     *   @return true if value is in the tree, otherwise false
     */
    public boolean contains(T value) {
        return this.contains(this.root, value);
    }
    private  boolean contains(TreeNode<T> current, T value) {
        if (current == null) {
            return false;
        }
        else {
            return value.equals(current.getData()) ||
                   this.contains(current.getLeft(), value) ||
                   this.contains(current.getRight(), value);
        }
    }

    /**
     * Removes one occurrence of the specified value.
     *   @param value the value to be removed
     *   @return true if the value was found and removed, else false
     */
    public boolean remove(T value) {
        if (!this.contains(value)) {
            return false;
        }
        else {
            this.root = this.remove(this.root, value);
            return true;
        }
    }
    private TreeNode<T> remove(TreeNode<T> current, T value) {
        if (value.equals(current.getData())) {
            if (current.getLeft() == null) {
                current = current.getRight();
            }
            else {
                TreeNode<T> righty = current.getLeft();
                while (righty.getRight() != null) {
                    righty = righty.getRight();
                }
                current.setData(righty.getData());
                current.setLeft(this.remove(current.getLeft(), current.getData()));
            }
        }
        else if (this.contains(current.getLeft(), value)) {
            current.setLeft(this.remove(current.getLeft(), value));
        }
        else {
            current.setRight(this.remove(current.getRight(), value));
        }
        return current;
    }
    /**
     * Method to create list
     * @return
     */
    public List<T> asList() {
        List<T> list = new ArrayList<>();
        this.asList(this.root, list);
        return list;
    }
    private void asList(TreeNode<T> current, List<T> list) {
        if (current == null) {
            return;
        }
        this.asList(current.getLeft(), list);
        list.add(current.getData());
        this.asList(current.getRight(), list);
    }
    
    /**
     * Converts the tree to a String using an inorder traversal.
     *   @return the String representation of the tree.
     */
    public String toString() {
        if (this.root == null) {
          return "[]";
        }
        String recStr = this.toString(this.root);
        return "[" + recStr.substring(0,recStr.length()-1) + "]";
    }
    private String toString(TreeNode<T> current) {
        if (current ==  null) {
          return "";
        }
        return this.toString(current.getLeft()) +
               current.getData().toString() + "," +
               this.toString(current.getRight());
    }


    ////////////////////////////////////////////////////////////////////////////
    /// FOR TESTING PURPOSES
    ////////////////////////////////////////////////////////////////////////////
    
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<Integer>();
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
