package com.jshop.jshopspringbootproject.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adminid")
	private int adminId;
	@Column(name = "adminname")
	private String adminName;
	@Column(name = "adminemail")
	private String adminEmail;
	@Column(name = "adminpassword")
	private String adminPassword;
	
	@OneToMany(mappedBy = "admin")
	@JsonIgnore
	private List<ProductOwner> owners;
}


