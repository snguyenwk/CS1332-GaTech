import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Steven Nguyen
 * @version 11.0.17
 * @userid snguyen96
 * @GTID 903644469
 *
 * Collaborators:
 *
 * Resources:
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        size = 0;
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("item cannot be null");
            } else {
                add(item);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        root = addH(root, data);
    }

    /**
     * Helper method for adding data to AVL
     * @param node node to compare
     * @param data to be added
     * @return node
     */
    private AVLNode<T> addH(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addH(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addH(node.getRight(), data));
        } else {
            return node;
        }
        update(node);
        return balanced(node);
    }

    /**
     * Helper method to update H and B
     * @param node node to balance
     */
    private void update(AVLNode<T> node) {
        int left = -1;
        int right = -1;

        if (node.getLeft() != null) {
            left = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            right = node.getRight().getHeight();
        }
        node.setHeight(1 + Math.max(left, right));
        node.setBalanceFactor(left - right);
    }

    /**
     * Helper method to get BF and balance
     * @param node node
     * @return node
     */
    private AVLNode<T> balanced(AVLNode<T> node) {
        if (node.getBalanceFactor() < - 1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rRotate(node.getRight()));
            }
            return lRotate(node);
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(lRotate(node.getLeft()));
            }
            return rRotate(node);
        }
        return node;
    }

    /**
     * Helper method for left rotate
     * @param node node
     * @return node
     */
    private AVLNode<T> lRotate(AVLNode<T> node) {
        AVLNode<T> root2 = node.getRight();
        node.setRight(root2.getLeft());
        root2.setLeft(node);
        update(node);
        update(root2);

        return root2;
    }

    /**
     * Helper method for right rotate
     * @param node node
     * @return node
     */
    private AVLNode<T> rRotate(AVLNode<T> node) {
        AVLNode<T> root2 = node.getLeft();
        node.setLeft(root2.getRight());
        root2.setRight(node);
        update(node);
        update(root2);

        return root2;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeH(root, data, removed);

        if (removed.getData() == null) {
            throw new NoSuchElementException("data not in tree");
        }
        size--;
        return removed.getData();
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {

    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {

    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {

    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the deepest
     * ancestor of the node holding data that contains a value less than data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {

    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {

    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
