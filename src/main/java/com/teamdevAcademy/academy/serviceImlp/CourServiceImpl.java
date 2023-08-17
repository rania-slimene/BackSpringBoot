package com.teamdevAcademy.academy.serviceImlp;

import com.teamdevAcademy.academy.dto.CreateCourDto;
import com.teamdevAcademy.academy.entities.*;
import com.teamdevAcademy.academy.repositories.ChapitreRepository;
import com.teamdevAcademy.academy.repositories.CourRepository;
import com.teamdevAcademy.academy.repositories.MatieresRepository;
import com.teamdevAcademy.academy.services.CourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourServiceImpl implements CourService{
    @Autowired
    CourRepository coursRepository;
    @Autowired
    MatieresRepository matieresRepository;

    @Override
    public Cour saveCour(CreateCourDto C) {
        Matieres matieres = matieresRepository.findById(C.getMatId()).get();
        Cour cour = new Cour();
        cour.setMatiere(matieres);
        cour.setNom(C.getNom());
        return coursRepository.save(cour);
    }

    @Override
    public Cour updateCour(Long id, CreateCourDto C) {
        try {
            Optional<Cour> courOptional = coursRepository.findById(id);

            if (courOptional.isPresent()) {
                Cour cour = courOptional.get();
                cour.setNom(C.getNom());

                Matieres ancienMatiere = cour.getMatiere();
                if (ancienMatiere != null) {
                    ancienMatiere.getCours().remove(cour);
                    matieresRepository.save(ancienMatiere);
                }

                // Associer le chapitre au nouveau cours
                Matieres nouveauMatiere = matieresRepository.findById(C.getMatId()).orElse(null);
                if (nouveauMatiere != null) {
                    nouveauMatiere.getCours().add(cour);
                    cour.setMatiere(nouveauMatiere);
                    matieresRepository.save(nouveauMatiere);
                }

                return coursRepository.save(cour);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error updating: " + e);
            return null;
        }
    }

    @Override
    public boolean deleteCourById(Long id) {
        Optional<Cour> coursOptional = coursRepository.findById(id);
        if (coursOptional.isPresent()){
            coursRepository.delete(coursOptional.get());
            coursRepository.flush(); // Sauvegarder les modifications
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Cour getCour(Long id) {
        return coursRepository.findById(id).get();
    }

    @Override
    public List<Cour> getCour() {
        return coursRepository.findAll();
    }

    @Override
    public Long countCours() {
        return coursRepository.count();
    }
}
