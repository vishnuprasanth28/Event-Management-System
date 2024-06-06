package com.chainsys.model;

import java.util.Date;

public class Event {
	
	public Event() {
		
	}
	
    public Event(int eventId, int userId, String eventName, Date startDate, Date endDate, String startTime,
			String endTime) {
		super();
		this.eventId = eventId;
		this.userId = userId;
		this.eventName = eventName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", userId=" + userId + ", eventName=" + eventName + ", startDate="
				+ startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	private int eventId;
    private int userId;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
}
