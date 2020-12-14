package com.tts.ECommerce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ECommerce.model.Product;
import com.tts.ECommerce.model.User;
import com.tts.ECommerce.service.ProductService;
import com.tts.ECommerce.service.UserService;

@Controller
@ControllerAdvice
public class CartController {
	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@ModelAttribute("loggedInUser")
	public User loggedInUser() {
		return userService.getLoggedInUser();
	}

	@ModelAttribute("cart")
      public Map<Product, Integer> cart() {
          User user = loggedInUser();
          if(user == null) return null;
          System.out.println("\n\n ***** Getting cart user:  " + user + "\t user.cart:  " + user.getCart());

  		Map<Product, Integer> userCart = new HashMap<Product, Integer>(user.getCart());
  		
          return user.getCart();
      }
	
	/**
     * Puts an empty list in the model that thymeleaf can use to sum up the cart total.
     */
    @ModelAttribute("list")
    public List<Double> list() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String showCart() {
        return "ecommerce/cart";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam long id) {
        Product p = productService.findById(id);
		Map<Product, Integer> cart = cart();
        setQuantity(p, cart().getOrDefault(p, 0) + 1);
        return "ecommerce/cart";
    }

//    @PatchMapping("/cart")
//    public String updateQuantities(@RequestParam long[] id, @RequestParam int[] quantity) {
//        for(int i = 0; i < id.length; i++) {
//            Product p = productService.findById(id[i]);
//            setQuantity(p, quantity[i]);
//        }
//        return "ecommerce/cart";
//    }

//    private void setQuantity(Product p, int quantity) {
//        if(quantity > 0)
//            cart().put(p, quantity);
//        else
//            cart().remove(p);
//
//        userService.updateCart(cart());
//    }
    
    //------------------------
    private void setQuantity(Product p, int quantity) {
		Map<Product, Integer> userMap = userService.getLoggedInUser().getCart();
		if (quantity > 0)
			userMap.put(p, quantity);
		else
			userMap.remove(p);

		userService.updateCart(userMap);
	}
    
    //------------------------------
    
    
//    @DeleteMapping("/cart/update")
    @PostMapping("/cart/update")
	public String updateQuantities(@RequestParam long[] id, @RequestParam int[] quantity) {
		for (int i = 0; i < id.length; i++) {
			Product p = productService.findById(id[i]);
			setQuantity(p, quantity[i]);
		}
		return "redirect:/cart";
	}
    

//	@DeleteMapping("/cart/delete")
	@PostMapping("/cart/delete")
    public String removeFromCart(@RequestParam long id) {
        Product p = productService.findById(id);
        setQuantity(p, 0);
        return "redirect:/cart";
    }

    
//    @PostMapping("/cart/delete")
//	public String removeFromCart(@RequestParam long id) {
//		Product p = productService.findById(id);
//		setQuantity(p, 0);
//		return "redirect:/ecommerce/cart";
//	}
  }

