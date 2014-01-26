/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.operaciones;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.helper.Conexion;
import net.rafaelaznar.bean.TipoproductoBean;
import net.rafaelaznar.dao.TipoproductoDao;
import net.rafaelaznar.helper.FilterBean;

/**
 *
 * @author rafa
 */
public class TipoproductoGetpage implements GenericOperation {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String data;
        try {
            int rpp;
            if (request.getParameter("rpp") == null) {
                rpp = 10;
            } else {
                rpp = Integer.parseInt(request.getParameter("rpp"));
            }
            int page;
            if (request.getParameter("page") == null) {
                page = 1;
            } else {
                page = Integer.parseInt(request.getParameter("page"));
            }
            ArrayList<FilterBean> alFilter = new ArrayList<>();
            if (request.getParameter("filter") != null) {
                if (request.getParameter("filteroperator") != null) {
                    if (request.getParameter("filtervalue") != null) {
                        FilterBean oFilterBean = new FilterBean();
                        oFilterBean.setFilter(request.getParameter("filter"));
                        oFilterBean.setFilterOperator(request.getParameter("filteroperator"));
                        oFilterBean.setFilterValue(request.getParameter("filtervalue"));
                        oFilterBean.setFilterOrigin("user");
                        alFilter.add(oFilterBean);
                    } 
                } 
            } 
            if (request.getParameter("systemfilter") != null) {
                if (request.getParameter("systemfilteroperator") != null) {
                    if (request.getParameter("systemfiltervalue") != null) {
                        FilterBean oFilterBean = new FilterBean();
                        oFilterBean.setFilter(request.getParameter("systemfilter"));
                        oFilterBean.setFilterOperator(request.getParameter("systemfilteroperator"));
                        oFilterBean.setFilterValue(request.getParameter("systemfiltervalue"));
                        oFilterBean.setFilterOrigin("system");
                        alFilter.add(oFilterBean);
                    }
                }
            }            
            HashMap<String, String> hmOrder = new HashMap<>();

            if (request.getParameter("order") != null) {
                if (request.getParameter("ordervalue") != null) {           
                    hmOrder.put(request.getParameter("order"), request.getParameter("ordervalue"));                  
                } else             hmOrder=null;
            } else             hmOrder=null;
            TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            List<TipoproductoBean> oTipoproductos = oTipoproductoDAO.getPage( rpp, page, alFilter,hmOrder );
            data = new Gson().toJson(oTipoproductos);
            data = "{\"list\":" + data + "}";
            return data;
        } catch (Exception e) {
            throw new ServletException("TipoproductoGetJson: View Error: " + e.getMessage());
        }
    }
}
