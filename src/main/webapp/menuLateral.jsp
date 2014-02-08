<%-- 
    Document   : menu02
    Created on : Jan 18, 2013, 12:30:02 PM
    Author     : rafa
--%>

<div class="well sidebar-nav">
    <ul class="nav nav-list">
        <li class="nav-header">Usuario</li>
        <li><a id="lnkHome"  href="<%=request.getContextPath()%>/index.html">Home</a></li>
        <li><a id="lnkLogout" href="<%=request.getContextPath()%>/usuario/logout.html">Logout</a></li>
        <li class="nav-header">Mantenimientos</li>            
        <li><a id="lnkCliente" href="#">Cliente</a></li>
        <li><a id="lnkProducto" href="#">Producto</a></li>
    </ul>
</div>


