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

import org.javarebel.chart.generator.DefaultAxisConfig;
import org.javarebel.chart.generator.DefaultItemLabelConfig;
import org.javarebel.chart.generator.PlotConfig;

public class ChartConfig implements Serializable {

	private static final long serialVersionUID = -3450600961508888952L;

	private Color background;
	private Color foreground;
	private PlotConfig plotConfig;
	private DefaultItemLabelConfig labelConfig;
	private DefaultAxisConfig axisConfig;

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public PlotConfig getPlotConfig() {
		return plotConfig;
	}

	public void setPlotConfig(PlotConfig plotConfig) {
		this.plotConfig = plotConfig;
	}

	public DefaultItemLabelConfig getLabelConfig() {
		return labelConfig;
	}

	public void setLabelConfig(DefaultItemLabelConfig labelConfig) {
		this.labelConfig = labelConfig;
	}

	public DefaultAxisConfig getAxisConfig() {
		return axisConfig;
	}

	public void setAxisConfig(DefaultAxisConfig axisConfig) {
		this.axisConfig = axisConfig;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}
}
