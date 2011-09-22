package com.mine.charts;

import java.io.IOException;
import java.util.logging.Logger;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.mine.chart.processor.ChartProcessor;
import com.mine.chart.processor.ChartProcessorFactory;

@FacesComponent("com.mine.ChartComponent")
public class ChartComponent extends UIComponentBase {
	
	private static final Logger logger = Logger.getLogger(ChartComponent.class.getName());

	private static final String CHART_TYPE_ATTR = "chartType";
	
	private static final String FAMILY = "com.mine.jsf.HtmlFamily";

	@Override
	public String getFamily() {
		return FAMILY;
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		Object chartTypeObj = "area";
		ValueExpression chartTypeExp = this.getValueExpression(CHART_TYPE_ATTR);
		if(chartTypeExp != null) {
			chartTypeObj = chartTypeExp.getValue(context.getELContext());
		} else 
			chartTypeObj = this.getAttributes().get(CHART_TYPE_ATTR);
		logger.info("Chart type received is " + chartTypeObj);
		
		ChartProcessor processor = 
			ChartProcessorFactory.getProcessor(String.valueOf(chartTypeObj));
		processor.process(this, context);
	}
}
