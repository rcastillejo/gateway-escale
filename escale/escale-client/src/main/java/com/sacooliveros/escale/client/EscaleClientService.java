package com.sacooliveros.escale.client;

import com.sacooliveros.escale.client.bean.InstitucionResponse;
import com.sacooliveros.escale.client.bean.InstitucionesResponse;


/**
 * Created by rcastillejo on 02/06/2016.
 */
public interface EscaleClientService {

    int getInstitutesCount(Filter filtro);

    InstitucionesResponse getInstitutes(Filter filtro);

    InstitucionResponse getInstituteDetails(String codigo, Filter filtro);

}
