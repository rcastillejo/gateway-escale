package com.sacooliveros.escale.client;

import com.sacooliveros.escale.client.bean.Institute;
import com.sacooliveros.escale.client.bean.InstitutesResponse;
import com.sacooliveros.escale.client.exception.EscaleConnectTimeoutException;
import com.sacooliveros.escale.client.exception.EscaleReadTimeoutException;
import com.sacooliveros.escale.client.exception.ResponseMalformatException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by rcastillejo on 02/06/2016.
 */
public class RestEscaleClientService implements EscaleClientService {
    public static final Logger LOGGER = LoggerFactory.getLogger(RestEscaleClientService.class);
    private EscaleClientServiceConfig config;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setConfig(EscaleClientServiceConfig config) {
        this.config = config;
    }


    private WebResource addLevelsParam(WebResource service, List<String> levels) {
        for (String level : levels) {
            service = service.queryParam("niveles", level);
        }
        return service;
    }

    private WebResource addStatesParam(WebResource service, List<String> states) {
        for (String state : states) {
            service = service.queryParam("estados", state);
        }
        return service;
    }

    private WebResource addStartParam(WebResource service, int start) {
        return service.queryParam("start", String.valueOf(start));
    }

    @Override
    public int getInstitutesCount(Filter filter) {
        try {
            LOGGER.debug("Consultando la cantidad de instituciones ["+filter+"]");
            WebResource service = client.resource(config.getUrl())
                    .path(config.getPathCount());
            service = addLevelsParam(service, filter.getLevels());
            service = addStatesParam(service, filter.getStates());
            String count = service.accept(MediaType.WILDCARD).get(String.class);
            LOGGER.debug("Cantidad de instituciones recibidas ["+count+"]");
            return Integer.parseInt(count);
        }catch (NumberFormatException e){
            throw new ResponseMalformatException("Error al leer la cantidad de instituciones", e);
        }catch (ClientHandlerException e){
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
    public InstitutesResponse getInstitutes(Filter filter) {

        try {
            LOGGER.debug("Consultando las instituciones ["+filter+"]");
            WebResource service = client.resource(config.getUrl())
                    .path(config.getPathInstitutes());
            service = addLevelsParam(service, filter.getLevels());
            service = addStatesParam(service, filter.getStates());
            service = addStartParam(service, filter.getStart());
            InstitutesResponse response = service.accept(MediaType.APPLICATION_XML_TYPE).get(InstitutesResponse.class);
            LOGGER.debug("Resultado de instituciones ["+response+"]");
            return response;
        }catch (ClientHandlerException e){
            if (e.getCause() instanceof SocketTimeoutException) {
                throw new EscaleReadTimeoutException("Error al obtener las instituciones", e.getCause());
            }
            if (e.getCause() instanceof ConnectException) {
                throw new EscaleConnectTimeoutException("Error al obtener las instituciones", e.getCause());
            }
            throw e;
        }
    }

    /*@Override
    public List<InstituteDetail> getInstituteDetails(String instituteCode, Filter filter) {
        return null;
    }*/
}
