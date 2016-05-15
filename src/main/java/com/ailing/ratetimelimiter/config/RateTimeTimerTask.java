/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeTimerTask.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.ailing.ratetimelimiter.config;

import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 *定时刷新配置task
 * @FileName  RatimeTimerTask.java
 * @Date  15-11-10 下午3:05
 * @author mayuanchao
 * @version 1.0
 */
public class RateTimeTimerTask extends TimerTask {
	private Logger logger = Logger.getLogger(getClass());
	private RateTimeConfigurer ratimeConfigurer;

	public RateTimeTimerTask(RateTimeConfigurer ratimeConfigurer) {
		this.ratimeConfigurer = ratimeConfigurer;
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();

		if (logger.isDebugEnabled()) {
			logger.debug(ratimeConfigurer.getServiceName() +
				" RatimeTimerTask refresh start ......");
		}

		System.err.println(new Date().toString());
		ratimeConfigurer.refresh();

		long endtime = System.currentTimeMillis();

		if (logger.isDebugEnabled()) {
			logger.debug(ratimeConfigurer.getServiceName() +
				" RatimeTimerTask refresh end,Cost time is " + (endtime - start) + "ms");
		}
	}
}