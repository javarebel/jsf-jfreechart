package com.mine.chart.data;

import java.awt.Color;
import java.io.Serializable;

import org.jfree.data.category.CategoryDataset;

public abstract class ChartData implements Serializable {

	private static final long serialVersionUID = -6505577643082945097L;
	
	private String width = "500";
	private String height = "300";
	protected Color seriesColor = new Color(100, 20, 255);
	
	public abstract String getType();
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

	public Color getSeriesColor() {
		return seriesColor;
	}

	public void setSeriesColor(Color seriesColor) {
		this.seriesColor = seriesColor;
	}
	public boolean isLegendVisible() {
		return true;
	}
	public void setLegendVisible(boolean legendVisible) {
	}
	
	public abstract CategoryDataset createDataset();
}
