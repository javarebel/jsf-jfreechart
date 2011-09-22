package com.mine.chart.generator;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChartGeneratorFactory {
	
	private static final Logger logger = 
			Logger.getLogger(ChartGeneratorFactory.class.getName());

	private static final ResourceBundle BUNDLE = 
			ResourceBundle.getBundle("META-INF/ChartGenerators");
	private static final Map<String, Object> chartTypeMap = 
			new Hashtable<String, Object>();
	
	static {
		chartTypeMap.put("area", new AreaChartGenerator());
		chartTypeMap.put("bar", new BarChartGenerator());
		
		try {
			Enumeration<String> keys = BUNDLE.getKeys();
			String key = null;
			while(keys.hasMoreElements()) {
				key = keys.nextElement();
				Class<?> genClass = Class.forName(BUNDLE.getString(key));
				chartTypeMap.put(key, genClass.newInstance());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
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
			throw new IllegalArgumentException(e);
		} catch (InstantiationException e) {
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
