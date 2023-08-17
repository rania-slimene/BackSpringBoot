package com.teamdevAcademy.academy.controller;
import com.teamdevAcademy.academy.dto.CreateCourDto;
import com.teamdevAcademy.academy.entities.Cour;
import com.teamdevAcademy.academy.services.CourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cours")
public class courController {
    @Autowired
    CourService courService;

    @GetMapping("")

    public List<Cour> getCour() {
        return this.courService.getCour();
    }

    @PostMapping("")
    public Cour saveCour(@RequestBody CreateCourDto Cour) {
        return this.courService.saveCour(Cour);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteCourById( @PathVariable long id) {
        if (courService.deleteCourById(id)== true){
            return    ResponseEntity.status(HttpStatus.OK).body(
                    Collections.singletonMap("message", " deleted with success"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap("message", "erreur id n'existe pas"));
    }



    @PutMapping("{id}")
    public Cour updateCour(@PathVariable Long id,@RequestBody CreateCourDto C) {
        return courService.updateCour(id,C);
    }

    @GetMapping("{id}")
    public Cour getCour(@PathVariable Long id) {
        return this.courService.getCour(id);
    }


    @GetMapping("/counts")
    public Long countCours () {

        return this.courService.countCours();
    }

}


