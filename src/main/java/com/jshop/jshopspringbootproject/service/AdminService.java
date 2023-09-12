package com.jshop.jshopspringbootproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jshop.jshopspringbootproject.dao.AdminDao;
import com.jshop.jshopspringbootproject.dao.ProductDao;
import com.jshop.jshopspringbootproject.dto.Admin;
import com.jshop.jshopspringbootproject.dto.Product;
import com.jshop.jshopspringbootproject.dto.ProductOwner;
import com.jshop.jshopspringbootproject.responsestructure.ResponseStructure;

import jakarta.servlet.http.HttpSession;

/**
 * @author Sumit
 */
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private ResponseStructure<Admin> responseStructure;
	
	@Autowired
	private ResponseStructure<List<ProductOwner>> responseStructure3;
	
	@Autowired
	private ResponseStructure<ProductOwner> responseStructure2;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ResponseStructure<Product> responseStructureProduct;
	
	public String validateEmail(String email) {
		
		Pattern alphabets = Pattern.compile("[a-z]");
		Pattern numbers = Pattern.compile("[0-9]");
		Pattern special = Pattern.compile("[@.]");
		
		Matcher alpha = alphabets.matcher(email);
		Matcher num = numbers.matcher(email);
		Matcher spec = special.matcher(email);
		
		if ((alpha.find()) && (num.find()) && (spec.find())) {
			
			return email;
		} else {
			return null;
		}
	}

	/*
	 * password validation
	 */
	public String validatePassword(String password) {
		
		if (password.length() == 8) {

			Pattern alphabets = Pattern.compile("[a-zA-Z]");
			Pattern numbers = Pattern.compile("[0-9]");
			Pattern special = Pattern.compile("[!@#$*&%]");

			Matcher alpha = alphabets.matcher(password);
			Matcher num = numbers.matcher(password);
			Matcher spec = special.matcher(password);
			if ((alpha.find()) && (num.find()) && (spec.find())) {
				
				return password;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/*
	 * signup code for admin saveMethod
	 */
	public ResponseStructure<Admin> saveAdminService(Admin admin) {

		String password=validatePassword(admin.getAdminPassword());
		String email = validateEmail(admin.getAdminEmail());
		if(email!=null) {
		
			if(password!=null) {
				
				admin.setAdminPassword(password);
				admin.setAdminEmail(email);
				Admin admin1=adminDao.saveAdminDao(admin);
				
				responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
				responseStructure.setStatusMsg("Admin----Registered");
				responseStructure.setDescription("congratulation---Please--Login");
				responseStructure.setData(admin1);
			}else {
				responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				responseStructure.setStatusMsg("Check--Your---Password");
				responseStructure.setDescription("your password length should be 8 character along with 1 lower case,1 upperCase,atleast 1 special Character(!@#$*&%),1 number");
				responseStructure.setData(null);
			}
		
		}else {
			responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure.setStatusMsg("Check--Your---Email");
			responseStructure.setDescription("your email should contain atleast alphabetnumber@hgja.com");
			responseStructure.setData(null);
		}
		return responseStructure;
	}
	
	/*
	 * login with admin
	 */
	public ResponseStructure<Admin> loginWithAdminService(String email,String password) {
		
		Admin admin=adminDao.loginWithAdminDao(email);
		
		if(admin!=null) {
			
			if(admin.getAdminPassword().equals(password)) {
				httpSession.setAttribute("password", admin.getAdminPassword());
				httpSession.setMaxInactiveInterval(120);
				responseStructure.setStatusCode(HttpStatus.ACCEPTED.value());
				responseStructure.setStatusMsg("login-success");
				responseStructure.setDescription("you have logged in....please perform your operation");
			}else {
				responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				responseStructure.setStatusMsg("login credentials failed");
				responseStructure.setDescription("please checck your password...and type correctly");
			}
			
		}else {
			responseStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure.setStatusMsg("login credentials failed");
			responseStructure.setDescription("please checck your email...and type correctly");
		}
		return responseStructure;
	}
	
	/*
	 * getAllProductOwnerAdminDao
	 */
	public ResponseStructure<List<ProductOwner>> getAllProductOwnerAdminService() {
		
		List<ProductOwner> owners = new ArrayList<ProductOwner>();
		
		if(httpSession.getAttribute("password")!=null)
		{
			for (ProductOwner productOwner : adminDao.getAllProductOwnerAdminDao()) {
				
				if(productOwner.getAdminVerify().equalsIgnoreCase("no")) {
					owners.add(productOwner);
				}
			}
			responseStructure3.setStatusCode(HttpStatus.ACCEPTED.value());
			responseStructure3.setStatusMsg("Success");
			responseStructure3.setDescription("Please find your data below");
			responseStructure3.setData(owners);
		}else {
			responseStructure3.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure3.setStatusMsg("Your session is logout");
			responseStructure3.setDescription("Please login again");
			responseStructure3.setData(null);
		}
		return responseStructure3;
	}
	/*
	 * getProductOwnerById
	 */
	public ResponseStructure<ProductOwner> getProductOwnerByIdAdminService(int productOwnerId){
		
		ProductOwner owner = adminDao.getProductOwnerByIdAdminDao(productOwnerId);
		
		if(owner!=null) {
			
			if(httpSession.getAttribute("password")!=null) {
				responseStructure2.setStatusCode(HttpStatus.ACCEPTED.value());
				responseStructure2.setStatusMsg("Success");
				responseStructure2.setDescription("please find your data below....");
				responseStructure2.setData(owner);
			}else {
				responseStructure2.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				responseStructure2.setStatusMsg("Your session is  logout");
				responseStructure2.setDescription("please login again");
				responseStructure2.setData(null);
			}
		}else {
			responseStructure2.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure2.setStatusMsg("ProductOwner id is not present");
			responseStructure2.setDescription("###############################");
			responseStructure2.setData(null);
		}
		return responseStructure2;
	}
	
	/*
	 * verify product owner from no to yes and unverified from yes to no
	 */
	public ResponseStructure<ProductOwner> verifyProductOwnerService(int productOwnerId)
	{
		ProductOwner owner = adminDao.getProductOwnerByIdAdminDao(productOwnerId);
		
		if(owner!=null) {
			
			if(httpSession.getAttribute("password")!=null) {
				
				ProductOwner owner2 = adminDao.verifyProductOwner(productOwnerId);
				responseStructure2.setStatusCode(HttpStatus.ACCEPTED.value());
				responseStructure2.setStatusMsg("Success");
				responseStructure2.setDescription("Product owner is verified successfully");
				responseStructure2.setData(owner2);
			}else {
				responseStructure2.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				responseStructure2.setStatusMsg("Your session is logout");
				responseStructure2.setDescription("Please login again");
				responseStructure2.setData(null);
			}
		}else {
			responseStructure2.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructure2.setStatusMsg("ProductOwner id is not present");
			responseStructure2.setDescription("###################################");
			responseStructure2.setData(null);
		}
		return responseStructure2;
	}
	/*
	 * verify product details by admin
	 */
	public ResponseStructure<Product> verifyProductDetailsByAdmin(int productId)
	{
		Product product = productDao.getProductByIdDao(productId);
		
		if(product!=null)
		{
			if(httpSession.getAttribute("password")!=null)
			{
				Product product2 = adminDao.verifyProductDetailsByAdmin(productId);
				responseStructureProduct.setStatusCode(HttpStatus.ACCEPTED.value());
				responseStructureProduct.setStatusMsg("Success");
				responseStructureProduct.setDescription("product is verified successfully");
				responseStructureProduct.setData(product2);
			}
			else
			{
				responseStructureProduct.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
				responseStructureProduct.setStatusMsg("Your session is  logout ");
				responseStructureProduct.setDescription("please login again");
				responseStructureProduct.setData(null);
			}
		}
		else
		{
			responseStructureProduct.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
			responseStructureProduct.setStatusMsg("given id is not present");
			responseStructureProduct.setDescription("please check once");
			responseStructureProduct.setData(null);
		}
		return responseStructureProduct;
	}
}
