package com.teamdevAcademy.academy.serviceImlp;

import com.teamdevAcademy.academy.dto.CreateCategoDto;
import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.repositories.CategoriesRepository;
import com.teamdevAcademy.academy.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;

    @Override
    public Categories saveCategories(Categories C) {
        return categoriesRepository.save(C);
    }

    @Override
    public Categories updateCategories(Long id, CreateCategoDto C) {
        try {
            System.out.println("Error updating salle" + id);
            System.out.println("Error 1 salle" + C);
            Optional<Categories> categoyOptional = categoriesRepository.findById(id);
            System.out.println("Error 1 salle" + categoyOptional.get());
            if (categoyOptional.isPresent()) {
                System.out.println("Error ------------ c" + C);

                Categories category = categoyOptional.get();
                category.setTitle(C.getTitle());
                category.setDescription(C.getDescription());
                System.out.println("Error ------2------ c" + category);
                return categoriesRepository.save(category);
            } else {
                return null; // Or throw an exception if desired
            }
        } catch (Exception e) {
            System.out.println("Error updating salle" + e);
            return null; // Or throw an exception if desired
        }
    }


    @Override
    public boolean deleteCategoriesById(Long id) {
        if (categoriesRepository.findById(id).isPresent()) {
            categoriesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Categories getCategories(Long id) {
        return categoriesRepository.findById(id).get();
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public Long countsCategories() {
        return categoriesRepository.count();
    }
}
