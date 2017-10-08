package com.netsgroup.jsonrole.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.netsgroup.jsonrole.annotation.JsonRoleSerializer;
import com.netsgroup.jsonrole.annotation.JsonSerializeOnRoleMatch;

@RestControllerAdvice(annotations = {JsonRoleSerializer.class})
public class JsonViewConfiguration  extends AbstractMappingJacksonResponseBodyAdvice {

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
			MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
		if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
			Set<String> acceptableAuthorities= SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toSet());
			seekFieldsWithAnnotations(bodyContainer.getValue().getClass() , acceptableAuthorities)
			.forEach(field -> {
				ReflectionUtils.setField(field, bodyContainer.getValue(), null);
			});
		}		
	}

	private List<Field> seekFieldsWithAnnotations(Class<?> clazz , Set<String> acceptableAuthorities) {
		List<Field> fieldsWithAnnotations = new ArrayList<>();
		List<Field> allFields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
		for(final Field field : allFields ) {
			if(field.isAnnotationPresent(JsonSerializeOnRoleMatch.class)) {
				Annotation annotInstance = 	field.getAnnotation(JsonSerializeOnRoleMatch.class);
				if(annotInstance.annotationType().isAnnotation()) {
					List<String> roles = Arrays.asList(((JsonSerializeOnRoleMatch)annotInstance).hasRoles());
					if(Collections.disjoint(acceptableAuthorities, roles)){
						//se non ho il ruolo torno il campo cosi da poterlo scremare dopo
						fieldsWithAnnotations.add(field);
					}              
				}
			}
		}
		return fieldsWithAnnotations;
	}
}