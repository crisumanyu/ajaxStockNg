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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.bean.TipoproductoBean;
import net.rafaelaznar.dao.TipoproductoDao;
import net.rafaelaznar.helper.EncodingUtil;
import net.rafaelaznar.helper.FilterBean;
import net.rafaelaznar.helper.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Tipoproducto {

    @Autowired
    TipoproductoDao oTipoproductoDAO;

//    @RequestMapping({"/index.html"})
//    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//        return new ModelAndView("index", "contenido", "usuario/inicio.jsp");
//    }
    @RequestMapping({"/tipoproducto/indexlist.html"})
    public ModelAndView indexlist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "tipoproducto/list.jsp");
    }

    @RequestMapping({"/tipoproducto/indexform.html"})
    public ModelAndView indexform(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "tipoproducto/form.jsp");
    }

    @RequestMapping({"/tipoproducto/list.html"})
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("tipoproducto/list");
    }

    @RequestMapping({"/tipoproducto/form.html"})
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("tipoproducto/form");
    }

    @RequestMapping({"/tipoproducto/{id}/get.json"})
    public ModelAndView get(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            TipoproductoBean oTipoproducto = new TipoproductoBean();
            oTipoproducto.setId(id);
            oTipoproductoDAO.get(oTipoproducto);
            data = new Gson().toJson(oTipoproducto);
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("TipoproductoGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/tipoproducto/{rpp}/{page}/getpage.json"})
    public ModelAndView getPage(@PathVariable Integer rpp, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            List<TipoproductoBean> oTipoproductos = oTipoproductoDAO.getPage(rpp, page, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = new Gson().toJson(oTipoproductos);
            data = "{\"list\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("TipoproductoGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/tipoproducto/{rpp}/getpages.json"})
    public ModelAndView getPages(@PathVariable Integer rpp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            int pages = oTipoproductoDAO.getPages(rpp, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("TipoproductoGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/tipoproducto/getprettycolumns.json"})
    public ModelAndView getPrettyColumns() throws UnsupportedEncodingException, ServletException {
        try {
            String data = "{\"data\": [\"id\", \"descripción\"]}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("TipoproductoGetpagesJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/tipoproducto/getcolumns.json"})
    public ModelAndView getColumns() throws UnsupportedEncodingException, ServletException {
        try {
            ArrayList<String> alColumns;
            //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            alColumns = oTipoproductoDAO.getColumnsNames();
            String data = new Gson().toJson(alColumns);
            data = "{\"data\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("TipoproductoGetcolumnsJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/tipoproducto/getregisters.json"})
    public ModelAndView getRegisters(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
        int pages = oTipoproductoDAO.getCount(OrderFilter.getFilter(request));
        String data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
        return new ModelAndView("messageAjax", "contenido", data);
    }

    @RequestMapping({"/tipoproducto/{id}/remove.json"})
    public ModelAndView getRemove(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException, Exception {

        try {
            //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            TipoproductoBean oTipoproducto = new TipoproductoBean(id);
            Map<String, String> data = new HashMap<>();
            oTipoproductoDAO.remove(oTipoproducto);
            data.put("status", "200");
            data.put("message", "se ha eliminado el registro con id=" + oTipoproducto.getId());
            Gson gson = new Gson();
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("TipoproductoRemoveJson: View Error: " + e.getMessage());
        }

    }

    @RequestMapping({"/tipoproducto/save.json"})
    public ModelAndView getRemove(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        try {
            //TipoproductoDao oTipoproductoDAO = new TipoproductoDao();
            TipoproductoBean oTipoproducto = new TipoproductoBean();
            Gson gson = new Gson();
            String jason = request.getParameter("json");
            jason = EncodingUtil.decodeURIComponent(jason);
            oTipoproducto = gson.fromJson(jason, oTipoproducto.getClass());
            Map<String, String> data = new HashMap<>();
            oTipoproducto = oTipoproductoDAO.set(oTipoproducto);
            data.put("status", "200");
            data.put("message", Integer.toString(oTipoproducto.getId()));
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("TipoproductoSaveJson: View Error: " + e.getMessage());
        }
    }

}
