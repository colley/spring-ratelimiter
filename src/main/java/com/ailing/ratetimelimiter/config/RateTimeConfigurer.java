/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeConfigurer.java 1492827 2015-11-13 08:07:36Z mayuanchao $
 */
package com.ailing.ratetimelimiter.config;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.springframework.beans.BeanUtils;

/**
 * 各配置信息成员，包括自己刷新，定时等
 * @FileName  RatimeConfigurer.java
 * @Date  15-11-9 上午9:04
 * @author mayuanchao
 * @version 1.0
 */
public class RateTimeConfigurer implements java.io.Serializable {
	private static final long serialVersionUID = -8303618631013751837L;
	protected final Logger logger = Logger.getLogger(getClass());
	private final static long DEFAULT_START_TIME = 15 * 60;
	private final static String TIMER_PREFIX = "_RatimeConfigRefreshTimer";
	
	/**
	 * 限流配置参数
	 */
	private RateConfig rateConfig;
	
	/**
	 * 超时机制配置参数
	 */
	private TimeConfig timeConfig;
	
	/**
	 * AspectRateTime 参数提供者
	 */
	private AspectRateTimeProvider aspectProvider;

	/**
	 * 限流的服务名称 可以多个服务用一个限流
	 */
	private String serviceName;

	/**
	 * 多久去更新最新的配置  单位秒
	 */
	private long intervalTime;

	/**
	 * 是否打开限流
	 */
	private boolean limitRate;

	/**
	 * 是否打开超时机制
	 */
	private boolean limitTimer;

	/**
	 * 是否定时更新配置
	 */
	private boolean refreshCfg;

	/**
	 * 定时去更新配置信息
	 */
	private Timer timer = null;

	/**
	 * 定时更新配置任务
	 */
	private RateTimeTimerTask ratimeTask;

	/**
	 * 配置数据提供类
	 */
	private RateTimelimitConfigurerProvider configProvider;

	private RateTimeClassBean rateTimeClazzBean;
	


	/**
	 * 判断是否开启限流措施
	 * @return
	 */
	public boolean isOpenRateLimit() {
		boolean isLimitOpen = rateConfig.isRelease();
		int startDelay = rateConfig.getStartDelay();
		CronExpression cronExpression = rateConfig.getCronExpression();
		
		if (isLimitOpen && (cronExpression == null)) {
			return true;
		}
		
		//如果设置的TPS为 Integer.MAX_VALUE 就取消限流
		if(rateConfig.getPermitsPerSecond()>=Integer.MAX_VALUE){
			logger.warn("["+this.serviceName+"] current PermitsPerSecond is "+ rateConfig.getPermitsPerSecond() +", glt Integer.MAX_VALUE");
			return false; 
		}
				
		//开关是否打开,否设置特定时间
		if (isLimitOpen && (cronExpression != null)) {
			Date currentDate = Calendar.getInstance().getTime();
			Date cronNextDate = cronExpression.getNextValidTimeAfter(currentDate);

			Date startDate = DateUtils.addMinutes(cronNextDate, -(startDelay / 2));
			Date endDate = DateUtils.addMinutes(cronNextDate, startDelay);

			if ((currentDate.compareTo(startDate) > -1) && (currentDate.compareTo(endDate) < 0)) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 判断是否开启超时机制
	 * @return
	 */
	public boolean isOpenTimeLimit() {
		boolean isLimitOpen = timeConfig.isRelease();
		int startDelay = timeConfig.getStartDelay();
		CronExpression cronExpression = timeConfig.getCronExpression();
		if (isLimitOpen && (cronExpression == null)) {
			return true;
		}
		//开关是否打开,否设置特定时间
		if (isLimitOpen && (cronExpression != null)) {
			Date currentDate = Calendar.getInstance().getTime();
			Date cronNextDate = cronExpression.getNextValidTimeAfter(currentDate);

			Date startDate = DateUtils.addMinutes(cronNextDate, -(startDelay / 2));
			Date endDate = DateUtils.addMinutes(cronNextDate, startDelay);

			if ((currentDate.compareTo(startDate) > -1) && (currentDate.compareTo(endDate) < 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 定时去更新配置信息
	 */
	public void timerStart() {
		if (logger.isDebugEnabled()) {
			logger.debug("RefreshTimer start serviceName is " + serviceName);
		}
		/**
		 * 如果设置成不更新配置,不执行定时更新任务
		 * 不能动态设置是否能定时更新配置，设置成false之后
		 */
		if(!this.refreshCfg){
			if(logger.isDebugEnabled()){
				logger.debug("["+serviceName+"] RefreshTimer don't start,Because the refreshCfg is " + this.refreshCfg);
			}
			return;
		}
		this.timer = new Timer(serviceName + TIMER_PREFIX);
		//默认是15分钟
		this.intervalTime = (this.intervalTime == 0) ? DEFAULT_START_TIME : intervalTime;

		RateTimeTimerTask ratimeTimerTask = new RateTimeTimerTask(this);
		setRatimeTask(ratimeTimerTask);

		long currentTime = System.currentTimeMillis() + (intervalTime * 1000);
		timer.schedule(ratimeTimerTask, new Date(currentTime), intervalTime * 1000);

		if (logger.isDebugEnabled()) {
			logger.debug("RefreshTimer end, Delay time is " + (this.intervalTime * 1000) + " ms");
		}
	}

	/**
	 * 更新间隔时间变化了，重启启动定时操作
	 */
	public void timerShutDown() {
		if (logger.isDebugEnabled()) {
			logger.debug("cancel  ConfigRefreshTimer start serviceName is" + serviceName);
		}

		if ((this.getTimer() != null) && (getRatimeTask() != null)) {
			getRatimeTask().cancel();
			getTimer().cancel();
			setRatimeTask(null);
			setTimer(null);
		}

		if (logger.isInfoEnabled()) {
			logger.debug("cancel  ConfigRefreshTimer end serviceName is" + serviceName);
		}
	}

	/**
	 * 刷新配置参数
	 */
	public void refresh() {
		RateTimeConfigurer newConfig = configProvider.create(this.serviceName, this.aspectProvider,this.rateTimeClazzBean.getClazzInvoker());

		if (newConfig != null) {
			long newInterval = newConfig.getIntervalTime();
			long oldInterval = this.getIntervalTime();
			BeanUtils.copyProperties(newConfig.getRateConfig(), this.getRateConfig());
			BeanUtils.copyProperties(newConfig.getTimeConfig(), this.getTimeConfig());

			if (newInterval != oldInterval) {
				timerShutDown();

				if ((this.getTimer() == null) && (this.getRatimeTask() == null)) {
					timerStart();
				}
			}
		}
	}

	/*************************************get and set method *****************************************/
	
	public RateTimeClassBean getRateTimeClazzBean() {
		return rateTimeClazzBean;
	}


	public void setRateTimeClazzBean(RateTimeClassBean rateTimeClazzBean) {
		this.rateTimeClazzBean = rateTimeClazzBean;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public boolean isLimitRate() {
		this.limitRate = isOpenRateLimit();
		return limitRate;
	}

	public boolean isLimitTimer() {
		this.limitTimer = isOpenTimeLimit();
		return limitTimer;
	}
	
	public RateTimeTimerTask getRatimeTask() {
		return ratimeTask;
	}

	public void setRatimeTask(RateTimeTimerTask ratimeTask) {
		this.ratimeTask = ratimeTask;
	}

	public void setLimitRate(boolean limitRate) {
		this.limitRate = limitRate;
	}

	public AspectRateTimeProvider getAspectProvider() {
		return aspectProvider;
	}

	public void setAspectProvider(AspectRateTimeProvider aspectProvider) {
		this.aspectProvider = aspectProvider;
	}

	public RateTimelimitConfigurerProvider getConfigProvider() {
		return configProvider;
	}

	public void setConfigProvider(RateTimelimitConfigurerProvider configProvider) {
		this.configProvider = configProvider;
	}

	public RateConfig getRateConfig() {
		return rateConfig;
	}

	public void setRateConfig(RateConfig rateConfig) {
		this.rateConfig = rateConfig;
	}

	public TimeConfig getTimeConfig() {
		return timeConfig;
	}

	public void setTimeConfig(TimeConfig timeConfig) {
		this.timeConfig = timeConfig;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public long getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	public void setLimitTimer(boolean limitTimer) {
		this.limitTimer = limitTimer;
	}

	public boolean isRefreshCfg() {
		return refreshCfg;
	}

	public void setRefreshCfg(boolean refreshCfg) {
		this.refreshCfg = refreshCfg;
	}
}