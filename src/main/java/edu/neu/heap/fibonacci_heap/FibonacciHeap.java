package edu.neu.heap.fibonacci_heap;

import java.util.*;

/*
    insert:     O(1)
    union:      O(1)
    find-min:   O(1)
    delete:     O(logn)
    delete-min: O(logn)
 */
public class FibonacciHeap {
    private Node minNode;
    private int nodeNum;

    private FibonacciHeap() {
        minNode = null;
        nodeNum = 0;
    }

    public static FibonacciHeap makeHeap() {
        return new FibonacciHeap();
    }

    public static void main(String[] args) {
        FibonacciHeap fibonacciHeap = makeHeap();
        for (int i = 0; i < 5; i++) {
            fibonacciHeap.insert(new Node(i));
        }

        fibonacciHeap.extractMin();
//        fibonacciHeap.print();
//        fibonacciHeap.decreaseKey(fibonacciHeap.search(4), -1);
        fibonacciHeap.delete(fibonacciHeap.search(4, true));
        fibonacciHeap.print();
    }

    public int size() {
        return nodeNum;
    }

    public Node minimum() {
        return minNode;
    }

    public Node searchByLabel(int searchLabel) {
        return search(searchLabel, false);
    }

    public Node searchByVal(int searchVal) {
        return search(searchVal, true);
    }

    private Node search(int searchVal, boolean byVal) {
        return search(minNode, searchVal, new HashSet<>(), byVal);
    }

    private Node search(Node node, int searchVal, Set<Node> visited, boolean byVal) {
        if (node != null && !visited.contains(node)) {
            if (byVal && node.key == searchVal) {
                return node;
            }

            if (!byVal && node.label == searchVal) {
                return node;
            }

            visited.add(node);
            Node result1 = search(node.right, searchVal, visited, byVal);
            if (result1 != null) {
                return result1;
            }

            Node result2 = search(node.child, searchVal, visited, byVal);
            if (result2 != null) {
                return result2;
            }
        }

        return null;
    }

    /*
    insert into the root list (become the left sibling of the root)
    nodeNum + 1
    */
    public void insert(Node node) {
        minNode = mergeLists(minNode, node);
        nodeNum++;
    }


    /*
    Add the minimum node's children into the root list and remove the minimum node.
    Then consolidate the root list by linking roots of equal degree until
      at most one root remains of each degree.
     */
    public Node extractMin() {
        Node extractedMin = minNode;
        if (extractedMin != null) {
            Node child = extractedMin.child;
            if (child != null) {
                // set all children's parent to null
                Node curt = child;
                while (curt.right != child) {
                    curt.parent = null;
                    curt = curt.right;
                }
            }
            // merge the children of the extracted minimum node with the root list
            minNode = mergeLists(minNode, extractedMin.child);

            // the node after the minNode becomes the tempMinNode
            Node tempMinNode = (minNode.right == minNode ? null : minNode.right);
            // remove minNode node
            removeNodeFromList(minNode);
            nodeNum--;

            if (tempMinNode != null) {
                minNode = tempMinNode;
                consolidate();
            }
        }

        return extractedMin;
    }

    /*
    Repeatedly execute the following steps until every root in the root list
       has a distinct degree value:
    1. Find two roots x and y in the root list with the same degree. Without
       loss of generality, let x.key<=y.key.
    2. Link y to x: remove y from the root list, and make y a child of x by
       calling the FIB-HEAP-LINK procedure. This procedure increments the
       attribute x.degree and clears the mark on y.
     */
    private void consolidate() {
        int size = nodeNum + 1;
        Node[] array = new Node[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }

        // traverse all root nodes
        NodeListIterator iter = new NodeListIterator(minNode);
        while (iter.hasNext()) {
            Node curt = iter.next();
            // merge heaps that has the same degree
            while (array[curt.degree] != null) {
                if (curt.key > array[curt.degree].key) {
                    Node temp = curt;
                    curt = array[curt.degree];
                    array[curt.degree] = temp;
                }
                link(array[curt.degree], curt);
                array[curt.degree] = null;
                curt.degree++;
            }
            array[curt.degree] = curt;
        }

        // reset the minNode
        minNode = null;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                Node nRoot = array[i];
                nRoot.left = nRoot;
                nRoot.right = nRoot;
                minNode = mergeLists(minNode, nRoot);
            }
        }
    }

    // link a as b's child
    private void link(Node a, Node b) {
        removeNodeFromList(a);
        b.child = mergeLists(a, b.child);
        a.parent = b;
        a.mark = false;
    }

    /*
    concatenate the root list of these two heaps, set the minmum node
         as the new minNode
    add the two heaps' nodeNum together
    */
    public Node union(FibonacciHeap heap) {
        minNode = mergeLists(minNode, heap.minNode);
        nodeNum += heap.nodeNum;
        return minNode;
    }

    /*
    If the srcNode is root or its key >= its parent's key, then no structural
       changes need occur, since min-heap order has not been violated.
    Otherwise perform cut and cascadeCut operations.
     */
    public void decreaseKey(Node srcNode, int destKey) {
        if (destKey > srcNode.key) {
            throw new IllegalArgumentException("new key is greater than current key");
        }

        srcNode.key = destKey;
        Node parent = srcNode.parent;
        if (parent != null && srcNode.key < parent.key) {
            cut(srcNode, parent);
            cascadingCut(parent);
        }

        if (srcNode.key < minNode.key) {
            minNode = srcNode;
        }
    }

    /*
    remove the node from its sibling list
    merge the node with the root list
    set its mark to false
     */
    private void cut(Node node, Node parent) {
        if (node.right == node) {
            parent.child = null;
        } else {
            parent.child = node.right;
        }
        removeNodeFromList(node);
        parent.degree--;
        mergeLists(minNode, node);
        node.mark = false;
    }

    /*
    if the node is marked and is not the root, cut it from the heap
    recursively repeat this process until arriving at the root or it is not marked
     */
    private void cascadingCut(Node node) {
        Node parent = node.parent;
        if (parent != null) {
            if (node.mark) {
                cut(node, parent);
                cascadingCut(parent);
            } else {
                node.mark = true;
            }
        }
    }

    public void delete(Node node) {
        decreaseKey(node, Integer.MIN_VALUE);
        extractMin();
    }

    private Node mergeLists(Node a, Node b) {
        if (a == null && b == null) {
            return null;
        }
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        Node temp = a.right;
        a.right = b.right;
        a.right.left = a;
        b.right = temp;
        b.right.left = b;

        return a.key < b.key ? a : b;
    }

    private void removeNodeFromList(Node node) {
        node.left.right = node.right;
        node.right.left = node.left;
        node.right = node;
        node.left = node;
        node.parent = null;
    }

    public void print() {
        minNode.print();
    }
}
