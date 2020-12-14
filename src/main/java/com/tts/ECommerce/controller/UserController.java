package com.tts.ECommerce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ECommerce.model.Product;
import com.tts.ECommerce.model.User;
import com.tts.ECommerce.service.ProductService;
import com.tts.ECommerce.service.UserService;


@Controller
public class UserController {

	@Autowired
	private UserService userService;

//	@Autowired
//	private ProductService tweetService;
	

	@Autowired
	private ProductService productService;
	
	private void cartCounts(List<Product> products, Model model) {
		HashMap<String, Integer> prodCount = new HashMap<>();
		for (Product prod : products) {
			Product product2 = productService.findById(prod.getId());
//			products.
			prodCount.put(prod.getName(), products.size());
		}
		model.addAttribute("prodCount", prodCount);
		
	}// end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@   $$$$$$$
	
	@GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable(value = "username") String username, Model model) {
        User loggedInUser = userService.getLoggedInUser();
        User user = userService.findByUsername(username);
        boolean isFollowing = false;

        boolean isSelfPage = loggedInUser.getUsername().equals(username);
        model.addAttribute("isSelfPage", isSelfPage);
        model.addAttribute("following", isFollowing);
        model.addAttribute("user", user);
        return "ecommerce/user";
    }
	
	 @GetMapping(value = "/users")
		public String getUsers(@RequestParam(value = "filter", required = false) String filter, Model model) {
			List<User> users = new ArrayList<User>();

			User loggedInUser = userService.getLoggedInUser();


			if (filter == null) {
				filter = "all";
			}
			model.addAttribute("users", users);


			return "ecommerce/users";
		}
	 
	 

		@ModelAttribute("loggedInUser")
		public User loggedInUser() {
			return userService.getLoggedInUser();
		}

	    

} // class