package com.teamdevAcademy.academy.repositories;

import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.entities.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
}
