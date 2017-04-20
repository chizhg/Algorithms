package edu.neu.dp;

import java.util.ArrayList;
import java.util.List;

/*
    1. Optimal substructure:
       Let n be the rod length and {x1,x2,...,xm} be lengths of cut rods in the optimal solution.
        {x1,x2,...,xm-1} must be the optimal solution for a rod of length n-xm
    2. Recursive solution:
       Define dp[i] to be the maximum revenue for cutting a rod of i length.
               |- 0                      if i = 0
       dp[i] = |- max(dp[j] + P[i-j])    for 0<=j<=i if i>0
 */
public class RodCutting {
    public int[] solve(int[] prices, int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (i - j < prices.length) {
                    dp[i] = Math.max(dp[j] + prices[i - j], dp[i]);
                }
            }
        }
        return dp;
    }

    private void printRodLen(int[] dp, int[] prices, int n) {
        List<Integer> rodLen = new ArrayList<>();
        int revenue = dp[n];
        for (int i = n, j = n; i >= 0 && revenue > 0; i--) {
            if (revenue - dp[i] != 0) {
                rodLen.add(0, j - i);
                revenue -= prices[j - i];
            }
        }

        System.out.println("The cut rod lengths are: ");
        for (Integer i : rodLen) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        RodCutting r = new RodCutting();
        int n = 10;
        int[] prices = new int[]{0, 1, 5, 8, 9, 10, 17, 18, 20, 24, 30};
        int[] dp = r.solve(prices, n);
        System.out.println("The maximum revenue for cutting the rod is: " + dp[n]);
        r.printRodLen(dp, prices, n);
    }
}
