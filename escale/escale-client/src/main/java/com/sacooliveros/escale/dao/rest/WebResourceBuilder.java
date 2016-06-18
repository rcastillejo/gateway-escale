package com.sacooliveros.escale.dao.rest;

import com.sun.jersey.api.client.WebResource;

import java.util.List;

/**
 * Created by Ricardo on 06/06/2016.
 */
public class WebResourceBuilder {
    private WebResource service;

    public WebResourceBuilder(WebResource service) {
        this.service = service;
    }

    public WebResourceBuilder addLevelParam(String level) {
        service = service.queryParam("nivel", level);
        return this;
    }

    public WebResourceBuilder addLevelsParam(List<String> levels) {
        for (String level : levels) {
            service = service.queryParam("niveles", level);
        }
        return this;
    }

    public WebResourceBuilder addStatesParam(List<String> states) {
        for (String state : states) {
            service = service.queryParam("estados", state);
        }
        return this;
    }

    public WebResourceBuilder addStartParam(int start) {
        service = service.queryParam("start", String.valueOf(start));
        return this;
    }

    public WebResourceBuilder addYearParam(String year) {
        service = service.queryParam("anio", year);
        return this;
    }

    public WebResourceBuilder addExpandLevelParam(String expandLevel) {
        service = service.queryParam("expandLevel", expandLevel);
        return this;
    }

    public WebResource getWebResource(){
        return this.service;
    }
}
