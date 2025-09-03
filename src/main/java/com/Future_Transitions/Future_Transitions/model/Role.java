package com.Future_Transitions.Future_Transitions.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public enum Role {

    USER,
    ADMIN;



    //this is for user permission as it a general permission
//    USER(Collections.emptySet()),

// this gives the permission of the admin
//    ADMIN(new HashSet<>(Arrays.asList(
//            Permission.ADMIN_READ,
//          Permission.ADMIN_UPDATE,
//          Permission.ADMIN_CREATE,
//          Permission.ADMIN_DELETE
//
//    )));
//    @Getter
//    private final java.util.Set<Permission> permissions;
//
//    public List<GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = permissions
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toList());
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//
//        return authorities;
    }



