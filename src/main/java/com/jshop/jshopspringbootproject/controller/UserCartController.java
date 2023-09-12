package com.jshop.jshopspringbootproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jshop.jshopspringbootproject.dto.UserCart;
import com.jshop.jshopspringbootproject.responsestructure.ResponseStructure;
import com.jshop.jshopspringbootproject.service.UserCartService;

@RestController
@RequestMapping("/userCart")
public class UserCartController {

	@Autowired
	private UserCartService cartService;
	
	/*
	 * add product in user cart
	 */
	@PostMapping("/userCartInsert/{productQuantity}/{productId}")
	public ResponseStructure<UserCart> addProductInUserCartController(@PathVariable int productQuantity, @PathVariable int productId)
	{
		return cartService.addProductInUserCartService(productQuantity, productId);
	}
}
