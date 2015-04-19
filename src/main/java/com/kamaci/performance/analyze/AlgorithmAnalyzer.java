package com.kamaci.performance.analyze;

import com.kamaci.performance.analyze.chart.ChartConfig;
import com.kamaci.performance.analyze.chart.DrawStatisticAnalyzer;
import com.kamaci.performance.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Furkan KAMACI
 */
public class AlgorithmAnalyzer {

    private Map<String, Statistic> statisticMap = new HashMap<>();
    private ChartConfig chartConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmAnalyzer.class);

    /**
     * AlgorithmAnalyzer constructor
     *
     * @param chartConfig chart config
     */
    public AlgorithmAnalyzer(ChartConfig chartConfig) {
        this.chartConfig = chartConfig;
    }

    /**
     * Analyzes statistics and draws chart for output file at given path
     *
     * @param outputPath File to analyze
     */
    public void analyze(String outputPath) {
        analyzeStatistics(outputPath);
        drawChart();
    }

    /**
     * Analyzes statistics for given algorithm output file
     *
     * @param outputPath File to analyze
     */
    public void analyzeStatistics(String outputPath) {
        LOGGER.info("Algorithm output file for analyze: " + outputPath);
        try (BufferedReader bufferedReader = Files.newBufferedReader(new File(outputPath).toPath(), Charset.defaultCharset())) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] currentStatistic = line.split(ApplicationUtils.OUTPUT_DELIMITER);

                Statistic statistic = new Statistic(currentStatistic[0], Integer.parseInt(currentStatistic[1]),
                        Integer.parseInt(currentStatistic[2]), Integer.parseInt(currentStatistic[3]));

                if (statisticMap.containsKey(statistic.getKey())) {
                    statisticMap.get(statistic.getKey()).addElapsedTime(Long.parseLong(currentStatistic[4]));
                } else {
                    statistic.addElapsedTime(Long.parseLong(currentStatistic[4]));
                    statisticMap.put(statistic.getKey(), statistic);
                }
            }
            analyzeElapsedTime(statisticMap);
        } catch (IOException ioex) {
            LOGGER.error(ioex.getMessage());
        }
    }

    /**
     * Analyzes elapsed time
     *
     * @param statisticMap statistic map with elapsed times are analyzed
     */
    protected void analyzeElapsedTime(Map<String, Statistic> statisticMap) {
        for (String key : statisticMap.keySet()) {
            Statistic statistic = statisticMap.get(key);
            statistic.analyzeElapsedTime();
            statisticMap.put(key, statistic);
        }
    }

    /**
     * Draws chart for statistics
     */
    public void drawChart() {
        DrawStatisticAnalyzer drawStatisticAnalyzer = new DrawStatisticAnalyzer(statisticMap, chartConfig);
        drawStatisticAnalyzer.drawAnalyze();
    }
}
