package com.netsgroup.jsonrole.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Annotazione per condizionare la serializzazione di un campo in funzione di un 
 * ruolo associato all'utente autorizzato.
 * 
 * Questa annotazione viene processata solo in presenza di una  {@link #JsonViewConfiguration}.
 * 
 * Accetta una lista comma separated di ruoli.
 * 
 * @author m.cenci
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSerializeOnRoleMatch {
	String[] hasRoles();
}