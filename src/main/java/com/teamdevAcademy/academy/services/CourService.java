package com.teamdevAcademy.academy.services;//package com.teamdevAcademy.academy.services;
import com.teamdevAcademy.academy.dto.CreateCourDto;

import com.teamdevAcademy.academy.dto.CreateCourDto;
import com.teamdevAcademy.academy.entities.Cour;

import java.util.List;

public interface CourService {

    Cour saveCour (CreateCourDto C);
    Cour updateCour ( Long id ,CreateCourDto C);
    boolean deleteCourById (Long id);
    Cour  getCour (Long id);
    List<Cour> getCour ();
    Long countCours();
}
