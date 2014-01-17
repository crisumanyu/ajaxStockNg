/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rafaelaznar.operaciones;

import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.rafaelaznar.data.MysqlData;

/**
 *
 * @author rafa
 */
public class TipoproductoGetprettycolumns implements GenericOperation {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String data = "{\"data\": [\"id\", \"descripción\"]}";
            return data;
        } catch (Exception e) {
            throw new ServletException("TipoproductoGetpagesJson: View Error: " + e.getMessage());
        }
    }
}
