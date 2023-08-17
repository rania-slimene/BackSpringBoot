package com.teamdevAcademy.academy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
//@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Matieres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Title ;
    private  Long rating;
    private  Long nombre_d_heures;
    @JsonIgnore
    @OneToMany(fetch =FetchType.LAZY , mappedBy = "matiere",cascade = CascadeType.ALL )
    private List<Categoty_matiere> category_matiere;
    @JsonIgnore
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "matiere", cascade = CascadeType.ALL)
    private List<Cour> cours;


   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "File_id")
    private File file;





}
