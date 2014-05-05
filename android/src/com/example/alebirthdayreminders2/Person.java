package com.example.alebirthdayreminders2;

import java.util.Date;

import org.apache.http.impl.cookie.DateUtils;

public class Person implements Comparable<Person> {

	private int id = -1;
	private Date birthday;
	private String name;
	private String email;
	private String image;
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param email - may be an empty string
	 * @param birthday
	 * @param image - location for image
	 */
	public Person(int id, String name, String email, Date birthday, String image) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		this.image = image;
	}
	
	/**
	 * 
	 * @param name
	 * @param email - may be an empty string
	 * @param birthday
	 * @param image - location for image
	 */
	public Person(String name, String email, Date birthday, String image) {
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		this.image = image;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getImageLocation() {
		return this.image;
	}
	
	public void setImageLocation(String image) {
		this.image = image;
	}
	
	/**
	 * Currently just uses the user's default formatting for dates
	 * @return
	 */
	public String getFormattedBirthday() {
		return DateUtils.formatDate(this.birthday);
	}
	
	public int compareTo(Person other) {
		return this.getBirthday().compareTo(other.getBirthday());
	}
}
