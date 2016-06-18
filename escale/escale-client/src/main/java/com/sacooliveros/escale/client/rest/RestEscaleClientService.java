package com.sacooliveros.escale.client.rest;

import com.sacooliveros.client.rest.filters.logging.JerseyLogginFilter;
import com.sacooliveros.escale.client.EscaleClientServiceConfig;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.exception.EscaleReadTimeoutException;
import com.sacooliveros.escale.client.exception.ResponseMalformatException;
import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import com.sacooliveros.escale.client.exception.EscaleConnectTimeoutException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;

/**
 * Created by rcastillejo on 02/06/2016.
 */
public class RestEscaleClientService implements EscaleClientService {
    public static final Logger LOGGER = LoggerFactory.getLogger(RestEscaleClientService.class);

    private EscaleClientServiceConfig config;
    private Client client;

    public static RestEscaleClientService newInstance(EscaleClientServiceConfig config){
        RestEscaleClientService clientService = new RestEscaleClientService();
        clientService.setConfig(config);
        clientService.setClient(new Client());
        clientService.addFilter(new JerseyLogginFilter());
        return clientService;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addFilter(ClientFilter filter) {
        this.client.addFilter(filter);
    }

    public void setConfig(EscaleClientServiceConfig config) {
        this.config = config;
    }

    @Override
    public int getInstitutesCount(Filter filter) {
        try {
            LOGGER.debug("Consultando la cantidad de instituciones [" + filter + "]");
            WebResource service = client.resource(config.getUrl())
                    .path(config.getPathCount());
            if(filter != null){
                service = new WebResourceBuilder(service)
                        .addLevelsParam(filter.getLevels())
                        .addStatesParam(filter.getStates())
                        .getWebResource();
            }
            String count = service.accept(MediaType.WILDCARD).get(String.class);
            LOGGER.debug("Cantidad de instituciones recibidas [" + count + "]");
            return Integer.parseInt(count);
        } catch (NumberFormatException e) {
            throw new ResponseMalformatException("Error al leer la cantidad de instituciones", e);
        } catch (ClientHandlerException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new EscaleReadTimeoutException("Error al obtener la cantidad de instituciones", e.getCause());
            }
            if (e.getCause() instanceof ConnectException) {
                throw new EscaleConnectTimeoutException("Error al obtener la cantidad de instituciones", e.getCause());
            }
            throw e;
        }
    }

    @Override
    public InstitucionesResponse getInstitutes(Filter filter) {
        try {
            LOGGER.debug("Consultando las instituciones [" + filter + "]");
            WebResource service = client.resource(config.getUrl())
                    .path(config.getPathInstitutes());
            if(filter != null){
                service = new WebResourceBuilder(service)
                        .addLevelsParam(filter.getLevels())
                        .addStatesParam(filter.getStates())
                        .addStartParam(filter.getStart())
                        .getWebResource();
            }
            InstitucionesResponse response = service.accept(MediaType.APPLICATION_XML_TYPE).get(InstitucionesResponse.class);
            LOGGER.debug("Resultado de instituciones [" + response + "]");
            return response;
        } catch (ClientHandlerException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new EscaleReadTimeoutException("Error al obtener las instituciones", e.getCause());
            }
            if (e.getCause() instanceof ConnectException) {
                throw new EscaleConnectTimeoutException("Error al obtener las instituciones", e.getCause());
            }
            throw e;
        }
    }

    @Override
    public InstitucionResponse getInstituteDetails(String codigo, Filter filter) {
        try {
            LOGGER.debug("Consultando las instituciones [codigo=" + codigo + ", filtro=" + filter + "]");
            WebResource service = client.resource(config.getUrl())
                    .path(MessageFormat.format(config.getPathInstituteDetail(), codigo));
            if(filter != null){
                service = new WebResourceBuilder(service)
                        .addExpandLevelParam( filter.getExpandLevel())
                        .addYearParam( filter.getYear())
                        .addLevelParam( filter.getFirstlevel())
                        .getWebResource();
            }
            InstitucionResponse response = service.accept(MediaType.APPLICATION_XML_TYPE).get(InstitucionResponse.class);
            LOGGER.debug("Resultado de institucion [" + codigo + "=" + response + "]");
            return response;
        } catch (ClientHandlerException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new EscaleReadTimeoutException("Error al obtener la institucion [" + codigo + "]", e.getCause());
            }
            if (e.getCause() instanceof ConnectException) {
                throw new EscaleConnectTimeoutException("Error al obtener la institucion [" + codigo + "]", e.getCause());
            }
            throw e;
        }
    }
}
