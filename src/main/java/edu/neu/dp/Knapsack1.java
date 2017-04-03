package edu.neu.dp;

/**
 * Created by HappyMole on 2/17/17.
 */
public class Knapsack1 {
    public void maxValue(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(values[i - 1] + dp[i - 1][j - weights[i - 1]], dp[i - 1][j]);
                }
//                System.out.print(dp[i][j] + ", ");
            }
//            System.out.println();
        }

        int remaining = capacity;
        System.out.print("Items we need to put into the knapsack: ");
        for (int i = n; i > 0; i--) {
            if (dp[i][remaining] > dp[i - 1][remaining]) {
                System.out.print(i - 1);
                System.out.print(" ");
                remaining -= weights[i - 1];
            }
        }
        System.out.println();
        System.out.println("The maximum value we can get is: " + dp[n][capacity]);
    }

    public static void main(String[] args) {
//        int[] weights = new int[]{1, 2, 3, 5};
//        int[] values = new int[]{6, 10, 12, 15};
        int[] weights = new int[]{2, 7, 3, 4, 8, 5, 8, 6, 4, 16};
        int[] values = new int[]{15, 25, 8, 9, 15, 9, 13, 9, 6, 14};

        new Knapsack1().maxValue(weights, values, 34);
    }
}
