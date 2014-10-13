package com.wordpress.felipenipo.entities;

public class Customer {
	private long id;
	private String name;
	private String email;
	private String device_model;
	private String password;
	private String url;
	
	public Customer() {
		super();
	}

	public Customer(long id, String name, String email, String device_model,
			String password, String url) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.device_model = device_model;
		this.password = password;
		this.url = url;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDevice_model() {
		return device_model;
	}
	
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
