package com.roca12.apolobot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Lessondatetime")
public class LessonDateTime {

	@Id
	private String id;
	private int month;
	private int dayOfWeek;
	private int hour;
	private int minute;
	private int second;

	public LessonDateTime() {

	}

	public LessonDateTime(String id, int month, int dayOfWeek, int hour, int minute, int second) {
		super();
		this.id = id;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "LessonDateTime [id=" + id + ", month=" + month + ", dayOfWeek=" + dayOfWeek + ", hour=" + hour
				+ ", minute=" + minute + ", second=" + second + "]";
	}

}