package com.teamdevAcademy.academy.services;

import com.teamdevAcademy.academy.dto.CreateChapDto;
import com.teamdevAcademy.academy.entities.Chapitre;

import java.util.List;

public interface ChapitreService {
    Chapitre saveChapitre (CreateChapDto Chap);
    Chapitre updateChapitre(Long id, CreateChapDto Chap);

    boolean deleteChapitreById (Long id);
    Chapitre getChapitre (Long id);
    List<Chapitre>getChapitre ();

   Long CountChapitre();
}
