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
package org.javarebel.chart;

import java.io.IOException;
import java.util.logging.Logger;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.javarebel.chart.processor.ChartProcessor;
import org.javarebel.chart.processor.ChartProcessorFactory;


@FacesComponent("org.javarebel.ChartComponent")
public class ChartComponent extends UIComponentBase {
	
	private static final Logger logger = Logger.getLogger(ChartComponent.class.getName());

	private static final String CHART_TYPE_ATTR = "chartType";
	
	private static final String FAMILY = "org.javarebel.jsf.HtmlFamily";

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
