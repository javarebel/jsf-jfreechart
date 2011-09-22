package com.mine.chart.generator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import com.mine.chart.data.AreaChartData;
import com.mine.chart.data.ChartData;

public class AreaChartGenerator extends ChartGenerator {

	@Override
	public JFreeChart generateChart(ChartData data) {
		if(data instanceof AreaChartData) {
			CategoryDataset dataset = data.createDataset();
			final JFreeChart chart = ChartFactory.createAreaChart(
											"Area Chart", "Months", "Value",
											dataset, PlotOrientation.VERTICAL,
											true, true, false);
			super.configureChart(chart, data);
			return chart;
		} else
			throw new IllegalArgumentException();
	}

}
