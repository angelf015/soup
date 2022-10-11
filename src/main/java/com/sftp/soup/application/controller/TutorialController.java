package com.sftp.soup.application.controller;

import com.sftp.soup.domain.model.Tutorial;
import com.sftp.soup.domain.repository.TutorialRepository;
import com.sftp.soup.domain.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    private final TutorialRepository tutorialRepository;
    private final DocumentsService documentsService;

    @Autowired
    public TutorialController(TutorialRepository tutorialRepository, DocumentsService documentsService) {
        this.tutorialRepository = tutorialRepository;
        this.documentsService = documentsService;
    }

    @GetMapping("/documents")
    public ResponseEntity<List<Tutorial>> getAllDocuments(@RequestParam(required = false) Long idUser) {
        try {
            List<Tutorial> tutorials = new ArrayList<>();

            if (idUser == null)
                tutorials.addAll(tutorialRepository.findAll());
            else
                tutorials.addAll(tutorialRepository.findByIdUserContaining(idUser));

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        return documentsService.getDocument(id);
    }

    @PostMapping("/addDocuments")
    public ResponseEntity<String> addDocuments(@RequestBody Tutorial tutorial) {
        return documentsService.addDocument(tutorial);
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<String> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.findById(id);

        if (_tutorial != null) {
            _tutorial.setId(id);
            _tutorial.setName(tutorial.getName());
            _tutorial.setIdUser(tutorial.getIdUser());
            _tutorial.setActivo(tutorial.isActivo());
            _tutorial.setUrl(tutorial.getUrl());

            tutorialRepository.update(_tutorial);
            return new ResponseEntity<>("Tutorial was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable("id") long id) {
        try {
            int result = tutorialRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Tutorial with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Tutorial was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete tutorial.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/documents")
    public ResponseEntity<String> deleteAllTutorials() {
        try {
            int numRows = tutorialRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Tutorial(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete tutorials.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/documents/activo/{idUser}")
    public ResponseEntity<List<Tutorial>> findByPublished(@PathVariable("idUser") long idUser) {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByActivo(true, idUser);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
