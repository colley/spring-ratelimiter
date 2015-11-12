/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */

package com.github.ailing.ratimelimiter;

import org.springframework.stereotype.Service;

import com.github.ailing.ratetimelimiter.RateTimeLimit;

/**
 *
 * @FileName  RateLimiterMock.java
 * @Date  15-11-11 下午5:58 
 * @author mayuanchao
 * @version 1.0
 */
@Service("rateLimiterMock")
public class RateLimiterMock {
	
	@RateTimeLimit(serviceName = "test", limitRate = false,refreshCfg=false,permits = 0)
	public void rateLimitTest(String param) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		System.err.println(param);

		// throw new RuntimeException("ee");
	}

	@RateTimeLimit(serviceName = "test1", limitRate = true, intervalTime = 3,refreshCfg=false)
	public void rateLimitTest1(String param) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		System.err.println(param);

		// throw new RuntimeException("ee");
	}

	@RateTimeLimit(serviceName = "test2", limitRate = true, permits = 1000)
	public void rateLimitTest2(String param) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		System.err.println(param);

		// throw new RuntimeException("ee");
	}
}