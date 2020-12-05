package com.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ecommerce.model.Product;


@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{
	
	public List<Product> findAll();
	
	public Product findById(int id);
	
	public List<Product> findByPriceGreaterThan(int prixLimit);
	
	public List<Product> findByNameLike(String shearch);
	
	public Product save(Product product);
	
	@Query("SELECT id, name, price FROM Product p where p.price > priceLimit")
	List<Product> searchExpensiveProduct(@Param("priceLimit") int price);
}
