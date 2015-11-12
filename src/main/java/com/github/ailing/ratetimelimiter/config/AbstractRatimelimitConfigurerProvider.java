/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: AbstractRatimelimitConfigurerProvider.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.config;


/**
 *简单封装配置信息的提供者
 * @FileName  AbstractRatimelimitConfigurerProvider.java
 * @Date  15-11-11 下午3:41
 * @author mayuanchao
 * @version 1.0
 */
public abstract class AbstractRatimelimitConfigurerProvider implements RateTimelimitConfigurerProvider {
	
	@Override
	public RateTimeConfigurer create(String serviceName,AspectRateTimeProvider aspectProvider) {
		AspectRateTime aspect = aspectProvider.create(serviceName);
		RateTimeConfigurer rateTimeConfigurer = new RateTimeConfigurer();
		if(aspect!=null){
			rateTimeConfigurer = config(aspect);
		}
		// 可以读库去更新
		updateConfig(rateTimeConfigurer);
		return rateTimeConfigurer;
	}

	public RateTimeConfigurer config(AspectRateTime aspect) {
		RateTimeConfigurer ratimeConfigurer = new RateTimeConfigurer();
		RateConfig rateConfig = new RateConfig(aspect.isLimitRate(), aspect.getPermits(),
				aspect.getRateDelay(), aspect.getRateCronExpr());
		ratimeConfigurer.setRateConfig(rateConfig);

		TimeConfig timeConfig = new TimeConfig(aspect.isLimitTimer(), aspect.getMaxLiveTime(),
				aspect.getTimeDelay(), aspect.getTimerCronExpr());
		ratimeConfigurer.setTimeConfig(timeConfig);
		ratimeConfigurer.setIntervalTime(aspect.getIntervalTime());
		ratimeConfigurer.setClazzInvoker(aspect.getInvoker());
		ratimeConfigurer.setServiceName(aspect.getServiceName());
		ratimeConfigurer.setRefreshCfg(aspect.isRefreshCfg());
		return ratimeConfigurer;
	}
}