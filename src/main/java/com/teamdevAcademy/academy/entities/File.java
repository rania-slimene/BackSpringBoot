package com.teamdevAcademy.academy.entities;

import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String URL;

   /* @OneToOne(mappedBy = "file")
    private Matieres matieres;*/
}
