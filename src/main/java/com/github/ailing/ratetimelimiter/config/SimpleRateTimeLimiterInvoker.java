/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: SimpleRateTimeLimiterInvoker.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;

import com.github.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;

import org.apache.log4j.Logger;

/**
 *封装的简单处理类，根据不同的业务去实现
 * @FileName  SimpleRatimeLimiterInvoker.java
 * @Date  15-11-6 下午4:03
 * @author mayuanchao
 * @version 1.0
 */
public class SimpleRateTimeLimiterInvoker implements RateTimeLimiterInvoker {
	protected final Logger logger = Logger.getLogger(getClass());

	@Override
	public <T> T invokehandler(Object param) {
		
		return null;
	}

	
}