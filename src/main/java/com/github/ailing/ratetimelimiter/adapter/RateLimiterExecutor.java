/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateLimiterExecutor.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.adapter;

import com.github.ailing.ratetimelimiter.config.RateLimitState;

import java.util.concurrent.TimeUnit;

/**
 *限流措施实现者接口
 * @FileName  RateLimiterExecutor.java
 * @Date  15-11-6 下午2:54
 * @author mayuanchao
 * @version 1.0
 */
public interface RateLimiterExecutor {
	public boolean isLimitOpen(String serviceName);

	public RateLimitState tryAcquire(String serviceName);

	public RateLimitState tryAcquire(String serviceName, int permits);

	public RateLimitState tryAcquire(String serviceName, long timeout, TimeUnit unit);

	public RateLimitState tryAcquire(String serviceName, int permits, long timeout, TimeUnit unit);

	public void acquire(String serviceName);

	public void acquire(String serviceName, int permits);
}