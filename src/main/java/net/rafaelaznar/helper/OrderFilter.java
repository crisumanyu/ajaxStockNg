/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.helper;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author rafa
 */
public class OrderFilter {

    public static HashMap<String, String> getOrder(HttpServletRequest request) {
        HashMap<String, String> hmOrder = new HashMap<>();
        if (request.getParameter("order") != null) {
            if (request.getParameter("ordervalue") != null) {
                hmOrder.put(request.getParameter("order"), request.getParameter("ordervalue"));
            } else {
                hmOrder = null;
            }
        } else {
            hmOrder = null;
        }
        return hmOrder;
    }

    public static ArrayList<FilterBean> getFilter(HttpServletRequest request) {
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
        return alFilter;
    }
}
