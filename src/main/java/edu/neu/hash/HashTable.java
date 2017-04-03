package edu.neu.hash;

import java.util.*;

class Position {
    int row;
    int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Node {
    String key;
    List<Position> value;
    Node next;

    public Node(String key, List<Position> value) {
        this.key = key;
        this.value = value;
    }
}

public class HashTable {
    private Node[] buckets;
    private int capacity;
    private int size;
    private Set<String> wordSet;

    public HashTable() {
        buckets = new Node[10];
        capacity = 10;
        size = 0;
        wordSet = new HashSet<>();
    }

    // insert the key into the map with the given value.
    // if the key has already been in the map, replace the value.
    public void insert(String key, List<Position> value) {
        Position pos = parsePos(key);
        key = parseWord(key);
        value.add(pos);
        put(key, value);
    }

    private void put(String key, List<Position> posList) {
        wordSet.add(key);

        int bucketPos = getBucketPos(key);
        Node head = buckets[bucketPos];

        if (head == null) {
            buckets[bucketPos] = new Node(key, posList);
            size++;
        } else {
            Node curt = head;
            while (curt != null) {
                if (curt.key.equals(key)) {
                    curt.value = posList;
                    break;
                }
                curt = curt.next;
            }

            if (curt == null) {
                Node newHead = new Node(key, posList);
                newHead.next = head;
                buckets[bucketPos] = newHead;
                size++;
            }
        }

        if ((1.0 * size) / capacity > 0.7) {
            Node[] oriBuckets = buckets;
            capacity *= 2;
            buckets = new Node[capacity];
            for (Node node : oriBuckets) {
                while (node != null) {
                    put(node.key, node.value);
                    node = node.next;
                }
            }
        }
    }

    // delete the key from the map, and return the position list of the word.
    // if the key is not in the map, return null.
    public List<Position> delete(String key) {
        key = parseWord(key);
        int bucketPos = getBucketPos(key);
        Node head = buckets[bucketPos];
        Node dummyHead = new Node("", new LinkedList<>());
        dummyHead.next = head;

        Node curt = dummyHead;
        while (curt.next != null) {
            if (curt.next.key.equals(key)) {
                List<Position> delValue = curt.next.value;
                curt.next = curt.next.next;
                buckets[bucketPos] = dummyHead.next;
                wordSet.remove(key);
                size--;
                return delValue;
            }
            curt = curt.next;
        }

        return null;
    }

    // Increase the value in the map for the given key and return the updated value.
    // If the key is not in the map, return null.
    public List<Position> increase(String key) {
        Position pos = parsePos(key);
        key = parseWord(key);

        int bucketPos = getBucketPos(key);
        Node head = buckets[bucketPos];
        while (head != null) {
            if (head.key.equals(key)) {
                head.value.add(pos);
                return head.value;
            }
            head = head.next;
        }

        return null;
    }

    // Find the value for a key. If the key is not in the map, return null.
    public List<Position> find(String key) {
        key = parseWord(key);
        int bucketPos = getBucketPos(key);
        Node head = buckets[bucketPos];
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }

        return null;
    }

    // return all keys in this map
    public Set<String> listAllKeys() {
        return wordSet;
    }

    private int getBucketPos(String key) {
        long hash = hashCode(key);
        int pos = (int) (hash % capacity);
        return pos;
    }

    // use ELF hash to compute the hash value for a string
    private long hashCode(String s) {
        long hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash << 4) + s.charAt(i);
            long t = hash & 0xF0000000;
            if (t != 0) {
                hash ^= (t >> 24);
            }
            hash &= ~t;
        }

        return hash;
    }

    private String parseWord(String key) {
        return key.split(" ")[0];
    }

    private Position parsePos(String key) {
        String[] arr = key.split(" ");
        int row = Integer.parseInt(arr[1].trim());
        int col = Integer.parseInt(arr[2].trim());
        return new Position(row, col);
    }
}