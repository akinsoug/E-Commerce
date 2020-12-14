package com.tts.ECommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.tts.ECommerce.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Role findByRole(String role);
	
	

}
