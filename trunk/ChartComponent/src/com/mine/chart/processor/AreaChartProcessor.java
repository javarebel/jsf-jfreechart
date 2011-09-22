package com.mine.chart.processor;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Logger;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.mine.chart.data.ChartData;
import com.mine.chart.data.ChartDataGenerator;
import com.mine.chart.utils.FacesUtil;

public class AreaChartProcessor implements ChartProcessor {
	
	private static final Logger logger = Logger.getLogger(AreaChartProcessor.class.getName());

	@Override
	public void process(UIComponentBase component, FacesContext context) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		ExternalContext eCtx = context.getExternalContext();
		
		final String compId = component.getClientId();
		String dataKey = String.format(KEY_TEMPLATE, compId);
		HttpSession session = (HttpSession) eCtx.getSession(true);
		
		Object xData = FacesUtil.getComponentAttribute(component, LABEL_ATTR);
		Comparable<?>[] xDataArray = (Comparable<?>[]) xData;
		
		Object valueSet = FacesUtil.getComponentAttribute(component, VALUE_ATR);
		Double[] yDataArray = (Double[]) valueSet;
		
		Object label = FacesUtil.getComponentAttribute(component, LEGEND_LABEL_ATTR);
		
		ChartData data = ChartDataGenerator.generateAreaChartData(xDataArray, yDataArray, label);
		
		ValueExpression sClrExp = component.getValueExpression(SERIES_COLOR_ATTR);
		if(sClrExp != null) {
			Object color = sClrExp.getValue(context.getELContext());
			Color sColor = (Color) color;
			data.setSeriesColor(sColor);
		}
		
		Object isLegendVisible = FacesUtil.getComponentAttribute(component, LEGEND_VISIBLE_ATTR);
		if(isLegendVisible != null)
			data.setLegendVisible(Boolean.valueOf(String.valueOf(isLegendVisible)));
		
		Object width = FacesUtil.getComponentAttribute(component, WIDTH_ATTR);
		if(width != null) 
			data.setWidth(String.valueOf(width));
		
		Object height = FacesUtil.getComponentAttribute(component, HEIGHT_ATTR);
		if(height != null) 
			data.setHeight(String.valueOf(height));
		session.setAttribute(dataKey, data);
		
		String contextName = ((ServletContext)eCtx.getContext()).getContextPath();
		logger.info("Application context is " + contextName);
		
		writer.startElement("img", null);
		String chartURL = String.format(CHART_URL_TEMPLATE,  contextName, dataKey);
		logger.info("Chart URL " + chartURL);
		writer.writeAttribute("src", chartURL, null);
		writer.endElement("img");
	}
}
