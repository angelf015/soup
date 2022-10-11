package com.sftp.soup.domain.repository;

import com.sftp.soup.domain.model.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTutorialRepository implements TutorialRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTutorialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Tutorial tutorial) {
        return jdbcTemplate.update("INSERT INTO documents (name, idUser, activo, url) VALUES(?,?,?,?)",
                new Object[]{tutorial.getName(), tutorial.getIdUser(), tutorial.isActivo(), tutorial.getUrl()});
    }

    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update("UPDATE documents SET name=?, idUser=?, activo=?, url=? WHERE id=?",
                new Object[]{tutorial.getName(), tutorial.getIdUser(), tutorial.isActivo(), tutorial.getUrl(), tutorial.getId()});
    }

    @Override
    public Tutorial findById(Long id) {
        try {
            Tutorial tutorial = jdbcTemplate.queryForObject("SELECT * FROM documents WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tutorial.class), id);

            return tutorial;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM documents WHERE id=?", id);
    }

    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query("SELECT * from documents", BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public List<Tutorial> findByActivo(boolean published, long idUser) {
        return jdbcTemplate.query("SELECT * from documents WHERE activo=? and idUser = ?",
                BeanPropertyRowMapper.newInstance(Tutorial.class), published, idUser);
    }

    public List<Tutorial> findByIdUserContaining(long idUser) {
        String q = "SELECT * from documents WHERE idUser = " + idUser + "";

        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from documents");
    }
}
