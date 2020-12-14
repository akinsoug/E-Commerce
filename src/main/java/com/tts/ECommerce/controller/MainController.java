package com.tts.ECommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ECommerce.service.ProductService;

import com.tts.ECommerce.model.Product;
//import com.tts.ECommerce.service.ProductService;

//import com.tts.ECommerce.model.Product;
//import com.tts.ECommerce.service.ProductService;

import lombok.Data;

@Data
@Controller
@ControllerAdvice // This makes the `@ModelAttribute`s of this controller available to all
					// controllers, for the navbar.
public class MainController {
	@Autowired
	ProductService productService; 

	@GetMapping("/")
	public String main() {
		return "ecommerce/main2";
	}

	@ModelAttribute("products")
	public List<Product> products() {
		System.out.println("\n\n\n " + productService.findAll());
		return productService.findAll();
	}

	@ModelAttribute("categories")
	public List<String> categories() {
		return productService.findDistinctCategories();
	}

	@ModelAttribute("brands")
	public List<String> brands() {
		return productService.findDistinctBrands();
	}

	@GetMapping("/filter")
	public String filter(@RequestParam(required = false) String category, @RequestParam(required = false) String brand,
			Model model) {
		List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
		model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
		return "ecommerce/main2";
	}

	@GetMapping("/about")
	public String about() {
		return "ecommerce/about";
	}
	
//	@GetMapping("/cart")
//	public String cart() {
//		return "ecommerce/cart";
//	}
	
	@GetMapping("/products")
	public String inventory() {
		List<Product> products = new ArrayList<>();
//		 products =  products();
		return "ecommerce/products";
	}
	
} //
 