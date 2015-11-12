/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateTimeServiceCallBack.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.adapter;

/**
 *
 * @FileName  RatimeServiceCallBack.java
 * @Date  15-11-11
 * @author mayuanchao
 * @version 1.0
 */
public interface RateTimeServiceCallBack<T> {
	public T invoker() throws Exception;
}