package com.mine.charts;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.JFreeChart;

import com.mine.chart.data.ChartData;
import com.mine.chart.generator.ChartGeneratorFactory;
import com.mine.chart.generator.IChartGenerator;

public class ChartComponentListener implements PhaseListener {

	private static final long serialVersionUID = -7161035876792824129L;
	
	private static Logger logger = Logger.getLogger(ChartComponentListener.class.getName());

	@Override
	public void afterPhase(PhaseEvent arg0) {

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		FacesContext context = arg0.getFacesContext();
		ExternalContext eCtx = context.getExternalContext();
		
		HttpServletRequest req = (HttpServletRequest) eCtx.getRequest();
		String data_KEY = req.getParameter("data_KEY");
		logger.info("Chart Data Key is " + data_KEY);

		HttpSession session = req.getSession();
		Object chartData = session.getAttribute(data_KEY);
		
		if (chartData != null) {
			
			ChartData data = ChartData.class.cast(chartData);
			logger.info("Chart Type received is " + data.getType());
			IChartGenerator chartGen = ChartGeneratorFactory.getChartGenerator(data.getType());
			logger.info("ChartGenerator in use -> " + chartGen.getClass().getName());
			JFreeChart chart = chartGen.generateChart(data);
			
			BufferedImage img = chart.createBufferedImage(
									Integer.valueOf(data.getWidth()), 
									Integer.valueOf(data.getHeight()));
			try {
				HttpServletResponse response = (HttpServletResponse) eCtx.getResponse();
				response.setContentType("image/png");
				response.setHeader("Cache-Control", "no-store");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0L);
				
				ServletOutputStream output = response.getOutputStream();
				ImageIO.write(img, "png", output);
				
				session.removeAttribute(data_KEY);
			
				context.responseComplete();
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new IllegalStateException(e);
			}
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
