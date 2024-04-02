package com.equbik.framework.adapters;

import com.equbik.framework.adapters.csv.MapCSVElements;
import com.equbik.framework.adapters.exceptions.AdapterConfigException;
import com.equbik.framework.adapters.sql.MapSQLiteElements;
import com.equbik.framework.models.input_models.Environment;
import com.equbik.framework.services.dictionaries.Adapters;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class AdapterConfigProvider {

    /*
     * AdapterConfigProvider class is used to get configuration information about the data structure
     * that you use in your suite and provide all the Elements
     */

    private static final Logger logger = Logger.getLogger(AdapterConfigProvider.class.getName());
    private final String adapterName;
    private final String adapterPath;
    private final AdapterConfig adapterConfig;

    public AdapterConfigProvider(Environment environment){
        this.adapterName = nullValueField(environment.getAdapter().getAdapter());
        this.adapterPath = nullValueField(environment.getAdapter().getAdapterPath());
        this.adapterConfig = provideAdapterConfig(environment);
    }

    private String nullValueField(String value){
        if(value != null){
            return value;
        } else {
            logger.warning("Adapter field value can't be a null value. Execution is being Skipped.");
            throw new AdapterConfigException("Adapter field value can't be a null value");
        }
    }

    private AdapterConfig provideAdapterConfig(Environment environment){
        if(adapterName.equals(Adapters.csv.toString())){
            logger.info("Set adapter to csv");
            return new MapCSVElements(adapterPath);
        } else if (adapterName.equals(Adapters.db.toString())){
            logger.info("Set adapter to db");
            String adapterTable = environment.getAdapter().getTable();
            if(adapterTable == null) adapterTable = "auto_elements";
            logger.info("adapter table name is :" + adapterTable);
            String path = "jdbc:sqlite:" + adapterPath;
            return new MapSQLiteElements(path, adapterTable);
        } else {
            logger.warning("Wrong adapter type. Execution is being Skipped.");
            throw new AdapterConfigException("Wrong adapter type");
        }
    }

    public AdapterConfig getAdapterConfig() {
        return adapterConfig;
    }

}
