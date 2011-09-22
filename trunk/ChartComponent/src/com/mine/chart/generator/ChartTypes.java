package com.mine.chart.generator;

public enum ChartTypes {
	AREA_CHART("area"), BAR_CHART("bar");
	
	private String value;
	
	private ChartTypes(String type) {
		this.value = type;
	}

	public String getValue() {
		return value;
	}
	
	public static ChartTypes getType(Object identifier) {
		String typeId = String.valueOf(identifier);
		if("area".equals(typeId)) return AREA_CHART;
		if("bar".equals(typeId)) return BAR_CHART;
		return AREA_CHART;
	}
}
