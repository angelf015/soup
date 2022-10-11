package com.sftp.soup.domain.service;

import com.sftp.soup.domain.model.Tutorial;
import org.springframework.http.ResponseEntity;

public interface DocumentsService {
    ResponseEntity<String> addDocument(Tutorial tutorial);

    ResponseEntity<Tutorial> getDocument(long id);
}
