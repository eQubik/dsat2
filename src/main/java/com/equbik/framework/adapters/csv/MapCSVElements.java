package com.equbik.framework.adapters.csv;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.Fields;
import com.equbik.framework.services.StaticVariables;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class MapCSVElements {

    /*
     * MapCSVToElement class is used for mapping csv model to Element class
     */

    private static final Logger logger = Logger.getLogger(MapCSVElements.class.getName());
    private final String csvFile;
    private final String[] HEADERS = {
            Fields.id.toString(),
            Fields.scenario.toString(),
            Fields.step.toString(),
            Fields.name.toString(),
            Fields.marker.toString(),
            Fields.action_type.toString(),
            Fields.code.toString(),
            Fields.value.toString(),
            Fields.related_element.toString()
    };

    public MapCSVElements(String csvFile) {
        this.csvFile = csvFile;
    }

    public List<Element> csvToElementsList() {
        List<Element> elementsList = new ArrayList<>();
        try (Reader reader = new FileReader(csvFile, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, getFormat())) {
            for (CSVRecord csvRecord : csvParser) {
                Integer id = Integer.parseInt(csvRecord.get(0));
                String scenario = csvRecord.get(1);
                String step = csvRecord.get(2);
                String name = csvRecord.get(3);
                String marker = csvRecord.get(4);
                String code = csvRecord.get(6);
                String value = csvRecord.get(7);
                String relatedElement = csvRecord.get(8);
                int actionType = Integer.parseInt(csvRecord.get(5));
                String actionName = StaticVariables.action(actionType);

                Element element = new Element(id, scenario, step, name, marker, new Element.Action(actionName, actionType), code, value, relatedElement);
                elementsList.add(element);
            }
            return elementsList;
        } catch (IOException e) {
            logger.warning("Skipping execution due to: " + e.getMessage());
            throw new RuntimeException("Skipping execution due to: " + e.getMessage());
        }
    }

    private CSVFormat getFormat() {
        return CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();
    }

}
