package com.teamdevAcademy.academy.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;


@Getter
@Setter
@ToString
public class UserDTO {

    private String Nom ;
    private String Prenom ;
    private Integer ntel ;
    private String email ;
    private String Password;
    private String ConfirmeMp;

}
