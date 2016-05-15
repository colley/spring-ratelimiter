/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeServiceExecutorAdapter.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.ailing.ratetimelimiter.adapter.executor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ailing.ratetimelimiter.RateTimeCreatingBeanFactory;
import com.ailing.ratetimelimiter.adapter.RateLimiterExecutor;
import com.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;
import com.ailing.ratetimelimiter.adapter.RateTimeServiceCallBack;
import com.ailing.ratetimelimiter.adapter.RateTimeServiceExecutor;
import com.ailing.ratetimelimiter.adapter.TimeLimiterExecutor;
import com.ailing.ratetimelimiter.config.RateLimitState;
import static com.ailing.ratetimelimiter.util.PreconditionUtil.*;

/**
 *
 * @FileName  RatetimeServiceExecutorAdapter.java
 * @Date  15-11-11 上午11:50
 * @author mayuanchao
 * @version 1.0
 */
@Component
public class RateTimeServiceExecutorAdapter implements RateTimeServiceExecutor {
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RateTimeCreatingBeanFactory rateTimeCreatingBeanFactory;

	@Override
	public <T> T execute(String serviceName, RateTimeServiceCallBack<T> callBack)
		throws Exception {
		T retVal = null;
		TimeLimiterExecutor timeLimiterExecutor = rateTimeCreatingBeanFactory.getTimeLimiterExecutor(serviceName);
		RateLimiterExecutor  rateLimiterExecutor = rateTimeCreatingBeanFactory.getRateLimiterExecutor(serviceName);
		RateTimeLimiterInvoker invoker = rateTimeCreatingBeanFactory.createLimiterInvoker(serviceName);
		checkNotNull(rateLimiterExecutor, "RateLimiterExecutor class Required");
		checkNotNull(timeLimiterExecutor, "TimeLimiterExecutor class Required");
		checkNotNull(invoker, "RatimeLimiterInvoker class Required");
		RateLimitState rateState = rateLimiterExecutor.tryAcquire(serviceName);
		
		if (logger.isDebugEnabled()) {
			logger.debug("current rate limiter state is " + rateState.name());
		}

		//限流,超过了最大的限流池
		if (RateLimitState.NOT_ACQUIRE.equals(rateState)) {
			retVal = invoker.invokehandler(null);
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
}