package com.equbik.framework.executors;

import com.equbik.framework.models.json_model.Environment;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class RestAssured implements Executor {

    //TODO
    // Finish

    private static final Logger logger = Logger.getLogger(RestAssured.class.getName());
    private final String baseUrl;
    private final Long socketTimeout;
    private final Long connectionTimeout;

    public RestAssured(Environment.Executor executor){
        this.baseUrl = executor.getBaseUrl();
        this.socketTimeout = nullValueNumber(executor.getSocketTimeout());
        this.connectionTimeout = nullValueNumber(executor.getConnectionTimeout());
    }

    private Long nullValueNumber(Long value){
        return value != null ? value : Long.valueOf(0L);
    }

    public String getBaseUrl() {
        if(baseUrl != null) {
            return baseUrl;
        } else {
            logger.warning("Base url can't be a null value. Execution is being skipped.");
            throw new RuntimeException("Base url can't be a null value");
        }
    }

    public Long getSocketTimeout() {
        return socketTimeout;
    }

    public Long getConnectionTimeout() {
        return connectionTimeout;
    }

}
