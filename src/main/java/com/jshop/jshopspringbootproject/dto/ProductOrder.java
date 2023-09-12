package com.jshop.jshopspringbootproject.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductOrder 
{
	@Id
	private long orderId;
	@UpdateTimestamp
	@Column(name = "bookingDate")
	private LocalDateTime bookingDateTime;
	private LocalDate deliverDate;
	private int quantity;
	private double price;
	private String deliveredAddress;

	@OneToOne
	@JoinColumn(name = "productid")
	private Product product;
	
	@OneToOne
	@JoinColumn(name = "userid")
	private User user;
}
