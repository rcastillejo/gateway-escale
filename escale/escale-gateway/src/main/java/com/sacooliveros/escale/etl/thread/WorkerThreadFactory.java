/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.etl.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ricardo
 */
public class WorkerThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadTrace;
    private final StringBuilder prefix;

    public WorkerThreadFactory(String poolName) {
        threadTrace = new AtomicInteger(1);
        group = new ThreadGroup(poolName);
        prefix = new StringBuilder(poolName);
        prefix.append("-");
    }

    public String getName(int trace) {
        return prefix.toString() + trace;
    }

    @Override
    public Thread newThread(Runnable runable) {
        Thread t = new Thread(group, runable, getName(threadTrace.getAndIncrement()), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
