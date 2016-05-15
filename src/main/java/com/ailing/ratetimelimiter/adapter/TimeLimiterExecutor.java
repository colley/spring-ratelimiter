/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: TimeLimiterExecutor.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.ailing.ratetimelimiter.adapter;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 *超时机制执行者
 * @FileName  TimeLimiterExecutor.java
 * @Date  15-11-11 上午11:35
 * @author mayuanchao
 * @version 1.0
 */
public interface TimeLimiterExecutor {
	public <T> T invokeByLimitTime(String serviceName, RateTimeServiceCallBack<T> callBack);

	public boolean isLimitOpen(String serviceName);

	public <T> T callWithTimeout(ExecutorServiceProvider executorServiceProvider,Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit,
		boolean amInterruptible) throws Exception;
}