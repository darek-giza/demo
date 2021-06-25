package com.giza.dariusz.demo;

import com.giza.dariusz.demo.service.CheckingFileService;
import com.giza.dariusz.demo.service.WatchHomeDirectoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    private static final String DIRECTORY_HOME = "src/main/java/com/giza/dariusz/demo/home";
    private static final String DIRECTORY_DEV = "src/main/java/com/giza/dariusz/demo/dev";
    private static final String DIRECTORY_TEST = "src/main/java/com/giza/dariusz/demo/test";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoApplication.class, args);
        createDir();
        CheckingFileService checkingFileService = new CheckingFileService();
        WatchHomeDirectoryService watchHomeDirectoryService = new WatchHomeDirectoryService(checkingFileService);
        watchHomeDirectoryService.watch();

    }

    private static void createDir() {
        new File(DIRECTORY_HOME).mkdir();
        new File(DIRECTORY_DEV).mkdir();
        new File(DIRECTORY_TEST).mkdir();

    }

}
