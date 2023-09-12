package com.jshop.jshopspringbootproject.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.jshop.jshopspringbootproject.dto.Admin;
import com.jshop.jshopspringbootproject.dto.Product;
import com.jshop.jshopspringbootproject.dto.ProductOwner;
import com.jshop.jshopspringbootproject.repository.AdminRepository;
import com.jshop.jshopspringbootproject.repository.ProductOwnerRepository;
import com.jshop.jshopspringbootproject.repository.ProductRepository;


/**
 * @author Sumit
 */
@Repository
public class AdminDao {

	int adminId=0;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductOwnerDao ownerDao;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductOwnerRepository ownerRepository;
	/*
	 * signup code for admin saveMethod
	 */
	public Admin saveAdminDao(Admin admin) {
		
		return adminRepository.save(admin);
	}
	
	/*
	 * login with admin
	 */
	public Admin loginWithAdminDao(String email) {
		
		Admin admin = adminRepository.findByAdminEmail(email);
		
		if(admin!=null) {
			adminId = admin.getAdminId();
		}
		return admin;
	}
	
	/*
	 * getAllProductOwnerAdminDao
	 */
	public List<ProductOwner> getAllProductOwnerAdminDao() {
		
		return ownerDao.getAllProductOwnerDao();
	}
	/*
	 * getProductOwnerById
	 */
	public ProductOwner getProductOwnerByIdAdminDao(int productOwnerId) {
		
		return ownerDao.getProductOwnerById(productOwnerId);
	}
	/*
	 * verify product owner from no to yes and Unverified from yes no
	 */
	public ProductOwner verifyProductOwner(int productOwner) {
		
		ProductOwner productOwner2 = getProductOwnerByIdAdminDao(productOwner);
		
		/*
		 * 
		 */
		Optional<Admin> optional = adminRepository.findById(adminId);
		Admin admin = null;
		if(optional.isPresent()) {
			admin = optional.get();
		}
		/*
		 * 
		 */
		if(productOwner2!=null) {
			
			if(productOwner2.getAdminVerify().equalsIgnoreCase("no")) {
				productOwner2.setAdminVerify("yes");
				productOwner2.setAdmin(admin);
			}else {
				admin = null;
				productOwner2.setAdminVerify("no");
				productOwner2.setAdmin(admin);
			}
			
			ownerRepository.save(productOwner2);
		}
		return productOwner2;
	}
	
	/*
	 * verify product details by admin 
	 */
	public Product verifyProductDetailsByAdmin(int productId)
	{
		Product product = productDao.getProductByIdDao(productId);
		
		if(product!=null)
		{
			if(product.getProductVerified().equalsIgnoreCase("no"))
			{
				product.setProductVerified("yes");
			}
			else {
				product.setProductVerified("no");
			}
		}
		productRepository.save(product);
		return product;
	}
}
