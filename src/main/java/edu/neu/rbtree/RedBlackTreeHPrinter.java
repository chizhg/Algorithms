package edu.neu.rbtree;

/**
 * Created by HappyMole on 3/12/17.
 */
public class RedBlackTreeHPrinter {
    public static void printTree(TreeNode root) {
        if (root.right != RedBlackTree.nil && root.right != null) {
            printTree(root.right, true, "");
        }
        printNodeValue(root);
        if (root.left != RedBlackTree.nil && root.right != null) {
            printTree(root.left, false, "");
        }
    }

    private static void printNodeValue(TreeNode node) {
        if (node.color == Color.black) {
            System.out.print("(b)" + node.val);
        } else {
            System.out.print("(r)" + node.val);
        }
        System.out.println();
    }

    private static void printTree(TreeNode node, boolean isRight, String indent) {
        if (node.right != RedBlackTree.nil && node.right != null) {
            printTree(node.right, true, indent + (isRight ? "        " : " |      "));
        }
        System.out.print(indent);
        if (isRight) {
            System.out.print(" /");
        } else {
            System.out.print(" \\");
        }
        System.out.print("----- ");
        printNodeValue(node);
        if (node.left != RedBlackTree.nil && node.left != null) {
            printTree(node.left, false, indent + (isRight ? " |      " : "        "));
        }
    }
}
