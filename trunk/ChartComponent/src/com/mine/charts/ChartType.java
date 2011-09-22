package com.mine.charts;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.mine.chart.generator.ChartTypes;

@ManagedBean(name="ChartType")
@ApplicationScoped
public class ChartType implements Serializable {

	private static final long serialVersionUID = 1058423512917560263L;

	public String getAREA_CHART() {
		return ChartTypes.AREA_CHART.getValue();
	}
	
	public String getBAR_CHART() {
		return ChartTypes.BAR_CHART.getValue();
	}
}
