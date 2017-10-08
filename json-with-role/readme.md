##Json Serialization based on Authorized User  
Simple but effective implementation based on Spring and Fasterxml of a custom field serializer.  
  
###How it works  
  
Given a POJO   
Annotate with   
`@JsonSerializeOnRoleMatch(hasRoles = {"ROLE_USER" , "ROLE_ADMIN" })`  
each class field you want to filter using current authorized user roles.

If you want to hide *filtered fields* from output simply add  
`@JsonInclude(value = Include.NON_NULL)`  
otherwise *all filtered* attribute will be valorized with `null`.  

Include in your controllers component scan **JsonViewConfiguration.class**

Annotate each controller where you want to enable this behavior with  
`@JsonRoleSerializer`  

Enjoy.