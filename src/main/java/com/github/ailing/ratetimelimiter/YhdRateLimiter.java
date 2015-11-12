/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */

package com.github.ailing.ratetimelimiter;

import java.util.concurrent.TimeUnit;

/**
 *
 * @FileName  YhdRateLimiter.java
 * @Date  15-11-11 下午11:04 
 * @author mayuanchao
 * @version 1.0
 */
public abstract class YhdRateLimiter {
	public abstract void setRate(double permitsPerSecond);

	public abstract double getRate();
	
	public abstract YhdRateLimiter  create(double permitsPerSecond, long warmupPeriod, TimeUnit unit);
	
	public abstract YhdRateLimiter create(double permitsPerSecond);
	
	
	public abstract boolean tryAcquire();

	public abstract boolean tryAcquire( int permits);

	public abstract boolean tryAcquire(long timeout, TimeUnit unit);

	public abstract boolean tryAcquire(int permits, long timeout, TimeUnit unit);

	public abstract void acquire();

	public abstract void acquire(int permits);
}