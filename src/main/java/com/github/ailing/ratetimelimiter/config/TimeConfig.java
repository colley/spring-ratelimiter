/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: TimeConfig.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;

import com.github.ailing.ratetimelimiter.util.CronExprUtils;

import org.quartz.CronExpression;

/**
 *超时机制封装类
 * @FileName  TimeConfig.java
 * @Date  15-11-11 上午9:43
 * @author mayuanchao
 * @version 1.0
 */
public class TimeConfig implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TimeConfig() {
	}

	public TimeConfig(boolean release, long maxLiveTime, int startDelay, String timerCronExpr) {
		this.release = release;
		this.maxLiveTime = maxLiveTime;
		this.startDelay = startDelay;
		this.timerCronExpr = timerCronExpr;
	}

	/**
	 * 超时机制是否开启
	 */
	private boolean release;

	/**
	 * 定点开关延迟时间 单位分钟
	 */
	private int startDelay;

	/**
	 * 针对相应的时间段实行超时机制
	 */
	private String timerCronExpr;

	/**
	 * 时间段CronExpression
	 */
	private CronExpression cronExpression;

	/**
	 * 超时最大时间,单位为 ms
	 */
	private long maxLiveTime;

	public boolean isRelease() {
		return release;
	}

	public void setRelease(boolean release) {
		this.release = release;
	}

	public int getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	public String getTimerCronExpr() {
		return timerCronExpr;
	}

	public void setTimerCronExpr(String timerCronExpr) {
		this.timerCronExpr = timerCronExpr;
	}

	public CronExpression getCronExpression() {
		this.cronExpression = CronExprUtils.parseCronExpr(timerCronExpr);

		return cronExpression;
	}

	public void setCronExpression(CronExpression cronExpression) {
		this.cronExpression = cronExpression;
	}

	public long getMaxLiveTime() {
		return maxLiveTime;
	}

	public void setMaxLiveTime(long maxLiveTime) {
		this.maxLiveTime = maxLiveTime;
	}
}