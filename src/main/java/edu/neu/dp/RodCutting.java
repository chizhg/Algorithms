package edu.neu.dp;

/**
 * Created by HappyMole on 2/18/17.
 */
public class RodCutting {
    public int maxRevenue(int[] prices, int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (i - j < prices.length) {
                    dp[i] = Math.max(dp[j] + prices[i - j], dp[i]);
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        RodCutting r = new RodCutting();
        System.out.println(r.maxRevenue(new int[]{0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30}, 10));
    }
}
