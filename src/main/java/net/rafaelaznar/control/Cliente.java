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
import net.rafaelaznar.bean.ClienteBean;
import net.rafaelaznar.dao.ClienteDao;
import net.rafaelaznar.helper.EncodingUtil;
import net.rafaelaznar.helper.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Cliente {

    @Autowired
    ClienteDao oClienteDAO;

    @RequestMapping({"/cliente/indexlist.html"})
    public ModelAndView indexlist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "cliente/list.jsp");
    }

    @RequestMapping({"/cliente/indexform.html"})
    public ModelAndView indexform(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "cliente/form.jsp");
    }

    @RequestMapping({"/cliente/list.html"})
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("cliente/list");
    }

    @RequestMapping({"/cliente/form.html"})
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("cliente/form");
    }

    @RequestMapping({"/cliente/{id}/get.json"})
    public ModelAndView get(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            ClienteBean oCliente = new ClienteBean();
            oCliente.setId(id);
            oClienteDAO.get(oCliente);
            data = new Gson().toJson(oCliente);
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("ClienteGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/cliente/{rpp}/{page}/getpage.json"})
    public ModelAndView getPage(@PathVariable Integer rpp, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            List<ClienteBean> oClientes = oClienteDAO.getPage(rpp, page, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = new Gson().toJson(oClientes);
            data = "{\"list\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("ClienteGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/cliente/{rpp}/getpages.json"})
    public ModelAndView getPages(@PathVariable Integer rpp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            int pages = oClienteDAO.getPages(rpp, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("ClienteGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/cliente/getprettycolumns.json"})
    public ModelAndView getPrettyColumns() throws UnsupportedEncodingException, ServletException {
        try {
            String data = "{\"data\": [\"id\", \"nombre\", \"primer apellido\", \"segundo apellido\", \"email\"]}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("ClienteGetpagesJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/cliente/getcolumns.json"})
    public ModelAndView getColumns() throws UnsupportedEncodingException, ServletException {
        try {
            ArrayList<String> alColumns;
            alColumns = oClienteDAO.getColumnsNames();
            String data = new Gson().toJson(alColumns);
            data = "{\"data\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("ClienteGetcolumnsJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/cliente/getregisters.json"})
    public ModelAndView getRegisters(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        int pages = oClienteDAO.getCount(OrderFilter.getFilter(request));
        String data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
        return new ModelAndView("messageAjax", "contenido", data);
    }

    @RequestMapping({"/cliente/{id}/remove.json"})
    public ModelAndView getRemove(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException, Exception {

        try {
            ClienteBean oCliente = new ClienteBean(id);
            Map<String, String> data = new HashMap<>();
            oClienteDAO.remove(oCliente);
            data.put("status", "200");
            data.put("message", "se ha eliminado el registro con id=" + oCliente.getId());
            Gson gson = new Gson();
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("ClienteRemoveJson: View Error: " + e.getMessage());
        }

    }

    @RequestMapping({"/cliente/save.json"})
    public ModelAndView getRemove(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        try {
            ClienteBean oCliente = new ClienteBean();
            Gson gson = new Gson();
            String jason = request.getParameter("json");
            jason = EncodingUtil.decodeURIComponent(jason);
            oCliente = gson.fromJson(jason, oCliente.getClass());
            Map<String, String> data = new HashMap<>();
            oCliente = oClienteDAO.set(oCliente);
            data.put("status", "200");
            data.put("message", Integer.toString(oCliente.getId()));
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("ClienteSaveJson: View Error: " + e.getMessage());
        }
    }

}
