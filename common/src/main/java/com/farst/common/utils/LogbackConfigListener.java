/**
 * Project: sp-common
 * 
 * File Created at 2018-10-11
 * $Id$
 * 
 * Copyright 2018 uup.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * uup Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with uup.com.
 */
package com.farst.common.utils;

import java.io.InputStream;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * @author MichaelWoo
 */
public class LogbackConfigListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(LogbackConfigListener.class);

	private static final String CONFIG_LOCATION = "logbackConfigLocation";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String logbackConfigLocation = event.getServletContext().getInitParameter(CONFIG_LOCATION);
		InputStream inputStream = event.getServletContext().getResourceAsStream(logbackConfigLocation);

		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			loggerContext.reset();
			JoranConfigurator joranConfigurator = new JoranConfigurator();
			joranConfigurator.setContext(loggerContext);
			joranConfigurator.doConfigure(inputStream);
			logger.debug("loaded slf4j configure file from {}", logbackConfigLocation);
		} catch (JoranException e) {
			logger.error("can loading slf4j configure file from " + logbackConfigLocation, e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}