package com.equbik.framework.adapters;

import com.equbik.framework.adapters.csv.AdapterCSV;
import com.equbik.framework.adapters.csv.MapCSVElements;
import com.equbik.framework.adapters.exceptions.AdapterException;
import com.equbik.framework.adapters.sql.AdapterSQL;
import com.equbik.framework.adapters.sql.MapSQLiteElements;
import com.equbik.framework.models.input_models.Scenario;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AdapterProvider {

    /*
     * AdapterProvider class is used to provide Adapter implementation according to the adapter configuration
     */

    private static final Logger logger = Logger.getLogger(AdapterProvider.class.getName());
    private final Adapter adapter;

    public AdapterProvider(AdapterConfig adapterConfig, Scenario scenario){
        this.adapter = provideAdapter(adapterConfig, scenario);
    }

    private Adapter provideAdapter(AdapterConfig adapterConfig, Scenario scenario){
        if(adapterConfig instanceof MapSQLiteElements){
            logger.fine("SQLite adapter provided for scenario " + scenario.getName());
            return new AdapterSQL(scenario, adapterConfig);
        } else if (adapterConfig instanceof MapCSVElements){
            logger.fine("CSV adapter provided for scenario " + scenario.getName());
            return new AdapterCSV(scenario, adapterConfig);
        } else {
            logger.warning("Wrong adapter type. Execution is being Skipped.");
            throw new AdapterException("Wrong adapter type");
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

}
