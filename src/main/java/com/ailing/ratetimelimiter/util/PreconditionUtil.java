/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: PreconditionUtil.java 1492040 2015-11-12 09:02:18Z mayuanchao $
 */
package com.ailing.ratetimelimiter.util;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import com.ailing.ratetimelimiter.exception.RatimeLimiterException;
import com.hs.common.utils.GetterUtil;

/**
 *
 * @FileName  RatimeVerify.java
 * @Date  15-11-6 下午12:54
 * @author mayuanchao
 * @version 1.0
 */
public final class PreconditionUtil {
	public static void verify(boolean expression) {
		if (!expression) {
			throw new RatimeLimiterException();
		}
	}

	public static void check(boolean expression, String errorMessageTemplate,
		Object... errorMessageArgs) {
		if (!expression) {
			throw new RatimeLimiterException(format(errorMessageTemplate, errorMessageArgs));
		}
	}

	public static <T> T checkNotNull(T reference) {
		return checkNotNull(reference, "expected a non-null reference");
	}

	public static <T> T checkNotNull(T reference, String errorMessageTemplate,
		Object... errorMessageArgs) {
		check(reference != null, errorMessageTemplate, errorMessageArgs);

		return reference;
	}

	public static void checkArgument(boolean expression, String errorMessageTemplate,
		Object... errorMessageArgs) {
		if (!expression) {
			throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
		}
	}

	public static String format(String errorMessageTemplate, Object... errorMessageArgs) {
		if (StringUtils.isNotEmpty(errorMessageTemplate)) {
			return MessageFormat.format(errorMessageTemplate, errorMessageArgs);
		}

		return errorMessageTemplate;
	}

	public static Exception throwCause(Exception e, boolean combineStackTraces)
		throws Exception {
		Throwable cause = e.getCause();

		if (cause == null) {
			throw e;
		}

		if (combineStackTraces) {
			StackTraceElement[] combined = GetterUtil.concat(cause.getStackTrace(),
					e.getStackTrace(), StackTraceElement.class);
			cause.setStackTrace(combined);
		}

		if (cause instanceof Exception) {
			throw (Exception)cause;
		}

		if (cause instanceof Error) {
			throw (Error)cause;
		}

		// The cause is a weird kind of 	, so throw the outer exception.
		throw e;
	}

	public static String getExceptionStackTrace(Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append(e);

		StackTraceElement[] trace = e.getStackTrace();

		for (int i = 0; i < trace.length; i++) {
			sb.append("\n " + trace[i]);
		}

		Throwable ourCause = e.getCause();

		if (ourCause != null) {
			getCauseStackTrace(sb, ourCause);
		}

		return sb.toString();
	}

	private static void getCauseStackTrace(StringBuffer sb, Throwable cause) {
		if (null != cause) {
			StackTraceElement[] trace = cause.getStackTrace();

			for (int i = 0; i < trace.length; i++) {
				sb.append("\n " + trace[i]);
			}

			Throwable ourCause = cause.getCause();

			if (ourCause != null) {
				getCauseStackTrace(sb, ourCause);
			}
		}
	}

	private PreconditionUtil() {
	}
}