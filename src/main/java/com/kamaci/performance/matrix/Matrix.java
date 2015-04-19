package com.kamaci.performance.matrix;

/**
 * Plain matrix without synchronization.
 */
public class Matrix {
    private int rows;
    private int cols;
    private int[][] array;

    /**
     * Matrix constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        array = new int[rows][rows];
    }

    /**
     * Increments all matrix cells.
     */
    public void increment() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j]++;
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