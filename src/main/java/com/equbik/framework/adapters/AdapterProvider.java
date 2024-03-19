package com.equbik.framework.adapters;

import com.equbik.framework.adapters.csv.AdapterCSV;
import com.equbik.framework.adapters.csv.MapCSVElements;
import com.equbik.framework.adapters.sql.AdapterSQL;
import com.equbik.framework.adapters.sql.MapSQLiteElements;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.services.Adapters;

import java.util.logging.Logger;

public class AdapterProvider {

    private static final Logger logger = Logger.getLogger(AdapterProvider.class.getName());
    private final String adapterName;
    private final String adapterPath;
    private final Adapter adapter;

    public AdapterProvider(Scenario scenario){
        this.adapterName = nullValueField(scenario.getEnvironment().getAdapter().getAdapter());
        this.adapterPath = nullValueField(scenario.getEnvironment().getAdapter().getAdapterPath());
        this.adapter = provideAdapter(scenario);
    }

    private String nullValueField(String value){
        if(value != null){
            return value;
        } else {
            logger.warning("Adapter field value can't be a null value. Execution is being Skipped.");
            throw new RuntimeException("Adapter field value can't be a null value");
        }
    }

    private Adapter provideAdapter(Scenario scenario){
        if(adapterName.equals(Adapters.csv.toString())){
            logger.info("Set adapter to csv");
            MapCSVElements csvElements = new MapCSVElements(adapterPath);
            return new AdapterCSV(scenario, csvElements);
        } else if (adapterName.equals(Adapters.db.toString())){
            logger.info("Set adapter to db");
            String adapterTable = scenario.getEnvironment().getAdapter().getTable();
            if(adapterTable == null) adapterTable = "auto_elements";
            String path = "jdbc:sqlite:" + adapterPath;
            MapSQLiteElements sqlLite = MapSQLiteElements.getInstance(path, adapterTable);
            return new AdapterSQL(scenario, sqlLite);
        } else {
            logger.warning("Wrong adapter type. Execution is being Skipped.");
            throw new RuntimeException("Wrong adapter type");
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

}
