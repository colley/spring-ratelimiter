/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: SimpleRatimelimitConfigurerProvider.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.config;

import org.springframework.stereotype.Component;

import com.github.ailing.ratetimelimiter.YdtRateLimiter;

/**
 *简单的原始信息提供类
 * @FileName  SimpleAbstractRatimelimitConfigurerProvider.java
 * @Date  15-11-11 下午11:21 
 * @author mayuanchao
 * @version 1.0
 */
@Component
public class SimpleRatimelimitConfigurerProvider extends AbstractRatimelimitConfigurerProvider {
	@Override
	public void updateConfig(RateTimeConfigurer rateTimeConfigurer) {
	}

	@Override
	public YdtRateLimiter getYdtRateLimiter() {
		return null;
	}
}