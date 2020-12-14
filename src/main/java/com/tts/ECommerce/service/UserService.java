package com.tts.ECommerce.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tts.ECommerce.model.Product;
import com.tts.ECommerce.model.Role;
import com.tts.ECommerce.model.User;
import com.tts.ECommerce.repository.RoleRepository;
import com.tts.ECommerce.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void saveNew(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User saveNewUser(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setActive(1);
	    Role userRole = roleRepository.findByRole("USER");
	    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	    return userRepository.save(user);
	  }
	
	
	public void saveExisting(User user) {
		userRepository.save(user);
	}

	public User getLoggedInUser() {
		return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	}

	public void updateCart(Map<Product, Integer> cart) {
		User user = getLoggedInUser();
		user.setCart(cart);
		saveExisting(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("Username not found.");
		return (UserDetails) user;	// -------
	}
}
	

