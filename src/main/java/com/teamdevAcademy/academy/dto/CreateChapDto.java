package com.teamdevAcademy.academy.dto;

import lombok.Data;

@Data
public class CreateChapDto {
    private String nom;
    private String description;
    private Integer orders;
    private Long CourId;
}
