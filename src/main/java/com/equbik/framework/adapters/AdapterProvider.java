package com.equbik.framework.adapters;

import com.equbik.framework.adapters.csv.AdapterCSV;
import com.equbik.framework.adapters.csv.MapCSVElements;
import com.equbik.framework.adapters.sql.AdapterSQL;
import com.equbik.framework.adapters.sql.MapSQLiteElements;
import com.equbik.framework.models.json_model.Environment;
import com.equbik.framework.models.json_model.Scenario;
import com.equbik.framework.services.Adapters;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AdapterProvider {

    /*
     * AdapterCSV class provides Adapter implementation and forms the Elements list for each step
     */

    private final Adapter adapter;

    public AdapterProvider(AdapterConfig adapterConfig, Scenario scenario){
        this.adapter = provideAdapterConfig(adapterConfig, scenario);
    }

    private Adapter provideAdapterConfig(AdapterConfig adapterConfig, Scenario scenario){
        //if(adapterConfig.getClass().isInstance(MapSQLiteElements.class)){
        if(adapterConfig.getClass().getSimpleName().equals("MapSQLiteElements")){
            //logger.info("Set adapter to csv");
            return new AdapterSQL(scenario, adapterConfig);
//        } else if (adapterConfig.getClass().isInstance(MapCSVElements.class)){
        } else if (adapterConfig.getClass().getSimpleName().equals("MapCSVElements")){
            //logger.info("Set adapter to db");
            return new AdapterCSV(scenario, adapterConfig);
        } else {
            //logger.warning("Wrong adapter type. Execution is being Skipped.");
            throw new RuntimeException("Wrong adapter type");
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

}
