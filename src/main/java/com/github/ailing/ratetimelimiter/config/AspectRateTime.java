/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: AspectRateTime.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;

import com.github.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;

/**
 *AspectRateTime 主要封装注解的参数
 * @FileName  AspectRateTime.java
 * @Date  15-11-11 下午4:16
 * @author mayuanchao
 * @version 1.0
 */
public class AspectRateTime implements java.io.Serializable {
	private static final long serialVersionUID = -3068540488162427604L;
	/**
	 * 限流数 TPS
	 */
	private double permits;
	/**
	 * 服务接口名
	 */
	private String serviceName;
	/**
	 * 限流时间段延迟时间
	 */
	private int rateDelay;
	/**
	 * 超时机制 延迟时间
	 */
	private int timeDelay;
	/**
	 * 限流时间段
	 */
	private String rateCronExpr;
	/**
	 * 超时机制时间段
	 */
	private String timerCronExpr;
	/**
	 * 超时或者超过限流数出来类
	 * 默认为 SimpleRateTimeLimiterInvoker
	 */
	private Class<?extends RateTimeLimiterInvoker<?>> invoker = SimpleRateTimeLimiterInvoker.class;
	/**
	 * 超时机制开关
	 */
	private boolean limitTimer;
	
	/**
	 * 限流开关
	 */
	private boolean limitRate;

	/**
	 * 超时时间
	 * 默认是毫秒 
	 */
	private long maxLiveTime;
	
	/**
	 * 更新配置时间
	 */
	private long intervalTime;
	
	/**
	 * 是否定时更新配置
	 * 默认为true
	 */
	private boolean refreshCfg = true;
	
	
	public AspectRateTime(String serviceName) {
		this.serviceName = serviceName;
	}
	

	public boolean isRefreshCfg() {
		return refreshCfg;
	}


	public void setRefreshCfg(boolean refreshCfg) {
		this.refreshCfg = refreshCfg;
	}


	public double getPermits() {
		return permits;
	}

	public void setPermits(double permits) {
		this.permits = permits;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getRateDelay() {
		return rateDelay;
	}

	public void setRateDelay(int rateDelay) {
		this.rateDelay = rateDelay;
	}

	public int getTimeDelay() {
		return timeDelay;
	}

	public void setTimeDelay(int timeDelay) {
		this.timeDelay = timeDelay;
	}

	public String getRateCronExpr() {
		return rateCronExpr;
	}

	public void setRateCronExpr(String rateCronExpr) {
		this.rateCronExpr = rateCronExpr;
	}

	public String getTimerCronExpr() {
		return timerCronExpr;
	}

	public void setTimerCronExpr(String timerCronExpr) {
		this.timerCronExpr = timerCronExpr;
	}

	public Class<?extends RateTimeLimiterInvoker<?>> getInvoker() {
		return invoker;
	}

	public void setInvoker(Class<?extends RateTimeLimiterInvoker<?>> invoker) {
		this.invoker = invoker;
	}

	public boolean isLimitTimer() {
		return limitTimer;
	}

	public void setLimitTimer(boolean limitTimer) {
		this.limitTimer = limitTimer;
	}

	public boolean isLimitRate() {
		return limitRate;
	}

	public void setLimitRate(boolean limitRate) {
		this.limitRate = limitRate;
	}

	public long getMaxLiveTime() {
		return maxLiveTime;
	}

	public void setMaxLiveTime(long maxLiveTime) {
		this.maxLiveTime = maxLiveTime;
	}

	public long getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}
}