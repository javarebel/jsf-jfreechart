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

import java.awt.Color;
import java.io.Serializable;

import org.jfree.data.category.CategoryDataset;


public abstract class ChartData implements Serializable {

	private static final long serialVersionUID = -6505577643082945097L;
	
	private String width = "500";
	private String height = "300";
	private String xaxisLabel;
	private String yaxisLabel;
	private String title;
	private ChartConfig config;
	private String orientation = "VERTICAL";
	private Color seriesColor = new Color(100, 20, 255);
	
	public ChartData() { }
	
	protected void init(String xaxisLabel, String yaxisLabel, String title) {
		this.xaxisLabel  = xaxisLabel;
		this.yaxisLabel = yaxisLabel;
		this.title = title;
	}
	
	public ChartData init(Comparable<?>[] labels, Double[] values,
			String legend, String xaxisLabel, String yaxisLabel, String title) {
		this.init(xaxisLabel, yaxisLabel, title);
		return this;
	}
	
	public abstract String getType();
	
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}

	public Color getSeriesColor() {
		return seriesColor;
	}

	public void setSeriesColor(Color seriesColor) {
		this.seriesColor = seriesColor;
	}
	
	public abstract boolean isLegendVisible();
	
	public abstract void setLegendVisible(boolean legendVisible);
	
	public String getXaxisLabel() {
		return xaxisLabel;
	}

	public void setXaxisLabel(String xaxisLabel) {
		this.xaxisLabel = xaxisLabel;
	}

	public String getYaxisLabel() {
		return yaxisLabel;
	}

	public void setYaxisLabel(String yaxisLabel) {
		this.yaxisLabel = yaxisLabel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract CategoryDataset createDataset();

	public ChartConfig getConfig() {
		return config;
	}

	public void setConfig(ChartConfig config) {
		this.config = config;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
}
