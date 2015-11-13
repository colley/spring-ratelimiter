/**
 * 
 */
package com.github.ailing.ratetimelimiter;

import java.util.List;

/**
 * @author wanyi
 *
 */
public class AdResult <T> extends CommonAdResult{

	private static final long serialVersionUID = 1L;
	private List<T> adList;                //返回结果数据
	
	/**
	 * @param messageType
	 * @param message
	 */
	public AdResult() {
	    super();
	}
	
	/**
	 * @param messageType
	 * @param message
	 */
	public AdResult(String messageType, String message) {
		super(messageType, message);
	}

	/**
	 * @param messageType
	 * @param message
	 * @param sourceList
	 */
	public AdResult(String messageType, String message, List<T> adList) {
	    this(messageType, message);
		this.adList = adList;
	}
	
	public List<T> getAdList() {
		return adList;
	}
	public void setAdList(List<T> adList) {
		this.adList = adList;
	}
	
}
