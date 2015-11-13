/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: SimpleRatimelimitConfigurerProvider.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;

import com.github.ailing.ratetimelimiter.YdtRateLimiter;
import com.github.ailing.ratetimelimiter.adapter.executor.RateLimiterExecutorImpl;
import com.github.ailing.ratetimelimiter.adapter.executor.SimpleExecutorServiceProvider;
import com.github.ailing.ratetimelimiter.adapter.executor.SimpleTimeLimiterExecutor;

import com.google.common.util.concurrent.RateLimiter;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
		if (rateTimeConfigurer.getRateTimeClazzBean() == null) {
			rateTimeConfigurer.setRateTimeClazzBean(new RateTimeClassBean());
		}

		RateTimeClassBean rateTimeClassBean = rateTimeConfigurer.getRateTimeClazzBean();
		rateTimeClassBean.setClazzConfigurerProvider(this.getClass());
		rateTimeClassBean.setClazzExecutorServiceProvider(SimpleExecutorServiceProvider.class);
		rateTimeClassBean.setClazzTimeLimiterExecutor(SimpleTimeLimiterExecutor.class);
		rateTimeClassBean.setClazzRateLimiterExecutor(RateLimiterExecutorImpl.class);
	}

	@Override
	public YdtRateLimiter getYdtRateLimiter() {
		return new AiRateLimiter();
	}
}

class AiRateLimiter extends YdtRateLimiter {
	private RateLimiter rateLimiter;

	@Override
	public void setRate(double permitsPerSecond) {
		this.rateLimiter.setRate(permitsPerSecond);
	}

	@Override
	public double getRate() {
		return this.rateLimiter.getRate();
	}

	@Override
	public YdtRateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
		this.rateLimiter = RateLimiter.create(permitsPerSecond, warmupPeriod, unit);

		return this;
	}

	@Override
	public YdtRateLimiter create(double permitsPerSecond) {
		this.rateLimiter = RateLimiter.create(permitsPerSecond);

		return this;
	}

	@Override
	public boolean tryAcquire() {
		return this.rateLimiter.tryAcquire();
	}

	@Override
	public boolean tryAcquire(int permits) {
		return this.rateLimiter.tryAcquire(permits);
	}

	@Override
	public boolean tryAcquire(long timeout, TimeUnit unit) {
		return this.rateLimiter.tryAcquire(timeout, unit);
	}

	@Override
	public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
		return this.rateLimiter.tryAcquire(permits, timeout, unit);
	}

	@Override
	public void acquire() {
		this.rateLimiter.acquire();
	}

	@Override
	public void acquire(int permits) {
		this.rateLimiter.acquire(permits);
	}
}