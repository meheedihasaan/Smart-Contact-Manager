package com.contact.manager.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;
	
	@NotBlank(message="Name field should not be empty.")
	@Size(min = 3, max = 32, message="Name should be between 3 to 32 characters.")
	private String name;
	
	@Size(min = 3, max = 32, message="Name should be between 3 to 32 characters.")
	private String nickname;

	private String work;
	
	@NotBlank(message="Phone number should not be empty.")
	private String phone;
	

	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email!")
	private String email;
	
	private String address;
	
	@Column(length = 10000)
	@Size(max = 10000, message="Description field should be between 1 to 1000 characters.")
	private String description;
	
	private String imageUrl;
	
	
	@ManyToOne
	@JsonIgnore
	private User user;

	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.cid == ((Contact)obj).getCid();
	}

//	@Override
//	public String toString() {
//		return "Contact [cid=" + cid + ", name=" + name + ", nickname=" + nickname + ", work=" + work + ", phone="
//				+ phone + ", email=" + email + ", address=" + address + ", description=" + description + ", imageUrl="
//				+ imageUrl + ", user=" + user + "]";
//	}
	
	
	
}
