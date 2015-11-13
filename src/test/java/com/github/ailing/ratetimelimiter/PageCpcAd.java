/**
 * Copyright NewHeight Co.,Ltd. (C), 2011-2016
 * File Name: PageCpcAd.java
 * Encoding: UTF-8
 * Date: Aug 29, 2013
 * History: 
 */
package com.github.ailing.ratetimelimiter;

import java.io.Serializable;

/**
 * 这个类仅仅只用于对外接口使用，内部不使用该接口
 * @author Wayne Wan(bestirwiny@163.com)
 * @version Revision: 1.00 Date: Aug 29, 2013
 */
public class PageCpcAd implements Serializable {

	private static final long serialVersionUID = -2202130106452332064L;

	// 广告计划ID
	private long adPlanId;

	// 广告创意ID
	private long adIdeasId;

	// 广告产品ID
	private long adProductId;

	// 素材名称，即显示文字
	private String ideaTitle;

	// 前台样式
	private int style;

	// 商家ID
	private long merchantId;

	// 扩展文字
	private String extendContent;

	// 分类ID(类目id)
	private long categoryId;

	// 普屏图片地址
	private String commonScreenPicUrl;

	// 宽屏图片地址
	private String wideScreenPicUrl;

	// 用户类型；1为商家，2为供应商
	private int userType = 1;

	// 页面链接
	private String pageUrl;
	
	//页面广告图片url
	private String pagePicUrl;
	
	//广告位id
	private long adRegId;
	/**
	 * 广告ref
	 * 格式如下：广告位ID_广告计划ID_广告ID_广告创意ID_类目ID_商品ID_省份ID_商家ID_广告排名INDEX
	 */
	private String ref;
	
	/**
	 * tc = ad.算法id.广告类型.广告id-素材id/创意id
	 */
	private String tc;
	
	/**
	 * tc_ext json格式，里面放的是ref里面的属性值对,如：{"adPlanId":planId, "adIdearId":ideaId}
	 */
	private String tc_ext;
	
	public String getPagePicUrl() {
		return pagePicUrl;
	}

	public void setPagePicUrl(String pagePicUrl) {
		this.pagePicUrl = pagePicUrl;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public long getAdPlanId() {
		return adPlanId;
	}

	public void setAdPlanId(long adPlanId) {
		this.adPlanId = adPlanId;
	}

	public long getAdIdeasId() {
		return adIdeasId;
	}

	public void setAdIdeasId(long adIdeasId) {
		this.adIdeasId = adIdeasId;
	}

	public long getAdProductId() {
		return adProductId;
	}
	public void setAdProductId(long adProductId) {
		this.adProductId = adProductId;
	}
	
	public String getIdeaTitle() {
		return ideaTitle;
	}

	public void setIdeaTitle(String ideaTitle) {
		this.ideaTitle = ideaTitle;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	public String getExtendContent() {
		return extendContent;
	}

	public void setExtendContent(String extendContent) {
		this.extendContent = extendContent;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCommonScreenPicUrl() {
		return commonScreenPicUrl;
	}

	public void setCommonScreenPicUrl(String commonScreenPicUrl) {
		this.commonScreenPicUrl = commonScreenPicUrl;
	}

	public String getWideScreenPicUrl() {
		return wideScreenPicUrl;
	}

	public void setWideScreenPicUrl(String wideScreenPicUrl) {
		this.wideScreenPicUrl = wideScreenPicUrl;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * @return the adRegId
	 */
	public long getAdRegId() {
		return adRegId;
	}

	/**
	 * @param adRegId the adRegId to set
	 */
	public void setAdRegId(long adRegId) {
		this.adRegId = adRegId;
	}
	
	public void setTc(String tc) {
		this.tc = tc;
	}
	
	public String getTc() {
		return tc;
	}
	
	public void setTc_ext(String tc_ext) {
		this.tc_ext = tc_ext;
	}
	
	public String getTc_ext() {
		return tc_ext;
	}
}
