

<div class="row-fluid">
    <div class="span12">       
        <h2>Vista de Cliente</h2>
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
                <tr><td>primer apellido</td><td>{{cli.ape1}}</td>
                <tr><td>segundo apellido</td><td>{{cli.ape2}}</td>
                <tr><td>email</td><td>{{cli.email}}</td>                
            </tbody>
        </table>            
    </div>
</div>
