<%-- 
    Document   : form
    Created on : Jan 21, 2013, 10:24:17 AM
    Author     : rafa
--%>
<form class="form-horizontal" action="#" id="formulario" name="formulario">
    <fieldset>
        <div class="control-group">
            <label class="control-label" for="id">Id: </label>
            <div class="controls">
                <input id="id" name="id" type="text" size="30" maxlength="50" />
            </div>
        </div>        
        <div class="control-group">
            <label class="control-label" for="codigo">Código: </label>
            <div class="controls">
                <input id="codigo" name="codigo" type="text" size="30" maxlength="50" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="descripcion">Descripción: </label> 
            <div class="controls">
                <input id="descripcion" name="descripcion" type="text" size="30" maxlength="50" />
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="precio">Precio: </label> 
            <div class="controls">
                <input id="precio" name="precio" type="text" size="30" maxlength="50" /> 
            </div>
        </div>           
        <div class="control-group">
            <label class="control-label" for="id_tipoproducto">Tipo de producto: </label> 
            <div class="controls"> 
                <!-- <span id="id_tipoproducto" class="alert alert-info"></span> -->
                <input readonly="true" id="id_tipoproducto" class="input-mini"
                       name="id_tipoproducto" type="text" size="5" maxlength="5" />  
                <a class="btn btn-mini" id="id_tipoproducto_button" href="#"><i class="icon-search"></i></a>
            </div>
            </div>
            <div class="control-group">
            <div class="controls">
                <span id="id_tipoproducto_desc" class="alert alert-success"></span>                                       
            </div>
        </div>             
        <div class="control-group">
            <div class="controls">
               <button type="submit" id="submitForm" class="btn submitForm">Submit</button>
            </div>
        </div>
    </fieldset>
</form>
