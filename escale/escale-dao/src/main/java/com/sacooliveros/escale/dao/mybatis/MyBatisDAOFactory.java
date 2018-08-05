package com.sacooliveros.escale.dao.mybatis;

import com.sacooliveros.escale.dao.ColegioDAO;
import com.sacooliveros.escale.dao.exception.DAOException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.Collection;

/**
 * Created by Ricardo on 18/06/2016.
 */
public class MyBatisDAOFactory {
    private static final Logger log = LoggerFactory.getLogger(MyBatisDAOFactory.class);
    private SqlSessionFactory sessionFactory;

    public MyBatisDAOFactory(String resource) {
        init(resource);
        printConfiguration();
    }

    private void init(String resource) {
        try {
            Reader reader = Resources.getResourceAsReader(resource);

            sessionFactory = new SqlSessionFactoryBuilder().build(reader);

            log.debug("Fabrica de sesiones MyBatis creado.");
        } catch (Exception e) {
            throw new DAOException("Error al crear la fabrica de sesiones", e);
        }
    }

    private void printConfiguration() {
        Configuration config = sessionFactory.getConfiguration();
        log.trace("Configuracion mybatis [{}]", config);
        if (config != null) {
            Collection<String> parameterNames = config.getParameterMapNames();
            log.trace("Configuracion mybatis parameterNames [{}]", parameterNames);
            for (String name : parameterNames) {
                log.trace("Parametros [{}={}]", new Object[]{name, sessionFactory.getConfiguration().getParameterMap(name)});
            }
        }
    }

    public ColegioDAO getColegioDAO() {
        return new ColegioMyBatisDAO(sessionFactory);
    }
}
