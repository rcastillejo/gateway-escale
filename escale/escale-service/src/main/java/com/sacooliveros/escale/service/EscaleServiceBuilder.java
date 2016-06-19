package com.sacooliveros.escale.service;

import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.mapper.EscaleMapper;

/**
 * Created by Ricardo on 19/06/2016.
 */
public final class EscaleServiceBuilder {

    private EscaleServiceBuilder(){

    }

    public static EscaleService newInstaceForReader(EscaleClientService client, ColegioDAO colegiodao, int institutesBlock){
        return new EscaleService(client, new EscaleMapper(), colegiodao, institutesBlock);
    }

    public static EscaleService newInstaceForWorker(EscaleClientService client, ColegioDAO colegiodao){
        return new EscaleService(client, new EscaleMapper(), colegiodao);
    }
}
