

<div class="row-fluid">
    <div class="span12">       
        <h2>Vista de Usuario</h2>
        <a ng-click="back()">Volver</a>
    </div>
    <div id="datos">
        <table class="table table-striped table-condensed">
            <thead>                
                <tr>    
                    <th>campo</th>
                    <th>valor</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>id</td><td>{{cli.id}}</td></tr>
                <tr><td>nombre</td><td>{{cli.nombre}}</td>
                <tr><td>email</td><td>{{cli.email}}</td>
                <tr><td>login</td><td>{{cli.login}}</td>   
                <tr><td>password</td><td>{{cli.password}}</td>   
                <tr><td>tipo de usuario</td><td>{{cli.id_tipousuario}}</td>
            </tbody>
        </table>            
    </div>
</div>
