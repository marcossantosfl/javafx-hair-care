package model;

import java.io.Serializable;

public class DateTimeProvider implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int idCustumer;
	private int idProvider;
	private int Year;
	private int Month;
	private int Day;
	private int Hour;
	private int Minute;
	private int Available;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCustumer() {
		return idCustumer;
	}
	public void setIdCustumer(int idCustumer) {
		this.idCustumer = idCustumer;
	}
	public int getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(int idProvider) {
		this.idProvider = idProvider;
	}
	public int getYear() {
		return Year;
	}
	public void setYear(int year) {
		Year = year;
	}
	public int getMonth() {
		return Month;
	}
	public void setMonth(int month) {
		Month = month;
	}
	public int getDay() {
		return Day;
	}
	public void setDay(int day) {
		Day = day;
	}
	public int getHour() {
		return Hour;
	}
	public void setHour(int hour) {
		Hour = hour;
	}
	public int getMinute() {
		return Minute;
	}
	public void setMinute(int minute) {
		Minute = minute;
	}
	public int getAvailable() {
		return Available;
	}
	public void setAvailable(int available) {
		Available = available;
	}
}
