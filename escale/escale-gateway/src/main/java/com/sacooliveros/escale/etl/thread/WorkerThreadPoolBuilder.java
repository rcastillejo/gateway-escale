/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.etl.thread;

import com.sacooliveros.escale.etl.config.ServerConfiguration;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricardo
 */
public class WorkerThreadPoolBuilder {
    public static final String NAME = "worker";

    private WorkerThreadPoolBuilder() {
    }


    public static ThreadPoolExecutor createThreadFactory(int numberThreads) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numberThreads,
                numberThreads, 30, TimeUnit.MINUTES,
                new SynchronousQueue<Runnable>(),
                new WorkerThreadFactory(NAME));

        return threadPoolExecutor;
    }
}
