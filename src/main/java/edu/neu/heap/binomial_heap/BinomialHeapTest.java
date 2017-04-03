package edu.neu.heap.binomial_heap;

import java.util.Scanner;

/**
 * Created by HappyMole on 4/3/17.
 */
public class BinomialHeapTest {
    public static void main(String[] args) {
        BinomialHeap heap = BinomialHeap.makeHeap();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input the command: ");
            String command = scanner.nextLine();
            if (command.startsWith("insert")) {
                int num = Integer.parseInt(command.split(" ")[1]);
                Node node = new Node(num);
                heap.insert(node);
                heap.print();
            } else if (command.equals("min")) {
                Node node = heap.minimum();
                System.out.printf("The minimum num in this heap is: " + node.key + "\n");
            } else if (command.equals("extract")) {
                Node node = heap.extractMin();
                System.out.println("The minimum node " + node.key + " is extracted");
                heap.print();
            } else if (command.startsWith("delete")) {
                int num = Integer.parseInt(command.split(" ")[1]);
                Node node = heap.searchByVal(num);
                if (node != null) {
                    heap.delete(node);
                }
                heap.print();
            } else if (command.startsWith("decrease")) {
                int srcNum = Integer.parseInt(command.split(" ")[1]);
                int destNum = Integer.parseInt(command.split(" ")[2]);
                Node srcNode = heap.searchByVal(srcNum);
                if (srcNode != null) {
                    heap.decreaseKey(srcNode, destNum);
                }
                heap.print();
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Unsupported command!");
                System.out.println("Only the following commands are supported: ");
                System.out.println("insert, delete, decrease, min, extract, exit");
                System.out.println();
                continue;
            }
        }
    }
}
