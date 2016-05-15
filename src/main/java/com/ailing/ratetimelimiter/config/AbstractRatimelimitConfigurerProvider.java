/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: AbstractRatimelimitConfigurerProvider.java 1492811 2015-11-13 08:01:26Z mayuanchao $
 */
package com.ailing.ratetimelimiter.config;

import com.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;

/**
 *简单封装配置信息的提供者
 * @FileName  AbstractRatimelimitConfigurerProvider.java
 * @Date  15-11-11 下午3:41
 * @author mayuanchao
 * @version 1.0
 */
public abstract class AbstractRatimelimitConfigurerProvider implements RateTimelimitConfigurerProvider {
	
	@Override
	public RateTimeConfigurer create(String serviceName,AspectRateTimeProvider aspectProvider,Class<? extends RateTimeLimiterInvoker> invoker) {
		AspectRateTime aspect = aspectProvider.create(serviceName);
		RateTimeConfigurer rateTimeConfigurer = new RateTimeConfigurer();
		if(aspect!=null && invoker!=null){
			aspect.setInvoker(invoker);
		}
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
		RateTimeClassBean rateTimeClassBean = new RateTimeClassBean(aspect.getInvoker());
		ratimeConfigurer.setRateTimeClazzBean(rateTimeClassBean);
		ratimeConfigurer.setServiceName(aspect.getServiceName());
		ratimeConfigurer.setRefreshCfg(aspect.isRefreshCfg());
		return ratimeConfigurer;
	}
}