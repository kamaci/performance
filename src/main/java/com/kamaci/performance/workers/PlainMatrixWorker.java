package com.kamaci.performance.workers;

import com.kamaci.performance.matrix.Matrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker for plain matrix without synchronization.
 *
 * @author Furkan KAMACI
 * @see Matrix
 */
public class PlainMatrixWorker extends Matrix implements Runnable {
    private AtomicInteger incrementCount = new AtomicInteger(WorkerDefaults.INCREMENT_COUNT);

    /**
     * Worker constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public PlainMatrixWorker(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Increments matrix up to a maximum number.
     *
     * @see WorkerDefaults
     */
    @Override
    public void run() {
        while (incrementCount.getAndDecrement() > 0) {
            increment();
        }
    }
}
