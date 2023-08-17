package com.teamdevAcademy.academy.repositories;

import com.teamdevAcademy.academy.entities.Matieres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface MatieresRepository extends JpaRepository<Matieres, Long> {
    @Modifying
    @Transactional
    @Query(value="DELETE FROM categoty_matiere WHERE matiere_id =:id" ,nativeQuery = true)
    Integer deleteCategoryMatiere(@Param("id") Long id);
}
