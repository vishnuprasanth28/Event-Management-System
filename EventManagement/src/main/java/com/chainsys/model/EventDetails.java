package com.chainsys.model;

public class EventDetails {
	 
	@Override
	public String toString() {
		return "EventDetails [venueId=" + venueId + ", venuePrice=" + venuePrice + ", eventType=" + eventType
				+ ", dateString=" + dateString + ", photoGraphyId=" + photoGraphyId + ", photographyPrice="
				+ photographyPrice + ", cateringId=" + cateringId + ", cateringPrice=" + cateringPrice
				+ ", estimatedPrice=" + estimatedPrice + "]";
	}
	public int getVenueId() {
		return venueId;
	}
	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}
	public int getVenuePrice() {
		return venuePrice;
	}
	public void setVenuePrice(int venuePrice) {
		this.venuePrice = venuePrice;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public int getPhotoGraphyId() {
		return photoGraphyId;
	}
	public void setPhotoGraphyId(int photoGraphyId) {
		this.photoGraphyId = photoGraphyId;
	}
	public int getPhotographyPrice() {
		return photographyPrice;
	}
	public void setPhotographyPrice(int photographyPrice) {
		this.photographyPrice = photographyPrice;
	}
	public int getCateringId() {
		return cateringId;
	}
	public void setCateringId(int cateringId) {
		this.cateringId = cateringId;
	}
	public int getCateringPrice() {
		return cateringPrice;
	}
	public void setCateringPrice(int cateringPrice) {
		this.cateringPrice = cateringPrice;
	}
	public double getEstimatedPrice() {
		return estimatedPrice;
	}
	public void setEstimatedPrice(double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}
	private int venueId;  
	private int venuePrice;
	private String eventType;
	private String dateString;
	private int photoGraphyId;
	
	private int photographyPrice;
	private int cateringId;
	private int cateringPrice;
	private double estimatedPrice;
	
}
