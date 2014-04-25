package com.mpmp.freya.mobile.provider;


public class Item {

	private long id;
	private String title;
	private String descr;
	private long startTime;
	private long endTime;
	private String location;
	private String url;

	public Item() {
	}

	public Item(long id, String title, String descr, long startTime,
			long endTime, String location, String url) {
		super();
		this.id = id;
		this.title = title;
		this.descr = descr;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", descr=" + descr
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", location=" + location + ", url=" + url + "]";
	}


}
