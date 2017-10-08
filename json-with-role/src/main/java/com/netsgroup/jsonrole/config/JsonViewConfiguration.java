package com.netsgroup.jsonrole.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@RestControllerAdvice
public class JsonViewConfiguration  extends AbstractMappingJacksonResponseBodyAdvice {

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

		List<Field> lista = seekFieldsWithAnnotations(bodyContainer.getValue());
		for (Field field : lista) {
			ReflectionUtils.setField(field, bodyContainer.getValue(), null);
		}
	}

	public static List<Field> seekFieldsWithAnnotations(Object o) {
		Class<?> clss = o.getClass();
		List<Field> fieldsWithAnnotations = new ArrayList<>();

		List<Field> allFields = new ArrayList<>(Arrays.asList(clss.getDeclaredFields()));
		for(final Field field : allFields ) {
			if(field.isAnnotationPresent(JsonRoleView.class)) {
				Annotation annotInstance = 	field.getAnnotation(JsonRoleView.class);
				if(annotInstance.annotationType().isAnnotation()) {
					if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
						Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
						Set<String> acceptableAuthorities= authorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toSet());
						List<String> roles = Arrays.asList(((JsonRoleView)annotInstance).hasRoles());
						if(Collections.disjoint(acceptableAuthorities, roles)){
							//se non ho il ruolo torno il campo cosi da poterlo scremare dopo
							fieldsWithAnnotations.add(field);
						}
					}	                
				}
			}
		}
		return fieldsWithAnnotations;
	}
}