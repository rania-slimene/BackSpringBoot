package com.teamdevAcademy.academy.services;

import com.teamdevAcademy.academy.dto.CreateMatierDto;
import com.teamdevAcademy.academy.entities.Matieres;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatieresService {

     Matieres CreateMatieres (CreateMatierDto M);
     List<Matieres>getAllMatieres ();
     Matieres getMatiereById(Long id);
     Matieres updateMatiere( Long id,CreateMatierDto M);
     boolean DeleteMatiereById(Long id);
     boolean deleteAllMatiere();
     Long CountMatieres();




}
