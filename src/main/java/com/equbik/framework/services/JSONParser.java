package com.equbik.framework.services;

import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.models.json_model.Scenario;
import com.fasterxml.jackson.databind.ObjectMapper;

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

}
