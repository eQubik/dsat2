//package com.equbik.framework;
//
//import com.equbik.framework.models.input_models.Environment;
//
//import java.util.logging.Logger;
//
///**
// * Emil Vasilyev
// * emilvasily@gmail.com
// * https://www.linkedin.com/in/emilvas/
// **/
//
//public class Adapter {
//
//    /*
//     * Adapter class is used to get configuration information about the data structure that you use in your scenario
//     */
//
//    private static final Logger logger = Logger.getLogger(Adapter.class.getName());
//    private final String adapter;
//    private final String adapterPath;
//
//    public Adapter(Environment.Adapter adapter){
//        this.adapter = nullValueField(adapter.getAdapter());
//        this.adapterPath = nullValueField(adapter.getAdapterPath());
//    }
//
//    private String nullValueField(String value){
//        try{
//            return value;
//        } catch (NullPointerException e){
//            logger.warning("Adapter's field can't be a null value. Execution is being skipped.");
//            throw new RuntimeException("Adapter's field can't be a null value");
//        }
//    }
//
//    public String getAdapter() {
//        return adapter;
//    }
//
//    public String getAdapterPath() {
//        return adapterPath;
//    }
//
//}
