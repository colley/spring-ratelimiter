/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeLimiterInvoker.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.adapter;

/**
 *超时或则限流处理者
 * @FileName  RatimeLimiterInvoker.java
 * @Date  15-11-11 下午5:58 
 * @author mayuanchao
 * @version 1.0
 */
public interface RateTimeLimiterInvoker {
	<T> T invokehandler(Object param);
}