/*
 * Copyright (c) 2015 NewHeight, Inc.
 * All rights reserved.
 * $Id: CommonAdResult.java 1487805 2015-11-05 02:57:00Z mayuanchao $
 */
package com.github.ailing.ratetimelimiter;

/**
 *
 * @FileName  BaseAdResult.java
 * @Date  15-11-4 上午11:12
 * @author mayuanchao@yhd.com
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CommonAdResult implements java.io.Serializable {

	private String messageType; //消息类型
	private String message; //消息信息

	/**
	 * @param messageType
	 * @param message
	 */
	public CommonAdResult() {

	}

	/**
	 * @param messageType
	 * @param message
	 */
	public CommonAdResult(String messageType,String message) {

		this.messageType = messageType;
		this.message = message;
	}

	public String getMessageType() {

		return messageType;
	}

	public void setMessageType(String messageType) {

		this.messageType = messageType;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}
	
	public boolean isSucc(){
	    return "1".equals(messageType);
	}
}