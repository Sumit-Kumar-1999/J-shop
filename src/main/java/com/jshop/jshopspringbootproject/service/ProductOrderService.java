package com.jshop.jshopspringbootproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jshop.jshopspringbootproject.dao.ProductDao;
import com.jshop.jshopspringbootproject.dao.ProductOrderDao;
import com.jshop.jshopspringbootproject.dto.Product;
import com.jshop.jshopspringbootproject.dto.ProductOrder;
import com.jshop.jshopspringbootproject.responsestructure.ResponseStructure;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductOrderService 
{
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private ProductOrderDao orderDao;
	
	@Autowired
	private ResponseStructure<ProductOrder> responseStructure;
	
	public ResponseStructure<ProductOrder> saveProductOrderService(int productId, int quantity, String address)
	{
		Product product = productDao.getProductByIdDao(productId);
		
		if((product!=null)&&(httpSession.getAttribute("password")!=null))
		{
			if(product.getProductVerified().equalsIgnoreCase("yes"))
			{
				if(quantity<=product.getProductQuantity())
				{
					ProductOrder productOrder = orderDao.saveProductOrder(productId, quantity, address);
					responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
					responseStructure.setStatusMsg("Product order success");
					responseStructure.setDescription("Your order will delivere in 3 days");
					responseStructure.setData(productOrder);
				}
				else
				{
					responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
					responseStructure.setStatusMsg("Product is not available");
					responseStructure.setDescription("Stack khtam ho gya");
					responseStructure.setData(null);
				}
			}
			else
			{
				responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				responseStructure.setStatusMsg("Product is not verified");
				responseStructure.setDescription("Wait for the verification to purchase");
				responseStructure.setData(null);
			}
		}
		else
		{
			responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure.setStatusMsg("Please! login first with user");
			responseStructure.setDescription("Kripya pahle login kare");
			responseStructure.setData(null);
		}
		return responseStructure;
	}
}
