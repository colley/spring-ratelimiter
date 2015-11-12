/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimelimitConfigurerProvider.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;

import com.github.ailing.ratetimelimiter.YdtRateLimiter;

/**
 *原始配置信息提供者
 * @FileName  RatimelimitConfigurerProvider.java
 * @Date  15-11-11 下午5:58
 * @author mayuanchao
 * @version 1.0
 */
public interface RateTimelimitConfigurerProvider {
	public RateTimeConfigurer create(String serviceName, AspectRateTimeProvider aspectProvider);

	public void updateConfig(RateTimeConfigurer rateTimeConfigurer);
	
	public YdtRateLimiter getYdtRateLimiter();
}