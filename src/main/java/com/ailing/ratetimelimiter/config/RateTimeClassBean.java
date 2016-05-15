/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */

package com.ailing.ratetimelimiter.config;

import com.ailing.ratetimelimiter.adapter.ExecutorServiceProvider;
import com.ailing.ratetimelimiter.adapter.RateLimiterExecutor;
import com.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;
import com.ailing.ratetimelimiter.adapter.TimeLimiterExecutor;

/**
 * 管理相关的bean
 * @FileName  RateTimeClassBean.java
 * @Date  15-11-13 上午11:56 
 * @author mayuanchao
 * @version 1.0
 */
public class RateTimeClassBean implements java.io.Serializable {
	private static final long serialVersionUID = 1271886416974806815L;
	/**
	 * 限流和超时机制的处理类
	 */
	private Class<? extends RateTimeLimiterInvoker> clazzInvoker;
	/***************************** **********************************/
	private Class<? extends RateTimelimitConfigurerProvider> clazzConfigurerProvider;
	private Class<? extends TimeLimiterExecutor> clazzTimeLimiterExecutor;
	private Class<? extends RateLimiterExecutor> clazzRateLimiterExecutor;
	private Class<? extends ExecutorServiceProvider> clazzExecutorServiceProvider;

	public RateTimeClassBean(){}
	
	public RateTimeClassBean(Class<?extends RateTimeLimiterInvoker> clazzInvoker){
		this(null, clazzInvoker, null, null, null);
	}
	
	public RateTimeClassBean(
			Class<?extends RateTimelimitConfigurerProvider> clazzConfigurerProvider,
			Class<?extends RateTimeLimiterInvoker> clazzInvoker,
			Class<?extends RateLimiterExecutor> clazzRateLimiterExecutor,
			Class<?extends TimeLimiterExecutor> clazzTimeLimiterExecutor,
			Class<?extends ExecutorServiceProvider> clazzExecutorServiceProvider){
		this.clazzConfigurerProvider = clazzConfigurerProvider;
		this.clazzInvoker = clazzInvoker;
		this.clazzRateLimiterExecutor = clazzRateLimiterExecutor;
		this.clazzTimeLimiterExecutor = clazzTimeLimiterExecutor;
		this.clazzExecutorServiceProvider = clazzExecutorServiceProvider;
	}
	
	public Class<?extends RateTimeLimiterInvoker> getClazzInvoker() {
		return clazzInvoker;
	}

	public void setClazzInvoker(Class<?extends RateTimeLimiterInvoker> clazzInvoker) {
		this.clazzInvoker = clazzInvoker;
	}

	public Class<?extends RateTimelimitConfigurerProvider> getClazzConfigurerProvider() {
		return clazzConfigurerProvider;
	}

	public void setClazzConfigurerProvider(
		Class<?extends RateTimelimitConfigurerProvider> clazzConfigurerProvider) {
		this.clazzConfigurerProvider = clazzConfigurerProvider;
	}

	public Class<?extends TimeLimiterExecutor> getClazzTimeLimiterExecutor() {
		return clazzTimeLimiterExecutor;
	}

	public void setClazzTimeLimiterExecutor(
		Class<?extends TimeLimiterExecutor> clazzTimeLimiterExecutor) {
		this.clazzTimeLimiterExecutor = clazzTimeLimiterExecutor;
	}

	public Class<?extends RateLimiterExecutor> getClazzRateLimiterExecutor() {
		return clazzRateLimiterExecutor;
	}

	public void setClazzRateLimiterExecutor(
		Class<?extends RateLimiterExecutor> clazzRateLimiterExecutor) {
		this.clazzRateLimiterExecutor = clazzRateLimiterExecutor;
	}

	public Class<?extends ExecutorServiceProvider> getClazzExecutorServiceProvider() {
		return clazzExecutorServiceProvider;
	}

	public void setClazzExecutorServiceProvider(
		Class<?extends ExecutorServiceProvider> clazzExecutorServiceProvider) {
		this.clazzExecutorServiceProvider = clazzExecutorServiceProvider;
	}
}