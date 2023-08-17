package com.teamdevAcademy.academy.controller;

import com.teamdevAcademy.academy.dto.CreateChapDto;
import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.entities.Chapitre;
import com.teamdevAcademy.academy.services.CategoriesService;
import com.teamdevAcademy.academy.services.ChapitreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/chapitre")
public class chapitreController {
    @Autowired
    ChapitreService chapitreService;

    @GetMapping("")
    public List<Chapitre> getChapitre() {
        return this.chapitreService.getChapitre();
    }

    @PostMapping("")
    public Chapitre saveChapitre(@RequestBody CreateChapDto chapitre) {
        return this.chapitreService.saveChapitre(chapitre);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteChapitreById(@PathVariable long id) {
        if (chapitreService.deleteChapitreById(id)== true){
            return    ResponseEntity.status(HttpStatus.OK).body(
                    Collections.singletonMap("message", " deleted with success"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap("message", "erreur id n'existe pas"));
    }


    @PutMapping("{id}")
    public Chapitre updateChapitre(@PathVariable Long id,@RequestBody CreateChapDto C) {
        return chapitreService.updateChapitre(id, C);
    }

    @GetMapping("{id}")
    public Chapitre getChapitre(@PathVariable Long id) {
        return this.chapitreService.getChapitre(id);
    }
    @GetMapping("/counts")
    public Long CountChapitre() {
        return this.chapitreService.CountChapitre();
    }

}


