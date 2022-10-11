package com.sftp.soup.domain.repository;

import com.sftp.soup.domain.model.Tutorial;

import java.util.List;

public interface TutorialRepository {
    int save(Tutorial book);

    int update(Tutorial book);

    Tutorial findById(Long id);

    int deleteById(Long id);

    List<Tutorial> findAll();

    List<Tutorial> findByActivo(boolean published, long idUser);

    List<Tutorial> findByIdUserContaining(long idUser);

    int deleteAll();
}
