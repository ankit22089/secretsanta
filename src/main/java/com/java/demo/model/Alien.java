package com.java.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Alien {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int aid;
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotBlank(message = "Address is mandatory")
	private String address;
	@NotBlank(message = "Mobile Number is mandatory")
	private String mobileNo;
	@NotBlank(message = "Email Id is mandatory")
	private String emailId;
	@NotBlank(message = "Gift is mandatory")
	private String gift;
	@NotBlank(message = "Link for gift is mandatory")
	private String giftLink1;
	@NotBlank(message = "Link for gift is mandatory")
	private String giftLink2;
	@NotBlank(message = "Description is mandatory")
	private String description;
	@NotNull
	private String parent;
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public String getGiftLink1() {
		return giftLink1;
	}
	public void setGiftLink1(String giftLink1) {
		this.giftLink1 = giftLink1;
	}
	public String getGiftLink2() {
		return giftLink2;
	}
	public void setGiftLink2(String giftLink2) {
		this.giftLink2 = giftLink2;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Alien [aid=" + aid + ", name=" + name + ", address=" + address + ", mobileNo=" + mobileNo + ", emailId="
				+ emailId + ", gift=" + gift + ", giftLink1=" + giftLink1 + ", giftLink2=" + giftLink2
				+ ", description=" + description + ", parent=" + parent + "]";
	}
	
	
	
}
