package com.teamdevAcademy.academy.repositories;

import com.teamdevAcademy.academy.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {


}
