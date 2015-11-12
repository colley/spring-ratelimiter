/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: AspectRateTimeProvider.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.config;

/**
 *AspectRateTime 创建者
 * @FileName  AspectRateTimeProvider.java
 * @Date  15-11-11 下午9:55 
 * @author mayuanchao
 * @version 1.0
 */
public interface AspectRateTimeProvider {
	public AspectRateTime create(String serviceName);
}