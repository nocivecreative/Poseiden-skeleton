package com.nnk.springboot.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nnk.springboot.domain.User;

import lombok.Getter;

/**
 * Cette Class est crée uniquement pour différencier le type User de
 * l'application
 * du type User de spring security et éviter la confusion
 * (org.springframework.security.core.userdetails.User)
 * 
 * Il n'est pas prévu d'avoir des rôles en base, donc on hardcode un role "USER"
 */
@Getter
public class SecurityUser implements UserDetails {

    private final Integer id;
    private final String username;
    private final String role;
    private static final String ROLE_PREFIX = "ROLE_";
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(ROLE_PREFIX + this.role));
    }

}
