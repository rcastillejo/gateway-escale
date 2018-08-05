package com.sacooliveros.escale.dao.mybatis;

import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Ricardo on 17/06/2016.
 */
public interface ColegioMyBatisMapper {
    Colegio get(@Param("codigo") String codigo);
    ColegioDetalle getDetalle(@Param("detalle") ColegioDetalle detalle);
    int insert(Colegio colegio);
    int update(Colegio colegio);
    int insertDetalle(ColegioDetalle colegioDetalle);
    int updateDetalle(ColegioDetalle colegioDetalle);
    //int deleteDetalle(ColegioDetalle colegioDetalle);

}
