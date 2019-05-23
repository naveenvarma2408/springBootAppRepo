package com.siemens.camerapoc.beans;

import java.util.List;

public class CameraImageBean {

	private int count;
	private String timeStamp;
	private List<UserDetailsBean> users;

	public List<UserDetailsBean> getUsers() {
		return users;
	}

	public void setUsers(List<UserDetailsBean> users) {
		this.users = users;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
