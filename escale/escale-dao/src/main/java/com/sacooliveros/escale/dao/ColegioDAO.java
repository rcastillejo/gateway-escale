package com.sacooliveros.escale.dao;

import com.sacooliveros.escale.bean.Colegio;

/**
 * Created by Ricardo on 17/06/2016.
 */
public interface ColegioDAO {

    Colegio get(String codigo);

    void insert(Colegio colegio);

    void update(Colegio colegio);
}
