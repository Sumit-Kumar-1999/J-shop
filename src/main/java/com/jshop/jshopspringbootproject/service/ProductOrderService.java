package com.jshop.jshopspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jshop.jshopspringbootproject.dto.ProductOrder;

public class ProductOrderService {

	@Autowired
	private ProductOrderService orderService;
	
	public ProductOrder saveProductOrder(int productId, int quantity, String address)
	{
		
	}
}
