package com.equbik;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class Launcher {

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        /*
         * Launcher class to start the app using CMD. Just provide the path to your environment and scenarios
         */

        //PROD

        // The first argument is environmentPath
        String envPath = args[0];

        // The rest of the arguments are scenarios
        String[] scenarios = new String[args.length - 1];
        System.arraycopy(args, 1, scenarios, 0, args.length - 1);

        DSAT app = new DSAT(envPath, scenarios);
        app.performSuite();

        //DEV

//        String envPath = "src/main/resources/scenarios/env.json";
//        String[] scenarios = {
//                "src/main/resources/scenarios/sc_wiki.json"
//        };
//        DSAT app = new DSAT(envPath, scenarios);
//        app.performSuite();
//        //System.out.println(app.getSuiteResult());

    }

}
