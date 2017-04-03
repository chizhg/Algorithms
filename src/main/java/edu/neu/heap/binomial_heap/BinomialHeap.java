package edu.neu.heap.binomial_heap;

import edu.neu.heap.IMergeableHeap;

public class BinomialHeap implements IMergeableHeap<Node> {
    private Node head;
    private int nodeNum;

    private BinomialHeap() {
        head = null;
        nodeNum = 0;
    }

    public static BinomialHeap makeHeap() {
        return new BinomialHeap();
    }

    public static void main(String[] args) {
        BinomialHeap binomialHeap = makeHeap();
        for (int i = 0; i < 109; i++) {
            binomialHeap.insert(new Node(i));
        }

        binomialHeap.print();
//        System.out.println(binomialHeap.search(binomialHeap.head, 10).key);
    }

    public int size() {
        return nodeNum;
    }

    public Node searchByLabel(int searchLabel) {
        return search(searchLabel, false);
    }

    public Node searchByVal(int searchVal) {
        return search(searchVal, true);
    }

    private Node search(int searchVal, boolean byVal) {
        return search(head, searchVal, byVal);
    }

    private Node search(Node node, int searchVal, boolean byVal) {
        if (node == null) {
            return null;
        }

        if (byVal && node.key == searchVal) {
            return node;
        }

        if (!byVal && node.label == searchVal) {
            return node;
        }

        Node result1 = search(node.sibling, searchVal, byVal);
        if (result1 != null) {
            return result1;
        }
        Node result2 = search(node.child, searchVal, byVal);
        if (result2 != null) {
            return result2;
        }

        return null;
    }

    public Node minimum() {
        if (head == null) {
            return null;
        } else {
            Node min = head;
            Node next = min.sibling;

            while (next != null) {
                if (next.key < min.key) {
                    min = next;
                }
                next = next.sibling;
            }

            return min;
        }
    }

    public void insert(Node node) {
        BinomialHeap tempHeap = makeHeap();
        tempHeap.head = node;
        head = union(tempHeap);
        nodeNum++;
    }

    public void decreaseKey(Node node, int newKey) {
        if (newKey > node.key) {
            throw new IllegalArgumentException("new key is greater than current key");
        }

        node.key = newKey;
        bubbleUp(node, false);
    }

    public void delete(Node node) {
        decreaseKey(node, Integer.MIN_VALUE);
        extractMin();
    }

    private Node bubbleUp(Node node, boolean toRoot) {
        Node parent = node.parent;
        while (parent != null && (toRoot || node.key < parent.key)) {
            int temp = node.key;
            node.key = parent.key;
            parent.key = temp;
            node = parent;
            parent = parent.parent;
        }
        return node;
    }

    public Node extractMin() {
        if (head == null) {
            return null;
        }

        Node min = head;
        Node minPrev = null;
        Node next = min.sibling;
        Node nextPrev = min;

        while (next != null) {
            if (next.key < min.key) {
                min = next;
                minPrev = nextPrev;
            }
            nextPrev = next;
            next = next.sibling;
        }

        removeTreeRoot(min, minPrev);
        nodeNum--;
        return min;
    }

    private void removeTreeRoot(Node root, Node prev) {
        // Remove root from the heap
        if (root == head) {
            head = root.sibling;
        } else {
            prev.sibling = root.sibling;
        }

        // Reverse the order of root's children and make a new heap
        Node newHead = null;
        Node child = root.child;
        while (child != null) {
            Node next = child.sibling;
            child.sibling = newHead;
            child.parent = null;
            newHead = child;
            child = next;
        }
        BinomialHeap newHeap = makeHeap();
        newHeap.head = newHead;

        // Union the heaps and set its head as this.head
        head = union(newHeap);
    }

    // Merge two binomial trees of the same order
    private void linkTree(Node minNodeTree, Node other) {
        other.parent = minNodeTree;
        other.sibling = minNodeTree.child;
        minNodeTree.child = other;
        minNodeTree.degree++;
    }

    // Union two binomial heaps into one and return the head
    public Node union(IMergeableHeap heap) {
        if (!(heap instanceof BinomialHeap)) {
            return null;
        }

        BinomialHeap binomialHeap = (BinomialHeap)heap;
        Node newHead = merge(this, binomialHeap);

        head = null;
        binomialHeap.head = null;

        if (newHead == null) {
            return null;
        }

        Node prev = null;
        Node curt = newHead;
        Node next = newHead.sibling;

        while (next != null) {
            if (curt.degree != next.degree || (next.sibling != null &&
                    next.sibling.degree == curt.degree)) {
                prev = curt;
                curt = next;
            } else {
                if (curt.key < next.key) {
                    curt.sibling = next.sibling;
                    linkTree(curt, next);
                } else {
                    if (prev == null) {
                        newHead = next;
                    } else {
                        prev.sibling = next;
                    }

                    linkTree(next, curt);
                    curt = next;
                }
            }

            next = curt.sibling;
        }

        nodeNum += binomialHeap.size();
        return newHead;
    }

    // merge the two heaps' head list, and return a new head
    private Node merge(BinomialHeap heap1, BinomialHeap heap2) {
        if (heap1.head == null) {
            return heap2.head;
        } else if (heap2.head == null) {
            return heap1.head;
        } else {
            Node head;
            Node heap1Next = heap1.head;
            Node heap2Next = heap2.head;

            if (heap1.head.degree <= heap2.head.degree) {
                head = heap1.head;
                heap1Next = heap1Next.sibling;
            } else {
                head = heap2.head;
                heap2Next = heap2Next.sibling;
            }

            Node curt = head;

            while (heap1Next != null && heap2Next != null) {
                if (heap1Next.degree <= heap2Next.degree) {
                    curt.sibling = heap1Next;
                    heap1Next = heap1Next.sibling;
                } else {
                    curt.sibling = heap2Next;
                    heap2Next = heap2Next.sibling;
                }

                curt = curt.sibling;
            }

            if (heap1Next != null) {
                curt.sibling = heap1Next;
            } else {
                curt.sibling = heap2Next;
            }

            return head;
        }
    }

    public void print() {
        head.print();
    }
}