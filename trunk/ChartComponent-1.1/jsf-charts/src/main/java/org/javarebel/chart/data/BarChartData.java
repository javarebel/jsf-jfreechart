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
package org.javarebel.chart.data;

import java.util.ResourceBundle;

import org.javarebel.chart.utils.ChartTypes;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;


public class BarChartData extends ChartData {

	private static final long serialVersionUID = 8404603182079729214L;
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("META-INF/" + BarChartData.class.getSimpleName());

	@Override
	public String getType() {
		return ChartTypes.BAR_CHART.value();
	}
	
	private Comparable<?>[] labels;
	private Double[] values;
	private String legend;
	private boolean legendVisible = Boolean.valueOf(BUNDLE.getString("legendVisible"));
	
	public ChartData init(Comparable<?>[] labels, Double[] values,
			String legend, String xaxisLabel, String yaxisLabel, String title) {
		super.init(xaxisLabel, yaxisLabel, title);
		this.labels = labels;
		this.values = values;
		this.legend = legend;
		return this;
	}
	
	public Comparable<?>[] getLabels() {
		return labels;
	}
	public Double[] getValues() {
		return values;
	}
	public String getLegend() {
		return legend;
	}

	public CategoryDataset createDataset() {
		final double[][] data = new double[1][values.length];
		int i= 0;
		for(Double d : values)
			data[0][i++] = d;

		final CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				new String[] { String.valueOf(this.legend) }, this.labels, data);
		return dataset;
	}

	public boolean isLegendVisible() {
		return legendVisible;
	}

	public void setLegendVisible(boolean legendVisible) {
		this.legendVisible = legendVisible;
	}
}
