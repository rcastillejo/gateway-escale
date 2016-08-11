package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.Institucion;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.mapper.EscaleMapper;
import com.sacooliveros.escale.service.exception.EscaleServiceException;
import com.sacooliveros.escale.service.exception.InstituteDetailNotFoundException;
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

    public EscaleService(EscaleClientService client, EscaleMapper escaleMapper, ColegioDAO colegiodao) {
        this.client = client;
        this.escaleMapper = escaleMapper;
        this.colegiodao = colegiodao;
    }


    public int calcularTotalColegios(Filter filter) {
        int cantidad = client.getInstitutesCount(filter);
        validarTotal(cantidad);
        pagination.setTotalSizeAndCalculate(cantidad);
        LOG.info("Colegios encontrados [" + pagination + "], filtros aplicados [" + filter + "]");
        return cantidad;
    }

    private void validarTotal(int cantidad) {
        if (cantidad <= 0) {
            throw new EscaleServiceException("Cantidad de colegios invalida [" + cantidad + "]");
        }
    }

    public boolean existeColegiosPorConsultar() {
        return pagination.hasMore();
    }

    public List<Institucion> consultarSiguienteGrupoColegios(Filter filter) {
        InstitucionesResponse response;
        Filter filterBlock;

        LOG.trace("Paginacion de instituciones  [" + pagination + "]");

        filterBlock = filter.clone();
        filterBlock.setStart(pagination.getCurrentBlockSize());

        response = client.getInstitutes(filterBlock);

        validarColegios(response);

        LOG.trace("Paginacion de instituciones recalculada [" + pagination + "]");

        pagination.nextBlock();

        LOG.info("Colegios consultados [filtros=" + filterBlock + ", colegios=" + response + "]");

        return response.getItems();
    }

    public int leidos(){
        return pagination.getCurrentBlockSize();
    }

    public int pendientes(){
        return pagination.getLeftBlockSize();
    }

    private void validarColegios(InstitucionesResponse response) {
        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            throw new EscaleServiceException("No encontraron colegios [" + response + "]");
        }
    }


    public List<ColegioDetalle> consultarDetalleColegio(Institucion colegio, Filter filter) {
        List<ColegioDetalle> colegioDetalle;
        InstitucionResponse response;
        Filter colegioFilter;

        colegioFilter = filter.clone();
        colegioFilter.setPrefixLevel(colegio.getNivelModalidad().getCodigo());

        LOG.info("Detalle de colegio [request=" + colegio.getCodigo()+", filter="+colegioFilter+"]");
        response = client.getInstituteDetails(colegio.getCodigo(), colegioFilter);
        LOG.info("Detalle de colegio [response="+colegio.getCodigo()+", detalle=" + response+"]");
        colegioDetalle = escaleMapper.mapFrom(colegio.getCodigo(), response, colegioFilter.getYear());

        validarDetalleColegio(colegio.getCodigo(), colegioDetalle);

        LOG.info("Detalle de colegio consultado [filtros=" + colegioFilter + ", colegios=" + (colegioDetalle == null ? colegioDetalle : colegioDetalle.size()) + "]");
        return colegioDetalle;
    }

    private void validarDetalleColegio(String codigoColegio, List<ColegioDetalle> detalle) {
        if (detalle == null || detalle.isEmpty()) {
            throw new InstituteDetailNotFoundException(codigoColegio, "No encontraron detalles del colegio");
        }
    }

    public void transformarGuardarColegio(Institucion colegioEscale) {
        Colegio colegio = escaleMapper.mapFrom(colegioEscale);

        colegiodao.guardarColegio(colegio);
        LOG.info("Colegio guardado [" + colegio + "]");
    }


    public void guardarDetalleColegio(List<ColegioDetalle> detalle) {
        colegiodao.guardarDetalles(detalle);
        LOG.info("Detalle Colegio guardados [" + detalle.size() + "]");
    }
}
