package com.doArtShow.dto;

public class PersonalRequestDto {
	private int reqNo;
	private String name;
	private String email;
	private String reqMessage;
	private String respMessage;
	private String reqTime;
	private String respTime;
	private String reqFlag;
	
	public PersonalRequestDto() {}

	public PersonalRequestDto(int reqNo, String name, String email, String reqMessage, String respMessage, String reqTime, String respTime, String reqFlag) {
		this.reqNo = reqNo;
		this.name = name;
		this.email = email;
		this.reqMessage = reqMessage;
		this.respMessage = respMessage;
		this.reqTime = reqTime;
		this.respTime = respTime;
		this.reqFlag = reqFlag;
	}

	public int getReqNo() {
		return reqNo;
	}

	public void setReqNo(int reqNo) {
		this.reqNo = reqNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReqMessage() {
		return reqMessage;
	}

	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getRespTime() {
		return respTime;
	}

	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}

	public String getReqFlag() {
		return reqFlag;
	}

	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag;
	}
}
