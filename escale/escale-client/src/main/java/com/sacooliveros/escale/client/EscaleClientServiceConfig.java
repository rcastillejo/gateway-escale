package com.sacooliveros.escale.client;

import com.sun.jersey.api.client.config.ClientConfig;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class EscaleClientServiceConfig {

    private String url;
    private String pathCount;
    private String pathInstitutes;
    private String pathInstituteDetail;
    private ClientConfig restConfig;
    private int institutesBlock;

    public int getInstitutesBlock() {
        return institutesBlock;
    }

    public void setInstitutesBlock(int institutesBlock) {
        this.institutesBlock = institutesBlock;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ClientConfig getRestConfig() {
        return restConfig;
    }

    public void setRestConfig(ClientConfig restConfig) {
        this.restConfig = restConfig;
    }

    public String getPathCount() {
        return pathCount;
    }

    public void setPathCount(String pathCount) {
        this.pathCount = pathCount;
    }

    public String getPathInstitutes() {
        return pathInstitutes;
    }

    public void setPathInstitutes(String pathInstitutes) {
        this.pathInstitutes = pathInstitutes;
    }

    public String getPathInstituteDetail() {
        return pathInstituteDetail;
    }

    public void setPathInstituteDetail(String pathInstituteDetail) {
        this.pathInstituteDetail = pathInstituteDetail;
    }
}
