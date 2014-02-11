/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.dao;

import net.rafaelaznar.bean.ClienteBean;
import net.rafaelaznar.helper.Conexion;

/**
 *
 * @author rafa
 */
public class ClienteDao extends GenericDaoImplementation<ClienteBean> {
  
    public ClienteDao() throws Exception {
        super(Conexion.getConection(),"cliente");
        oMysql.conexion(enumTipoConexion);
    }
    
}
