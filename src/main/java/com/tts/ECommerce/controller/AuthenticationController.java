package com.tts.ECommerce.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tts.ECommerce.model.User;
import com.tts.ECommerce.service.UserService;

@Controller
public class AuthenticationController {
	@Autowired
	private UserService userService;

	@GetMapping("/signin")
	public String login() {
		return "ecommerce/signin";
	}
	

	@PostMapping("/signup")
	public String singup(@Valid User user, BindingResult bindingResult, Model model) {
		
		User userExist = userService.findByUsername(user.getUsername());
		if (userExist != null) {
			bindingResult.rejectValue("username", "error.user", "Username is already taken");
		}
		if (!bindingResult.hasErrors()) {
			userService.saveNewUser(user);
			model.addAttribute("Success", "Signup successfull");
			model.addAttribute("user", new User());
		}	

		return "ecommerce/postsignup";
	}
	
	@GetMapping(value="/signup")
	  public String registration(Model model){
	    User user = new User();
	    model.addAttribute("user", user);
	    return "ecommerce/signup";
	  }

//	@PostMapping("/signin")
//	public String singup(@Valid User user, @RequestParam String submit, BindingResult bindingResult,
//			HttpServletRequest request) throws ServletException {
//		String password = user.getPassword();
//		if (submit.equals("up")) {
//			if (userService.findByUsername(user.getUsername()) == null) {
//				userService.saveNew(user);
//			} else {
//				bindingResult.rejectValue("username", "error.user", "Username is already taken.");
//				return "signin";
//			}
//		}
//		request.login(user.getUsername(), password);
//		return "redirect:/";
//	}
	
	
	
}
	
