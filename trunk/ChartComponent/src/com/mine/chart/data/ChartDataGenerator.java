package com.mine.chart.data;


public class ChartDataGenerator {

	public static ChartData generateAreaChartData(Comparable<?>[] labels, 
			Double[] values, Object legend) {
		return new AreaChartData(labels, values, String.valueOf(legend));
	}
	
	public static ChartData generateBarChartData(Comparable<?>[] labels, 
			Double[] values, Object legend) {
		return new BarChartData(labels, values, String.valueOf(legend));
	}
}
