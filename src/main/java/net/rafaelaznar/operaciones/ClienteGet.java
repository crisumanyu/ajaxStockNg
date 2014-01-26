/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.operaciones;

/**
 *
 * @author rafa
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rafaelaznar.bean.ClienteBean;

import com.google.gson.Gson;
import net.rafaelaznar.dao.ClienteDao;

public class ClienteGet implements GenericOperation {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String data;         
        try {            
            if (request.getParameter("id") == null) {
                data = "{\"error\":\"id is mandatory\"}";
            } else {
                ClienteDao oClienteDAO = new ClienteDao();
                ClienteBean oCliente = new ClienteBean();
                oCliente.setId(Integer.parseInt(request.getParameter("id")));
                oClienteDAO.get(oCliente);
                data = new Gson().toJson(oCliente);
            }
            return data;
        } catch (Exception e) {
            throw new ServletException("ClienteGetJson: View Error: " + e.getMessage());
        }
    }
}