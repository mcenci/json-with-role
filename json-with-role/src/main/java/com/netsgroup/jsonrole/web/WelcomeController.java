package com.netsgroup.jsonrole.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("/welcome")
	public UserDto welcome(Authentication authentication){
		UserDto user = new UserDto();
		user.setName(authentication.getName());
		user.setRoles(authentication.getAuthorities());
		user.setAlwaysAvailableField("semprePresente");
		user.setUserField("sia User che Admin");
		user.setAdminField("solo admin");
		return user;
	}
}
