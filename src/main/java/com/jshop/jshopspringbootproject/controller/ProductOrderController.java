package com.jshop.jshopspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jshop.jshopspringbootproject.dto.ProductOrder;
import com.jshop.jshopspringbootproject.responsestructure.ResponseStructure;
import com.jshop.jshopspringbootproject.service.ProductOrderService;


@RestController
@RequestMapping("/order")
public class ProductOrderController {

	@Autowired
	private ProductOrderService orderService;
	
	@PutMapping("/productOrer/{productId}/{quantity}/{address}")
	public ResponseStructure<ProductOrder> saveProductOrderController(@PathVariable int productId, @PathVariable int quantity,@PathVariable String address)
	{
		return orderService.saveProductOrderService(productId, quantity, address);
	}
}
