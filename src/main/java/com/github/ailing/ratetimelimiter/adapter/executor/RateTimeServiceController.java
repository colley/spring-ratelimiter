/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeServiceController.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.adapter.executor;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.ailing.ratetimelimiter.adapter.RateTimeServiceCallBack;
import com.github.ailing.ratetimelimiter.config.AspectRateTime;
import com.github.ailing.ratetimelimiter.config.AspectRateTimeProvider;
import com.github.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;
import com.github.ailing.ratetimelimiter.config.RateTimelimitConfigurerProvider;

/**
 *该服务用于不适用注解的形式下，直接调用该服务来实现限流和超时机制
 * @FileName  RateTimeServiceController.java
 * @Date  15-11-11 下午9:53 
 * @author mayuanchao
 * @version 1.0
 */
public class RateTimeServiceController {
	
	/**
	 * 限流和超时机制适配类
	 */
	private RateTimeServiceExecutorAdapter ratimeServiceExecutor;
	
	/**
	 * 配置工厂，提供配置相关信息和创建每个服务的配置信息
	 */
	@Autowired
	private RateTimeConfigurerFactory configurerFactory;	
	
	/**
	 * 配置信息提供者
	 */
	private RateTimelimitConfigurerProvider configurerProvider;
	

	public RateTimeServiceExecutorAdapter getRatimeServiceExecutor() {
		return ratimeServiceExecutor;
	}


	public void setRatimeServiceExecutor(RateTimeServiceExecutorAdapter ratimeServiceExecutor) {
		this.ratimeServiceExecutor = ratimeServiceExecutor;
	}


	public RateTimeConfigurerFactory getConfigurerFactory() {
		return configurerFactory;
	}


	public void setConfigurerFactory(RateTimeConfigurerFactory configurerFactory) {
		this.configurerFactory = configurerFactory;
	}


	public RateTimelimitConfigurerProvider getConfigurerProvider() {
		return configurerProvider;
	}


	public void setConfigurerProvider(RateTimelimitConfigurerProvider configurerProvider) {
		this.configurerProvider = configurerProvider;
	}

	public Object execute(String serviceName, RateTimeServiceCallBack<Object> callBack)throws Exception {
		configurerFactory.config(serviceName, configurerProvider,new AspectRateTimeProvider() {
			@Override
			public AspectRateTime create(String serviceName) {
				return new AspectRateTime(serviceName);
			}
		});
		return ratimeServiceExecutor.execute(serviceName,callBack);
	}
}