package com.example.demo.core.interfaces.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileUploadController {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/upload")
    public String upload(MultipartFile uploadFile, HttpServletRequest req) {
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        String oldFilename = uploadFile.getOriginalFilename();
        String[] splitedNames = oldFilename.split("\\.");
        String newFileName;
        if (splitedNames.length == 2) {
            newFileName = splitedNames[0] + "-" + UUID.randomUUID().toString() + "." + splitedNames[splitedNames.length-1];
        } else {
            newFileName = oldFilename + UUID.randomUUID();
        }

        try {
            uploadFile.transferTo(new File(folder, newFileName));
            String filePath =
                    req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() +
                            "/uploadFile/" + format + "/" + newFileName;
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Upload failed.";
    }
}
