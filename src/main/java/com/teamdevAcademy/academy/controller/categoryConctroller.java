package com.teamdevAcademy.academy.controller;

import com.teamdevAcademy.academy.dto.CreateCategoDto;
import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/categories")
public class categoryConctroller {
    @Autowired
    CategoriesService categoriesService;

    @GetMapping("")
    @PreAuthorize("permitAll")
    public List<Categories> getCategories() {
        return this.categoriesService.getAllCategories();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('admin:create')")
    public Categories saveCategories(@RequestBody Categories Categorie) {
        return this.categoriesService.saveCategories(Categorie);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Object>  deleteCategoriesById(@PathVariable Long id) {
            if (categoriesService.deleteCategoriesById(id)== true)
            {
                return    ResponseEntity.status(HttpStatus.OK).body(
                        Collections.singletonMap("message", " deleted with success"));
            }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap("message", "erreur id n'existe pas"));
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public Categories updateCategories(@PathVariable Long id, @RequestBody CreateCategoDto C) {
        return categoriesService.updateCategories(id, C);
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll")
    public Categories getCategories(@PathVariable Long id) {
        return this.categoriesService.getCategories(id);
    }

    @GetMapping("/counts")
    @PreAuthorize("permitAll")
    public Long countsCategories () {
        return this.categoriesService.countsCategories();
    }

}

