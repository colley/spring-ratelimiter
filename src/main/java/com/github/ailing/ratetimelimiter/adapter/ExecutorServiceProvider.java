/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: ExecutorServiceProvider.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */

package com.github.ailing.ratetimelimiter.adapter;

import java.util.concurrent.ExecutorService;

/**
 *ExecutorService 服务提供者
 * @FileName  ExecutorServiceProvider.java
 * @Date  15-11-11 下午5:58 
 * @author mayuanchao
 * @version 1.0
 */
public interface ExecutorServiceProvider {
	ExecutorService getExecutor();
}