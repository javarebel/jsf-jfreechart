package com.mine.chart.generator;

import java.awt.Color;
import java.awt.Paint;

public class PlotConfig {

	public float getForegroundAlpha() {
		return 0.7F;
	}
	
	public Paint getBackgroundPaint() {
		return Color.WHITE;
	}
	
	public Paint getDomainGridlinePaint() {
		return Color.LIGHT_GRAY;
	}
	
	public boolean isDomainGridlinesVisible() {
		return true;
	}
	
	public Paint getRangeGridlinePaint() {
		return Color.LIGHT_GRAY;
	}
}
