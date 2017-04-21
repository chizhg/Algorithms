package edu.neu.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
    For example, given the following triangle
    [
         [2],
        [3,4],
       [6,5,7],
      [4,1,8,3]
    ]
    The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 */

/*
    1. Optimal substructure:

    2. Recursive solution:

 */
public class Triangle {
    public static void main(String[] args) {
        List<Integer> line0 = new ArrayList<>(Arrays.asList(2));
        List<Integer> line1 = new ArrayList<>(Arrays.asList(3, 4));
        List<Integer> line2 = new ArrayList<>(Arrays.asList(6, 5, 7));
        List<Integer> line3 = new ArrayList<>(Arrays.asList(4, 1, 8, 3));
        List<List<Integer>> triangle = Arrays.asList(line0, line1, line2, line3);

        Triangle t = new Triangle();
        System.out.println(t.solve(triangle));
    }

    public int solve(List<List<Integer>> triangle) {
        List<Integer> lastLine = triangle.get(triangle.size() - 1);
        int[] dp = new int[lastLine.size()];

        for (int i = 0; i < lastLine.size(); i++) {
            dp[i] = lastLine.get(i);
        }

        for (int i = triangle.size() - 2; i >= 0; i--) {
            List<Integer> line = triangle.get(i);
            for (int j = 0; j < line.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + line.get(j);
            }
        }

        return dp[0];
    }
}
