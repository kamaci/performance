package com.kamaci.performance.analyze;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Furkan KAMACI
 */
public class Statistic {

    private String matrixType;
    private int matrixSize;
    private int poolSize;
    private int threadCount;
    private List<Long> elapsedTimeList = new ArrayList<>();
    private double averageElapsedTime;
    private double elapsedTimeStandardError;

    /**
     * Statistics constructor
     *
     * @param matrixType matrix type
     * @param matrixSize matrix size
     * @param threadCount thread count
     * @param poolSize pool size
     */
    public Statistic(String matrixType, int matrixSize, int threadCount, int poolSize) {
        this.matrixType = matrixType;
        this.matrixSize = matrixSize;
        this.threadCount = threadCount;
        this.poolSize = poolSize;
    }

    /**
     * generates access key
     *
     * @return access key
     */
    public String getKey() {
        return matrixType + "-" + matrixSize + "-" + threadCount + "-" + poolSize;
    }

    /**
     * tracks elapsed time
     *
     * @param elapsedTime elapsed time
     */
    public void addElapsedTime(long elapsedTime) {
        this.elapsedTimeList.add(elapsedTime);
    }

    /**
     * calculates average elapsed time and standard error of it
     */
    public void analyzeElapsedTime() {
        calculateAverageElapsedTime();
        calculateElapsedTimeStandardError();
    }

    /**
     * calculates average elapsed time
     */
    protected void calculateAverageElapsedTime() {
        long totalElapsedTime = 0;
        for (Long elapsedTime : elapsedTimeList) {
            totalElapsedTime += elapsedTime;
        }
        this.averageElapsedTime = ((double) totalElapsedTime / elapsedTimeList.size());
    }

    /**
     * calculates standard error of elapsed time
     */
    protected void calculateElapsedTimeStandardError() {
        long meanDistanceSquare = 0;
        for (Long elapsedTime : elapsedTimeList) {
            meanDistanceSquare += Math.pow(averageElapsedTime - elapsedTime, 2);
        }
        double standardDeviation = Math.sqrt(meanDistanceSquare / (elapsedTimeList.size() - 1));
        this.elapsedTimeStandardError = standardDeviation / Math.sqrt(elapsedTimeList.size());
    }

    public String getMatrixType() {
        return matrixType;
    }

    public void setMatrixType(String matrixType) {
        this.matrixType = matrixType;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public List<Long> getElapsedTimeList() {
        return elapsedTimeList;
    }

    public void setElapsedTimeList(List<Long> elapsedTimeList) {
        this.elapsedTimeList = elapsedTimeList;
    }

    public double getAverageElapsedTime() {
        return averageElapsedTime;
    }

    public void setAverageElapsedTime(double averageElapsedTime) {
        this.averageElapsedTime = averageElapsedTime;
    }

    public double getElapsedTimeStandardError() {
        return elapsedTimeStandardError;
    }

    public void setElapsedTimeStandardError(double elapsedTimeStandardError) {
        this.elapsedTimeStandardError = elapsedTimeStandardError;
    }
}
