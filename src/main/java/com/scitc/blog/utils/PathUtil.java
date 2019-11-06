package com.scitc.blog.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathUtil {

    private static String UPLOAD_WINDOW;
    private static String UPLOAD_LINUX;

    @Value(value = "${file.uploadWindow}")
    public void setUploadWindow(String uploadWindow) {
        UPLOAD_WINDOW = uploadWindow;
    }

    @Value("${file.uploadLinux}")
    public void uploadLinux(String uploadLinux) {
        UPLOAD_LINUX = uploadLinux;
    }

    public static String ImageBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().contains("win")) {
            basePath = UPLOAD_WINDOW;
        } else {
            basePath = UPLOAD_LINUX;
        }
        return basePath;
    }

    public static String getImagePath(Integer userId) {
        String imagePath = "/upload/user/" + userId + "/";
        return imagePath;
    }

}
