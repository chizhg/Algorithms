package edu.neu.rbtree;

enum Color {
    red, black
}

enum Direction {
    left, right
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode parent;
    Color color;

    public TreeNode(int x) {
        this.val = x;
        this.color = Color.black;
        this.parent = RedBlackTree.nil;
        this.left = RedBlackTree.nil;
        this.right = RedBlackTree.nil;
    }
}

public class RedBlackTree {
    static final TreeNode nil = new TreeNode(0);
    TreeNode root = nil;

    public TreeNode min(TreeNode x) {
        while (x.left != nil) {
            x = x.left;
        }

        return x;
    }

    public TreeNode max(TreeNode x) {
        while (x.right != nil) {
            x = x.right;
        }

        return x;
    }

    public TreeNode search(TreeNode x, int val) {
        if (x == nil || x.val == val) {
            return x;
        }

        if (val > x.val) {
            return search(x.right, val);
        } else {
            return search(x.left, val);
        }
    }

    public TreeNode successor(TreeNode x) {
        if (x.right != nil) {
            return min(x.right);
        } else {
            TreeNode y = x.parent;
            while (y != nil && x == y.right) {
                x = y;
                y = y.parent;
            }
            return y;
        }
    }

    public TreeNode predecessor(TreeNode x) {
        if (x.left != nil) {
            return max(x.left);
        } else {
            TreeNode y = x.parent;
            while (y != nil && x == y.left) {
                x = y;
                y = y.parent;
            }
            return y;
        }
    }

    public void sort(TreeNode root) {
        if (root != nil && root != null) {
            sort(root.left);
            System.out.print(root.val + " ");
            sort(root.right);
        }
    }


    public int height(TreeNode root) {
        if (root == nil || root == null) {
            return 0;
        }

        return Math.max(height(root.left), height(root.right)) + 1;
    }

    public void rotate(TreeNode x, Direction direction) {
        if (direction == Direction.left) {
            leftRotate(x);
        } else if (direction == Direction.right) {
            rightRotate(x);
        }
    }

    private void leftRotate(TreeNode x) {
        TreeNode y = x.right;
        x.right = y.left;
        if (y.left != nil) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == nil) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(TreeNode y) {
        TreeNode x = y.left;
        y.left = x.right;
        if (x.right != nil) {
            x.right.parent = y;
        }

        x.parent = y.parent;
        if (y.parent == nil) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        x.right = y;
        y.parent = x;
    }

    public void insert(TreeNode z) {
        // x is the position that z should be put
        // y is z's expected parent
        TreeNode y = nil;
        TreeNode x = root;
        while (x != nil) {
            y = x;
            if (z.val < x.val) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;

        /*
        if y is nil, then z is the first element
        else if z.val < y.val, then put z as y's left child
        else if z.val > y.val, then put z as y's right child
        */
        if (y == nil) {
            root = z;
        } else if (z.val < y.val) {
            y.left = z;
        } else {
            y.right = z;
        }

        // important: set z's color as red
        z.left = nil;
        z.right = nil;
        z.color = Color.red;

        insertFixup(z);
    }

    private void insertFixup(TreeNode z) {
        while (z.parent.color == Color.red) {
            // z has a right uncle
            if (z.parent == z.parent.parent.left) {
                // y is z's right uncle
                TreeNode y = z.parent.parent.right;
                // case 1: z's uncle y is red
                if (y.color == Color.red) {
                    z.parent.color = Color.black;
                    y.color = Color.black;
                    z.parent.parent.color = Color.red;
                    z = z.parent.parent;
                } else {
                    // case 2: z's uncle y is black and z is a right child
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    // case 3: z's uncle y is black and z is a left child
                    // the leftRotate(z) in case 2 will change it to case 3
                    z.parent.color = Color.black;
                    z.parent.parent.color = Color.red;
                    rightRotate(z.parent.parent);
                }
            } else {
                TreeNode y = z.parent.parent.left;
                // case 1: z's uncle y is red
                if (y.color == Color.red) {
                    z.parent.color = Color.black;
                    y.color = Color.black;
                    z.parent.parent.color = Color.red;
                    z = z.parent.parent;
                } else {
                    // case 2: z's uncle y is black and z is a left child
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    // case 3: z's uncle y is black and z is a right child
                    // the rightRotate(z) in case 2 will change it to case 3
                    z.parent.color = Color.black;
                    z.parent.parent.color = Color.red;
                    leftRotate(z.parent.parent);
                }
            }
        }

        root.color = Color.black;
    }

    public void delete(TreeNode z) {
        TreeNode y = z;
        TreeNode x;
        Color yOriColor = y.color;
        /*
        if z doesn't have left child, transplant its right child to its parent
        else if z doesn't have right child, transplant its left child to its parent
        else if z has both left and right child:
            1. find z's successor y
            2. if y is z's right child, replace z as a child of its parent by y and
               replace y's left child by z's left child
            3. else if y is not z's right child, replace y as a child of its parent
               by y's right child and turn z's right child into y's right child, and
               then execute the step 2
         */
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = min(z.right);
            yOriColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriColor == Color.black) {
            deleteFixup(x);
        }
    }

    private void transplant(TreeNode u, TreeNode v) {
        if (u.parent == nil) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }

        v.parent = u.parent;
    }

    private void deleteFixup(TreeNode x) {
        while (x != root && x.color == Color.black) {
            if (x == x.parent.left) {
                TreeNode w = x.parent.right;
                // case 1 : x's sibling w is red
                if (w.color == Color.red) {
                    w.color = Color.black;
                    x.parent.color = Color.red;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }

                // case 2 : x's sibling w is black, and both of w's children are black
                if (w.left.color == Color.black && w.right.color == Color.black) {
                    w.color = Color.red;
                    x = x.parent;
                } else {
                    // case 3 : x's sibling w is black, w's left child is red, and w's right child is black
                    if (w.right.color == Color.black) {
                        w.left.color = Color.black;
                        w.color = Color.red;
                        rightRotate(w);
                        w = x.parent.right;
                    }

                    // case 4 : x's sibling w is black, and w's right child is red
                    w.color = x.parent.color;
                    x.parent.color = Color.black;
                    w.right.color = Color.black;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                TreeNode w = x.parent.left;
                // case 1 : x's sibling w is red
                if (w.color == Color.red) {
                    w.color = Color.black;
                    x.parent.color = Color.red;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }

                // case 2 : x's sibling w is black, and both of w's children are black
                if (w.right.color == Color.black && w.left.color == Color.black) {
                    w.color = Color.red;
                    x = x.parent;
                } else {
                    // case 3 : x's sibling w is black, w's right child is red, and w's left child is black
                    if (w.left.color == Color.black) {
                        w.right.color = Color.black;
                        w.color = Color.red;
                        leftRotate(w);
                        w = x.parent.left;
                    }

                    // case 4 : x's sibling w is black, and w's left child is red
                    w.color = x.parent.color;
                    x.parent.color = Color.black;
                    w.left.color = Color.black;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        x.color = Color.black;
    }
}
