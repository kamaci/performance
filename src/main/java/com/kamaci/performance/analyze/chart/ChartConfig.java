package com.kamaci.performance.analyze.chart;

import com.kamaci.performance.enums.MatrixType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Furkan KAMACI
 */
public class ChartConfig {
    private int[] matrixSizeArray;
    private int[] threadCountArray;
    private int[] poolSizeArray;
    private List<List<String>> keyList;
    private boolean consistent = true;

    /**
     * ChartConfig constructor
     *
     * @param matrixSizeArray matrix size array
     * @param threadCountArray thread count array
     * @param poolSizeArray pool size array
     */
    public ChartConfig(int[] matrixSizeArray, int[] threadCountArray, int[] poolSizeArray) {
        this.matrixSizeArray = matrixSizeArray.clone();
        this.threadCountArray = threadCountArray.clone();
        this.poolSizeArray = poolSizeArray.clone();
        consistent = false;
        generateKeyList();
        consistent = true;
    }

    /**
     * Generates access keys statistics objects
     */
    private void generateKeyList() {
        int poolThread = Math.min(threadCountArray.length, poolSizeArray.length);
        keyList = new LinkedList<>();
        List<String> chartList = new LinkedList<>();
        for (int i = 0; i < poolThread; i++) {
            for (int j = 0; j < MatrixType.values().length; j++) {
                for (int matrixSize : matrixSizeArray) {
                    chartList.add(MatrixType.values()[j] + "-" + matrixSize + "-" + poolSizeArray[i] + "-" + threadCountArray[i]);
                }
                keyList.add(chartList);
                chartList = new ArrayList<>();
            }
        }
    }

    /**
     * Returns key list if it is at consistent state
     *
     * @return key list
     */
    public List<List<String>> getKeyList() {
        if (consistent) {
            return keyList;
        } else {
            return null;
        }
    }

}
