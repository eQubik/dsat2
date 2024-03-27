package com.equbik;

import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class Launcher {

    private static final Logger logger = Logger.getLogger(Launcher.class.getName());

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        /*
         * Launcher class to start the app using CMD. Just provide the path to your scenario
         */

        //PROD

//        String scenarioPath = args[0];
//        String printArgument = args[1];
//        boolean print = printArgument.equals("1");
//        DSAT app = new DSAT(scenarioPath, print);

        //DEV

        String envPath = "C:\\Users\\Emil\\IdeaProjects\\DSATLOGIC\\TIM\\scenarios\\env.json";
        String[] scenarios = {
                "C:\\Users\\Emil\\IdeaProjects\\DSATLOGIC\\TIM\\scenarios\\sc1.json"
        };
        DSAT app = new DSAT(envPath, scenarios);
        app.performScenario();
        System.out.println(app.getSuiteResult());

        /*
                Environment.Adapter adapter = environment.getAdapter();
        //Checking if an adapter is configured
        if (!isAdapterProvided(adapter))
            throw new RuntimeException("Adapter is not provided, or not valid");
                private boolean isAdapterProvided(Environment.Adapter adapter) {
        return adapter != null;
    }
         */
    }

}
