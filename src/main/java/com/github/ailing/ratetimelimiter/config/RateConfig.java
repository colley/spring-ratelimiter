/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateConfig.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;

import com.github.ailing.ratetimelimiter.util.CronExprUtils;

import org.quartz.CronExpression;

/**
 *限流参数bean
 * @FileName  RateConfig.java
 * @Date  15-11-11 上午9:39
 * @author mayuanchao
 * @version 1.0
 */
public class RateConfig implements java.io.Serializable {
	private static final long serialVersionUID = -3016356560746319609L;

	public RateConfig() {
	}

	public RateConfig(boolean release, double permitsPerSecond, int startDelay, String rateCronExpr) {
		this.release = release;
		this.permitsPerSecond = permitsPerSecond;
		this.startDelay = startDelay;
		this.rateCronExpr = rateCronExpr;
	}

	/**
	 * 限流是否开启
	 */
	private boolean release;

	/**
	 * 流速 tps
	 */
	private double permitsPerSecond;

	/**
	 * 定点开关延迟时间 单位分钟
	 */
	private int startDelay;

	/**
	 * 针对相应的时间段限流
	 */
	private String rateCronExpr;

	/**
	 * 时间段CronExpression
	 */
	private CronExpression cronExpression;

	public boolean isRelease() {
		return release;
	}

	public void setRelease(boolean release) {
		this.release = release;
	}

	public CronExpression getCronExpression() {
		this.cronExpression = CronExprUtils.parseCronExpr(rateCronExpr);

		return cronExpression;
	}

	public void setCronExpression(CronExpression cronExpression) {
		this.cronExpression = cronExpression;
	}

	public double getPermitsPerSecond() {
		return permitsPerSecond;
	}

	public void setPermitsPerSecond(double permitsPerSecond) {
		this.permitsPerSecond = permitsPerSecond;
	}

	public int getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}

	public String getRateCronExpr() {
		return rateCronExpr;
	}

	public void setRateCronExpr(String rateCronExpr) {
		this.rateCronExpr = rateCronExpr;
	}
}