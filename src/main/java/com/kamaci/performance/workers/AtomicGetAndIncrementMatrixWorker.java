package com.kamaci.performance.workers;

import com.kamaci.performance.matrix.AtomicGetAndIncrementMatrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker for synchronized matrix with {@link AtomicInteger#getAndIncrement()}.
 *
 * @author Furkan KAMACI
 * @see AtomicGetAndIncrementMatrix
 */
public class AtomicGetAndIncrementMatrixWorker extends AtomicGetAndIncrementMatrix implements Runnable {
    private AtomicInteger incrementCount = new AtomicInteger(WorkerDefaults.INCREMENT_COUNT);

    /**
     * Worker constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public AtomicGetAndIncrementMatrixWorker(int rows, int cols) {
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
