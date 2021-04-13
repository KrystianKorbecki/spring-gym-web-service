package com.api.gym.ftp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FTPClientImpl extends FTPClient
{
    @Value("${ftp.hostname}")
    private String hostname;

    @Value("${ftp.userName}")
    private String userName;

    @Value("${ftp.password}")
    private String password;

    public void connect() throws IOException
    {
        super.connect(hostname);
    }

    public boolean login() throws IOException {
        return super.login(userName, password);
    }
}
