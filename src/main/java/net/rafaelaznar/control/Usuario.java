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
import net.rafaelaznar.bean.UsuarioBean;
import net.rafaelaznar.bean.UsuarioBean;
import net.rafaelaznar.dao.UsuarioDao;
import net.rafaelaznar.dao.UsuarioDao_Mysql;
import net.rafaelaznar.helper.Conexion;
import net.rafaelaznar.helper.EncodingUtil;
import net.rafaelaznar.helper.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Usuario {

//    @Autowired
//    UsuarioDao oUsuarioDAO_Mysql;
    @RequestMapping({"/index.html"})
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "usuario/inicio.jsp");
    }

    @RequestMapping({"/usuario/login01.html"})
    public ModelAndView login01(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "usuario/login01.jsp");
    }

    @RequestMapping({"/usuario/login02.html"})
    public ModelAndView login02(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, Exception {

        UsuarioBean oUsuario = new UsuarioBean();

        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        if (!login.equals("") && !pass.equals("")) {
            oUsuario.setLogin(login);
            oUsuario.setPassword(pass);
            UsuarioDao_Mysql oUsuarioDao = new UsuarioDao_Mysql(Conexion.getConection());
            oUsuario = oUsuarioDao.getFromLogin(oUsuario);
            if (oUsuario.getId() != 0) {
                oUsuario = oUsuarioDao.get(oUsuario);
                if (oUsuario.getLogin().equals(login) && oUsuario.getPassword().equals(pass)) {
                    request.getSession().setAttribute("usuarioBean", oUsuario);
                }
            }
        }
        return new ModelAndView("index", "contenido", "usuario/login02.jsp");
    }

    @RequestMapping({"/usuario/logout.html"})
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, Exception {

        request.getSession().invalidate();
        return new ModelAndView("index", "contenido", "usuario/logout.jsp");
    }

    @Autowired
    UsuarioDao oUsuarioDAO;

    @RequestMapping({"/Usuario/indexlist.html"})
    public ModelAndView indexlist(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "Usuario/list.jsp");
    }

    @RequestMapping({"/Usuario/indexform.html"})
    public ModelAndView indexform(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("index", "contenido", "Usuario/form.jsp");
    }

    @RequestMapping({"/Usuario/list.html"})
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("Usuario/list");
    }

    @RequestMapping({"/Usuario/form.html"})
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        return new ModelAndView("Usuario/form");
    }

    @RequestMapping({"/Usuario/{id}/get.json"})
    public ModelAndView get(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            UsuarioBean oUsuario = new UsuarioBean();
            oUsuario.setId(id);
            oUsuarioDAO.get(oUsuario);
            data = new Gson().toJson(oUsuario);
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("UsuarioGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/Usuario/{rpp}/{page}/getpage.json"})
    public ModelAndView getPage(@PathVariable Integer rpp, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            List<UsuarioBean> ousuarios = oUsuarioDAO.getPage(rpp, page, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = new Gson().toJson(ousuarios);
            data = "{\"list\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("UsuarioGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/Usuario/{rpp}/getpages.json"})
    public ModelAndView getPages(@PathVariable Integer rpp, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException {
        String data;
        try {
            int pages = oUsuarioDAO.getPages(rpp, OrderFilter.getFilter(request), OrderFilter.getOrder(request));
            data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
            return new ModelAndView("messageAjax", "contenido", data);

        } catch (Exception e) {
            throw new ServletException("UsuarioGetJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/Usuario/getprettycolumns.json"})
    public ModelAndView getPrettyColumns() throws UnsupportedEncodingException, ServletException {
        try {
            String data = "{\"data\": [\"id\", \"nombre\", \"primer apellido\", \"segundo apellido\", \"email\"]}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("UsuarioGetpagesJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/Usuario/getcolumns.json"})
    public ModelAndView getColumns() throws UnsupportedEncodingException, ServletException {
        try {
            ArrayList<String> alColumns;
            alColumns = oUsuarioDAO.getColumnsNames();
            String data = new Gson().toJson(alColumns);
            data = "{\"data\":" + data + "}";
            return new ModelAndView("messageAjax", "contenido", data);
        } catch (Exception e) {
            throw new ServletException("UsuarioGetcolumnsJson: View Error: " + e.getMessage());
        }
    }

    @RequestMapping({"/Usuario/getregisters.json"})
    public ModelAndView getRegisters(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        int pages = oUsuarioDAO.getCount(OrderFilter.getFilter(request));
        String data = "{\"data\":\"" + Integer.toString(pages) + "\"}";
        return new ModelAndView("messageAjax", "contenido", data);
    }

    @RequestMapping({"/Usuario/{id}/remove.json"})
    public ModelAndView getRemove(@PathVariable Integer id) throws UnsupportedEncodingException, ServletException, Exception {

        try {
            UsuarioBean oUsuario = new UsuarioBean(id);
            Map<String, String> data = new HashMap<>();
            oUsuarioDAO.remove(oUsuario);
            data.put("status", "200");
            data.put("message", "se ha eliminado el registro con id=" + oUsuario.getId());
            Gson gson = new Gson();
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("UsuarioRemoveJson: View Error: " + e.getMessage());
        }

    }

    @RequestMapping({"/Usuario/save.json"})
    public ModelAndView getRemove(HttpServletRequest request) throws UnsupportedEncodingException, ServletException, Exception {
        try {
            UsuarioBean oUsuario = new UsuarioBean();
            Gson gson = new Gson();
            String jason = request.getParameter("json");
            jason = EncodingUtil.decodeURIComponent(jason);
            oUsuario = gson.fromJson(jason, oUsuario.getClass());
            Map<String, String> data = new HashMap<>();
            oUsuario = oUsuarioDAO.set(oUsuario);
            data.put("status", "200");
            data.put("message", Integer.toString(oUsuario.getId()));
            String resultado = gson.toJson(data);
            return new ModelAndView("messageAjax", "contenido", resultado);
        } catch (Exception e) {
            throw new ServletException("usuariosaveJson: View Error: " + e.getMessage());
        }
    }
    
}
