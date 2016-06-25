package com.sacooliveros.escale.dao.mybatis;


import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.dao.exception.DAOException;
import com.sacooliveros.escale.dao.exception.DataNotRegisterDAOException;
import com.sacooliveros.escale.log.Logp;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ricardo on 17/06/2016.
 */
public class ColegioMyBatisDAO implements ColegioDAO {
    public static final Logger LOG = LoggerFactory.getLogger(ColegioMyBatisDAO.class);

    private final SqlSessionFactory sessionFactory;

    public ColegioMyBatisDAO(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void guardarColegio(Colegio model) {
        SqlSession session = null;
        ColegioMyBatisMapper mapper;

        try {

            session = sessionFactory.openSession();
            mapper = session.getMapper(ColegioMyBatisMapper.class);

            Colegio colegioEncontrado = get(model.getCodigo(), mapper);

            if (colegioEncontrado == null) {
                insert(model, mapper);
                LOG.info("Colegio registrado [" + model + "]");
            } else {
                update(model, mapper);
                LOG.info("Colegio actualizado [" + model + "]");
            }

            session.commit();

        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw new DAOException("Error al guardar colegio", e);
        } finally {
            closeConnection(session);
        }
    }

    private Colegio get(String id, ColegioMyBatisMapper mapper) {
        try{
            long init = System.currentTimeMillis();
            Colegio model = mapper.get(id);
            LOG.info("Registro encontrado[{}]", model);
            Logp.show("GET_COLEGIO", init);
            return model;
        } catch (Exception e) {
            throw new DAOException("Error al consultar", e);
        }
    }

    private void insert(Colegio model, ColegioMyBatisMapper mapper) {
        long init = System.currentTimeMillis();
        LOG.debug("Registrando [{}] ...", model);
        if (mapper.insert(model) == 0) {
            throw new DataNotRegisterDAOException("No se pudo registrar [" + model + "]");
        }
        Logp.show("INSERT_COLEGIO", init);
        LOG.info("Registrado [{}]", model);
    }

    private void update(Colegio model,  ColegioMyBatisMapper mapper) {
        long init = System.currentTimeMillis();
        LOG.debug("Registrando [{}] ...", model);
        if (mapper.update(model) == 0) {
            throw new DataNotRegisterDAOException("No se pudo actualizar [" + model + "]");
        }
        Logp.show("UPDATE_COLEGIO", init);
        LOG.info("Registrado [{}]", model);
    }


    public void guardarDetalles(List<ColegioDetalle> models) {
        SqlSession session = null;
        ColegioMyBatisMapper mapper;

        try {
            session = sessionFactory.openSession();
            mapper = session.getMapper(ColegioMyBatisMapper.class);

            for (ColegioDetalle detalle : models) {
                guardarDetalle(detalle, mapper);
            }

            session.commit();

        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw new DAOException("Error al guardar detalles", e);
        } finally {
            closeConnection(session);
        }
    }

    private void guardarDetalle(ColegioDetalle model, ColegioMyBatisMapper mapper) {
        try {
            ColegioDetalle detalleEncontrado = getDetalle(model, mapper);
            if (detalleEncontrado == null) {
                insert(model, mapper);
            } else {
                update(model, mapper);
            }
        } catch (Exception e) {
            LOG.error("No se pudo guardar el detalle [" + model + "]", e);
        }
    }

    private ColegioDetalle getDetalle(ColegioDetalle model, ColegioMyBatisMapper mapper) {
        long init = System.currentTimeMillis();
        ColegioDetalle modelEncontrado;
        modelEncontrado = mapper.getDetalle(model);
        LOG.info("Detalle encontrado[{}]", modelEncontrado);
        Logp.show("GET_DETALLE", init);
        return modelEncontrado;
    }

    private void insert(ColegioDetalle model, ColegioMyBatisMapper mapper) {
        long init = System.currentTimeMillis();
        LOG.debug("Registrando [{}] ...", model);
        if (mapper.insertDetalle(model) == 0) {
            throw new DataNotRegisterDAOException("No se pudo registrar [" + model + "]");
        }
        Logp.show("UPDATE_COLEGIO", init);
        LOG.info("Registrado [{}]", model);

    }

    private void update(ColegioDetalle model, ColegioMyBatisMapper mapper) {
        long init = System.currentTimeMillis();
        LOG.debug("Actualizando [{}] ...", model);
        if (mapper.updateDetalle(model) == 0) {
            throw new DataNotRegisterDAOException("No se pudo actualizar  [" + model + "]");
        }
        Logp.show("UPDATE_DETALLE", init);
        LOG.info("Actualizado [{}]", model);
    }

    private void closeConnection(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }

}
