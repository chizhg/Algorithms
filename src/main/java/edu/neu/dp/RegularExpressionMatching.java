package edu.neu.dp;

/*
    Rules for regular expression:
        "." matches any single character
        "*" matches zero or more of the preceding element
 */

/*
    1. Optimal substructure
       Let {s1,s2,...,sm} be the string, and {p1,p2,...,pn} be the regular expression
       a) if pn='*', then if {s1,s2,...,sm} can match with {p1,p2,...,pn-2}, S and P can match
       b) if pn='*' and (pn-1='.' or pn-1=sm), if {s1,s2,...,sm-1} can match with {p1,p2,...,pn}, S and P can match
       c) else only if (sm=pn or pn='.') and {s1,s2,...,sm-1} match with {p1,p2,...,pn-1}, can S and P match
    2. Recursive solution
       Define dp[i][j]: if the first i characters of S can match with the first j characters of P
                  |- true                      if i = 0 and j = 0
                  |- dp[0][j-2]                if i = 0 and pj = '*'
       dp[i][j] = |- dp[i][j-2]                if pj = '*' and (si!=pj-1 && pj-1!='.')
                  |- dp[i][j-2] || dp[i-1][j]  if pj = '*' and (si=pj-1 || pj-1='.')
                  |- dp[i-1][j-1] && (si=pj || pj='.')    if pj != '*'
 */
public class RegularExpressionMatching {
    public static void main(String[] args) {
        RegularExpressionMatching r = new RegularExpressionMatching();
        String s = "aab";
        String p = "c*a*b";
        System.out.println(r.solve(s, p));
    }

    public boolean solve(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 2; j <= n; j++) {
            dp[0][j] = (dp[0][j - 2] && p.charAt(j - 1) == '*');
        }

        for (int i = 1; i <= m; i++) {
            char letter = s.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char pattern = p.charAt(j - 1);
                if (pattern == '*') {
                    if (p.charAt(j - 2) == letter || p.charAt(j - 2) == '.') {
                        // match zero or more
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                    } else {
                        // match zero
                        dp[i][j] = dp[i][j - 2];
                    }
                } else {
                    dp[i][j] = (dp[i - 1][j - 1] && (pattern == letter || pattern == '.'));
                }
            }
        }

        return dp[m][n];
    }
}
