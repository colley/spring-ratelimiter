/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.ailing.ratetimelimiter;

import com.ailing.ratetimelimiter.adapter.RateTimeServiceCallBack;
import com.ailing.ratetimelimiter.adapter.executor.RateTimeServiceExecutorAdapter;
import com.ailing.ratetimelimiter.config.AspectRateTime;
import com.ailing.ratetimelimiter.config.AspectRateTimeProvider;
import com.ailing.ratetimelimiter.config.RateTimeConfigurerFactory;
import com.ailing.ratetimelimiter.config.RateTimelimitConfigurerProvider;
import com.ailing.ratetimelimiter.util.JoinPointUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 *注解类
 * @FileName  AbstractRateLimitAspect.java
 * @Date  15-10-14 上午9:45
 * @author MColley
 * @version $Revision: 1492119 $ $Date: 2015-11-12 17:52:20 +0800 (Thu, 12 Nov 2015) $
 */
@Aspect
public class RateTimeLimiterAspect implements ApplicationContextAware {
    private RateTimeServiceExecutorAdapter rateTimeServiceExecutorAdapter;
    @Autowired
    private RateTimeCreatingBeanFactory rateTimeCreatingBeanFactory;
    private ApplicationContext applicationContext;

    @Around(value = "@annotation(RateTimeLimit)", argNames = "RateTimeLimit")
    public Object aroundProcess(final ProceedingJoinPoint point, final RateTimeLimit ratelimit)
        throws Exception {
        //获取configurer提供者和生产者
        RateTimeConfigurerFactory configurerFactory = rateTimeCreatingBeanFactory.getConfigurerFactory();
        RateTimelimitConfigurerProvider configurerProvider = rateTimeCreatingBeanFactory.getConfigurerProvider(ratelimit.configurer());
        configurerFactory.config(ratelimit.serviceName(), configurerProvider,
            new AspectRateTimeProvider() {
                @Override
                public AspectRateTime create(String serviceName) {
                    return builder(ratelimit);
                }
            }, null);

        return rateTimeServiceExecutorAdapter.execute(ratelimit.serviceName(), new AspectRatimeServiceCallBack(point));
    }

    public class AspectRatimeServiceCallBack implements RateTimeServiceCallBack<Object> {
        private ProceedingJoinPoint point;

        public AspectRatimeServiceCallBack(ProceedingJoinPoint point) {
            this.point = point;
        }

        @Override
        public Object invoker() throws Exception {
            return JoinPointUtils.process(point);
        }
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
        ratimelimitConfig.setConfigurer(ratelimit.configurer());
        return ratimelimitConfig;
    }

    @Override
    public void setApplicationContext(ApplicationContext paramApplicationContext)
        throws BeansException {
        this.applicationContext = paramApplicationContext;
        rateTimeServiceExecutorAdapter = applicationContext.getBean(RateTimeServiceExecutorAdapter.class);
    }
}
