package com.mine.chart.generator;

import java.awt.Color;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

import com.mine.chart.data.ChartData;

public abstract class ChartGenerator implements IChartGenerator {
	
	protected void configureChart(JFreeChart chart, ChartData data) {
		chart.setBackgroundPaint(Color.WHITE);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		configurePlot(plot, new PlotConfig());

		final CategoryItemRenderer renderer = plot.getRenderer();
		configureBaseItemLabel(renderer, new BaseItemLabelConfig() {
			public Paint getPaint() {
				return Color.BLACK;
			}
		});
		renderer.setBaseSeriesVisibleInLegend(data.isLegendVisible());
		renderer.setSeriesPaint(0, data.getSeriesColor());

		configureAxis(plot.getDomainAxis(), new AxisConfig());

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	}
	
	protected void configureBaseItemLabel(CategoryItemRenderer renderer, 
										  BaseItemLabelConfig config) {
		renderer.setBaseItemLabelGenerator(config.getGenerator());
		renderer.setBaseItemLabelsVisible(config.isVisible());
		renderer.setBaseItemLabelPaint(config.getPaint());
		renderer.setBaseItemLabelFont(config.getFont());
	}
	
	protected void configureAxis(CategoryAxis axis, AxisConfig config) {
		axis.setCategoryLabelPositions(config.getLabelPosition());
		axis.setLabelFont(config.getLabelFont());
		axis.setTickLabelFont(config.getTickLabelFont());
		axis.setLowerMargin(config.getLowerMargin());
		axis.setUpperMargin(config.getUpperMargin());
		axis.setCategoryMargin(config.getCategoryMargin());
	}
	
	protected void configurePlot(CategoryPlot plot, PlotConfig config) {
		plot.setForegroundAlpha(config.getForegroundAlpha());
		plot.setBackgroundPaint(config.getBackgroundPaint());
		plot.setDomainGridlinePaint(config.getDomainGridlinePaint());
		plot.setDomainGridlinesVisible(config.isDomainGridlinesVisible());
		plot.setRangeGridlinePaint(config.getRangeGridlinePaint());
		ValueAxis axis = plot.getRangeAxis();
		axis.setRange(0, axis.getUpperBound() + 1.0D);
	}

	@Override
	public abstract JFreeChart generateChart(ChartData data);
}

class ChartLabelGenerator implements CategoryItemLabelGenerator {

	private static final NumberFormat numFrmt = new DecimalFormat("###0.00");
	
	@Override
	public String generateColumnLabel(CategoryDataset arg0, int arg1) {
		return null;
	}

	@Override
	public String generateLabel(CategoryDataset arg0, int arg1, int arg2) {
		Number val = arg0.getValue(arg1, arg2);
		return (val != null && val.doubleValue() != 0.0D) ? numFrmt.format(val) : "";
	}

	@Override
	public String generateRowLabel(CategoryDataset arg0, int arg1) {
		return null;
	}
}
