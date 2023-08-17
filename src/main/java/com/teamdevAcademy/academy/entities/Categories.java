package com.teamdevAcademy.academy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.swing.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Title ;
    private  String Description;
    //private ImageIcon Image ;
  //  @OneToMany(mappedBy = "Category" , cascade = CascadeType.ALL)

    @OneToMany(fetch =FetchType.LAZY , mappedBy = "categorie", cascade= CascadeType.ALL )
    private List<Categoty_matiere> category_matiere;

}
