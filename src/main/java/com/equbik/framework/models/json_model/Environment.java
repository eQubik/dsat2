package com.equbik.framework.models.json_model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Environment {

    /*
     * Environment class represents environment settings
     */

    private Executor executor;
    private Adapter adapter;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Highlight{
        private String color;
        private int border;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Sleep{
        private Long sec;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Adapter{
        private String adapter;
        @JsonProperty("adapter_path")
        private String adapterPath;
        private String table;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Executor{
        private String type;
        @JsonProperty("resource_url")
        private String resourceUrl;
        //Selenium parts
        private boolean remote;
        private String wait;
        @JsonProperty("host_name")
        private String hostName;
        @JsonProperty("host_port")
        private String hostPort;
        private String driver;
        private String chromium;
        private List<String> args;
        @JsonProperty("quit_driver")
        private boolean quitDriver;
        private Highlight highlight;
        private Sleep sleep;
        //RestAssured parts
        @JsonProperty("base_url")
        private String baseUrl;
        @JsonProperty("socket_timeout")
        private Long socketTimeout;
        @JsonProperty("connection_timeout")
        private Long connectionTimeout;
    }

}
