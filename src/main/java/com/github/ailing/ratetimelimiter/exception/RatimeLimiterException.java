/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: RatimeLimiterException.java 1492040 2015-11-12 09:02:18Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter.exception;


/**
 *
 * @FileName  RatimeLimiterException.java
 * @Date  15-11-9 上午9:39
 * @author mayuanchao
 * @version 1.0
 */
@SuppressWarnings("serial")
public class RatimeLimiterException extends RuntimeException {
	public RatimeLimiterException() {
		super();
	}

	public RatimeLimiterException(String message) {
		super(message);
	}

	public RatimeLimiterException(String message, Throwable cause) {
		super(message, cause);
	}

	public RatimeLimiterException(Throwable cause) {
		super(cause);
	}
}