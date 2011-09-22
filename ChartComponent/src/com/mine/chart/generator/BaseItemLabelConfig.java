package com.mine.chart.generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import org.jfree.chart.labels.CategoryItemLabelGenerator;

public class BaseItemLabelConfig {
	
	private static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", Font.PLAIN, 8);
	private static ChartLabelGenerator generator = new ChartLabelGenerator();

	public boolean isVisible() {
		return true;
	}
	
	public CategoryItemLabelGenerator getGenerator() {
		return generator;
	}
	
	public Paint getPaint() {
		return Color.BLACK;
	}
	
	public Font getFont() {
		return DEFAULT_LABEL_FONT;
	}
}
