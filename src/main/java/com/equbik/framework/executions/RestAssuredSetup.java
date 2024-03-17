package com.equbik.framework.executions;

import com.equbik.framework.executors.Executor;
import com.equbik.framework.executors.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class RestAssuredSetup implements Execution {

    /*
     * RestAssuredSetup class is used to initialize RestAssured execution with all the necessary settings
     */

    private static final Logger logger = Logger.getLogger(RestAssuredSetup.class.getName());

    public RestAssuredSetup(Executor executor){
        RestAssured restAssured = (RestAssured) executor;
        io.restassured.RestAssured.baseURI = restAssured.getBaseUrl();
        logger.info("RestAssured baseURL set to: " + restAssured.getBaseUrl());
        setSocketTimeout(restAssured.getSocketTimeout());
        setConnectionTimeout(restAssured.getConnectionTimeout());
    }

    private void setSocketTimeout(Long timeout){
        if(timeout != 0) {
            io.restassured.RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.socket.timeout", timeout));
            logger.info("RestAssured socket timeout is set to " + timeout);
        }
    }

    private void setConnectionTimeout(Long timeout) {
        if (timeout != 0) {
            io.restassured.RestAssured.config = RestAssuredConfig.config()
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", timeout));
            logger.info("RestAssured connection timeout is set to " + timeout);
        }
    }

}
