package com.mine.charts;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.mine.chart.processor.BarChartProcessor;

@FacesComponent("com.mine.BarChart")
public class BarChartComponent extends UIComponentBase {
	
	private static final String FAMILY = "com.mine.jsf.HtmlFamily";

	@Override
	public String getFamily() {
		return FAMILY;
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		new BarChartProcessor().process(this, context);
	}
}
