/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.etl.config;

import com.sacooliveros.escale.client.EscaleClientServiceConfig;
import com.sacooliveros.escale.etl.util.ResourceHelper;

import java.util.Properties;

/**
 * @author Ricardo
 */
public class ServerConfiguration {
    public static final String SEPARATOR = ",";
    protected final String brokerName;
    protected final int timeForThreads;
    protected final int brokerInterval;
    protected final int numThreads;
    protected final String serverName;
    protected final long timeout;
    private Properties config;
    private EscaleClientServiceConfig clientConfig;
    private String[] levels;
    private String[] states;
    private String[] years;
    private String expandLevel;
    private String myBatisResource;
    private int instituteBlock;

    public ServerConfiguration(String configname) {
        this(ResourceHelper.loadConfig(configname));
    }

    public ServerConfiguration(Properties config) {
        this.config = config;
        brokerName = config.getProperty("BrokerName");
        timeForThreads = Integer.parseInt(config.getProperty("BrokerTimeToBusyThreads"));
        brokerInterval = Integer.parseInt(config.getProperty("BrokerInterval"));

        numThreads = Integer.parseInt(config.getProperty("ControllerThreads"));
        serverName = config.getProperty("SIXServer");

        timeout = Long.parseLong(config.getProperty("proxy.timeout"));

        this.configClientService();
        this.configClientFilter();
        this.myBatisResource = config.getProperty("escale.persistence", "mybatis-config.xml");
        this.instituteBlock = Integer.parseInt(config.getProperty("escale.rest.institutesBlock", "50"));
    }

    private void configClientService() {
        this.clientConfig = new EscaleClientServiceConfig();
        this.clientConfig.setUrl(config.getProperty("escale.rest.url", "http://escale.minedu.gob.pe/padron/rest/instituciones"));
        this.clientConfig.setPathCount(config.getProperty("escale.rest.pathCount", "cuenta"));
        this.clientConfig.setPathInstitutes(config.getProperty("escale.rest.pathInstitutes", ""));
        this.clientConfig.setPathInstituteDetail(config.getProperty("escale.rest.pathInstituteDetail", "{0}/0/"));

    }

    private void configClientFilter() {
        this.levels = readAndSplit("escale.levels");
        this.states = readAndSplit("escale.states");
        this.years = readAndSplit("escale.years");
        this.expandLevel = config.getProperty("escale.expandLevel", "");
    }

    private String[] readAndSplit(String param) {
        String readConfig = config.getProperty(param, "");
        return readConfig.split(SEPARATOR);
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


    public long getTimeout() {
        return timeout;
    }


    public String[] getLevels() {
        return levels;
    }

    public String[] getStates() {
        return states;
    }

    public String[] getYears() {
        return years;
    }

    public String getExpandLevel() {
        return expandLevel;
    }

    public EscaleClientServiceConfig getClientConfig() {
        return clientConfig;
    }

    public String getMyBatisResource() {
        return myBatisResource;
    }

    public int getInstituteBlock() {
        return instituteBlock;
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" + "brokerName=" + brokerName + ", timeForThreads=" + timeForThreads + ", brokerInterval=" + brokerInterval + ", numThreads=" + numThreads + ", serverName=" + serverName + '}';
    }
}
