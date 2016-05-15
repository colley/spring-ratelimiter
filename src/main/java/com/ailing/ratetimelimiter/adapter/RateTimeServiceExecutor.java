/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeServiceExecutor.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.ailing.ratetimelimiter.adapter;

/**
 *超时机和限流措施 统管理者
 * @FileName  RatimeServiceExecutor.java
 * @Date  15-11-10 下午2:31
 * @author mayuanchao
 * @version 1.0
 */
public interface RateTimeServiceExecutor {
	<T> T execute(String serviceName, RateTimeServiceCallBack<T> callBack)
		throws Exception;
}