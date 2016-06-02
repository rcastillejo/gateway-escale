/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.gepsac.evaluador.thread;

import com.sacooliveros.gepsac.evaluador.config.Configuration;

import java.util.Enumeration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ricardo
 */
public class ThreadPoolBuilder {

    private static ConcurrentHashMap<String, ThreadPoolExecutor> pools;
    private static AtomicInteger poolNumber;

    public static void init() {
        pools = new ConcurrentHashMap<String, ThreadPoolExecutor>();
        poolNumber = new AtomicInteger(1);
    }

    public static ThreadPoolExecutor createThreadFactory(Configuration config) {
        String poolName = config.getServerName();

        if (!pools.containsKey(poolName)) {
            int numberThreads = config.getNumThreads();

            ThreadFactory factory = new BaseThreadFactory(poolName, poolNumber.getAndIncrement());

            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numberThreads,
                    numberThreads, 30, TimeUnit.MINUTES,
                    new SynchronousQueue<Runnable>(), factory);

            pools.put(poolName, threadPoolExecutor);
        }
        return pools.get(poolName);
    }

    public static void execute(Configuration config, Runnable task) {
        ThreadPoolExecutor executor = createThreadFactory(config);
        executor.execute(task);
    }

    public static void destroy() {
        if (pools != null && !pools.isEmpty()) {
            Enumeration<ThreadPoolExecutor> enumeration = pools.elements();
            while (enumeration.hasMoreElements()) {
                ThreadPoolExecutor poolExecutor = enumeration.nextElement();
                try {
                    poolExecutor.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                }
            }
            pools.clear();
        }
        pools = null;
    }
}
