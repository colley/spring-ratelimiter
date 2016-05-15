/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id: CronExprUtils.java 1492040 2015-11-12 09:02:18Z mayuanchao $
 */
package com.ailing.ratetimelimiter.util;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronExpression;

/**
 *
 * @FileName  ParserUtils.java
 * @Date  15-11-6 下午2:30
 * @author mayuanchao
 * @version 1.0
 */
public final class CronExprUtils {
	private CronExprUtils() {
	}

	private static final Logger logger = Logger.getLogger(CronExprUtils.class);

	public static CronExpression parseCronExpr(String cronExpression) {
		CronExpression cronExpr = null;

		if (StringUtils.isNotEmpty(cronExpression)) {
			try {
				cronExpr = new CronExpression(cronExpression);
			} catch (ParseException e) {
				logger.warn("create cronExpression error", e);
			}
		}

		return cronExpr;
	}
}