package edu.neu.dp;

/*
    1. Optimal substructure:
       Let (x1,x2,...,xm) be the solution for the knapsack problem "knapsack(n,W,V,C)", then:
       a). If m=n(the optimal solution includes the nth item),
           (x1,x2,...,xm-1) must be an optimal solution for the problem "knapsack(n-1, W, V, C-Wn)"
       b). If m!=n(the optimal solution does not include the nth item),
           (x1,x2,...,xm) must be an optimal solution for the problem "knapsack(n-1, W, V, C)
    2. Recursive solution:
       Define dp[i, j] to be the maximum value we can get by putting the first i items into a knapsack with j capacity
                  |- 0                                      if i = 0 or j = 0
       dp[i, j] = |- dp[i-1, j]                             if j < Wi (we cannot put the ith item into the knapsack)
                  |- max(dp[i-1, j], dp[i-1, j-Wi] + Vi)    if j >= Wi
 */
public class Knapsack {
    public static void main(String[] args) {
//        int[] weights = new int[]{1, 2, 3, 5};
//        int[] values = new int[]{6, 10, 12, 15};
        int[] weights = new int[]{2, 7, 3, 4, 8, 5, 8, 6, 4, 16};
        int[] values = new int[]{15, 25, 8, 9, 15, 9, 13, 9, 6, 14};
        int capacity = 34;

        Knapsack knapsack = new Knapsack();
        int[][] dp = knapsack.solve(weights, values, 34);
        System.out.println("The maximum value we can get is: " + dp[weights.length][capacity]);
        knapsack.printItems(dp, weights, capacity);
    }

    public int[][] solve(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (weights[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(values[i - 1] + dp[i - 1][j - weights[i - 1]],
                            dp[i - 1][j]);
                }
            }
        }

        return dp;
    }

    private void printItems(int[][] dp, int[] weights, int capacity) {
        int n = weights.length;
        int remaining = capacity;
        System.out.print("Items we need to put into the knapsack: ");
        for (int i = n; i > 0; i--) {
            // we have put this item into the knapsack to get the maximum value
            if (dp[i][remaining] > dp[i - 1][remaining]) {
                System.out.print(i - 1);
                System.out.print(" ");
                remaining -= weights[i - 1];
            }
        }
    }
}
