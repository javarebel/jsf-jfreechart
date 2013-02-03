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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javarebel.chart.utils.ChartException;
import org.javarebel.chart.utils.ChartTypes;


public class ChartDataGenerator {
	
	private static final Logger logger = Logger.getLogger(ChartDataGenerator.class.getName());
	
	private static final Hashtable<String, Class<?  extends ChartData>> generatorMap =
			new Hashtable<String, Class<? extends ChartData>>();
	
	private static ResourceBundle BUNDLE;
	
	static {
		generatorMap.put(ChartTypes.AREA_CHART.value(), AreaChartData.class);
		generatorMap.put(ChartTypes.BAR_CHART.value(), BarChartData.class);
		
		try {
			BUNDLE = ResourceBundle.getBundle("META-INF/ChartDataGenerators");
			
			Enumeration<String> keys = BUNDLE.getKeys();
			String key = null;
			while(keys.hasMoreElements()) {
				key = keys.nextElement();
				Class<?> genClass = Class.forName(BUNDLE.getString(key));
				if(ChartData.class.isAssignableFrom(genClass)) {
					generatorMap.put(key, genClass.asSubclass(ChartData.class));
				}
			}
		} catch (MissingResourceException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}
	
	public static ChartData generateChartData(String chartType, Comparable<?>[] labels, 
			Double[] values, Object legend, String xaxisLabel, String yaxisLabel, String title) {
		Class<? extends ChartData> chartDataCls = generatorMap.get(chartType);
		logger.info(String.format("Chart Data Class is for chart type %s is %s ", 
				chartType, chartDataCls));
		
		if(chartDataCls != null) {
			try {
				ChartData chartData = chartDataCls.newInstance();
				return chartData.init(labels, values, String.valueOf(legend), xaxisLabel, yaxisLabel, title);
			} catch (IllegalAccessException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new ChartException(e.getMessage(), e);
			} catch (InstantiationException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new ChartException(e.getMessage(), e);
			}
		}
		return null;
	}
}
