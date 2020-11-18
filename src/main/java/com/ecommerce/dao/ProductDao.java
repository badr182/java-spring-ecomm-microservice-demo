package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.model.Product;


public interface ProductDao {
	
	public List<Product> findAll();
	
	public Product findById(int id);
	
	public Product save(Product product);
}
