package com.mine.chart.processor;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

public interface ChartProcessor {
	
    String LABEL_ATTR = "label";
    String VALUE_ATR = "value";
    String LEGEND_LABEL_ATTR = "legendLabel";
    String WIDTH_ATTR = "width";
    String HEIGHT_ATTR = "height";
    String SERIES_COLOR_ATTR = "seriesColor";
    String LEGEND_VISIBLE_ATTR = "legendVisible";
	
	String KEY_TEMPLATE = "com.mine.charts.%s.dataKey";
	String CHART_URL_TEMPLATE = "%s/faces/com.mine.Chart?data_KEY=%s";
	
	public void process(UIComponentBase component, FacesContext context) throws IOException;
}
