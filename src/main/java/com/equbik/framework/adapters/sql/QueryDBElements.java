//package com.equbik.framework.adapters.sql;
//
//import com.equbik.framework.models.json_model.Scenario;
//import com.equbik.framework.models.json_model.Step;
//import com.equbik.framework.services.dictionaries.Fields;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.logging.Logger;
//
//public class QueryDBElements {
//

//DEPRECATED, BUT CAN BE USED FOR FURTHER OPTIMIZATION

//    /*
//     * QueryDBElements class ...
//     */
//
//    private static final Logger logger = Logger.getLogger(QueryDBElements.class.getName());
//    private final Scenario scenario;
//    private final List<Step> steps;
//    private final String tableName;
//
//    public QueryDBElements(Scenario scenario){
//        this.scenario = scenario;
//        this.steps = scenario.getSteps();
//        this.tableName = scenario.getEnvironment().getAdapter().getTable();
//    }
//
//    public List<String> getAllScenarioElements() {
//        List<String> queries = new LinkedList<>();
//        List<String> elementsInStep;
//        for (Step step : steps) {
//            elementsInStep = step.getElements();
//            String namesIn = String.join("', '", elementsInStep);
//            String query = "SELECT * FROM " + tableName + " WHERE " + Fields.scenario + "='" + scenario.getFlowName() + "' AND " + Fields.step + "='" + step.getStepName() + "' AND " + Fields.name + " IN ('" + namesIn + "') ";
//            String order = "ORDER BY CASE";
//            String s = "";
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < elementsInStep.size(); i++) {
//                sb.append(" WHEN " + Fields.name + " = '" + String.join("", elementsInStep.get(i)) + "' THEN " + (i + 1));
//                s = String.join(" ", sb);
//            }
//            order = order + s + " END;";
//            String complete = query + order;
//            logger.fine("Query added: " + complete);
//            queries.add(complete);
//        }
//        return queries;
//    }
//
//}
