package com.kamaci.performance.workers;

import com.kamaci.performance.matrix.LockMatrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker for synchronized matrix with locks.
 *
 * @author Furkan KAMACI
 * @see LockMatrix
 */
public class LockMatrixWorker extends LockMatrix implements Runnable {
    private AtomicInteger incrementCount = new AtomicInteger(WorkerDefaults.INCREMENT_COUNT);

    /**
     * Worker constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public LockMatrixWorker(int rows, int cols) {
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
