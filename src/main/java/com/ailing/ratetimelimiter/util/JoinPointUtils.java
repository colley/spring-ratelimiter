/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */

package com.ailing.ratetimelimiter.util;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 *
 * @FileName  JoinPointUtils.java
 * @Date  15-11-12 下午5:57 
 * @author mayuanchao
 * @version 1.0
 */
public class JoinPointUtils {
	public static Object process(ProceedingJoinPoint point, Object[] args)
		throws Exception {
		try {
			return point.proceed(args);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}

	public static Object process(ProceedingJoinPoint point)
		throws Exception {
		try {
			return point.proceed();
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
}