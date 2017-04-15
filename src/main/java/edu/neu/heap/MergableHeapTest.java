package edu.neu.heap;

import edu.neu.heap.binomial_heap.BinomialHeap;
import edu.neu.heap.fibonacci_heap.FibonacciHeap;

import java.util.Scanner;

/**
 * Created by HappyMole on 4/14/17.
 */
public class MergableHeapTest<T extends BaseNode> {
    public static void testHeap(IMergableHeap heap, NodeFactory nodeFactory) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input the command: ");
            String command = scanner.nextLine();
            if (command.startsWith("insert")) {
                int num = Integer.parseInt(command.split(" ")[1]);
                BaseNode node = nodeFactory.create(num);
                heap.insert(node);
                heap.print();
            } else if (command.equals("min")) {
                BaseNode node = heap.minimum();
                System.out.printf("The minimum num in this heap is: " + node.key + "\n");
            } else if (command.equals("extract")) {
                BaseNode node = heap.extractMin();
                System.out.println("The minimum node " + node.key + " is extracted");
                heap.print();
            } else if (command.startsWith("delete")) {
                int num = Integer.parseInt(command.split(" ")[1]);
                BaseNode node = heap.searchByVal(num);
                if (node != null) {
                    heap.delete(node);
                }
                heap.print();
            } else if (command.startsWith("decrease")) {
                int srcNum = Integer.parseInt(command.split(" ")[1]);
                int destNum = Integer.parseInt(command.split(" ")[2]);
                BaseNode srcNode = heap.searchByVal(srcNum);
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

    public static void main(String[] args) throws NoSuchMethodException {
//        IMergableHeap binomialHeap = new BinomialHeap();
//        testHeap(binomialHeap, new NodeFactory(1));

        IMergableHeap fibonacciHeap = new FibonacciHeap();
        testHeap(fibonacciHeap, new NodeFactory(2));
    }
}
