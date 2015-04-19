package com.kamaci.performance.workers;

import com.kamaci.performance.matrix.AtomicGetCompareAndSetMatrix;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Worker for synchronized matrix with {@link AtomicInteger#get} and {@link AtomicInteger#compareAndSet(int, int)}.
 *
 * @author Furkan KAMACI
 * @see AtomicGetCompareAndSetMatrix
 */
public class AtomicGetCompareAndSetMatrixWorker extends AtomicGetCompareAndSetMatrix implements Runnable {
    private AtomicInteger incrementCount = new AtomicInteger(WorkerDefaults.INCREMENT_COUNT);

    /**
     * Worker constructor.
     *
     * @param rows number of rows
     * @param cols number of columns
     */
    public AtomicGetCompareAndSetMatrixWorker(int rows, int cols) {
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
