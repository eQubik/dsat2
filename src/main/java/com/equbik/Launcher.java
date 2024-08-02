package com.equbik;

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

        String envPath = args[0];
        String[] scenarios = new String[args.length - 1];
        System.arraycopy(args, 1, scenarios, 0, args.length - 1);

        DSAT app = new DSAT(envPath, scenarios);
        app.performSuite();
        app.getSuiteResult();

        //DEV

//        String envPath = "src/main/resources/scenarios/env.json";
//        String[] scenarios = {
//                "src/main/resources/scenarios/robot.json"
//        };
//        DSAT app = new DSAT(envPath, scenarios);
//        app.performSuite();
//        app.getSuiteResult();

    }

}
