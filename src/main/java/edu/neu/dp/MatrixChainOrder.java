package edu.neu.dp;

/**
 * Created by HappyMole on 2/18/17.
 */
public class MatrixChainOrder {
    int[][] m;
    int[][] s;

    public static void main(String[] args) {
        MatrixChainOrder matrixChainOrder = new MatrixChainOrder();
//        matrixChainOrder.calculateOptimalOrder(new int[]{30, 35, 15, 5, 10, 20, 25});
//        matrixChainOrder.calculateOptimalOrder(new int[]{5, 10, 3, 12, 5, 50, 6});
//        System.out.println(matrixChainOrder.m[1][6]);

//        System.out.println(matrixChainOrder.recursiveMatrixChain(new int[]{30, 35, 15, 5, 10, 20, 25}));
        System.out.println(matrixChainOrder.memorizedMatrixChain(new int[]{30, 35, 15, 5, 10, 20, 25}));
    }

    public void calculateOptimalOrder(int[] p) {
        int n = p.length;
        m = new int[n][n];
        s = new int[n][n];
        for (int i = 1; i < n; i++) {
            m[i][i] = 0;
        }

        for (int l = 2; l < n; l++) {  // length of matrices
            for (int i = 1; i < n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = m[i + 1][j] + p[i - 1] * p[i] * p[j];
                s[i][j] = i;
                for (int k = i + 1; k < j; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        printOptimalParens(1, 6);
        System.out.println();
    }

    public void printOptimalParens(int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
        } else {
            System.out.print("(");
            printOptimalParens(i, s[i][j]);
            printOptimalParens(s[i][j] + 1, j);
            System.out.print(")");
        }
    }

    public int recursiveMatrixChain(int[] p) {
        int n = p.length;
        int[][] m = new int[n][n];
        return recursiveMatrixChain(p, m, 1, p.length - 1);
    }

    private int recursiveMatrixChain(int[] p, int[][] m, int i, int j) {
        if (i == j) {
            return 0;
        }

        m[i][j] = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int q = recursiveMatrixChain(p, m, i, k) +
                    recursiveMatrixChain(p, m, k + 1, j) +
                    p[i - 1] * p[k] * p[j];
            if (q < m[i][j]) {
                m[i][j] = q;
            }
        }

        return m[i][j];
    }

    public int memorizedMatrixChain(int[] p) {
        int n = p.length;
        int[][] m = new int[n][n];
        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                m[i][j] = Integer.MAX_VALUE;
            }
        }
        return memorizedMatrixChain(p, m, 1, n - 1);
    }

    private int memorizedMatrixChain(int[] p, int[][] m, int i, int j) {
        if (m[i][j] != Integer.MAX_VALUE) {
            return m[i][j];
        }

        if (i == j) {
            m[i][j] = 0;
        } else {
            for (int k = i; k < j; k++) {
                int q = memorizedMatrixChain(p, m, i, k) +
                        memorizedMatrixChain(p, m, k + 1, j) +
                        p[i - 1] * p[k] * p[j];
                if (q < m[i][j]) {
                    m[i][j] = q;
                }
            }
        }
        return m[i][j];
    }
}
