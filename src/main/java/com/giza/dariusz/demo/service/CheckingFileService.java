package com.giza.dariusz.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckingFileService {

    private static final String DIRECTORY_HOME = "src/main/java/com/giza/dariusz/demo/home/";
    private static final String TARGET_DEV = "src/main/java/com/giza/dariusz/demo/dev/";
    private static final String TARGET_TEST = "src/main/java/com/giza/dariusz/demo/test/";
    private static final String JAR = ".jar";
    private static final String XML = ".xml";
    private int devCount = 0;
    private int testCount = 0;
    private int count = 0;
    public static final Path PATH = Path.of(DIRECTORY_HOME);

    public void moveFileToDevOrTest(String filename) throws IOException {

        if ((isFile(filename, JAR) && isEvenHour()) || isFile(filename, XML)) {
            moveFile(filename, TARGET_DEV);
            devCount++;
            createCountFile();
        }

        if (isFile(filename, JAR) && !isEvenHour()) {
            moveFile(filename, TARGET_TEST);
            testCount++;
            createCountFile();
        }

    }

    private void moveFile(String filename, String url) throws IOException {
        Path fileToMovePath = Paths.get(DIRECTORY_HOME + filename);
        Path targetPath = Paths.get(url + filename);
        Files.move(fileToMovePath, targetPath);
    }

    private void createCountFile() throws IOException {
        count++;
        File file = new File(DIRECTORY_HOME, "count.txt");
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("Count:" + count);
        writer.newLine();
        writer.write("Count dev: " + devCount);
        writer.newLine();
        writer.write("Count test: " + testCount);
        writer.newLine();
        writer.close();
    }

    private boolean isEvenHour() throws IOException {
        Object creationTime = Files.getAttribute(PATH, "creationTime");
        LocalDateTime localDateTime = new Timestamp(((FileTime) creationTime).toMillis()).toLocalDateTime();
        return (localDateTime.getHour() % 2) == 0;
    }

    private boolean isFile(String filename, String pattern) {
        String substring = filename.substring(filename.length() - 4);
        return substring.equals(pattern);
    }
}
