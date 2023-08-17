package com.teamdevAcademy.academy.repositories;

import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.entities.Categoty_matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryMatiereRepository extends JpaRepository<Categoty_matiere, Long> {
    @Modifying
    @Transactional
    @Query(value="DELETE FROM categoty_matiere WHERE matiere_id =:id" ,nativeQuery = true)
    Integer deleteCategoryMatiere(@Param("id") Long id);
}
