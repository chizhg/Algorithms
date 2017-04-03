package edu.neu.dp;

/**
 * Created by HappyMole on 2/27/17.
 */
public class LongestPalindromeSubsequence {
    public int solve(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j == i + 1) {
                        dp[i][j] = 2;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1] + 2;
                    }
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        int left = 0, right = n - 1;
        int maxLen = dp[left][right];
        while (left < right) {
            if (dp[left + 1][right - 1] == maxLen - 2) {
                result.append(2);
                result.append(s.charAt(left));
                maxLen -= 2;
                left++;
                right--;
            } else if (dp[left + 1][right] == dp[left][right]) {
                left++;
            } else {
                right--;
            }

            if (maxLen == 1 || maxLen == 0) {
                break;
            }
        }

        if (maxLen == 1) {
            result.append(1);
            result.append(s.charAt(left));
        }
        System.out.println(result.toString());
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        LongestPalindromeSubsequence l = new LongestPalindromeSubsequence();
//        System.out.println(l.solve("character"));
        System.out.println(l.solve("rvamdcebcxaur"));
    }
}
