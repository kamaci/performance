package com.kamaci.performance;

import com.kamaci.performance.analyze.AlgorithmAnalyzer;
import com.kamaci.performance.analyze.chart.ChartConfig;
import com.kamaci.performance.enums.MatrixType;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runner class for application.
 *
 * @author Furkan KAMACI
 */
public final class ApplicationRunner {

    private static final int DEFAULT_LOOP_COUNT = 20;
    private static int repeatCount = DEFAULT_LOOP_COUNT;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunner.class);

    /**
     * Private constructor to prevent instantiation
     */
    private ApplicationRunner() {
    }

    /**
     * Main class for application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("ms", "matrixSizes", true, "one dimension of square matrix");
        options.addOption("tc", "threadCounts", true, "number of threads");
        options.addOption("ps", "poolSizes", true, "thread pool sizes");
        options.addOption("rc", "repeatCount", true, "repeat count");
        options.addOption("an", "analyze", false, "analyze algorithm");
        options.addOption("fan", "forceAnalyze", true, "algorithm out path");
        options.addOption("ab", "about", false, "get info about application");

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar performance-{version}.jar -ms [MATRIX SIZES] -tc [THREAD COUNTS] -ps [POOL SIZES] -rc [REPEAT COUNT] [-an] -fan [ANALYZE PATH]",
                "-------------------------------------------------------------",
                options,
                "-------------------------------------------------------------");

        // create the parser
        CommandLineParser parser = new BasicParser();

        // parse the command line arguments
        CommandLine commandLine;

        int[] matrixSizeArray;
        int[] threadCountArray;
        int[] poolSizeArray;

        try {
            commandLine = parser.parse(options, args);

            matrixSizeArray = parseArgArray(commandLine, "ms");
            threadCountArray = parseArgArray(commandLine, "tc");
            poolSizeArray = parseArgArray(commandLine, "ps");

            if (commandLine.hasOption("rc")) {
                repeatCount = Integer.parseInt(commandLine.getOptionValue("rc"));
            }

            if (commandLine.hasOption("ab")) {
                LOGGER.info("This application is developed by Furkan KAMACI.");
            }

            if (matrixSizeArray == null) {
                matrixSizeArray = AlgorithmRunner.DEFAULT_MATRIX_SIZES;
            }

            if (threadCountArray == null) {
                threadCountArray = AlgorithmRunner.DEFAULT_THREAD_COUNTS;
            }

            if (poolSizeArray == null) {
                poolSizeArray = AlgorithmRunner.DEFAULT_POOL_SIZES;
            }

            printAlgorithmParameters(matrixSizeArray, threadCountArray, poolSizeArray);

            if (commandLine.hasOption("fan")) {
                String outputPath = commandLine.getOptionValue("fan");

                LOGGER.info("Forcing Analyzer!");
                AlgorithmAnalyzer algorithmAnalyzer = new AlgorithmAnalyzer(new ChartConfig(matrixSizeArray, threadCountArray, poolSizeArray));
                algorithmAnalyzer.analyze(outputPath);
                return;
            }

            AlgorithmRunner algorithmRunner = new AlgorithmRunner(matrixSizeArray, threadCountArray, poolSizeArray);

            MatrixType[] matrixTypes = new MatrixType[MatrixType.values().length];

            for (int i = 0; i < MatrixType.values().length; i++) {
                matrixTypes[i] = MatrixType.values()[i];
            }

            for (int i = 0; i < repeatCount; i++) {
                LOGGER.info("Loop: " + i);
                algorithmRunner.runStatistics(matrixTypes);
                LOGGER.info("---------------------------");
            }

            if (commandLine.hasOption("an")) {
                LOGGER.info("Analyzer is enabled!");
                AlgorithmAnalyzer algorithmAnalyzer = new AlgorithmAnalyzer(new ChartConfig(matrixSizeArray, threadCountArray, poolSizeArray));
                algorithmAnalyzer.analyze("./logs/FurkanKAMACI_performance.output");
            }

        } catch (ParseException e) {
            LOGGER.error("Check how to run application!");
        }
    }

    private static int[] parseArgArray(CommandLine commandLine, String option) throws ParseException {
        int[] inputArray = null;
        if (commandLine.hasOption(option)) {
            String[] inputStrArray = commandLine.getOptionValue(option).split(",");
            inputArray = new int[inputStrArray.length];
            for (int i = 0; i < inputStrArray.length; i++) {
                int arg = Integer.parseInt(inputStrArray[i]);
                if (arg <= 0) {
                    LOGGER.error("Input is not greater than for parameter: " + option);
                    throw new ParseException("Input is not greater than for parameter: " + option);
                }
                inputArray[i] = arg;
            }
        }
        return inputArray;
    }

    private static void printAlgorithmParameters(int[] matrixSizeArray, int[] threadCountArray, int[] poolSizeArray) {
        LOGGER.info("Algorithm parameters:");

        StringBuilder matrixSizesString = new StringBuilder("[ ");
        for (int matrixSize : matrixSizeArray) {
            matrixSizesString.append(matrixSize).append(" ");
        }
        matrixSizesString.append("]");

        StringBuilder threadCountsString = new StringBuilder("[ ");
        for (int threadCount : threadCountArray) {
            threadCountsString.append(threadCount).append(" ");
        }
        threadCountsString.append("]");

        StringBuilder poolSizesString = new StringBuilder("[ ");
        for (int poolSize : poolSizeArray) {
            poolSizesString.append(poolSize).append(" ");
        }
        poolSizesString.append("]");

        LOGGER.info("Matrix Sizes:" + matrixSizesString);
        LOGGER.info("Thread Counts:" + threadCountsString);
        LOGGER.info("Pool Sizes:" + poolSizesString);
    }
}
