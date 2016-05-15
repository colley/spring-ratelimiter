/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.ailing.ratetimelimiter;

/**
 *@FileName  CommonAdResult.java
 *@Date  16-5-15 下午2:22
 *@author Colley
 *@version 1.0
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
    public CommonAdResult(String messageType, String message) {
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

    public boolean isSucc() {
        return "1".equals(messageType);
    }
}
