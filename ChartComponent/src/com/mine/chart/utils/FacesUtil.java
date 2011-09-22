package com.mine.chart.utils;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

public class FacesUtil {
	
	public static Object getComponentAttribute(UIComponentBase component, String attribute) {
		FacesContext context = FacesContext.getCurrentInstance();
		ValueExpression attribExp = component.getValueExpression(attribute);
		Object attribVal = null;
		if(attribExp != null) 
			attribVal = attribExp.getValue(context.getELContext());
		else
			attribVal = component.getAttributes().get(attribute);
		return attribVal;
	}
}
