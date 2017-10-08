package com.netsgroup.jsonrole.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonRoleView {
	String[] hasRoles();
}