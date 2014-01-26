/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.dao;

import net.rafaelaznar.bean.CompraBean;
import net.rafaelaznar.helper.Conexion;

/**
 *
 * @author rafa
 */
public class CompraDao extends GenericDaoImplementation<CompraBean> {
  
    public CompraDao() throws Exception {
        super(Conexion.getConection(),"compra");
    }
    
}