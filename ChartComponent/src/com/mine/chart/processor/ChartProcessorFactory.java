package com.mine.chart.processor;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChartProcessorFactory {

	private static final Logger logger = Logger
			.getLogger(ChartProcessorFactory.class.getName());

	private static final ResourceBundle BUNDLE = ResourceBundle
			.getBundle("META-INF/ChartProcessors");
	private static final Map<String, Object> chartProcessorMap = new Hashtable<String, Object>();

	static {
		chartProcessorMap.put("area", new AreaChartProcessor());
		chartProcessorMap.put("bar", new BarChartProcessor());
		
		try {
			Enumeration<String> keys = BUNDLE.getKeys();
			String key = null;
			while(keys.hasMoreElements()) {
				key = keys.nextElement();
				Class<?> genClass = Class.forName(BUNDLE.getString(key));
				chartProcessorMap.put(key, genClass.newInstance());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	public static ChartProcessor getProcessor(String chartType) {
		Object processorObj = chartProcessorMap.get(chartType);
		return ChartProcessor.class.cast(processorObj);
	}

	public static void registerChartProcessor(String chartName,
			Class<? extends ChartProcessor> processorClass) {
		Object genObj = chartProcessorMap.get(chartName);
		if (genObj != null)
			throw new IllegalStateException("Processor already exists for "
					+ chartName);
		try {
			Object obj = processorClass.newInstance();
			chartProcessorMap.put(chartName, obj);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		}

	}

	public static final void logAvailableGenerators() {
		logger.info("Avaialable Processor List");
		Iterator<String> keyIter = chartProcessorMap.keySet().iterator();
		String key = null;
		while (keyIter.hasNext()) {
			key = keyIter.next();
			logger.info(String.format("Chart Type %s - Processor is %s", key,
					chartProcessorMap.get(key)));
		}
	}
}
