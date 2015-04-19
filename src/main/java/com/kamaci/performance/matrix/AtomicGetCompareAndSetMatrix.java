package com.kamaci.performance.matrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Synchronized matrix with {@link AtomicInteger#get} and {@link AtomicInteger#compareAndSet(int, int)}.
 *
 * @author Furkan KAMACI
 * @see AtomicInteger
 */
public class AtomicGetCompareAndSetMatrix {
    private int rows;
    private int cols;
    private AtomicInteger[][] array;

    /**
     * Matrix constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public AtomicGetCompareAndSetMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        array = new AtomicInteger[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = new AtomicInteger(0);
            }
        }
    }

    /**
     * Increments all matrix cells.
     */
    public void increment() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (; ; ) {
                    int current = array[i][j].get();
                    int next = current + 1;
                    if (array[i][j].compareAndSet(current, next)) {
                        break;
                    }
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
                rowSum += array[i][j].get();
            }
            s.append(rowSum);
            s.append(" ");
        }
        return s.toString();
    }
}