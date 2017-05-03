package edu.neu.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Sorting {

    public static int[] bubbleSort(int a[]) {
        int len = a.length;

        for (int i = len - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }
        return a;
    }

    public static int[] selectionSort(int a[]) {
        int len = a.length;
        for (int i = 0; i < len; i++) {
            int smallestIndex = i;
            for (int j = i; j < len; j++) {
                if (a[j] < a[smallestIndex]) {
                    smallestIndex = j;
                }
            }
            swap(a, i, smallestIndex);
        }
        return a;
    }

    public static int[] insertionSort(int a[]) {
        int len = a.length;
        for (int i = 1; i < len; i++) {
            int num = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > num) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = num;
        }
        return a;
    }

    private static void swap(int a[], int fromIndex, int toIndex) {
        int tmp = a[fromIndex];
        a[fromIndex] = a[toIndex];
        a[toIndex] = tmp;
    }

    // O(n) space
    // O(nlogn) time complexity
    public static int[] mergeSort(int a[]) {
        int[] tmp = new int[a.length];
        mergeSort(a, 0, a.length - 1, tmp);
        return a;
    }

    private static void mergeSort(int a[], int low, int high, int tmp[]) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(a, low, mid, tmp);
            mergeSort(a, mid + 1, high, tmp);
//            System.out.println(low + "," + mid + "," + high);
            merge(a, low, mid, high, tmp);
        }
    }

    private static void merge(int a[], int low, int mid, int high, int tmp[]) {
        int i = low;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }

        while (i <= mid) {
            tmp[k++] = a[i++];
        }

        while (j <= high) {
            tmp[k++] = a[j++];
        }

        for (int n = 0; n < high - low + 1; n++) {
            a[low + n] = tmp[n];
        }
    }

    // O(1) space
    // O(nlogn) time complexity in average, O(n^2) in the worst case
    public static int[] quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
        return a;
    }

    private static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int mid = partition(a, low, high);
            quickSort(a, low, mid - 1);
            quickSort(a, mid + 1, high);
        }
    }

    private static int partition(int[] a, int low, int high) {
        int pivot = a[(low + high) / 2];
        int i = low;
        int j = high;
        while (i < j) {
            while (i < j && a[j] >= pivot) {
                j--;
            }
            while (i < j && a[i] <= pivot) {
                i++;
            }

            if (i != j) {
                swap(a, i, j);
            }
        }

        a[low] = a[i];
        a[i] = pivot;
        return i;
    }

    public static void heapSort(int[] a) {
        int heapSize = a.length - 1;
        buildMaxHeap(a, heapSize);
        for (int i = a.length - 1; i > 0; i--) {
            swap(a, 0, i);
            heapSize--;
            maxHeapify(a, heapSize, 0);
        }
    }

    private static void buildMaxHeap(int[] a, int heapSize) {
        for (int i = a.length / 2; i >= 0; i--) {
            maxHeapify(a, heapSize, i);
        }
    }

    private static void maxHeapify(int[] a, int heapSize, int i) {
        int left = 2 * i;
        int right = 2 * i + 1;
        int largest = i;
        if (left <= heapSize && a[left] > a[i]) {
            largest = left;
        }
        if (right <= heapSize && a[right] > a[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(a, largest, i);
            maxHeapify(a, heapSize, largest);
        }
    }

    public static void countingSort(int[] a) {
        int maxNum = getMaxNum(a);
        countingSort(a, maxNum);
    }

    private static int getMaxNum(int[] a) {
        int maxNum = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > maxNum) {
                maxNum = a[i];
            }
        }
        return maxNum;
    }

    private static void countingSort(int[] a, int maxNum) {
        int[] tempArr = new int[a.length];
        int[] counter = new int[maxNum + 1];

        // counter[i] now contains the number of elements equal to i
        for (int j = 0; j < a.length; j++) {
            counter[a[j]] = counter[a[j]] + 1;
        }

        // counter[i] now contains the number of elements less than or equal to i
        for (int i = 1; i < counter.length; i++) {
            counter[i] = counter[i] + counter[i - 1];
        }

        // numbers in a[] are now placed in tempArr's right position
        for (int j = a.length - 1; j >= 0; j--) {
            tempArr[counter[a[j]] - 1] = a[j];
            counter[a[j]] = counter[a[j]] - 1;
        }

        for (int j = 0; j < a.length; j++) {
            a[j] = tempArr[j];
        }
    }

    public static void bucketSort(int[] a) {
        // default bucket size
        int bucketSize = 20;

        // determine the bucket count based on the input range
        int min = a[0], max = a[0];
        for (int i = 0; i < a.length; i++) {
            min = Math.min(min, a[i]);
            max = Math.max(max, a[i]);
        }
        int bucketCount = (max - min + 1) / bucketSize + 1;

        // initialize the buckets
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        // distribute numbers into the buckets
        for (int i = 0; i < a.length; i++) {
            buckets[(a[i] - min) / bucketSize].add(a[i]);
        }

        // restore input following their sequences in the buckets
        int cur = 0;
        for (int i = 0; i < bucketCount; i++) {
            List<Integer> bucket = buckets[i];
            // sort the bucket
            Collections.sort(bucket);
            for (int j = 0; j < bucket.size(); j++) {
                a[cur++] = bucket.get(j);
            }
        }
    }

    private static void printArray(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i] + ",");
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    private static int[] createRandomIntArray(int length) {
        int[] a = new int[length];
        for (int i = 0; i < length; i++) {
            a[i] = new Random().nextInt(length);
        }
        return a;
    }

    private static float[] createRandomFloatArray(int length) {
        float[] a = new float[length];
        for (int i = 0; i < length; i++) {
            a[i] = new Random().nextFloat();
        }
        return a;
    }

    private static int[] createReversedArray(int length) {
        int[] a = new int[length];
        for (int i = 0; i < length; i++) {
            a[i] = length - i;
        }
        return a;
    }

    public static void main(String[] args) {
//        int a[] = {2, 4, 5, 63, 2432, 5, 2435, 432, 34245, 2, -1, 0, 432, 5435, 431, 5346, 3425, 43, 7, 9};
//        int a[] = {5, 4, 3, 2, 1};
//        int a[] = createReversedArray(100);
        int a[] = createRandomIntArray(1000);
//        int a[] = {2, 1};
//        float a[] = createRandomFloatArray(100);
//        System.out.println(a);

        printArray(a);
        StopWatch stopWatch = new StopWatch();
//        bubbleSort(a);
//        selectionSort(a);
//        insertionSort(a);
//        mergeSort(a);
        quickSort(a);
//        heapSort(a);
//        countingSort(a);
//        bucketSort(a);
        printArray(a);
        int i = 1;
        while (i < a.length) {
            if (a[i] < a[i - 1]) {
                System.err.println("failed to sort the given array");
                break;
            }
            i++;
        }

        if (i == a.length) {
            System.out.println("successfully sorted the given array");
        }

        System.out.println(stopWatch.ellapseTime());
    }
}