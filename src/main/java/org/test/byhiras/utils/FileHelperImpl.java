package org.test.byhiras.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by leszek on 26/03/17.
 */
public class FileHelperImpl implements FileHelper {

    private static final Logger logger = LoggerFactory.getLogger(FileHelperImpl.class);

    @Override
    public List<String> readFromFile(String name) {
        logger.info("Reading from file: " + name);
        List<String> list = null;
        try {
            list = Files.readAllLines(new ClassPathResource(name).getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
