package com.teamdevAcademy.academy.serviceImlp;

import com.teamdevAcademy.academy.dto.CreateMatierDto;
import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.entities.Categoty_matiere;
import com.teamdevAcademy.academy.entities.File;
import com.teamdevAcademy.academy.entities.Matieres;
import com.teamdevAcademy.academy.repositories.CategoriesRepository;
import com.teamdevAcademy.academy.repositories.CategoryMatiereRepository;
import com.teamdevAcademy.academy.repositories.FileRepository;
import com.teamdevAcademy.academy.repositories.MatieresRepository;
import com.teamdevAcademy.academy.services.MatieresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MatieresServiceImpl implements MatieresService {
    @Autowired
    MatieresRepository matieresRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    CategoryMatiereRepository  categoryMatiereRepository;
   @Autowired
    FileRepository fileRepository;


   @Override
    public Matieres CreateMatieres(CreateMatierDto M) {
        Matieres matieres = new Matieres();
        matieres.setTitle(M.getTitle());
        matieres.setNombre_d_heures(M.getNombre_d_heures());
        matieres.setRating(M.getRating());

        Matieres matiere = matieresRepository.save(matieres);

        if (M.getSelectedCategoriesIds() != null && !M.getSelectedCategoriesIds().isEmpty()) {
            for (Long categoryId : M.getSelectedCategoriesIds()) {
                Categories category = categoriesRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    Categoty_matiere matiereCategorie = new Categoty_matiere();
                    matiereCategorie.setMatiere(matiere);
                    matiereCategorie.setCategorie(category);
                    categoryMatiereRepository.save(matiereCategorie);
                }
            }
        }

        return matiere;
    }



   /* @Override
    public Matieres CreateMatieres(CreateMatierDto M) {
        Categories category=categoriesRepository.findById(M.getCategoriesId()).get();
        Matieres matieres=new Matieres();
        System.out.println(category);
        matieres.setTitle(M.getTitle());
        matieres.setNombre_d_heures(M.getNombre_d_heures());
        matieres.setRating(M.getRating());
        Matieres matiere= matieresRepository.save(matieres);
        Categoty_matiere _categoryMatiere=new Categoty_matiere();
        _categoryMatiere.setMatiere(matiere);
        _categoryMatiere.setCategorie(category);
       categoryMatiereRepository.save(_categoryMatiere);
        return  matiere;
    }*/

    @Override
    public List<Matieres> getAllMatieres() {
        return matieresRepository.findAll();
    }

    @Override
    public Matieres getMatiereById(Long id) {
        return matieresRepository.findById(id).get();

    }

    @Override
    public Matieres updateMatiere(Long id, CreateMatierDto M) {
        try {
            Optional<Matieres> matOptional = matieresRepository.findById(id);
            if (matOptional.isPresent()) {
                Matieres mat = matOptional.get();
                mat.setTitle(M.getTitle());
                mat.setNombre_d_heures(M.getNombre_d_heures());
                mat.setRating(M.getRating());

                // Supprimer les relations existantes entre la matière et les catégories
                categoryMatiereRepository.deleteCategoryMatiere(mat.getId());

                // Créer de nouvelles relations entre la matière et les catégories sélectionnées
                for (Long categoryId : M.getSelectedCategoriesIds()) {
                    Categories category = categoriesRepository.findById(categoryId).orElse(null);
                    if (category != null) {
                        Categoty_matiere categoryMatiere = new Categoty_matiere();
                        categoryMatiere.setMatiere(mat);
                        categoryMatiere.setCategorie(category);
                        categoryMatiereRepository.save(categoryMatiere);
                    }
                }

                return matieresRepository.save(mat);
            } else {
                return null; // Ou lancez une exception appropriée si nécessaire
            }
        } catch (Exception e) {
            return null; // Ou lancez une exception appropriée si nécessaire
        }
    }



/*
@Override
public Matieres updateMatiere(Long id, CreateMatierDto M) {
    try {
        Optional<Matieres> matOptional = matieresRepository.findById(id);
        if (matOptional.isPresent()) {
            Matieres mat = matOptional.get();
            mat.setTitle(M.getTitle());
            mat.setNombre_d_heures(M.getNombre_d_heures());
            mat.setRating(M.getRating());

            // Mise à jour de l'image associée à la matière
            if (M.getFile() != null) {
                // Vérifiez si la matière a déjà une image associée
                if (mat.getFile() != null) {
                    // Supprimez l'ancienne image associée à la matière
                    fileRepository.delete(mat.getFile());
                }

                // Enregistrez la nouvelle image associée à la matière
                File imageFile = new File();
                imageFile.setName(M.getFile().getOriginalFilename());
                imageFile.setURL("/images/" + M.getFile().getOriginalFilename());
                fileRepository.save(imageFile);

                // Associez la nouvelle image à la matière
                mat.setFile(imageFile);
            }

            // Sauvegarder la matière mise à jour dans la base de données
            return matieresRepository.save(mat);
        } else {
            return null; // Ou lancez une exception appropriée si nécessaire
        }
    } catch (Exception e) {
        return null; // Ou lancez une exception appropriée si nécessaire
    }
}
*/
    /*@Override
    public Matieres updateMatiere( Long id, CreateMatierDto M) {

        try {
            Matieres MatOptional = matieresRepository.findById(id).get();

            System.out.println(MatOptional);
            if (MatOptional != null) {

                MatOptional.setTitle(M.getTitle());
                MatOptional.setNombre_d_heures(M.getNombre_d_heures());
                MatOptional.setRating(M.getRating());
            //    mat.setCategories(M.getCategories());
                System.out.println(MatOptional.getId());

                categoryMatiereRepository.deleteCategoryMatiere(MatOptional.getId());

                Categoty_matiere _categoryMatiere=new Categoty_matiere();
                _categoryMatiere.setMatiere(MatOptional);
                Categories category=categoriesRepository.findById(M.getSelectedCategoriesIds()).get();
                _categoryMatiere.setCategorie(category);
                categoryMatiereRepository.save(_categoryMatiere);
                return matieresRepository.save(MatOptional);

            } else {
                return null; // Or throw an exception if desired
            }
        } catch (Exception e) {
            return null; // Or throw an exception if desired
        }
    }*/

    @Override
    public boolean DeleteMatiereById(Long id) {
        Optional<Matieres> matieresOptional= matieresRepository.findById(id);
       if( matieresOptional.isPresent()) {
           matieresRepository.delete(matieresOptional.get());
           return true;
       }
        return false;
    }

    @Override
    public boolean deleteAllMatiere() {
        List<Matieres> allMatieres = matieresRepository.findAll();
        if (!allMatieres.isEmpty()) {
            matieresRepository.deleteAll();
            return true;
        }
        return false;
    }

    @Override
    public Long CountMatieres() {
        return this.matieresRepository.count();
    }

}
