package com.sacooliveros.escale.dao;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;

import java.util.List;

/**
 * Created by Ricardo on 17/06/2016.
 */
public interface ColegioDAO {

    void guardarColegio(Colegio colegio);

    void guardarDetalles(List<ColegioDetalle> detalle);
}
