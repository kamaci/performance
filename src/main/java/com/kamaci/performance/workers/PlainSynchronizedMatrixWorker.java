package com.kamaci.performance.workers;

import com.kamaci.performance.matrix.SynchronizedMatrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker for synchronized matrix with synchronized block.
 *
 * @author Furkan KAMACI
 * @see SynchronizedMatrix
 */
public class PlainSynchronizedMatrixWorker extends SynchronizedMatrix implements Runnable {
    private AtomicInteger incrementCount = new AtomicInteger(WorkerDefaults.INCREMENT_COUNT);

    /**
     * Worker constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public PlainSynchronizedMatrixWorker(int rows, int cols) {
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
