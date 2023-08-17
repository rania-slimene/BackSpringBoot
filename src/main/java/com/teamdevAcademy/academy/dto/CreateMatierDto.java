package com.teamdevAcademy.academy.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateMatierDto {
    private String title;
    private List<Long> selectedCategoriesIds;
    private  Long rating;
    private  Long nombre_d_heures;
    private MultipartFile imagefile;

}
