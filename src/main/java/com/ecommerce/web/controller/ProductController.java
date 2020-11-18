package com.ecommerce.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Product;

@RestController
public class ProductController {
	
	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public List<Product> listProduct() {
		return productDao.findAll();
	}
	
	@GetMapping(value= "/products/{id}")
	public Product showProduct(@PathVariable int id) {
		// Product p = new Product(id, new String("Adapter"), 100 );
		// return p;
		return productDao.findById(id);
	}
}
