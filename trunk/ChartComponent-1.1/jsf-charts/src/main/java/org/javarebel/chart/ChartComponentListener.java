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
package org.javarebel.chart;

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

import org.javarebel.chart.data.ChartData;
import org.javarebel.chart.generator.ChartGeneratorFactory;
import org.javarebel.chart.generator.IChartGenerator;
import org.jfree.chart.JFreeChart;


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
		if(data_KEY != null) {
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
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
