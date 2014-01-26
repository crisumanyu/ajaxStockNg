<%-- 
    Document   : form
    Created on : Jan 21, 2013, 10:24:17 AM
    Author     : rafa
--%>
<%@page import="java.text.SimpleDateFormat"%>
<form class="form-horizontal" action="#" id="formulario" name="formulario">
    <fieldset>

        <div class="control-group">
            <label class="control-label" for="id">Id: </label>
            <div class="controls">
                <input id="id" name="id" type="text" size="30" maxlength="50" />
            </div>
        </div>        


        <div class="control-group">
            <label class="control-label" for="id_cliente">Cliente: </label> 
            <div class="controls">           
                <input readonly="true" id="id_cliente" class="input-mini"
                       name="id_cliente" type="text" size="5" maxlength="5" />  
                <a class="btn btn-mini" id="id_cliente_button" href="#"><i class="icon-search"></i></a>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <span id="id_cliente_desc" class="alert alert-success"></span>                                       
            </div>
        </div> 

        <div class="control-group">
            <label class="control-label" for="id_producto">Producto: </label> 
            <div class="controls">                
                <input readonly="true" id="id_producto" class="input-mini"
                       name="id_producto" type="text" size="5" maxlength="5" />  
                <a class="btn btn-mini" id="id_producto_button" href="#"><i class="icon-search"></i></a>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <span id="id_producto_desc" class="alert alert-success"></span>                                       
            </div>
        </div> 




        <div class="control-group">
            <label class="control-label" for="codigo">Cantidad: </label>
            <div class="controls">
                <input id="cantidad" name="cantidad" type="text" size="30" maxlength="50" />
            </div>
        </div>


       <div class="control-group">
            <label class="control-label" for="descripcion">Fecha: </label> 
            <div class="controls">
                <input id="fecha" name="fecha" type="text" size="10" maxlength="50" value="" /> 
            </div>
        </div>
        <script>$("#fecha").datepicker({
                showOn: 'both',
                buttonImageOnly: true,
                changeYear: true,
                numberOfMonths: 1});
        </script>

        <div class="control-group">
            <div class="controls">
                <button type="submit" id="submitForm" class="btn submitForm">Submit</button>
            </div>
        </div>
    </fieldset>
</form>
<script>
    $(function() {
        $("#datepicker").datepicker();
    });
</script>
