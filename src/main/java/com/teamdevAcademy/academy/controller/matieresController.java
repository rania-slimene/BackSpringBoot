package com.teamdevAcademy.academy.controller;

import com.teamdevAcademy.academy.dto.CreateMatierDto;
import com.teamdevAcademy.academy.entities.Categories;
import com.teamdevAcademy.academy.entities.Categoty_matiere;
import com.teamdevAcademy.academy.entities.File;
import com.teamdevAcademy.academy.entities.Matieres;
import com.teamdevAcademy.academy.repositories.CategoriesRepository;
import com.teamdevAcademy.academy.repositories.MatieresRepository;
import com.teamdevAcademy.academy.services.FilesService;
import com.teamdevAcademy.academy.services.MatieresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@RequestMapping("/Matieres")
public class matieresController {
    @Autowired
    MatieresService matieresService;
    @Autowired
    FilesService filesService;

    @GetMapping("")
    public List<Matieres> getAllMatieres() {
        return this.matieresService.getAllMatieres();
    }

    @PostMapping("")

    public Matieres CreateMatieres(@RequestBody CreateMatierDto matieres ) {
        return this.matieresService.CreateMatieres(matieres);
    }

    @GetMapping("{id}")
    public Matieres getMatiereById(@PathVariable long id) {
        return this.matieresService.getMatiereById(id);
    }

    @PutMapping("{id}")
    public Matieres updateMatiere(@PathVariable Long id, @RequestBody CreateMatierDto matieres) {
        return this.matieresService.updateMatiere(id, matieres);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> DeleteMatiereById(@PathVariable long id) {
        if (matieresService.DeleteMatiereById(id)== true){
            return    ResponseEntity.status(HttpStatus.OK).body(
                    Collections.singletonMap("message", " deleted with success"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap("message", "erreur id n'existe pas"));
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllMatieres() {
        if (matieresService.deleteAllMatiere()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    Collections.singletonMap("message", "All matieres deleted with success"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap("message", "No matieres to delete"));
    }

    @GetMapping("/counts")
    public Long CountMatieres() {
        return this.matieresService.CountMatieres();
    }

    private static final String UPLOAD_DIR = "src/main/resources/static/images";
   /* @PostMapping("/{id}/upload")
    public String uploadImage(@PathVariable("id") Long matiereId,
                              @RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Veuillez sélectionner un fichier");
            return "redirect:/Matieres/" + matiereId;
        }

        try {
            // Enregistrer le fichier sur le serveur
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + "/" + fileName);
            Files.write(filePath, file.getBytes());

            // Créer une instance Files pour représenter le fichier
            File imageFile = new File();
            imageFile.setName(fileName);
            imageFile.setURL( "/images/"+ fileName);

            // Sauvegarder l'entité Files dans la base de données
            filesService.saveFile(imageFile);

            // Associer le fichier à la matière
            Matieres matiere = matieresService.getMatiereById(matiereId);
            matiere.setFile(imageFile);
            // Créer un objet CreateMatierDto à partir des données de la matière
            CreateMatierDto matiereDto = new CreateMatierDto();
            matiereDto.setTitle(matiere.getTitle());
            matiereDto.setNombre_d_heures(matiere.getNombre_d_heures());
            matiereDto.setRating(matiere.getRating());
            // Ajoutez d'autres propriétés de Matieres à CreateMatierDto si nécessaire

            // Sauvegarder la matière mise à jour dans la base de données en utilisant l'objet CreateMatierDto
            matieresService.updateMatiere(matiereId, matiereDto);

            redirectAttributes.addFlashAttribute("message", "L'image a été téléchargée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Une erreur s'est produite lors du téléchargement de l'image.");
        }

        return "redirect:/Matieres/" + matiereId;
    }*/


    @Autowired
    MatieresRepository matieresRepository;
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadImage(@PathVariable("id") Long matiereId,
                                              @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez sélectionner un fichier");
        }
        try {
            // Enregistrer le fichier sur le serveur
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName); // Utilisez File.separator
            Files.write(filePath, file.getBytes());

            // Créer une instance File pour représenter le fichier
            File imageFile = new File();
            imageFile.setName(fileName);
            imageFile.setURL("/images/" + fileName);

            // Sauvegarder l'entité File dans la base de données
            filesService.saveFile(imageFile);

            // Associer le fichier à la matière
            Matieres matiere = matieresService.getMatiereById(matiereId);
            matiere.setFile(imageFile);
            matieresRepository.save(matiere);

            // Si le téléchargement est réussi, renvoyer une réponse avec le statut 201
            return ResponseEntity.status(HttpStatus.CREATED).body("image ajouter ");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors du téléchargement de l'image.");
        }
    }
}
