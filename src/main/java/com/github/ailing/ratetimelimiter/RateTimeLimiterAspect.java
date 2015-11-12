/*
 * Copyright (c) 2015 NewHeight, Inc.
 * All rights reserved.
 * $Id: RateTimeLimiterAspect.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ailing.ratetimelimiter.adapter.RateTimeServiceCallBack;
import com.github.ailing.ratetimelimiter.adapter.executor.RateTimeServiceExecutorAdapter;
import com.github.ailing.ratetimelimiter.config.AspectRateTime;
import com.github.ailing.ratetimelimiter.config.AspectRateTimeProvider;
import com.github.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;
import com.github.ailing.ratetimelimiter.config.RateTimelimitConfigurerProvider;
import com.github.ailing.ratetimelimiter.util.JoinPointUtils;

/**
 *注解类
 * @FileName  AbstractRateLimitAspect.java
 * @Date  15-10-14 上午9:45
 * @author MColley
 * @version $Revision: 1492119 $ $Date: 2015-11-12 17:52:20 +0800 (Thu, 12 Nov 2015) $
 */
@Aspect
public class RateTimeLimiterAspect {
	
	/**
	 * 需要注入
	 */
	private RateTimeServiceExecutorAdapter rateTimeServiceExecutor;
	
	@Autowired
	private RateTimeConfigurerFactory configurerFactory;
	
	/**
	 * 需要注入
	 */
	private RateTimelimitConfigurerProvider configurerProvider;
	
	@Around(value = "@annotation(RateTimeLimit)", argNames = "RateTimeLimit")
	public Object aroundProcess(final ProceedingJoinPoint point, final RateTimeLimit ratelimit)
		throws Exception {
		configurerFactory.config(ratelimit.serviceName(), configurerProvider,new AspectRateTimeProvider() {
			@Override
			public AspectRateTime create(String serviceName) {
				return builder(ratelimit);
			}
		});
		return rateTimeServiceExecutor.execute(ratelimit.serviceName(),new AspectRatimeServiceCallBack(point));
	}
	
	public class AspectRatimeServiceCallBack implements RateTimeServiceCallBack<Object>{
		private ProceedingJoinPoint point;
		public AspectRatimeServiceCallBack(ProceedingJoinPoint point){
			this.point = point;
		}
		
		@Override
		public Object invoker() throws Exception {
			return JoinPointUtils.process(point);
		}
	}
	
	public void setRateTimeServiceExecutor(RateTimeServiceExecutorAdapter rateTimeServiceExecutor) {
		this.rateTimeServiceExecutor = rateTimeServiceExecutor;
	}


	public void setConfigurerProvider(RateTimelimitConfigurerProvider configurerProvider) {
		this.configurerProvider = configurerProvider;
	}

	public AspectRateTime builder(RateTimeLimit ratelimit) {
		AspectRateTime ratimelimitConfig = new AspectRateTime(ratelimit.serviceName());
		ratimelimitConfig.setPermits(ratelimit.permits());
		ratimelimitConfig.setRateCronExpr(ratelimit.rateCronExpr());
		ratimelimitConfig.setTimerCronExpr(ratelimit.timerCronExpr());
		ratimelimitConfig.setRateDelay(ratelimit.rateDelay());
		ratimelimitConfig.setTimeDelay(ratelimit.timeDelay());
		ratimelimitConfig.setInvoker(ratelimit.invoker());
		ratimelimitConfig.setIntervalTime(ratelimit.intervalTime());
		ratimelimitConfig.setLimitRate(ratelimit.limitRate());
		ratimelimitConfig.setLimitTimer(ratelimit.limitTimer());
		ratimelimitConfig.setMaxLiveTime(ratelimit.maxLiveTime());
		ratimelimitConfig.setRefreshCfg(ratelimit.refreshCfg());
		return ratimelimitConfig;
	}
}