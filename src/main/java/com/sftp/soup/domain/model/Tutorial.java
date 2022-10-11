package com.sftp.soup.domain.model;

import lombok.Data;

@Data
public class Tutorial {

    private long id;
    private String name;
    private long idUser;
    private boolean activo;
    private String url;
    private String base64;

}

