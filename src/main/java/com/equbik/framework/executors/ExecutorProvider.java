package com.equbik.framework.executors;

import com.equbik.framework.executors.exceptions.ExecutorException;
import com.equbik.framework.models.input_models.Environment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * Emil Vasilyev
 * emilvasily@gmail.com
 * https://www.linkedin.com/in/emilvas/
 **/

public class ExecutorProvider {

    /*
     * ExecutorProvider class is used to provide the correct Executor with Environment settings
     */

    private static final Logger logger = Logger.getLogger(ExecutorProvider.class.getName());
    private final Executor executor;

    public ExecutorProvider(String executorIgnoreCase, Environment.Executor executorConfig){
        Class<?> executorClass = getExecutorClass(executorIgnoreCase);
        Constructor<?> executorConstructor = getExecutorConstructor(executorClass);
        this.executor = getExecutor(executorConstructor, executorConfig);
    }

    private Class<?> getExecutorClass(String executor) {
        try {
            return Class.forName("com.equbik.framework.executors." + executor);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            logger.warning("Executor class not found. Execution is being skipped.");
            throw new ExecutorException("Executor class not found");
        }
    }

    private Constructor<?> getExecutorConstructor(Class<?> executorClass) {
        if (Executor.class.isAssignableFrom(executorClass)) {
            try {
                return executorClass.getDeclaredConstructor(Environment.Executor.class);
            } catch (NoSuchMethodException e) {
                logger.warning("Error creating executor's constructor. Execution is being skipped.");
                throw new ExecutorException("Error creating executor's constructor");
            }
        } else {
            logger.warning("Wrong executor type. Execution is being skipped.");
            throw new ExecutorException("Wrong executor type");
        }
    }

    private Executor getExecutor(Constructor<?> executorConstructor, Environment.Executor executorConfig) {
        try {
            return (Executor) executorConstructor.newInstance(executorConfig);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.warning("Error during executor Object creation. Execution is being skipped.");
            throw new ExecutorException("Error during executor Object creation");
        }
    }

    public Executor getExecutor() {
        return executor;
    }

}
