/**
 *
 * License Agreement.
 *
 * jsf-charts 1.1 - Simple JSF Chart Component
 *
 * Copyright (C) 2013 Naveen Sisupalan <naveensisupalan@gmail.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * The Software shall be used for Good, not Evil.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.javarebel.chart.processor;

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

import org.javarebel.chart.data.ChartConfig;
import org.javarebel.chart.data.ChartData;
import org.javarebel.chart.data.ChartDataGenerator;
import org.javarebel.chart.utils.FacesUtil;


public abstract class AbstractChartProcessor implements ChartProcessor {
	
	private static final Logger logger = Logger.getLogger(AbstractChartProcessor.class.getName());
	
	protected String chartType;

	@Override
	public void process(UIComponentBase component, FacesContext context)
			throws IOException {
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
		Object xaxisLabel = FacesUtil.getComponentAttribute(component, XAXIS_LABEL_ATTR);
		Object yaxisLabel = FacesUtil.getComponentAttribute(component, YAXIS_LABEL_ATTR);
		Object title = FacesUtil.getComponentAttribute(component, CHART_TITLE_ATTR);
		
		ChartData data = ChartDataGenerator.generateChartData(
						chartType, xDataArray, yDataArray, label,
						xaxisLabel != null ? xaxisLabel.toString() : "", 
						yaxisLabel != null ? yaxisLabel.toString() : "",
						title != null ? title.toString() : "Area Chart");
		
		ValueExpression sClrExp = component.getValueExpression(SERIES_COLOR_ATTR);
		if(sClrExp != null) {
			Object color = sClrExp.getValue(context.getELContext());
			Color sColor = (Color) color;
			data.setSeriesColor(sColor);
		}
		
		Object additionalConfig = FacesUtil.getComponentAttribute(component, CHART_CONFIG_ATTR);
		if(additionalConfig != null) {
			if(additionalConfig instanceof ChartConfig) {
				ChartConfig config = ChartConfig.class.cast(additionalConfig);
				data.setConfig(config);
			}
		}
		
		Object orientation = FacesUtil.getComponentAttribute(component, CHART_ORIENTATION_ATTR);
		if(orientation != null)
			data.setOrientation(orientation.toString());
		
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
