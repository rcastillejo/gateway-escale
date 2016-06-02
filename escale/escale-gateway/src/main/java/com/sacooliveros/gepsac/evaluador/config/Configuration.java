/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.gepsac.evaluador.config;

import com.sacooliveros.gepsac.evaluador.task.Type;
import com.sacooliveros.gepsac.evaluador.util.ResourceHelper;

import java.util.List;
import java.util.Properties;

/**
 * @author Ricardo
 */
public class Configuration {

    protected final String brokerName;
    protected final int timeForThreads;
    protected final int brokerInterval;

    protected final int numThreads;
    protected final String serverName;
    protected final long timeout;

    protected List<Type> types;

    public Configuration(String configname) {
        this(ResourceHelper.loadConfig(configname));
    }

    public Configuration(Properties config) {
        brokerName = config.getProperty("BrokerName");
        timeForThreads = Integer.parseInt(config.getProperty("BrokerTimeToBusyThreads"));
        brokerInterval = Integer.parseInt(config.getProperty("BrokerInterval"));

        numThreads = Integer.parseInt(config.getProperty("ControllerThreads"));
        serverName = config.getProperty("SIXServer");

        timeout = Long.parseLong(config.getProperty("proxy.timeout"));

        configType(config.getProperty("BrokerTypes"));
    }

    private void configType(String typeConfig) {
        String[] typesSplit;

        typesSplit = typeConfig.split(",");

        for (String string : typesSplit) {
            types.add(Type.valueOf(string));
        }
    }

    public String getBrokerName() {
        return brokerName;
    }

    public int getTimeForThreads() {
        return timeForThreads;
    }

    public int getBrokerInterval() {
        return brokerInterval;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public String getServerName() {
        return serverName;
    }

    public List<Type> getTypes() {
        return types;
    }

    public long getTimeout() {
        return timeout;
    }

    @Override
    public String toString() {
        return "Configuration{" + "brokerName=" + brokerName + ", timeForThreads=" + timeForThreads + ", brokerInterval=" + brokerInterval + ", numThreads=" + numThreads + ", serverName=" + serverName + '}';
    }

}
