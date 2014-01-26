/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.dao;

import net.rafaelaznar.bean.TipoproductoBean;
import net.rafaelaznar.helper.Conexion;

/**
 *
 * @author rafa
 */
public class TipoproductoDao extends GenericDaoImplementation<TipoproductoBean> {
  
    public TipoproductoDao() throws Exception {
        super(Conexion.getConection(),"tipoproducto");
    }
    
}
