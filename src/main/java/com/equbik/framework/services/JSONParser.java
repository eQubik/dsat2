package com.equbik.framework.services;

import com.equbik.framework.models.input_models.Environment;
import com.equbik.framework.models.input_models.Scenario;
import com.equbik.framework.models.output_models.SuiteResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class JSONParser {

    /*
     * JSONParser class. Hope there is no need to describe what it's used for
     */

    //TODO
    // To be refactored

    private static final Logger logger = Logger.getLogger(JSONParser.class.getName());

    public static Scenario parseScenario(String scJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        Scenario scenario;
        try {
            Path filePath = Path.of(scJson);
            String content = Files.readString(filePath);
            scenario = objectMapper.readValue(content, Scenario.class);
        } catch (IOException e) {
            logger.warning("Skipping execution due to: " + e.getMessage());
            throw new RuntimeException("Skipping execution due to: " + e.getMessage());
        }
        return scenario;
    }

    public static Environment parseEnvironment(String envJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        Environment environment;
        try {
            Path filePath = Path.of(envJson);
            String content = Files.readString(filePath);
            environment = objectMapper.readValue(content, Environment.class);
        } catch (IOException e) {
            logger.warning("Skipping execution due to: " + e.getMessage());
            throw new RuntimeException("Skipping execution due to: " + e.getMessage());
        }
        return environment;
    }

    public static void convertSuite(SuiteResult suiteResult) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File directory = new File("results");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory + "/" + suiteResult.getSuiteName() + ".json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, suiteResult);
        } catch (IOException e) {
            logger.warning("Skipping execution due to: " + e.getMessage());
            throw new RuntimeException("Skipping execution due to: " + e.getMessage());
        }
    }

}
