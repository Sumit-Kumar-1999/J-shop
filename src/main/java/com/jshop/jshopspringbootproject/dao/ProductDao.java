package com.jshop.jshopspringbootproject.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jshop.jshopspringbootproject.dto.Product;
import com.jshop.jshopspringbootproject.repository.ProductRepository;

@Repository
public class ProductDao {

	@Autowired
	private ProductRepository productRepository;
	
	/*
	 * save product
	 */
	public Product saveProductDao(Product product)
	{
		return productRepository.save(product);
	}
	/*
	 * getProductDataById
	 */
	public Product getProductByIdDao(int productId)
	{
		Optional<Product> optional = productRepository.findById(productId);
		
		if(optional.isPresent())
		{
			return optional.get();
		}else {
			return null;
		}
	}
}

