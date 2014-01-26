/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.bean.CompraBean;
import net.rafaelaznar.dao.CompraDao;
import net.rafaelaznar.helper.EncodingUtil;
import net.rafaelaznar.helper.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Compra {

    @Autowired
    CompraDao oCompraDAO;

    @RequestMapping({"/compra/indexlist.html"})
    public ModelAndView indexlist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "c/list.jsp");
    }

    @RequestMapping({"/compra/indexform.html"})
    public ModelAndView indexform(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "compra/form.jsp");
    }

    @RequestMapping({"/compra/list.html"})
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("compra/list");
    }

    @RequestMapping({"/compra/form.html"})
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("compra/form");
    }

    @RequestMapping({"/compra/{id}/get.json"})
    public ModelAndView get(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            CompraBean oCompra = new CompraBean();
            oCompra.setId(id);
            oCompraDAO.get(oCompra);
            data = new Gson().toJson(oCompra);
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("CompraGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/compra/{rpp}/{page}/getpage.json"})
    public ModelAndView getPage(@PathVariable Integer rpp, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            List<CompraBean> oCompras = oCompraDAO.getPage(rpp, page, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = new Gson().toJson(oCompras);
            data = "{\"list\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("CompraGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/compra/{rpp}/getpages.json"})
    public ModelAndView getPages(@PathVariable Integer rpp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            int pages = oCompraDAO.getPages(rpp, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("CompraGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/compra/getprettycolumns.json"})
    public ModelAndView getPrettyColumns() throws UnsupportedEncodingException, ServletException {
        try {            
            String data = "{\"data\": [\"id\", \"cliente\", \"producto\", \"cantidad\", \"fecha\", \"factura\"]}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("CompraGetpagesJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/compra/getcolumns.json"})
    public ModelAndView getColumns() throws UnsupportedEncodingException, ServletException {
        try {
            ArrayList<String> alColumns;
            alColumns = oCompraDAO.getColumnsNames();
            String data = new Gson().toJson(alColumns);
            data = "{\"data\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("CompraGetcolumnsJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/compra/getregisters.json"})
    public ModelAndView getRegisters(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        int pages = oCompraDAO.getCount(OrderFilter.getFilter(request));
        String data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
        return new ModelAndView("messageAjax", "contenido", data);
    }

    @RequestMapping({"/compra/{id}/remove.json"})
    public ModelAndView getRemove(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException, Exception {

        try {
            CompraBean oCompra = new CompraBean(id);
            Map<String, String> data = new HashMap<>();
            oCompraDAO.remove(oCompra);
            data.put("status", "200");
            data.put("message", "se ha eliminado el registro con id=" + oCompra.getId());
            Gson gson = new Gson();
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("CompraRemoveJson: View Error: " + e.getMessage());
        }

    }

    @RequestMapping({"/compra/save.json"})
    public ModelAndView getRemove(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        try {
            CompraBean oCompra = new CompraBean();
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            String jason = request.getParameter("json");
            jason = EncodingUtil.decodeURIComponent(jason);
            oCompra = gson.fromJson(jason, oCompra.getClass());
            Map<String, String> data = new HashMap<>();
            oCompra = oCompraDAO.set(oCompra);
            data.put("status", "200");
            data.put("message", Integer.toString(oCompra.getId()));
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("CompraSaveJson: View Error: " + e.getMessage());
        }
    }

}
