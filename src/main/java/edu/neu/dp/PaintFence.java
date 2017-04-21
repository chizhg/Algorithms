package edu.neu.dp;

/*
    There is a fence with n posts, each post can be painted with one of the k colors.
    You have to paint all the posts such that no more than two adjacent fence posts have the same color.
    Return the total number of ways you can paint the fence.

    Note:
    n and k are non-negative integers.
 */
public class PaintFence {
    public static void main(String[] args) {
        int n = 5, k = 7;
        PaintFence p = new PaintFence();
        System.out.println(p.solve(n, k));
    }

    public int solve(int n, int k) {
        if (n == 0 || k == 0) {
            return 0;
        }

        if (n == 1) {
            return k;
        }

        // same[i] means the ith post has the same color with the (i-1)th
        int[] same = new int[n];
        // diff[i] means the ith post has a different color with the (i-1)th
        int[] diff = new int[n];
        same[0] = same[1] = k;
        diff[0] = k;
        diff[1] = k * (k - 1);

        for (int i = 2; i < n; i++) {
            same[i] = Math.max(same[i - 1], diff[i - 1]);
            diff[i] = (k - 1) * (same[i - 1] + diff[i - 1]);
        }

        return same[n - 1] + diff[n - 1];
    }
}
