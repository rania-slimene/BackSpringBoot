package com.teamdevAcademy.academy.repositories;

import com.teamdevAcademy.academy.entities.Cour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CourRepository extends JpaRepository<Cour, Long> {

}
