package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.EscaleClientServiceConfig;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import com.sacooliveros.escale.service.exception.EscaleServiceException;
import com.sacooliveros.escale.utils.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ricardo on 12/06/2016.
 */
public class EscaleService {
    public static final Logger LOG = LoggerFactory.getLogger(EscaleService.class);
    private EscaleClientService client;
    private EscaleMapper escaleMapper;
    private ColegioDAO colegiodao;
    private Pagination pagination;

    public EscaleService(EscaleClientService client, EscaleMapper escaleMapper, ColegioDAO colegiodao, int institutesBlock) {
        this.client = client;
        this.escaleMapper = escaleMapper;
        this.colegiodao = colegiodao;
        this.pagination = new Pagination(institutesBlock);
    }



    public int calcularTotalColegios(Filter filter) {
        int cantidad = client.getInstitutesCount(filter);

        if(cantidad <= 0){
            throw new EscaleServiceException("Cantidad de colegios invalida ["+cantidad+"]");
        }

        pagination.setTotalSizeAndCalculate(cantidad);
        LOG.trace("Paginacion calculada [" + pagination + "]");
        return cantidad;
    }

    public void reiniciarCalculoTotalColegios(){
        pagination.resetCalculate();
        LOG.trace("Paginacion reniciada [" + pagination + "]");
    }

    public boolean existeColegiosPorConsultar(){
        return pagination.hasMore();
    }

    public List<Colegio> consultarColegios(Filter filter) {
        List<Colegio> colegios;
        InstitucionesResponse response;

        LOG.trace("Paginacion de instituciones  [" + pagination + "]");

        filter.setStart(pagination.getCurrentBlockSize());

        response = client.getInstitutes(filter);


        if(response == null || response.getItems() == null || response.getItems().isEmpty()){
            throw new EscaleServiceException("No encontraron colegios ["+response+"]");
        }

        pagination.nextBlock();

        LOG.trace("Paginacion de instituciones recalculada [" + pagination + "]");

        colegios = escaleMapper.mapFrom(response);
        return colegios;
    }

    public List<Colegio> consultarDetalleColegios(List<Colegio> colegios, Filter filter) {
        for (Colegio colegio : colegios) {
            colegio.setDetalle(consultarDetalleColegio(colegio, filter));
        }
        return colegios;
    }

    public List<ColegioDetalle> consultarDetalleColegio(Colegio colegio, Filter filter) {
        List<ColegioDetalle> colegioDetalle;
        InstitucionResponse response;
        Filter colegioFilter;

        colegioFilter = filter.clone();
        colegioFilter.setPrefixLevel(colegio.getCodigoNivel());

        response = client.getInstituteDetails(colegio.getCodigo(), colegioFilter);
        colegioDetalle = escaleMapper.mapFrom(response, colegioFilter.getYear());
        return colegioDetalle;
    }

    public void guardarColegios(List<Colegio> colegios) {
        for (Colegio colegio : colegios) {
            guardarColegio(colegio);
        }
    }

    public void guardarColegio(Colegio colegio) {
        Colegio colegioEncontrado = colegiodao.get(colegio.getCodigo());

        LOG.debug("colegio encontrado:"+colegioEncontrado);

        if(colegioEncontrado == null){
            colegiodao.insert(colegio);
        }else{
            colegiodao.update(colegio);
        }
    }
}
