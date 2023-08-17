package com.teamdevAcademy.academy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chapitre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom;
    private  String description;
    private Integer orders;
    @ManyToOne(fetch = FetchType.EAGER, optional = false , cascade = CascadeType.ALL)
    @JoinColumn(name = "Cour_id")
    @JsonIgnore
    private Cour cour;

}
