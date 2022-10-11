package com.sftp.soup.application.serviceImp;

import com.jcraft.jsch.*;
import com.sftp.soup.domain.service.SftpConnectionService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;

@Service
public class SftpConnectionServiceImpl implements SftpConnectionService {

    private String remoteHost = "test.rebex.net";
    private String username = "demo";
    private String password = "password";

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("C:/Users/angel/known_hosts");
//        jsch.setKnownHosts("/Users/angel/known_hosts");
        Session jschSession = jsch.getSession(username, remoteHost);
        jschSession.setPassword(password);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    @Override
    public void whenUploadFileUsingJsch_thenSuccess(String base64) throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();

        String remoteDir = "/pub/example/";


//        byte[] data = BaseEncoding.base64().decode(fileToBeUploaded);
        byte[] data = Base64.getDecoder().decode(base64);
        ;
        InputStream is = new ByteArrayInputStream(data);
//        channelSftp.put(is, filename);

        channelSftp.put(is, remoteDir + "tesdt.pdf");

        channelSftp.exit();
    }

    @Override
    public String whenDownloadFileUsingJsch_thenSuccess() throws JSchException, SftpException, IOException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();

        String localDir = "/pub/example/";

        InputStream stream = channelSftp.get(localDir + "readme.txt");

        byte[] bytes = convertInputStreamToBytes(stream);

        String encodedString = Base64.getEncoder().encodeToString(bytes);

        channelSftp.exit();
        return encodedString;
    }


    private byte[] convertInputStreamToBytes(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toByteArray();
    }
}
