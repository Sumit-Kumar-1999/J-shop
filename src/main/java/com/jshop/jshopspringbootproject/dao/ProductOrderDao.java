package com.jshop.jshopspringbootproject.dao;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jshop.jshopspringbootproject.dto.Product;
import com.jshop.jshopspringbootproject.dto.ProductOrder;


@Repository
public class ProductOrderDao {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductOrder productOrder;
	
	/*
	 * saveProduct
	 */
	public ProductOrder saveProductOrder(int productId, int quantity, String address)
	{
		Product product = productDao.getProductByIdDao(productId);
		
		if(product!=null)
		{
			/*
			 * total Price calculation
			 */
			double totalPrice = product.getProductPrice()*quantity;
			
			/*
			 * random order id
			 */
			long orderId = (long)Math.floor(Math.random()*9000000000L) + 1000000000L;
			
			/*
			 * deliverd date
			 */
			LocalDate localDate = LocalDate.now();
			localDate = localDate.plusDays(3);
			
			productOrder.setOrderId(orderId);
			productOrder.setQuantity(quantity);
			productOrder.setProduct(product);
			productOrder.setPrice(totalPrice);
			productOrder.setDeliveredAddress(address);
			product.setProductQuantity(product.getProductQuantity()-quantity);
			
			productOrder.setDeliverDate(localDate);
		}
		return null;
	}
}
