package edu.neu.sorting;

/**
 * Created by HappyMole on 16/8/1.
 */
public class StopWatch {
    private long startTime;

    public StopWatch() {
        this.startTime = System.currentTimeMillis();
    }

    public long ellapseTime() {
        long now = System.currentTimeMillis();
        return now - startTime;
    }
}
