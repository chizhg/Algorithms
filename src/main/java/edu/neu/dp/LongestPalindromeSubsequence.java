package edu.neu.dp;

/*
    1. Optimal substructure:
       Let X={x1,x2,...,xn} be the given string, and let Y={y1,y2,...,ym} be the LPS of X.
       a) If x1=xn, then y1=ym=x1=xn and {y2,...,ym-1} is the LPS of {x2,...,xn-1)
       b) If x1!=xn and y1=ym=x1, then {y1,y2,...ym} is the LPS of {x1,x2,...,xn-1)
       c) If x1!=xn and y1=ym=xn, then {y1,y2,...ym} is the LPS of {x2,x3,...,xn)
       d) Else {y1,y2,...,ym} is the LPS of {x2,x3,...,xn-1}
    2. Recursive solution:
       Define dp[i, j] to be the LPS of substring {xi, xi+1, ..., xj}.
                  |- 1                           if i=j (the shortest LPS is 1)
       dp[i, j] = |- 2                           if xi=xj and xj-xi=1
                  |- dp[i+1,j-1] + 2             if xi=xj and xj-xi!=1
                  |- max(dp[i+1,j], dp[i][j-1])  if xi!=xj
 */
public class LongestPalindromeSubsequence {
    public int[][] solve(String s) {
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

        return dp;
    }

    private void printLPS(String s, int[][] dp) {
        int n = s.length();
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
        System.out.println("The LPS for this string is: " + result.toString());
    }

    public static void main(String[] args) {
        LongestPalindromeSubsequence l = new LongestPalindromeSubsequence();
        String s = "rvamdcebcxabur";
        int[][] dp = l.solve(s);
        System.out.println("The length of the LPS is: " + dp[0][s.length() - 1]);
        l.printLPS(s, dp);
    }
}
