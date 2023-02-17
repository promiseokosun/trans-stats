package com.seerbit.transstats.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AlgorithmsSolution {

    public static void main(String[] args) {
        int [] arr = {2, 8, 5, -20, 10, 15, 5};
        findPairsWithSumEqualsToX(arr, 10);
        findFirstAndLast(arr, 50);
        int [][] mergeArr = {{1, 6}, {2, 8}, {10, 15}, {16, 20}};

        int[][] merge = merge(mergeArr);
        for(int i =0; i < merge.length; i++) {
            for(int j = 0; j < merge[i].length; j++) {
                System.out.print(merge[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void findPairsWithSumEqualsToX(int arr[], int x) {
        if (arr.length < 2) return;

        System.out.println("The pair whose sum is equal to " + x);
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int tempSum = arr[i] + arr[j];
                if (tempSum == x) System.out.println(arr[i] + " " + arr[j]);
            }
        }
    }

    public static void findFirstAndLast(int arr[], int x)
    {
        int first = -1, last = -1;
        for (int i = 0; i < arr.length; i++) {
            if (x != arr[i]) continue;
            if (first == -1) first = i;
            last = i;
        }
        if (first != -1) System.out.println("First Occurrence = " + first + "\nLast Occurrence = " + last);
        else System.out.println("-1");
    }


    public static int[][] merge(int[][] intervals) {
        int len = intervals.length;
        int i = 0;
        List<int[]> result = new ArrayList<>();
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int first = intervals[i][0];
        int second = intervals[i][1];

        while (i < len) {
            if (intervals[i][0] <= second) {
                second = Math.max(second, intervals[i][1]);
            } else {
                result.add(new int[]{first, second});
                first = intervals[i][0];
                second = intervals[i][1];
            }
            i++;
        }
        result.add(new int[]{first, second});
        return result.toArray(new int[0][]);
    }
}
