package com.teamdevAcademy.academy.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Categoty_matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER ,optional = false)
    @JoinColumn(name = "categorie_id")
    @JsonIgnore
    private Categories categorie;

    @ManyToOne(fetch = FetchType.EAGER ,optional = false)
    @JoinColumn(name = "matiere_id")

    private Matieres matiere;
}
