package com.sftp.soup.application.serviceImp;

import com.sftp.soup.domain.model.Tutorial;
import com.sftp.soup.domain.repository.TutorialRepository;
import com.sftp.soup.domain.service.DocumentsService;
import com.sftp.soup.domain.service.SftpConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DocumentsServiceImpl implements DocumentsService {
    private final SftpConnectionService sftpConnectionService;
    private final TutorialRepository tutorialRepository;

    @Autowired
    public DocumentsServiceImpl(SftpConnectionService sftpConnectionService, TutorialRepository tutorialRepository) {
        this.sftpConnectionService = sftpConnectionService;
        this.tutorialRepository = tutorialRepository;
    }

    @Override
    public ResponseEntity<String> addDocument(Tutorial tutorial) {
        try {
//            sftpConnectionService.whenUploadFileUsingJsch_thenSuccess(tutorial.getBase64());
            tutorialRepository.save(tutorial);
            return new ResponseEntity<>("Tutorial was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Tutorial> getDocument(long id) {
        try {
            String base64 = sftpConnectionService.whenDownloadFileUsingJsch_thenSuccess();

            Tutorial tutorial = tutorialRepository.findById(id);
            if (tutorial != null) {
                tutorial.setBase64(base64);
                return new ResponseEntity<>(tutorial, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
