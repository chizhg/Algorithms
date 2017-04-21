package edu.neu.dp;

/*
    We are playing the Guess Game. The game is as follows:
    I pick a number from 1 to n. You have to guess which number I picked.
    Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.
    However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.

    Example:
    n = 10, I pick 8.
    First round:  You guess 5, I tell you that it's higher. You pay $5.
    Second round: You guess 7, I tell you that it's higher. You pay $7.
    Third round:  You guess 9, I tell you that it's lower. You pay $9.
    Game over. 8 is the number I picked.
    You end up paying $5 + $7 + $9 = $21.

    Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
 */
public class GuessGame {
    public int getMoneyAmount(int n) {
        // dp[i][j] (j > i) means the minimum money amount we need to cost to guess a number in the range of i and j
        int[][] dp = new int[n + 1][n + 1];
        return calculate(1, n, dp);
    }

    private int calculate(int low, int high, int[][] dp) {
        // if remaining only one number or no number, no money needs to cost
        if (low >= high) {
            return 0;
        }

        if (dp[low][high] != 0) {
            return dp[low][high];
        }

        int minCost = Integer.MAX_VALUE;
        for (int i = low; i <= high; i++) {
            int cost = i + Math.max(calculate(low, i - 1, dp), calculate(i + 1, high, dp));
            minCost = Math.min(cost, minCost);
        }
        dp[low][high] = minCost;
        return minCost;
    }
}
