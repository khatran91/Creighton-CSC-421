/**
 * Generic TreeNode class for storing nodes in a binary tree.
 *   @author Kha Tran
 *   @version 8/30/24
 */
public class TreeNode<T> {
    private T data;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private int size;
    private int height;
    /**
     * Constructs a node with the specified contents.
     *   @param d the data value to be stored
     *   @param l the left child/subtree
     *   @param r the right child/subtree
     */
    public TreeNode(T d, TreeNode<T> l, TreeNode<T> r) {
        this.data = d;
        this.left = l;
        this.right = r;
        this.updateSizeAndHeight();
    }

    /**
     * Accessor method for the data value.
     *   @return the data value stored in the node
     */
    public T getData() {
        return this.data;
    }

    /**
     * Accessor method for the left child/subtree.
     *   @return the left child/subtree
     */
    public TreeNode<T> getLeft() {
        return this.left;
    }

    /**
     * Accessor method for the right child/subtree.
     *   @return the right child/subtree
     */
    public TreeNode<T> getRight() {
        return this.right;
    }

    /**
     * Setter method for changing the data value.
     *   @param newData the new data value
     */
    public void setData(T newData) {
        this.data = newData;
    }

    /**
     * Setter method for changing the left child/subtree.
     *   @param newLeft the new left child/subtree
     */
    public void setLeft(TreeNode<T> newLeft) {
        this.left = newLeft;
        this.updateSizeAndHeight();
    }
    
    /**
     * Setter method for changing the right child/subtree.
     *   @param newRight the new right child/subtree
     */
    public void setRight(TreeNode<T> newRight) {
        this.right = newRight;
        this.updateSizeAndHeight();
    }
    /**
     * Method to get size of the tree.
     * @return  size of the tree rooted at this node
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Method to get the height of the tree.
     * @return height of the tree
     */
    public int getHeight() {
        return this.height;
    }
    
    /**
     *  Method to update the size and height of the tree.
     */
    public void updateSizeAndHeight() {
        int leftSize = (this.left == null) ? 0 : this.left.getSize();
        int rightSize = (this.right == null) ? 0 : this.right.getSize();
        this.size = 1 + leftSize + rightSize;

        int leftHeight = (this.left == null) ? 0 : this.left.getHeight();
        int rightHeight = (this.right == null) ? 0 : this.right.getHeight();
        this.height = 1 + Math.max(leftHeight, rightHeight);
    }
}