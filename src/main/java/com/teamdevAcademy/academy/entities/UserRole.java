package com.teamdevAcademy.academy.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

import static com.teamdevAcademy.academy.entities.Permission.*;

@RequiredArgsConstructor
public enum UserRole {
    USER(Set.of(
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_DELETE,
            Permission.USER_CREATE
    )),
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_CREATE,
                    Permission.USER_READ,
                    Permission.USER_UPDATE,
                    Permission.USER_DELETE,
                    Permission.USER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getRolesAsAuthorities() {
        var authorities = new HashSet<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }

    }
