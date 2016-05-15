/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeConfigurerFactory.java 1492811 2015-11-13 08:01:26Z mayuanchao $
 */
package com.ailing.ratetimelimiter.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ailing.ratetimelimiter.YdtRateLimiter;
import com.ailing.ratetimelimiter.adapter.RateTimeLimiterInvoker;
import com.ailing.ratetimelimiter.exception.RatimeLimiterException;
import static com.ailing.ratetimelimiter.util.PreconditionUtil.checkNotNull;

/**
 *不同的服务有不同的配置  一个服务对应一个配置
 *配置信息成员创建者
 * @FileName  RatimeConfigurerFactory.java
 * @Date  15-11-6 上午10:42
 * @author mayuanchao
 * @version 1.0
 */
@Component
public class RateTimeConfigurerFactory {
	protected final Logger logger = Logger.getLogger(getClass());
	private final Map<String, RateTimeConfigurer> ratimeConfigurers = Collections.synchronizedMap(new HashMap<String, RateTimeConfigurer>());

	/**
	 * 配置对应的配置
	 * @param ratimelimitConfig
	 * @return
	 * @throws RatimeLimiterException
	 */
	public void config(String serviceName, RateTimelimitConfigurerProvider configProvider,AspectRateTimeProvider aspectProvider,Class<? extends RateTimeLimiterInvoker> invoker)
		throws RatimeLimiterException {
		RateTimeConfigurer ratimeConfigure = ratimeConfigurers.get(serviceName);

		if (ratimeConfigure == null) {
			checkNotNull(configProvider, "expected a non-null RatimelimitConfigurerProvider");
			ratimeConfigure = configProvider.create(serviceName,aspectProvider,invoker);

			if (ratimeConfigure != null) {
				//更新配置接口
				ratimeConfigure.setConfigProvider(configProvider);
				ratimeConfigure.setAspectProvider(aspectProvider);
				ratimeConfigurers.put(serviceName, ratimeConfigure);
				//启动刷新进程
				ratimeConfigure.timerStart();
			}
		}

		checkNotNull(ratimeConfigure, "expected a non-null RatimeConfigurer");
	}

	public RateTimeConfigurer getRatimeConfigurer(String serviceName) {
		RateTimeConfigurer ratimeConfigurer = ratimeConfigurers.get(serviceName);
		checkNotNull(ratimeConfigurer, "expected a non-null RatimeConfigurer");
	
		return ratimeConfigurer;
	}

	public YdtRateLimiter getYhdRateLimiter(String serviceName){
		RateTimeConfigurer ratimeConfigurer =  getRatimeConfigurer(serviceName);
		return ratimeConfigurer.getConfigProvider().getYdtRateLimiter();
	}
}