package com.sacooliveros.escale.dao.mybatis;


import com.sacooliveros.escale.bean.Colegio;
import com.sacooliveros.escale.bean.ColegioDetalle;
import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.dao.exception.DAOException;
import com.sacooliveros.escale.dao.exception.DataNotRegisterDAOException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Ricardo on 17/06/2016.
 */
public class ColegioMyBatisDAO implements ColegioDAO {
    public static final Logger log = LoggerFactory.getLogger(ColegioMyBatisDAO.class);

    private final SqlSessionFactory sessionFactory;

    public ColegioMyBatisDAO(SqlSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Colegio get(String id) {
        Colegio model;
        SqlSession session = null;
        ColegioMyBatisMapper mapper;

        try {
            session = sessionFactory.openSession();
            mapper = session.getMapper(ColegioMyBatisMapper.class);
            model = mapper.get(id);
            log.info("Registro encontrado[{}]", model);
            return model;
        } catch (Exception e) {
            throw new DAOException("Error al consultar", e);
        } finally {
            closeConnection(session);
        }
    }

    public void insert(Colegio model) {

        SqlSession session = null;
        ColegioMyBatisMapper mapper;

        try {
            session = sessionFactory.openSession();
            mapper = session.getMapper(ColegioMyBatisMapper.class);

            log.debug("Registrando [{}] ...", model);
            if (mapper.insert(model) == 0) {
                throw new DataNotRegisterDAOException("No se pudo registrar ["+model+"]");
            }

            if (model != null) {
                insert(model.getDetalle(), mapper);
            }

            session.commit();
            log.info("Registrado [{}]", model);

        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw new DAOException("Error al grabar", e);
        } finally {
            closeConnection(session);
        }
    }


    public void insert(List<ColegioDetalle> model, ColegioMyBatisMapper mapper) {

        for (ColegioDetalle detalle: model) {

            log.debug("Registrando [{}] ...", model);
            if (mapper.insertDetalle(detalle) == 0) {
                throw new DataNotRegisterDAOException("No se pudo registrar ["+detalle+"]");
            }
            log.info("Registrado [{}]", model);
        }

    }


    public void update(Colegio model) {

        SqlSession session = null;
        ColegioMyBatisMapper mapper;

        try {
            session = sessionFactory.openSession();
            mapper = session.getMapper(ColegioMyBatisMapper.class);

            log.debug("Registrando [{}] ...", model);
            if (mapper.update(model) == 0) {
                throw new DataNotRegisterDAOException("No se pudo registrar ["+model+"]");
            }

            if (model.getDetalle() != null) {
                update(model.getDetalle(), mapper);
            }

            session.commit();
            log.info("Registrado [{}]", model);

        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw new DAOException("Error al grabar", e);
        } finally {
            closeConnection(session);
        }
    }


    public void update(List<ColegioDetalle> model, ColegioMyBatisMapper mapper) {

        for (ColegioDetalle detalle: model) {

            log.debug("Registrando [{}] ...", model);
            if (mapper.updateDetalle(detalle) == 0) {
                log.warn("Registro no se pudo actualizar, procediendo a registrar");
                mapper.insertDetalle(detalle);
            }
            log.info("Registrado [{}]", model);
        }

    }

    public void closeConnection(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }


}
