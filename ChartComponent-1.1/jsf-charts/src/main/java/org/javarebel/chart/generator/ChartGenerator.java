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
package org.javarebel.chart.generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import org.javarebel.chart.data.ChartConfig;
import org.javarebel.chart.data.ChartData;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

public abstract class ChartGenerator implements IChartGenerator {
	
	protected void configureChart(JFreeChart chart, ChartData data) {
		ChartConfig config = data.getConfig();
		chart.setBackgroundPaint((config == null || config.getBackground() == null) ? 
							Color.WHITE : config.getBackground());
		
		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		PlotConfig plotConfig = new DefaultPlotConfig();
		if(config != null && config.getPlotConfig() != null)
			plotConfig = config.getPlotConfig();
		configurePlot(plot, plotConfig);

		final CategoryItemRenderer renderer = plot.getRenderer();
		ItemLabelConfig labelConfig = new DefaultItemLabelConfig();
		if(config != null && config.getLabelConfig() != null)
			labelConfig = config.getLabelConfig();
		configureBaseItemLabel(renderer, labelConfig);
		
		renderer.setBaseSeriesVisibleInLegend(data.isLegendVisible());
		if(config != null && config.getForeground() != null)
			renderer.setSeriesPaint(0, config.getForeground());
		else
			renderer.setSeriesPaint(0, data.getSeriesColor());

		DefaultAxisConfig axisConfig = new DefaultAxisConfig();
		if(config != null && config.getAxisConfig() != null)
			axisConfig = config.getAxisConfig();
		configureAxis(plot.getDomainAxis(), axisConfig);

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	}
	
	protected void configureBaseItemLabel(CategoryItemRenderer renderer, 
											ItemLabelConfig config) {
		renderer.setBaseItemLabelGenerator(config.getGenerator());
		renderer.setBaseItemLabelsVisible(config.isVisible());
		renderer.setBaseItemLabelPaint(config.getLabelPaint());
		renderer.setBaseItemLabelFont(config.getLabelFont());
		
		Font[] seriesFonts = config.getSeriesFont();
		for(int i=0;i<seriesFonts.length;++i)
			renderer.setSeriesItemLabelFont(i, seriesFonts[i]);
		
		Paint[] seriesPaints = config.getSeriesPaint();
		for(int i=0;i<seriesPaints.length;++i)
			renderer.setSeriesItemLabelPaint(i, seriesPaints[i]);
	}
	
	protected void configureAxis(CategoryAxis axis, DefaultAxisConfig config) {
		axis.setCategoryLabelPositions(config.getLabelPosition());
		axis.setLabelFont(config.getLabelFont());
		axis.setTickLabelFont(config.getTickLabelFont());
		axis.setLowerMargin(config.getLowerMargin());
		axis.setUpperMargin(config.getUpperMargin());
		axis.setCategoryMargin(config.getCategoryMargin());
	}
	
	protected void configurePlot(CategoryPlot plot, PlotConfig config) {
		plot.setForegroundAlpha(config.getForegroundAlpha());
		plot.setBackgroundPaint(config.getBackgroundPaint());
		plot.setDomainGridlinePaint(config.getDomainGridlinePaint());
		plot.setDomainGridlinesVisible(config.isDomainGridlinesVisible());
		plot.setRangeGridlinePaint(config.getRangeGridlinePaint());
		plot.setNoDataMessage(config.getNoDataMessage());
		plot.setNoDataMessageFont(config.getNoDataMessageFont());
		plot.setNoDataMessagePaint(config.getNoDataMessagePaint());
		ValueAxis axis = plot.getRangeAxis();
		axis.setRange(0, axis.getUpperBound() + 1.0D);
	}

	@Override
	public abstract JFreeChart generateChart(ChartData data);
}

