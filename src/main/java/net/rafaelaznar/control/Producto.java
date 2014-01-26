/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.control;

import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.bean.ProductoBean;
import net.rafaelaznar.dao.ProductoDao;
import net.rafaelaznar.helper.EncodingUtil;
import net.rafaelaznar.helper.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Producto {

    @Autowired
    ProductoDao oProductoDAO;

//    @RequestMapping({"/index.html"})
//    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//        return new ModelAndView("index", "contenido", "usuario/inicio.jsp");
//    }
    @RequestMapping({"/producto/indexlist.html"})
    public ModelAndView indexlist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "producto/list.jsp");
    }

    @RequestMapping({"/producto/indexform.html"})
    public ModelAndView indexform(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "producto/form.jsp");
    }

    @RequestMapping({"/producto/list.html"})
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("producto/list");
    }

    @RequestMapping({"/producto/form.html"})
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("producto/form");
    }

    @RequestMapping({"/producto/{id}/get.json"})
    public ModelAndView get(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            //ProductoDao oProductoDAO = new ProductoDao();
            ProductoBean oProducto = new ProductoBean();
            oProducto.setId(id);
            oProductoDAO.get(oProducto);
            data = new Gson().toJson(oProducto);
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("ProductoGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/producto/{rpp}/{page}/getpage.json"})
    public ModelAndView getPage(@PathVariable Integer rpp, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            //ProductoDao oProductoDAO = new ProductoDao();
            List<ProductoBean> oProductos = oProductoDAO.getPage(rpp, page, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = new Gson().toJson(oProductos);
            data = "{\"list\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("ProductoGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/producto/{rpp}/getpages.json"})
    public ModelAndView getPages(@PathVariable Integer rpp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            //ProductoDao oProductoDAO = new ProductoDao();
            int pages = oProductoDAO.getPages(rpp, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("ProductoGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/producto/getprettycolumns.json"})
    public ModelAndView getPrettyColumns() throws UnsupportedEncodingException, ServletException {
        try {
            String data = "{\"data\": [\"id\", \"código\", \"descripción\", \"precio\", \"tipo\"]}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("ProductoGetpagesJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/producto/getcolumns.json"})
    public ModelAndView getColumns() throws UnsupportedEncodingException, ServletException {
        try {
            ArrayList<String> alColumns;
            //ProductoDao oProductoDAO = new ProductoDao();
            alColumns = oProductoDAO.getColumnsNames();
            String data = new Gson().toJson(alColumns);
            data = "{\"data\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("ProductoGetcolumnsJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/producto/getregisters.json"})
    public ModelAndView getRegisters(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        //ProductoDao oProductoDAO = new ProductoDao();
        int pages = oProductoDAO.getCount(OrderFilter.getFilter(request));
        String data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
        return new ModelAndView("messageAjax", "contenido", data);
    }

    @RequestMapping({"/producto/{id}/remove.json"})
    public ModelAndView getRemove(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException, Exception {

        try {
            //ProductoDao oProductoDAO = new ProductoDao();
            ProductoBean oProducto = new ProductoBean(id);
            Map<String, String> data = new HashMap<>();
            oProductoDAO.remove(oProducto);
            data.put("status", "200");
            data.put("message", "se ha eliminado el registro con id=" + oProducto.getId());
            Gson gson = new Gson();
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("ProductoRemoveJson: View Error: " + e.getMessage());
        }

    }

    @RequestMapping({"/producto/save.json"})
    public ModelAndView getRemove(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        try {
            //ProductoDao oProductoDAO = new ProductoDao();
            ProductoBean oProducto = new ProductoBean();
            Gson gson = new Gson();
            String jason = request.getParameter("json");
            jason = EncodingUtil.decodeURIComponent(jason);
            oProducto = gson.fromJson(jason, oProducto.getClass());
            Map<String, String> data = new HashMap<>();
            oProducto = oProductoDAO.set(oProducto);
            data.put("status", "200");
            data.put("message", Integer.toString(oProducto.getId()));
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("ProductoSaveJson: View Error: " + e.getMessage());
        }
    }

}
