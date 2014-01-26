/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.control;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.bean.ClienteBean;
import net.rafaelaznar.bean.UsuarioBean;
import net.rafaelaznar.dao.ClienteDao;
import net.rafaelaznar.dao.UsuarioDao_Mysql;
import net.rafaelaznar.helper.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
