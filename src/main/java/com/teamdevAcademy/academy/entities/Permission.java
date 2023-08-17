package com.teamdevAcademy.academy.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    USER_READ("USER:read"),
    USER_UPDATE("USER:update"),
    USER_CREATE("USER:create"),
    USER_DELETE("USER:delete");

    @Getter
    private final String permission;
}