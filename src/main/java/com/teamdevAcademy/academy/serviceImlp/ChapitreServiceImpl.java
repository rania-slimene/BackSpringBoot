package com.teamdevAcademy.academy.serviceImlp;

import com.teamdevAcademy.academy.dto.CreateChapDto;
import com.teamdevAcademy.academy.entities.*;
import com.teamdevAcademy.academy.repositories.ChapitreRepository;
import com.teamdevAcademy.academy.repositories.CourRepository;
import com.teamdevAcademy.academy.services.ChapitreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ChapitreServiceImpl implements ChapitreService {
    @Autowired
    ChapitreRepository chapitreRepository;
  @Autowired
    CourRepository coursRepository;
    @Override
    public Chapitre saveChapitre(CreateChapDto Chap) {
        Cour cours = coursRepository.findById(Chap.getCourId()).get();
        System.out.println(cours);
        Chapitre chapitre = new Chapitre();
        chapitre.setCour(cours);
        chapitre.setNom(Chap.getNom());
        chapitre.setDescription(Chap.getDescription());
        chapitre.setOrders(Chap.getOrders());
        return chapitreRepository.save(chapitre);
    }
   @Override
   public Chapitre updateChapitre(Long id, CreateChapDto M) {
       try {
           Optional<Chapitre> chapOptional = chapitreRepository.findById(id);

           if (chapOptional.isPresent()) {
               Chapitre chapitre = chapOptional.get();
               chapitre.setNom(M.getNom());
               chapitre.setDescription(M.getDescription());
               chapitre.setOrders(M.getOrders());

               Cour ancienCours = chapitre.getCour();
               if (ancienCours != null) {
                   ancienCours.getChapitres().remove(chapitre);
                   coursRepository.save(ancienCours);
               }

               // Associer le chapitre au nouveau cours
               Cour nouveauCours = coursRepository.findById(M.getCourId()).orElse(null);
               if (nouveauCours != null) {
                   nouveauCours.getChapitres().add(chapitre);
                   chapitre.setCour(nouveauCours);
                   coursRepository.save(nouveauCours);
               }

               return chapitreRepository.save(chapitre);
           } else {
               return null;
           }
       } catch (Exception e) {
           System.out.println("Error updating: " + e);
           return null;
       }
   }

    @Override
    public boolean deleteChapitreById(Long id) {
        Optional<Chapitre> chapitresOptional= chapitreRepository.findById(id);
        if( chapitresOptional.isPresent()) {
            chapitreRepository.delete(chapitresOptional.get());
            return true;
        }
        return false;
    }

    @Override
    public Chapitre getChapitre(Long id) {
        return  chapitreRepository.findById(id).get();
    }

    @Override
    public List<Chapitre> getChapitre() {
        return chapitreRepository.findAll();
    }

    @Override
    public Long CountChapitre() {
        return this.chapitreRepository.count();
    }
}
