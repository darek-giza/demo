package com.giza.dariusz.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.rmi.server.ExportException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchHomeDirectoryService {

    private static final String DIRECTORY_HOME = "src/main/java/com/giza/dariusz/demo/home/";
    private static final Path PATH = Path.of(DIRECTORY_HOME);
    private final CheckingFileService checkingFileService ;

    public void watch() throws IOException {

        WatchService watchService = FileSystems.getDefault().newWatchService();

        try {

            PATH.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind:" + event.kind()
                        + ". File affected: " + event.context() + ".");

                    Object context =  event.context();
                    checkingFileService.moveFileToDevOrTest(context.toString());
                }
                key.reset();
            }
        } catch (Exception e) {
            log.error("File moving failure: ", e);
            throw new ExportException("");
        }


    }





}
