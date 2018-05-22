package com.kerdotnet.utility.executors;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Singleton executor for email send works
 * Yevhen Ivanov, 2018-05-22
 */
public class EmailSendExecutorFactory {
    private static ThreadPoolExecutor executorInstance;

    private EmailSendExecutorFactory() {
    }

    public static synchronized ThreadPoolExecutor getExecutor() {
        if (executorInstance == null){
            executorInstance = new ThreadPoolExecutor(
                    10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());;
        }
        return executorInstance;
    }
}
