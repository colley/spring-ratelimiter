/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeServiceController.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.adapter.executor;

import static com.github.ailing.ratetimelimiter.util.PreconditionUtil.checkNotNull;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ailing.ratetimelimiter.RateTimeCreatingBeanFactory;
import com.github.ailing.ratetimelimiter.adapter.RateLimiterExecutor;
import com.github.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;
import com.github.ailing.ratetimelimiter.adapter.RateTimeServiceCallBack;
import com.github.ailing.ratetimelimiter.adapter.RateTimeServiceExecutor;
import com.github.ailing.ratetimelimiter.adapter.TimeLimiterExecutor;
import com.github.ailing.ratetimelimiter.config.AspectRateTimeProvider;
import com.github.ailing.ratetimelimiter.config.RateLimitState;
import com.github.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;
import com.github.ailing.ratetimelimiter.config.RateTimelimitConfigurerProvider;

/**
 *该服务用于不适用注解的形式下，直接调用该服务来实现限流和超时机制
 * @FileName  RateTimeServiceController.java
 * @Date  15-11-11 下午9:53 
 * @author mayuanchao
 * @version 1.0
 */
@Service
public class RateTimeServiceController {
	protected final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private RateTimeCreatingBeanFactory rateTimeCreatingBeanFactory;
	
	private Class<?extends RateTimelimitConfigurerProvider> clazzConfigurer;
	
	
	/**
	 * 初始化配置提供者，调用
	 * @param clazzConfigurer
	 */
	public void init(Class<?extends RateTimelimitConfigurerProvider> clazzConfigurer){
		this.clazzConfigurer = clazzConfigurer;
	}
	
	/**
	 * 集成了超时机制和限流措施
	 * @param serviceName
	 * @param callBack
	 * @param aspectProvider
	 * @return
	 * @throws Exception
	 */
	public <T> T  execute(String serviceName, RateTimeServiceCallBack<T> callBack,AspectRateTimeProvider aspectProvider)throws Exception {
		checkNotNull(
				clazzConfigurer,"RateTimelimitConfigurerProvider Required,fisrt call init method");
		RateTimeConfigurerFactory configurerFactory = rateTimeCreatingBeanFactory.getConfigurerFactory();
		RateTimelimitConfigurerProvider configProvider = rateTimeCreatingBeanFactory.getConfigurerProvider(clazzConfigurer);
		configurerFactory.config(serviceName, configProvider, aspectProvider);
		RateTimeServiceExecutor ratimeServiceExecutor = (RateTimeServiceExecutor) rateTimeCreatingBeanFactory.getApplicationContext().getBean(RateTimeServiceExecutorAdapter.class);
		return ratimeServiceExecutor.execute(serviceName,callBack);
	}
	
	/**
	 * 单独针对限流接口
	 * @param serviceName
	 * @param callBack
	 * @param aspectProvider
	 * @return
	 * @throws Exception
	 */
	public <T> T  rateLimiterExecute(String serviceName, RateTimeServiceCallBack<T> callBack,AspectRateTimeProvider aspectProvider)throws Exception {
		RateLimiterExecutor rateLimiterExecutor = getRateLimiterExecutor(serviceName, callBack, aspectProvider);
		RateLimitState rateState = rateLimiterExecutor.tryAcquire(serviceName);
		if (logger.isDebugEnabled()) {
			logger.debug("current rate limiter state is " + rateState.name());
		}
		T retVal = null;
		RateTimeLimiterInvoker invoker = rateTimeCreatingBeanFactory.createLimiterInvoker(serviceName);
		//限流,超过了最大的限流池
		if (RateLimitState.NOT_ACQUIRE.equals(rateState)) {
			retVal = invoker.invokehandler(null);
		}else{
			retVal = callBack.invoker();
		}
		
		return retVal;
	}
	
	
	/**
	 * 单独针对超时机制接口
	 * @param serviceName
	 * @param callBack
	 * @param aspectProvider
	 * @return
	 * @throws Exception
	 */
	public <T> T  timeLimiterExecute(String serviceName, RateTimeServiceCallBack<T> callBack,AspectRateTimeProvider aspectProvider)throws Exception {
		TimeLimiterExecutor timeLimiterExecutor = getTimeLimiterExecutor(serviceName, callBack, aspectProvider);
		return timeLimiterExecutor.invokeByLimitTime(serviceName, callBack);
	}
	
	
	/**
	 * 获取超时机制执行者
	 * @param serviceName
	 * @param callBack
	 * @param aspectProvider
	 * @return
	 */
	public <T> TimeLimiterExecutor getTimeLimiterExecutor(String serviceName, RateTimeServiceCallBack<T> callBack,AspectRateTimeProvider aspectProvider) {
		checkNotNull(
				clazzConfigurer,"RateTimelimitConfigurerProvider Required");
		RateTimeConfigurerFactory configurerFactory = rateTimeCreatingBeanFactory.getConfigurerFactory();
		RateTimelimitConfigurerProvider configProvider = rateTimeCreatingBeanFactory.getConfigurerProvider(clazzConfigurer);
		configurerFactory.config(serviceName, configProvider, aspectProvider);
		return rateTimeCreatingBeanFactory.getTimeLimiterExecutor(serviceName);
	}

	/**
	 * 获取限流措施执行者
	 * @param serviceName
	 * @param callBack
	 * @param aspectProvider
	 * @return
	 */
	public <T> RateLimiterExecutor getRateLimiterExecutor(String serviceName, RateTimeServiceCallBack<T> callBack,AspectRateTimeProvider aspectProvider) {
		checkNotNull(
				clazzConfigurer,"RateTimelimitConfigurerProvider Required");
		RateTimeConfigurerFactory configurerFactory = rateTimeCreatingBeanFactory.getConfigurerFactory();
		RateTimelimitConfigurerProvider configProvider = rateTimeCreatingBeanFactory.getConfigurerProvider(clazzConfigurer);
		configurerFactory.config(serviceName, configProvider, aspectProvider);
		return rateTimeCreatingBeanFactory.getRateLimiterExecutor(serviceName);
	}
}