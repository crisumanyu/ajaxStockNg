
<%-- 
    Document   : infologin
    Created on : Jan 19, 2013, 9:28:49 AM
    Author     : rafa
--%>
<%@page import="net.rafaelaznar.bean.UsuarioBean"%>
<%UsuarioBean user = (UsuarioBean) request.getSession().getAttribute("usuarioBean");%>                   
 <p class="navbar-text pull-right">           
<%if (user != null) {%>
Estas logueado como <%=user.getNombre()%>&nbsp;&nbsp;&nbsp;&nbsp;
<a class="navbar-link" href="<%=request.getContextPath()%>/usuario/logout.html">(Salir del sistema)</a>
<%} else {%>
<a class="navbar-link" href="<%=request.getContextPath()%>/usuario/login01.html">Login</a>
<%}%>
 </p>