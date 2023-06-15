package model;

import java.io.Serializable;

/*
 * Admin class
 */
public class Admin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int location;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
}
