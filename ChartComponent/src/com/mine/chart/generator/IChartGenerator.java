package com.mine.chart.generator;

import org.jfree.chart.JFreeChart;

import com.mine.chart.data.ChartData;

public interface IChartGenerator {
	
	public JFreeChart generateChart(ChartData data);
}
