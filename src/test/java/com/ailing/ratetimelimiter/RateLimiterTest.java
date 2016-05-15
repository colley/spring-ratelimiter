/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.ailing.ratetimelimiter;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.databene.contiperf.junit.ParallelRunner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;


/**
 *
 * @FileName  RateLimiterTest.java
 * @Date  15-11-5 下午3:20
 * @author mayuanchao
 * @version 1.0
 */
@RunWith(ParallelRunner.class)
public class RateLimiterTest extends BaseTest {
    private RateLimiterMock rateLimiterMock;
    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @Before
    public void setup() {
        rateLimiterMock = (RateLimiterMock) getBean("rateLimiterMock");
    }

    @PerfTest(invocations = 1000, threads = 10)
    @Test
    public void testRate() {
        rateLimiterMock.rateLimitTest("ok~~~~~~~~~~");

        rateLimiterMock.rateLimitTest1("ok1~~~~~~~~~~");
        //rateLimiterMock.rateLimitTest1("ok1~~~~~~~~~~");
        //testRate1();
    }

    public void testRate1() {
        rateLimiterMock.rateLimitTest1("ok1~~~~~~~~~~");
        rateLimiterMock.rateLimitTest2("ok2~~~~~~~~~~");
    }
}
