//package com.demo.auth;
//
//
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//public class JwtAuthToken extends AbstractAuthenticationToken {
//
//    private final Object credential;
//    private final Object principal;
//
//
//    public JwtAuthToken(Object credential, Object principal,
//                        Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);
//        this.credential = credential;
//        this.principal = principal;
//        super.setAuthenticated(true);
//
//    }
//    @Override
//    public Object getCredentials() {
//        return this.credential;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return this.principal;
//    }
//}
