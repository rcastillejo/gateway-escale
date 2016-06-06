package com.sacooliveros.escale.client;

import com.sacooliveros.escale.client.bean.Institute;
import com.sacooliveros.escale.client.bean.InstitutesResponse;


/**
 * Created by rcastillejo on 02/06/2016.
 */
public interface EscaleClientService {

    int getInstitutesCount(Filter filter);

    InstitutesResponse getInstitutes(Filter filter);

    //List<InstituteDetail> getInstituteDetails(Institute institute, Filter filter);

}
