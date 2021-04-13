package com.api.gym.ftp;

import com.api.gym.repository.implementation.UserService;
import com.api.gym.service.users.UsersService;
import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.Property;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FTPUtils
{
    Logger logger = LoggerFactory.getLogger(FTPUtils.class);
    UsersService usersService;
    UserService userService;

    FTPUtils(UsersService usersService, UserService userService)
    {
        this.usersService = usersService;
        this.userService = userService;
    }


    private static Property property = null;

    private static String userName = "ftpuser";

    private static String passWord = "2556";

    private static String hostName = "localhost";

    private static int port = 21;

    private static String path = "/";

    private static String header = "";

    private static String configFile = "application.properties";

    private static String tmstctpFilePath;

    public static boolean closeFTP(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
                System.out.println ( "ftp has been closed");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static String uploadFile(String partPath, byte[] decoderBytes,String imgName) throws Exception {
        FTPClient ftp = new FTPClient();

        try {
            // connect to the FTP server
            ftp.connect(hostName, port);
            // The following three lines of code must be, and can not change the encoding format, or can not download the file correctly Chinese
            ftp.setControlEncoding("GBK");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            // Log in ftp
            ftp.login(userName, passWord);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println ( "server connection failure");
            }
            System.out.println ( "login server successfully");
            String realPath = path + "/" + partPath;
            boolean changeWD = ftp.changeWorkingDirectory (realPath); // transferred to the specified FTP server directory
            if (!changeWD) {
                if (!CreateDirecroty(realPath, ftp)) {
                    throw new Exception ( "Failed to create remote folder!");
                }
            }
            FTPFile [] fs = ftp.listFiles (); // get the file directory list
            String fileName = imgName;
            fileName = FTPUtils.changeName(fileName, fs);
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            realPath = new String(realPath.getBytes("GBK"), "ISO-8859-1");
            // Go to the specified upload directory
            ftp.changeWorkingDirectory(realPath);
            // stores to upload files to a specified directory
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            // If the default is normal but the sentence transmission txt file transfers pictures, and other formats garbled
            ftp.storeFile(fileName, new ByteArrayInputStream(decoderBytes));
            // exit ftp
            ftp.logout();
            System.out.println ( "upload success ......");
            return header + realPath + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // close connection ftp
            closeFTP(ftp);
        }
    }

    /**
     * Determine whether the existing file.
     *
     * @param fileName
     * @param fs
     * @return
     */
    public static boolean isFileExist(String fileName, FTPFile[] fs) {
        for (int i = 0; i < fs.length; i++) {
            FTPFile ff = fs[i];
            if (ff.getName().equals(fileName)) {
                return true; // return if there is a correct signal
            }
        }
        return false; // return an error if there is no signal
    }

    /**
     * Generate a new file name of the same name based on the results of judgment
     *
     * @param fileName
     * @param fs
     * @return
     */
    public static String changeName(String fileName, FTPFile[] fs) {
        int n = 0;
        // fileName = fileName.append(fileName);
        while (isFileExist(fileName.toString(), fs)) {
            n++;
            String a = "[" + n + "]";
            int b = fileName.lastIndexOf ( "."); // last decimal place appeared
            int c = fileName.lastIndexOf ( "["); // last "[" where they appear
            if (c < 0) {
                c = b;
            }
            StringBuffer name = new StringBuffer (fileName.substring (0, c)); // file name
            StringBuffer suffix = new StringBuffer (fileName.substring (b + 1)); // name suffix
            fileName = name.append(a) + "." + suffix;
        }
        return fileName.toString();
    }

    /**
     * Create a remote server directories recursively
     *
     * @param remote
     * Remote server file absolute path
     *
     * @Return directory creation is successful
     * @throws IOException
     */
    public static boolean CreateDirecroty(String remote, FTPClient ftp) throws IOException {
        boolean success = true;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        // If the remote directory does not exist, create a remote server directories recursively
        if (!directory.equalsIgnoreCase("/") && !ftp.changeWorkingDirectory(new String(directory))) {

            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end));
                if (!ftp.changeWorkingDirectory(subDirectory)) {
                    if (ftp.makeDirectory(subDirectory)) {
                        ftp.changeWorkingDirectory(subDirectory);
                    } else {
                        System.out.println ( "Failed to create directory");
                        success = false;
                        return success;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // Check if all directory is created
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }
}
