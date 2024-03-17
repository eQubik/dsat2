package com.equbik.framework.adapters.sql;

import com.equbik.framework.models.element_model.Element;
import com.equbik.framework.services.Fields;
import com.equbik.framework.services.StaticVariables;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class MapSQLiteElements {

    /*
     * MapSQLiteElements class is used for mapping SQLite model to Element class
     */

    private static final Logger logger = Logger.getLogger(MapSQLiteElements.class.getName());
    private static MapSQLiteElements sqlLite = null;
    private final String jdbcURL;

    private MapSQLiteElements(String dbPath){
        this.jdbcURL = dbPath;
    }

    public static MapSQLiteElements getInstance(String dbPath){
        if(sqlLite == null)
            sqlLite = new MapSQLiteElements(dbPath);
        return sqlLite;
    }

    public List<Element> getElementsList(String query){
        try (Connection connection = DriverManager.getConnection(jdbcURL);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return dbElements(resultSet);
        } catch (SQLException e) {
            logger.warning("Skipping execution due to: " + e.getMessage());
            throw new RuntimeException("Skipping execution due to: " + e.getMessage());
        }
    }

    private List<Element> dbElements(ResultSet resultSet) throws SQLException{
        List<Element> elementsList = new LinkedList<>();
        while (resultSet.next()) {
            Element element = new Element();
            element.setId(resultSet.getInt(Fields.id.toString()));
            element.setScenario(resultSet.getString(Fields.scenario.toString()));
            element.setStep(resultSet.getString(Fields.step.toString()));
            element.setName(resultSet.getString(Fields.name.toString()));
            element.setMarker(resultSet.getString(Fields.marker.toString()));
            //If you don't know what is it used for, check the StaticVariables class
            int actionType = resultSet.getInt(Fields.action_type.toString());
            String actionName = StaticVariables.action(actionType);
            element.setActionType(new Element.Action(actionName, actionType));
            //
            element.setCode(resultSet.getString(Fields.code.toString()));
            element.setValue(resultSet.getString(Fields.value.toString()));
            element.setRelatedElement(resultSet.getString(Fields.related_element.toString()));
            elementsList.add(element);
        }
        return elementsList;
    }

}
