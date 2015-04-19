package com.kamaci.performance.analyze.chart;

import com.kamaci.performance.analyze.Statistic;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.SwingWrapper;
import com.kamaci.performance.enums.MatrixType;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author Furkan KAMACI
 */
public class DrawStatisticAnalyzer {

    private Map<String, Statistic> statisticMap;
    private ChartConfig chartConfig;

    /**
     * DrawStatisticAnalyzer constructor
     *
     * @param statisticMap statistic map
     * @param chartConfig chart config
     */
    public DrawStatisticAnalyzer(Map<String, Statistic> statisticMap, ChartConfig chartConfig) {
        this.statisticMap = statisticMap;
        this.chartConfig = chartConfig;
    }

    /**
     * Draws analyzes
     */
    public void drawAnalyze() {
        int currentChartKeyIndex = 0;
        while (currentChartKeyIndex < chartConfig.getKeyList().size()) {
            Chart analyzeChart = new Chart(900, 700);
            analyzeChart.getStyleManager().setErrorBarsColor(Color.black);
            analyzeChart.setChartTitle("Pool Size: " + chartConfig.getKeyList().get(currentChartKeyIndex).get(0).split("-")[2] +
                    " Thread Count: " + chartConfig.getKeyList().get(currentChartKeyIndex).get(0).split("-")[3]);
            analyzeChart.setXAxisTitle("Matrix Size (n*n)");
            analyzeChart.setYAxisTitle("Elapsed Time (ms)");

            for (int i = 0; i < MatrixType.values().length; i++) {
                List<String> chartKeys = chartConfig.getKeyList().get(currentChartKeyIndex);

                double[] xData = new double[chartKeys.size()];
                double[] yData = new double[chartKeys.size()];
                double[] errData = new double[chartKeys.size()];

                for (int j = 0; j < yData.length; j++) {
                    xData[j] = Double.parseDouble(chartKeys.get(j).split("-")[1]);
                    yData[j] = statisticMap.get(chartKeys.get(j)).getAverageElapsedTime();
                    errData[j] = statisticMap.get(chartKeys.get(j)).getElapsedTimeStandardError();
                }
                analyzeChart.addSeries(chartKeys.get(0).split("-")[0], xData, yData, errData);
                currentChartKeyIndex++;
            }
            new SwingWrapper(analyzeChart).displayChart("Furkan KAMACI");
        }

    }
}