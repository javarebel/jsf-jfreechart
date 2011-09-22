package com.mine.charts;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import com.mine.chart.processor.AreaChartProcessor;

@FacesComponent("com.mine.AreaChart")
public class AreaChartComponent extends UIComponentBase {

	private static final String FAMILY = "com.mine.jsf.HtmlFamily";

	@Override
	public String getFamily() {
		return FAMILY;
	}
	
	public void encodeEnd(FacesContext context) throws IOException {
		new AreaChartProcessor().process(this, context);
	}
}
