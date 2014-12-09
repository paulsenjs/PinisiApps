package com.pinisielektra.apps.object;

import java.io.Serializable;

public class MerchantObj implements Serializable {
	private static final long serialVersionUID = 1L;
	private String merchantId;
	private String merchantName;
	private String address;
	private String creator;
	private String dateCreated;
	
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}