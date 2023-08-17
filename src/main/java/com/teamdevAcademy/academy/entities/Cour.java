package com.teamdevAcademy.academy.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Entity
@Setter
@Getter
//@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom;
    private Integer orders;
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
   @JoinColumn(name = "matiere_id")
   private Matieres matiere;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "cour", cascade = CascadeType.ALL)
    List<Chapitre> chapitres;
}
