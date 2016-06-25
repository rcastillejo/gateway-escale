package com.sacooliveros.escale.service;

import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.mapper.EscaleMapper;

import java.util.Properties;

/**
 * Created by Ricardo on 19/06/2016.
 */
public final class EscaleServiceBuilder {

    private EscaleServiceBuilder(){

    }

    public static EscaleService newInstaceForReader(EscaleClientService client, Properties config, ColegioDAO colegiodao, int institutesBlock){
        return new EscaleService(client, EscaleMapper.newInstace(config), colegiodao, institutesBlock);
    }

    public static EscaleService newInstaceForWorker(EscaleClientService client, Properties config, ColegioDAO colegiodao){
        return new EscaleService(client, EscaleMapper.newInstace(config), colegiodao);
    }
}
