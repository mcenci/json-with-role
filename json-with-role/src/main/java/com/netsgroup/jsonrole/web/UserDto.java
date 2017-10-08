package com.netsgroup.jsonrole.web;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.netsgroup.jsonrole.config.JsonRoleView;

@JsonInclude(value = Include.NON_NULL)
public class UserDto {

	public String name;
	
	public Collection<? extends GrantedAuthority> roles;

	@JsonRoleView(hasRoles = {"ROLE_PIPPO"})
	public String alwaysAvailableField;
	
	@JsonRoleView(hasRoles = {"ROLE_USER" , "ROLE_ADMIN" })
	public String userField;
	
	@JsonRoleView(hasRoles = {"ROLE_ADMIN"})
	public String adminField;

	public void setName(String name) {
		this.name = name;
	}

	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}

	public void setAlwaysAvailableField(String alwaysAvailableField) {
		this.alwaysAvailableField = alwaysAvailableField;
	}

	public void setUserField(String userField) {
		this.userField = userField;
	}

	public void setAdminField(String adminField) {
		this.adminField = adminField;
	}
}
