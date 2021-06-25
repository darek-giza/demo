package com.giza.dariusz.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    public static final String DIRECTORY_HOME = "src/main/java/com/giza/dariusz/demo/home/";

    public String storeFileInHome(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String name = DIRECTORY_HOME + file.getOriginalFilename();

                BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();

            } catch (Exception e) {
                log.error("File saving failure: ", e);
                throw new IOException("File saving failure");
            }
        }
        return file.getOriginalFilename();
    }
}
