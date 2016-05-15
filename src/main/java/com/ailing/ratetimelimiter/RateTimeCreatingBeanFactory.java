/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */
package com.ailing.ratetimelimiter;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ailing.ratetimelimiter.adapter.ExecutorServiceProvider;
import com.ailing.ratetimelimiter.adapter.RateLimiterExecutor;
import com.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;
import com.ailing.ratetimelimiter.adapter.TimeLimiterExecutor;
import com.ailing.ratetimelimiter.config.RateTimeClassBean;
import com.ailing.ratetimelimiter.config.RateTimeConfigurer;
import com.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;
import com.ailing.ratetimelimiter.config.RateTimelimitConfigurerProvider;
import com.ailing.ratetimelimiter.config.SimpleRateTimeLimiterInvoker;
import com.ailing.ratetimelimiter.exception.RatimeLimiterException;
import static com.ailing.ratetimelimiter.util.PreconditionUtil.*;
/**
 *
 *限流和超时机制bean工厂
 * @FileName  RateTimeBeansFactory.java
 * @Date  15-11-13 上午11:26
 * @author mayuanchao
 * @version 1.0
 */
@Component
public class RateTimeCreatingBeanFactory implements ApplicationContextAware {
	protected final Logger logger = Logger.getLogger(getClass());
	
	private ApplicationContext context;
	
	@Autowired
	private RateTimeConfigurerFactory configurerFactory;


	public RateTimeConfigurerFactory getConfigurerFactory() {
		return configurerFactory;
	}
	
	public RateTimelimitConfigurerProvider getConfigurerProvider(Class<?extends RateTimelimitConfigurerProvider> configurer) {
		RateTimelimitConfigurerProvider configurerProvider =context.getBean(configurer);
		checkNotNull(
				configurerProvider,
				"RateTimelimitConfigurerProvider Required checked bean name rateTimeConfigurerProvider ");
		return configurerProvider;
	}
	
	public RateTimeClassBean getRateTimeClassBean(String serviceName) {
		RateTimeConfigurer ratimeConfigurer = configurerFactory.getRatimeConfigurer(serviceName);
		checkNotNull(ratimeConfigurer, "expected a non-null RatimeConfigurer");
		RateTimeClassBean rateTimeClassBean = ratimeConfigurer.getRateTimeClazzBean();
		checkNotNull(ratimeConfigurer, "expected a non-null RateTimeClassBean");
		return rateTimeClassBean;
	}


	public TimeLimiterExecutor getTimeLimiterExecutor(String serviceName) {
		RateTimeClassBean rateTimeClassBean = getRateTimeClassBean(serviceName);
		TimeLimiterExecutor timeLimiterExecutor =
				context.getBean(rateTimeClassBean.getClazzTimeLimiterExecutor());
		checkNotNull(
				timeLimiterExecutor,
				"expected a non-null TimeLimiterExecutor");
		return timeLimiterExecutor;
	}

	public RateLimiterExecutor getRateLimiterExecutor(String serviceName) {
		RateTimeClassBean rateTimeClassBean = getRateTimeClassBean(serviceName);
		RateLimiterExecutor rateLimiterExecutor =
				context.getBean(rateTimeClassBean.getClazzRateLimiterExecutor());
		checkNotNull(
				rateLimiterExecutor,
				"expected a non-null RateLimiterExecutor");
		return rateLimiterExecutor;
	}

	public ExecutorServiceProvider getExecutorServiceProvider(String serviceName) {
		RateTimeClassBean rateTimeClassBean = getRateTimeClassBean(serviceName);
		ExecutorServiceProvider executorServiceProvider =
				context.getBean(rateTimeClassBean.getClazzExecutorServiceProvider());
		checkNotNull(
				executorServiceProvider,
				"expected a non-null ExecutorServiceProvider");
		return executorServiceProvider;
	}
	
	public RateTimeLimiterInvoker createLimiterInvoker(String serviceName) {
		RateTimeLimiterInvoker invoker = null;
		RateTimeClassBean rateTimeClassBean = getRateTimeClassBean(serviceName);
		checkNotNull(rateTimeClassBean, "expected a non-null RateTimeClassBean");
		if (rateTimeClassBean.getClazzInvoker() == null) {
			rateTimeClassBean.setClazzInvoker(SimpleRateTimeLimiterInvoker.class);
		}
		try {
			invoker = rateTimeClassBean.getClazzInvoker().newInstance();
		} catch (InstantiationException e) {
			logger.error("Rate and timer Invoker instance not exits", e);
			throw new RatimeLimiterException("RatimeLimiterInvoker class Required");
		} catch (IllegalAccessException e) {
			logger.error("Rate and timer Invoker instance not exits", e);
			throw new RatimeLimiterException("RatimeLimiterInvoker class Required");
		}
		return invoker;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
		throws BeansException {
		this.context = context;
	}
}