package com.ecommerce.web.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Product;
import com.ecommerce.web.exception.*;
import com.ecommerce.web.exception.ProductInrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class ProductController {
	
	@Autowired
	private ProductDao productDao;
	
//	@RequestMapping(value="/products", method=RequestMethod.GET)
//	public List<Product> listProduct() {
//		return productDao.findAll();
//	}
	
	/**
	 * @TODO: review this part 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/products", method = RequestMethod.GET)	
	public MappingJacksonValue listProducts() {
		
		List<Product> products = productDao.findAll();
		
		SimpleBeanPropertyFilter nameFilter = SimpleBeanPropertyFilter.serializeAllExcept("priceSell");
		
		FilterProvider listOfOurFilters = new SimpleFilterProvider().addFilter("FilterDynamique",nameFilter);
		
		MappingJacksonValue productFilters = new MappingJacksonValue(products); 
		
		productFilters.setFilters(listOfOurFilters);
		
		return productFilters;
	}
	
	
	@GetMapping(value= "/products/{id}")
	public Product showProduct(@PathVariable int id) {
		// Product p = new Product(id, new String("Adapter"), 100 );
		// return p;
		Product product = productDao.findById(id);
		if ( product == null )
			throw new ProductInrouvableException("Product with id :"+id+"is not found ");
		
		return product ;
	}
	
	@GetMapping(value= "test/products/{priceLimit}")
	public List<Product> testRequest(@PathVariable int priceLimit) {
		
		return this.productDao.findByPriceGreaterThan(400);
	}
	
	@GetMapping(value= "test/products/{search}")
	public List<Product> testSearchRequest(@PathVariable String search) {
		
		return this.productDao.findByNameLike("%"+search+"%");
	}
	
	// add product 
//	@PostMapping("products")
//	public  void addProduct(@RequestBody Product product) {
//		this.productDao.save(product);
//	}
	
	@PostMapping(value="/products")
	public ResponseEntity<Void> addProduct(@Valid @RequestBody Product product  ){
		
		Product productAdded = productDao.save(product);
		
		if (productAdded == null)
			// build construct header 
			// send code status 204
			return ResponseEntity.noContent().build();
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(productAdded.getId())
				.toUri();
		// send with code status 201		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(value = "/products/{id}")
	public void deleteProduct(@PathVariable int id) {
		this.productDao.deleteById(id);
	}
	
	@PutMapping(value="/products")
	public void updateProduct(@RequestBody Product product) {		
		this.productDao.save(product);
	}
	
}
