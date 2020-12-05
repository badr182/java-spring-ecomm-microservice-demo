package com.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor 
@AllArgsConstructor
@Data
@ToString
// @JsonIgnoreProperties(value = {"prixAchat", "id"})
// @JsonFilter("FilterDynamique")
@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Length(min=3,max=20)
	private String name;
	
	@Min(value=1)
	private int price;
	
	// @JsonIgnore
	// we won't expose this info
	private int priceSell;
	
}
