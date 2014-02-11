/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.dao;


import net.rafaelaznar.bean.ProductoBean;
import net.rafaelaznar.helper.Conexion;

/**
 *
 * @author rafa
 */

public class ProductoDao extends GenericDaoImplementation<ProductoBean> {
  
    public ProductoDao() throws Exception {
        super(Conexion.getConection(),"producto");
        oMysql.conexion(enumTipoConexion);
    }
    
}