/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */
package com.github.ailing.ratetimelimiter.adapter;

import static com.github.ailing.ratetimelimiter.util.PreconditionUtil.checkNotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ailing.ratetimelimiter.config.RateLimitState;
import com.github.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;

/**
 *
 * @FileName  RatetimeServiceExecutorAdapter.java
 * @Date  15-11-11 上午11:50
 * @author mayuanchao
 * @version 1.0
 */

public class RateTimeServiceExecutorAdapter implements RateTimeServiceExecutor<Object> {
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RateTimeConfigurerFactory configurerFactory;
	
	private RateLimiterExecutor rateLimiterExecutor;
	
	private TimeLimiterExecutor<Object> timeLimiterExecutor;

	@Override
	public Object execute(String serviceName, RateTimeServiceCallBack<Object> callBack)
		throws Exception {
		Object retVal = null;
		RateTimeLimiterInvoker<?> invoker = configurerFactory.createLimiterInvoker(serviceName);
		checkNotNull(rateLimiterExecutor, "RateLimiterExecutor class Required");
		checkNotNull(timeLimiterExecutor, "TimeLimiterExecutor class Required");
		checkNotNull(invoker, "RatimeLimiterInvoker class Required");

		RateLimitState rateState = rateLimiterExecutor.tryAcquire(serviceName);

		if (logger.isDebugEnabled()) {
			logger.debug("current rate limiter state is " + rateState.name());
		}

		//限流,超过了最大的限流池
		if (RateLimitState.NOT_ACQUIRE.equals(rateState)) {
			retVal = invoker.handler();
		} else {
			//不限流和取到令牌,执行超时机制处理
			if (timeLimiterExecutor.isLimitOpen(serviceName)) {
				retVal = timeLimiterExecutor.invokeByLimitTime(serviceName, callBack);
			} else {
				retVal = callBack.invoker();
			}
		}

		return retVal;
	}
	
	@Override
	public void setRateLimiterExecutor(RateLimiterExecutor rateExecutor) {
		this.rateLimiterExecutor = rateExecutor;
	}

	@Override
	public void setTimeLimiterExecutor(TimeLimiterExecutor<Object> timeExecutor) {
		this.timeLimiterExecutor = timeExecutor;
	}
	
}