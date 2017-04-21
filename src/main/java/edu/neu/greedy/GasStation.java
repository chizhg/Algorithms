package edu.neu.greedy;

/*
    There are N gas stations along a circular route, where the amount of gas at station i is gas[i].
    You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station i to its next station (i+1). You begin the journey with an empty tank at one of the gas stations.
    Return the starting gas station's index if you can travel around the circuit once, otherwise return -1.

    Note:
    The solution is guaranteed to be unique.
 */
public class GasStation {
    // greedy choice:
    // if we cannot drive from the start point to i (gas in the tank becomes < 0), reset the start point to i + 1
    public int solve(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }

        int gasSum = 0, costSum = 0, start = 0, tank = 0;
        for (int i = 0; i < gas.length; i++) {
            gasSum += gas[i];
            costSum += cost[i];
            tank += gas[i] - cost[i];
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }

        if (gasSum - costSum < 0) {
            return -1;
        } else {
            return start;
        }
    }
}
