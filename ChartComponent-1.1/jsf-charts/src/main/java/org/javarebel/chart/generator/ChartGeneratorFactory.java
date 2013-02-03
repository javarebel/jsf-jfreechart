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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javarebel.chart.utils.ChartTypes;

public class ChartGeneratorFactory {
	
	private static final Logger logger = 
			Logger.getLogger(ChartGeneratorFactory.class.getName());

	private static ResourceBundle BUNDLE;
	private static final Map<String, Object> chartTypeMap = 
			new Hashtable<String, Object>();
	
	static {
		chartTypeMap.put(ChartTypes.AREA_CHART.value(), new AreaChartGenerator());
		chartTypeMap.put(ChartTypes.BAR_CHART.value(), new BarChartGenerator());
		
		try {
			BUNDLE = ResourceBundle.getBundle("META-INF/ChartGenerators");
			Enumeration<String> keys = BUNDLE.getKeys();
			String key = null;
			while(keys.hasMoreElements()) {
				key = keys.nextElement();
				Class<?> genClass = Class.forName(BUNDLE.getString(key));
				chartTypeMap.put(key, genClass.newInstance());
			}
		} catch (MissingResourceException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}
	
	public static IChartGenerator getChartGenerator(String chartType) {
		Object generatorObj = chartTypeMap.get(chartType);
		return ChartGenerator.class.cast(generatorObj);
	}
	
	public static void registerChartType(String chartName, 
					Class<? extends IChartGenerator> generatorClass) {
		Object genObj = chartTypeMap.get(chartName);
		if(genObj != null) 
			throw new IllegalStateException("Generator already exists for " + chartName);
		try {
			Object obj = generatorClass.newInstance();
			chartTypeMap.put(chartName, obj);
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalArgumentException(e);
		} catch (InstantiationException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
		
	}
	
	public static final void logAvailableGenerators() {
		logger.info("Avaialable Generator List");
		Iterator<String> keyIter = chartTypeMap.keySet().iterator();
		String key = null;
		while(keyIter.hasNext()) {
			key = keyIter.next();
			logger.info(String.format("Chart Type %s - Generator is %s", key, chartTypeMap.get(key)));
		}
	}
}
