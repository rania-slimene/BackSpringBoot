package com.teamdevAcademy.academy.services;

import com.teamdevAcademy.academy.dto.CreateCategoDto;
import com.teamdevAcademy.academy.entities.Categories;

import java.util.List;

public interface CategoriesService {
    Categories saveCategories (Categories C);
    Categories updateCategories (Long id, CreateCategoDto C);
    boolean deleteCategoriesById (Long id);
    Categories getCategories (Long id);
    List<Categories> getAllCategories ();
    Long countsCategories();



}
