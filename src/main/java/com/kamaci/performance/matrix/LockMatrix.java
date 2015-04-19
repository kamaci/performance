package com.kamaci.performance.matrix;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronized matrix with locks.
 *
 * @author Furkan KAMACI
 * @see ReentrantLock
 */
public class LockMatrix {
    private int rows;
    private int cols;
    private int[][] array;

    private Lock lock;

    /**
     * Matrix constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public LockMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        array = new int[rows][rows];
        lock = new ReentrantLock();
    }

    /**
     * Increments all matrix cells.
     */
    public void increment() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                lock.lock();
                try {
                    array[i][j]++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * Returns a string representation of the object which shows row sums of each row.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        int rowSum;
        for (int i = 0; i < rows; i++) {
            rowSum = 0;
            for (int j = 0; j < cols; j++) {
                rowSum += array[i][j];
            }
            s.append(rowSum);
            s.append(" ");
        }
        return s.toString();
    }
}