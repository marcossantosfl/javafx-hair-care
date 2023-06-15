package model;

import java.io.Serializable;

/*
 * Review class
 */
public class Review implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Variables
	 */
	private int id;
	private int idDate;
	private int idAccountReview;
	private int idProviderReview;
	private int starGiven;
	private String providerName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdDate() {
		return idDate;
	}
	public void setIdDate(int idDate) {
		this.idDate = idDate;
	}
	public int getIdAccountReview() {
		return idAccountReview;
	}
	public void setIdAccountReview(int idAccountReview) {
		this.idAccountReview = idAccountReview;
	}
	public int getIdProviderReview() {
		return idProviderReview;
	}
	public void setIdProviderReview(int idProviderReview) {
		this.idProviderReview = idProviderReview;
	}
	public int getStarGiven() {
		return starGiven;
	}
	public void setStarGiven(int starGiven) {
		this.starGiven = starGiven;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
}
