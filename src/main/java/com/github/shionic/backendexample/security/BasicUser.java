package com.github.shionic.backendexample.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface BasicUser extends UserDetails {
    String getUsername();
    Long getId();
    Long getSessionId();
    List<String> getRoles();
    default boolean hasRole(String role) {
        for(var e : getRoles()) {
            if(e.equals(role)) {
                return true;
            }
        }
        return false;
    }

    default String getPassword() {
        return null;
    }

    default Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for(var e : getRoles()) {
            list.add(new SimpleGrantedAuthority(e));
        }
        return list;
    }
}
