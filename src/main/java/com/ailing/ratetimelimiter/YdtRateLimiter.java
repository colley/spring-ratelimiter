/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: YdtRateLimiter.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.ailing.ratetimelimiter;

import java.util.concurrent.TimeUnit;

/**
 *限流控制器，可自己实现，实例 中使用了RateLimiter
 * @FileName  YhdRateLimiter.java
 * @Date  15-11-11 下午11:04 
 * @author mayuanchao
 * @version 1.0
 */
public abstract class YdtRateLimiter {
	public abstract void setRate(double permitsPerSecond);

	public abstract double getRate();
	
	public abstract YdtRateLimiter  create(double permitsPerSecond, long warmupPeriod, TimeUnit unit);
	
	public abstract YdtRateLimiter create(double permitsPerSecond);
	
	
	public abstract boolean tryAcquire();

	public abstract boolean tryAcquire( int permits);

	public abstract boolean tryAcquire(long timeout, TimeUnit unit);

	public abstract boolean tryAcquire(int permits, long timeout, TimeUnit unit);

	public abstract void acquire();

	public abstract void acquire(int permits);
}