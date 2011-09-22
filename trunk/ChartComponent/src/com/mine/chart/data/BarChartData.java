package com.mine.chart.data;

import java.util.ResourceBundle;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import com.mine.chart.generator.ChartTypes;

public class BarChartData extends ChartData {

	private static final long serialVersionUID = 8404603182079729214L;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BarChartData.class.getName());

	@Override
	public String getType() {
		return ChartTypes.BAR_CHART.getValue();
	}
	
	private Comparable<?>[] labels;
	private Double[] values;
	private String legend;
	private boolean legendVisible = Boolean.valueOf(BUNDLE.getString("legendVisible"));
	
	public BarChartData(Comparable<?>[] labels, Double[] values,
			String legend) {
		super();
		this.labels = labels;
		this.values = values;
		this.legend = legend;
	}
	
	public Comparable<?>[] getLabels() {
		return labels;
	}
	public Double[] getValues() {
		return values;
	}
	public String getLegend() {
		return legend;
	}

	public CategoryDataset createDataset() {
		final double[][] data = new double[1][values.length];
		int i= 0;
		for(Double d : values)
			data[0][i++] = d;

		final CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				new String[] { String.valueOf(this.legend) }, this.labels, data);
		return dataset;
	}

	public boolean isLegendVisible() {
		return legendVisible;
	}

	public void setLegendVisible(boolean legendVisible) {
		this.legendVisible = legendVisible;
	}
}
