package edu.neu.rbtree;

import java.util.Scanner;

/**
 * Created by HappyMole on 3/12/17.
 */
public class RedBlackTreeTest {
    private RedBlackTree redBlackTree;

    public RedBlackTreeTest() {
        this.redBlackTree = new RedBlackTree();
    }

    public void listenToConsole() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input the command: ");
            String command = scanner.nextLine();
            if (command.equals("sort")) {
                redBlackTree.sort(redBlackTree.root);
                System.out.println();
            } else if (command.startsWith("insert")) {
                int num = Integer.parseInt(command.split(" ")[1].trim());
                TreeNode node = redBlackTree.search(redBlackTree.root, num);
                if (node != redBlackTree.nil) {
                    System.out.println("Cannot insert duplicate node, stay unchanged");
                } else {
                    redBlackTree.insert(new TreeNode(num));
                    RedBlackTreeHPrinter.printTree(redBlackTree.root);
                }
            } else if (command.startsWith("delete")) {
                int num = Integer.parseInt(command.split(" ")[1].trim());
                TreeNode node = redBlackTree.search(redBlackTree.root, num);
                if (node != redBlackTree.nil) {
                    redBlackTree.delete(node);
                    RedBlackTreeHPrinter.printTree(redBlackTree.root);
                } else {
                    System.out.println("Current red black tree does not have this node, stay unchanged");
                }
            } else if (command.startsWith("search")) {
                int num = Integer.parseInt(command.split(" ")[1].trim());
                TreeNode node = redBlackTree.search(redBlackTree.root, num);
                if (node != redBlackTree.nil) {
                    System.out.println("Current red black tree has this node.");
                } else {
                    System.out.println("Current red black tree does not have this node.");
                }
            } else if (command.equals("min")) {
                int min = redBlackTree.min(redBlackTree.root).val;
                System.out.println("The min value is: " + min);
            } else if (command.equals("max")) {
                int max = redBlackTree.max(redBlackTree.root).val;
                System.out.println("The max value is: " + max);
            } else if (command.startsWith("successor")) {
                int num = Integer.parseInt(command.split(" ")[1].trim());
                TreeNode node = redBlackTree.search(redBlackTree.root, num);
                int successor = redBlackTree.successor(node).val;
                System.out.println("The successor value is: " + successor);
            } else if (command.startsWith("predecessor")) {
                int num = Integer.parseInt(command.split(" ")[1].trim());
                TreeNode node = redBlackTree.search(redBlackTree.root, num);
                int predecessor = redBlackTree.predecessor(node).val;
                System.out.println("The predecessor value is: " + predecessor);
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Unsupported command!");
                System.out.println("Only the following commands are supported: ");
                System.out.println("sort, search, insert, delete, exit");
                System.out.println();
                continue;
            }
            System.out.println();
            System.out.println("Current height of this red black tree: " + redBlackTree.height(redBlackTree.root));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        RedBlackTreeTest redBlackTreeTest = new RedBlackTreeTest();
        redBlackTreeTest.listenToConsole();
    }
}
