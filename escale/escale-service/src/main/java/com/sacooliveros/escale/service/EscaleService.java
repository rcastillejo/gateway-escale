package com.sacooliveros.escale.service;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.client.EscaleClientService;
import com.sacooliveros.escale.client.Filter;
import com.sacooliveros.escale.client.dto.InstitucionResponse;
import com.sacooliveros.escale.client.dto.InstitucionesResponse;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.mapper.EscaleMapper;
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

    public List<Colegio> consultarSiguienteGrupoColegios(Filter filter) {
        List<Colegio> colegios;
        InstitucionesResponse response;
        Filter filterBlock;

        LOG.trace("Paginacion de instituciones  [" + pagination + "]");

        filterBlock = filter.clone();
        filterBlock.setStart(pagination.getCurrentBlockSize());

        response = client.getInstitutes(filterBlock);

        validarColegios(response);

        LOG.trace("Paginacion de instituciones recalculada [" + pagination + "]");

        colegios = escaleMapper.mapFrom(response);

        pagination.nextBlock();

        LOG.info("Colegios consultados [filtros=" + filterBlock + ", colegios=" + (colegios == null ? colegios : colegios.size()) + "]");

        return colegios;
    }

    private void validarColegios(InstitucionesResponse response) {
        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            throw new EscaleServiceException("No encontraron colegios [" + response + "]");
        }
    }

    protected List<Colegio> consultarDetalleColegios(List<Colegio> colegios, Filter filter) {
        for (Colegio colegio : colegios) {
            try {
                colegio.setDetalle(consultarDetalleColegio(colegio, filter));
            }catch (EscaleServiceException e){
                LOG.warn("Error al consultar detalle colegio["+colegio.getCodigo()+"], filtro["+filter+"]", e);
            }
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

        validarDetalleColegio(colegioDetalle);

        LOG.info("Detalle de colegio consultado [filtros=" + colegioFilter + ", colegios=" + (colegioDetalle == null ? colegioDetalle : colegioDetalle.size()) + "]");
        return colegioDetalle;
    }

    private void validarDetalleColegio(List<ColegioDetalle> detalle) {
        if (detalle == null || detalle.isEmpty()) {
            throw new EscaleServiceException("No encontraron detalles del colegio");
        }
    }

    protected void guardarColegios(List<Colegio> colegios) {
        for (Colegio colegio : colegios) {
            guardarColegio(colegio);
        }
    }

    public void guardarColegio(Colegio colegio) {
        colegiodao.guardarColegio(colegio);
        LOG.info("Colegio guardado [" + colegio + "]");
    }


    public void guardarDetalleColegio(List<ColegioDetalle> detalle) {
        colegiodao.guardarDetalles(detalle);
        LOG.info("Detalle Colegio guardados [" + detalle.size() + "]");
    }
}
