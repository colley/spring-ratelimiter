/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RateLimitState.java 1492119 2015-11-12 09:52:20Z mayuanchao $
 */
package com.ailing.ratetimelimiter.config;

/**
 * ACQUIRE=能正常拿到令牌
 * NOT_ACQUIRE=拿不到令牌
 * CLOSED=限流关闭状态
 * @author mayuanchao
 */
public enum RateLimitState {
	ACQUIRE, NOT_ACQUIRE, CLOSED;
}