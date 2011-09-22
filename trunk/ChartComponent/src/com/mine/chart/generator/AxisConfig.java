package com.mine.chart.generator;

import java.awt.Font;

import org.jfree.chart.axis.CategoryLabelPositions;

public class AxisConfig {
	
	private static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", Font.BOLD, 10);
	private static final Font DEFAULT_TICK_LABEL_FONT = new Font("SansSerif", Font.PLAIN, 9);
	
	public CategoryLabelPositions getLabelPosition() {
		return CategoryLabelPositions.UP_90;
	}
	
	public Font getLabelFont() {
		return DEFAULT_LABEL_FONT;
	}
	
	public Font getTickLabelFont() {
		return DEFAULT_TICK_LABEL_FONT;
	}
	
	public double getLowerMargin() {
		return 0.0D;
	}
	
	public double getUpperMargin() {
		return 0.0D;
	}
	
	public double getCategoryMargin() {
		return 0.0D;
	}
}
