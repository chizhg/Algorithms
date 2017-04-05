package edu.neu.hash;

import java.util.*;

class Node<K, V> {
    K key;
    V value;
    Node next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class HashTable<K, V> {
    private Node<K, V>[] buckets;
    private int capacity;
    private int size;
    private Set<K> keySet;

    public HashTable() {
        buckets = new Node[10];
        capacity = 10;
        size = 0;
        keySet = new HashSet<>();
    }

    private void put(K key, V value) {
        keySet.add(key);

        int bucketPos = getBucketPos(key);
        Node head = buckets[bucketPos];

        if (head == null) {
            buckets[bucketPos] = new Node(key, value);
            size++;
        } else {
            Node curt = head;
            while (curt != null) {
                if (curt.key.equals(key)) {
                    curt.value = value;
                    break;
                }
                curt = curt.next;
            }

            if (curt == null) {
                Node newHead = new Node(key, value);
                newHead.next = head;
                buckets[bucketPos] = newHead;
                size++;
            }
        }

        if ((1.0 * size) / capacity > 0.7) {
            Node<K, V>[] oriBuckets = buckets;
            capacity *= 2;
            buckets = new Node[capacity];
            for (Node<K, V> node : oriBuckets) {
                while (node != null) {
                    put(node.key, node.value);
                    node = node.next;
                }
            }
        }
    }

    public boolean remove(K key) {
        int bucketPos = getBucketPos(key);
        Node head = buckets[bucketPos];
        Node dummyHead = new Node("", new LinkedList<>());
        dummyHead.next = head;

        Node curt = dummyHead;
        while (curt.next != null) {
            if (curt.next.key.equals(key)) {
                curt.next = curt.next.next;
                buckets[bucketPos] = dummyHead.next;
                keySet.remove(key);
                size--;
                return true;
            }
            curt = curt.next;
        }

        return false;
    }

    // Find the value for a key. If the key is not in the map, return null.
    public V get(K key) {
        int bucketPos = getBucketPos(key);
        Node<K, V> head = buckets[bucketPos];
        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }

        return null;
    }

    // return all keys in this map
    public Set<K> keySet() {
        return keySet;
    }

    private int getBucketPos(K key) {
        long hash = hashCode(key);
        int pos = (int) (hash % capacity);
        return pos;
    }

    private int hashCode(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /*
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
    } */
}