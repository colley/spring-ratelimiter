/*
 * Copyright (c) 2015
 * All rights reserved.
 * $Id$
 */
package com.ailing.ratetimelimiter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @FileName  BaseTest.java
 * @Date  15-11-6 下午3:31
 * @author mayuanchao
 * @version 1.0
 */
public class BaseTest {
	private static ApplicationContext context;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/spring.xml" });

			Logger log = Logger.getLogger("com.ailing");
			log.setLevel(Level.DEBUG);

			Logger logRoot = Logger.getRootLogger();
			logRoot.setLevel(Level.WARN);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
	}

	protected static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	protected static <T> T getBean(Class<T> clz) {
		return context.getBean(clz);
	}
}