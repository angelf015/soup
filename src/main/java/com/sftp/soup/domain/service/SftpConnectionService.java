package com.sftp.soup.domain.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;

public interface SftpConnectionService {
    void whenUploadFileUsingJsch_thenSuccess(String base64) throws JSchException, SftpException, IOException;

    String whenDownloadFileUsingJsch_thenSuccess() throws JSchException, SftpException, IOException;
}
