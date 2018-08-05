/*
 * @(#)Identificador.java
 * Copyright (c) 2005 NOVATRONIC SAC
 * All rights reserved.
 * Creado el 04 de Enero 2006
 */
package com.sacooliveros.escale.etl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase generadora de un codigo de identificacion
 *
 * @author Ricardo Castillejo Luna - NOVATRONIC SAC
 * @version 01.00.00
 * @since Creado 23-11-2012 <br> <table border=1> <tr> <td
 * align='center'>Version</td> <td align='center'>Fecha</td> <td
 * align='center'>Modificado por</td><td align='center'>Metodo Modificado</td>
 * <td align='center'>Explicacion del cambio</td> </tr>
 * <tr><td>01.00.00</td><td>05-12-2012</td><td>Ricardo Castillejo Luna
 * (RCL)</td><td>todos</td><td>Refactorizacion de Código</td></tr> </table>
 */
public class Identificador {

    private static int conta;
    private String ccode;
    private SimpleDateFormat sdf;
    /**
     * Instancia de la clase
     */
    private static Identificador instancia = null;

    /**
     * Devuelve la instancia de la clase
     *
     * @param controllerCode codigo del controlador/broker
     * @return Objeto singleton de la clase
     */
    public static Identificador getInstance(String controllerCode) {
        synchronized (Identificador.class) {

            if (instancia == null) {

                //  Obtiene el objeto singleton
                instancia = new Identificador(controllerCode);
            }
        }
        return instancia;
    }

    /**
     * Metodo constructor de la clase
     *
     * @param controllerCode codigo del controlador/broker
     */
    Identificador(String controllerCode) {
        int maxLength = 8;
        String aux;

        aux = controllerCode + "        ";

        conta = 0;
        ccode = aux.substring(0, maxLength);

        sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    }

    /**
     * Metodo que obtiene un codigo
     *
     * @return String - Codigo generado como identificador
     */
    public synchronized String getCode() {

        int len = 4;
        int max4Reset = 9999;
        String df;
        String aux;

        df = sdf.format(new Date());

        /* Armamos codigo */
        aux = ccode + df + lpad("" + conta, "0", len);

        /* Incrementamos contador */
        conta++;

        if (conta > max4Reset) {
            conta = 0;
        }

        return aux;
    }

    private static String lpad(String src, String car, int len) {
        int i, j;
        String res;
        String strSrc = src;

        if (strSrc == null) {
            strSrc = "";
        }

        res = "";
        j = len - strSrc.length();
        for (i = 0; i < j; i++) {
            res += car;
        }
        res += strSrc;

        return (res);
    }
}