package com.kamaci.performance;

import com.kamaci.performance.workers.*;
import com.kamaci.performance.enums.MatrixType;
import com.kamaci.performance.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Runner class for algorithm.
 *
 * @author Furkan KAMACI
 */
public class AlgorithmRunner {

    private int[] matrixSizeArray;
    private int[] threadCountArray;
    private int[] poolSizeArray;

    public static final int[] DEFAULT_MATRIX_SIZES = {100, 150, 200, 250, 300, 350, 400};
    public static final int[] DEFAULT_THREAD_COUNTS = {2, 4, 6, 8, 10, 12};
    public static final int[] DEFAULT_POOL_SIZES = {2, 4, 6, 8, 10, 12};

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmRunner.class);
    private static final Marker ALGORITHM_MARKER = MarkerFactory.getMarker("ALGORITHM.OUTPUT");

    /**
     * Algorithm runner constructor
     *
     * @param matrixSizeArray  Matrix dimensions array. Only one dimension should be given per matrix. Square matrices will be generated according to that dimension.
     * @param threadCountArray Number of threads array.
     * @param poolSizeArray    Number of thread pool sizes array.
     */
    public AlgorithmRunner(int[] matrixSizeArray, int[] threadCountArray, int[] poolSizeArray) {
        this.matrixSizeArray = matrixSizeArray.clone();
        this.threadCountArray = threadCountArray.clone();
        this.poolSizeArray = poolSizeArray.clone();
    }

    /**
     * Algorithm runner constructor
     *
     * @param matrixSizeArray Matrix dimensions array. Only one dimension should be given per matrix. Square matrices will be generated according to that dimension.
     */
    public AlgorithmRunner(int[] matrixSizeArray) {
        this(matrixSizeArray, DEFAULT_THREAD_COUNTS, DEFAULT_POOL_SIZES);
    }

    /**
     * Algorithm runner constructor
     *
     * @param threadCountArray Number of threads array.
     * @param poolSizeArray    Number of thread pool sizes array.
     */
    public AlgorithmRunner(int[] threadCountArray, int[] poolSizeArray) {
        this(DEFAULT_MATRIX_SIZES, threadCountArray, poolSizeArray);
    }

    /**
     * Algorithm runner constructor with default values.
     */
    public AlgorithmRunner() {
        this(DEFAULT_MATRIX_SIZES, DEFAULT_THREAD_COUNTS, DEFAULT_POOL_SIZES);
    }

    /**
     * Runs tests and calculates algorithm statistics.
     */
    public void runStatistics(MatrixType[] matrixTypes) {
        for (MatrixType matrixType : matrixTypes) {
            LOGGER.info("---------------------------");
            LOGGER.info("Matrix Type: " + matrixType);
            caseRunner(matrixType);
            LOGGER.info("---------------------------");
        }
    }

    /**
     * Runs test case for given {@link MatrixType}
     *
     * @param matrixType matrix type to run case for.
     */
    public void caseRunner(MatrixType matrixType) {
        for (int matrixSize : matrixSizeArray) {
            LOGGER.info("Matrix Size: " + matrixSize);
            for (int poolSize : poolSizeArray) {
                LOGGER.info("Pool Size: " + poolSize);
                ExecutorService executor;
                Runnable worker;
                for (int threadCount : threadCountArray) {
                    LOGGER.info("Thread Count: " + threadCount);
                    executor = Executors.newFixedThreadPool(poolSize);
                    worker = getRunnableInstance(matrixSize, matrixSize, matrixType);

                    long startTime = System.nanoTime();
                    for (int i = 0; i < threadCount; i++) {
                        executor.execute(worker);
                    }
                    executor.shutdown();
                    while (!executor.isTerminated()) {
                        //wait until executor is shutdown
                    }
                    long finishTime = System.nanoTime();

                    //nanoseconds used to track duration and it is converted to millis
                    long durationMillis = TimeUnit.NANOSECONDS.toMillis(finishTime - startTime);

                    LOGGER.info(matrixType + ": " + worker.toString());
                    LOGGER.info(ALGORITHM_MARKER, matrixType + ApplicationUtils.OUTPUT_DELIMITER + matrixSize + ApplicationUtils.OUTPUT_DELIMITER
                            + poolSize + ApplicationUtils.OUTPUT_DELIMITER + threadCount + ApplicationUtils.OUTPUT_DELIMITER + durationMillis);
                }
            }
            LOGGER.info("***************************");
        }
    }

    /**
     * Returns runnable instance for given matrix type with defined row and columns counts.
     *
     * @param rows       number of rows
     * @param cols       number of columns
     * @param matrixType matrix type
     * @return runnable instance
     * @see MatrixType
     */
    public Runnable getRunnableInstance(int rows, int cols, MatrixType matrixType) {
        Runnable runnable;
        switch (matrixType) {
            case PLAIN_SYNCHRONIZED:
                runnable = new PlainSynchronizedMatrixWorker(rows, cols);
                break;
            case LOCK_SYNCHRONIZED:
                runnable = new LockMatrixWorker(rows, cols);
                break;
            case ATOMIC_GET_AND_INCREMENT:
                runnable = new AtomicGetAndIncrementMatrixWorker(rows, cols);
                break;
            case ATOMIC_GET_COMPARE_AND_SET:
                runnable = new AtomicGetCompareAndSetMatrixWorker(rows, cols);
                break;
            case PLAIN:
            default:
                runnable = new PlainMatrixWorker(rows, cols);
        }
        return runnable;
    }
}
