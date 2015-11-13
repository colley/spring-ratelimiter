/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateLimiterExecutorImpl.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.adapter.executor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ailing.ratetimelimiter.YdtRateLimiter;
import com.github.ailing.ratetimelimiter.adapter.RateLimiterExecutor;
import com.github.ailing.ratetimelimiter.config.RateConfig;
import com.github.ailing.ratetimelimiter.config.RateLimitState;
import com.github.ailing.ratetimelimiter.config.RateTimeConfigurer;
import com.github.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;

/**
 *限流实现类
 * @FileName  RateLimiterExecutorImpl.java
 * @Date  15-11-11 下午5:21
 * @author mayuanchao
 * @version 1.0
 */
@Component
public class RateLimiterExecutorImpl implements RateLimiterExecutor {
	private static Map<String, YdtRateLimiter> allRateLimiter = Collections.synchronizedMap(new HashMap<String, YdtRateLimiter>());
	
	@Autowired
	private RateTimeConfigurerFactory configurerFactory;

	//50毫秒的缓存期
	private static final long WARM_UP_PERIOD = 50L;

	@Override
	public boolean isLimitOpen(String serviceName) {
		RateTimeConfigurer ratimeConfigurer = configurerFactory.getRatimeConfigurer(serviceName);

		return ratimeConfigurer.isLimitRate();
	}

	public YdtRateLimiter create(String serviceName) {
		RateTimeConfigurer ratimeConfigurer = configurerFactory.getRatimeConfigurer(serviceName);
		RateConfig rateConfig = ratimeConfigurer.getRateConfig();

		if (isLimitOpen(serviceName)) {
			double permitsPerSecond = rateConfig.getPermitsPerSecond();
			YdtRateLimiter rateLimiter  = allRateLimiter.get(serviceName);

			if (rateLimiter == null) {
				rateLimiter = configurerFactory.getYhdRateLimiter(serviceName).create(permitsPerSecond, WARM_UP_PERIOD,
						TimeUnit.MILLISECONDS);
				allRateLimiter.put(serviceName, rateLimiter);
			}

			//如果限流伐值配置中心改变，这里动态改变限流伐值
			BigDecimal currentRate = new BigDecimal(rateLimiter.getRate());
			BigDecimal bigpermitsPerSecond = new BigDecimal(permitsPerSecond);

			if ((permitsPerSecond > 0) && (currentRate.compareTo(bigpermitsPerSecond) != 0)) {
				rateLimiter.setRate(permitsPerSecond);
			}

			return rateLimiter;
		}

		//清掉缓存中的数据用于垃圾回收
		allRateLimiter.put(serviceName, null);

		return null;
	}

	@Override
	public RateLimitState tryAcquire(String serviceName) {
		RateLimitState retState = RateLimitState.CLOSED;
		YdtRateLimiter rateLimiter  = create(serviceName);

		if (rateLimiter != null) {
			retState = rateLimiter.tryAcquire() ? RateLimitState.ACQUIRE : RateLimitState.NOT_ACQUIRE;
		}

		return retState;
	}

	@Override
	public RateLimitState tryAcquire(String serviceName, int permits) {
		RateLimitState retState = RateLimitState.CLOSED;
		YdtRateLimiter rateLimiter  = create(serviceName);

		if (rateLimiter != null) {
			retState = rateLimiter.tryAcquire(permits) ? RateLimitState.ACQUIRE
													   : RateLimitState.NOT_ACQUIRE;
		}

		return retState;
	}

	@Override
	public RateLimitState tryAcquire(String serviceName, long timeout, TimeUnit unit) {
		RateLimitState retState = RateLimitState.CLOSED;
		YdtRateLimiter rateLimiter  = create(serviceName);

		if (rateLimiter != null) {
			retState = rateLimiter.tryAcquire(timeout, unit) ? RateLimitState.ACQUIRE
															 : RateLimitState.NOT_ACQUIRE;
		}

		return retState;
	}

	@Override
	public RateLimitState tryAcquire(String serviceName, int permits, long timeout, TimeUnit unit) {
		RateLimitState retState = RateLimitState.CLOSED;
		YdtRateLimiter rateLimiter  = create(serviceName);

		if (rateLimiter != null) {
			retState = rateLimiter.tryAcquire(permits, timeout, unit) ? RateLimitState.ACQUIRE
																	  : RateLimitState.NOT_ACQUIRE;
		}

		return retState;
	}

	@Override
	public void acquire(String serviceName) {
		YdtRateLimiter rateLimiter  = create(serviceName);

		if (rateLimiter != null) {
			//阻塞操作，调用该方法会一直等待，等待前面的处理完之后才会释放
			rateLimiter.acquire();
		}
	}

	@Override
	public void acquire(String serviceName, int permits) {
		YdtRateLimiter rateLimiter  = create(serviceName);

		if (rateLimiter != null) {
			//阻塞操作，调用该方法会一直等待，等待前面的处理完之后才会释放
			rateLimiter.acquire(permits);
		}
	}
}