package com.xmjj.jujianglibrary.http.entity;

import com.google.gson.JsonElement;

/**
 * 功能描述：
 * Created by wzw
 * 2017/8/18
 */

public class BaseRespond {
	private String msg;
	private String status;
	private JsonElement result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JsonElement getResult() {
		return result;
	}

	public void setResult(JsonElement result) {
		this.result = result;
	}
}
