package edu.neu.numtheory;

/**
 * Created by HappyMole on 4/20/17.
 */
public class GCDSolver {
    public static void main(String[] args) {
        GCDSolver gcdSolver = new GCDSolver();
        int[] mn = new int[2];
        int a = 60, b = 32;
        int gcd = gcdSolver.extendedGCD(a, b, mn);
        System.out.println(gcd + "=" + a + "*" + mn[0] + "+" + b + "*" + mn[1]);
    }

    public int gcd(int m, int n) {
        if (m > n) {
            if (m % n == 0) {
                return n;
            } else {
                return gcd(n, m % n);
            }
        } else {
            if (n % m == 0) {
                return m;
            } else {
                return gcd(m, n % m);
            }
        }
    }

    // GCDSolver(a, b) = m * a + n * b
    public int extendedGCD(int a, int b, int[] mn) {
        if (a == 0) {
            mn[0] = 0;
            mn[1] = 1;
            return b;
        }

        int[] tempMN = new int[2];
        int gcd = extendedGCD(b % a, a, tempMN);
        mn[0] = tempMN[1] - (b / a) * tempMN[0];
        mn[1] = tempMN[0];
        return gcd;
    }
}
