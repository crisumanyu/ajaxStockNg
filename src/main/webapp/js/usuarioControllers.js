'use strict';

/* Controllers */

var moduloUsuario = angular.module('myApp.UsuarioControllers', []);



moduloUsuario.controller('controlusuariosList', function($scope, $routeParams, serverService) {

    $scope.numPagina = $routeParams.numpage;
    $scope.nrpp = $routeParams.numrpp;
    $scope.botoneraNrpp = serverService.getNrppBar('usuarios', $scope.numPagina, $scope.nrpp);

    $scope.pages = serverService.getPages('Usuario', $scope.nrpp, null, null, null, null, null, null).then(function(datos5) {
        $scope.pages = datos5['data'];
        if (parseInt($scope.numPagina) > parseInt($scope.pages))
            $scope.numPagina = $scope.pages;
            //$location.path( "#/usuarios/" +$scope.pages + "/" + $scope.pages);
    });

    $scope.$watch('pages', function() {
        $scope.$broadcast('myApp.construirBotoneraPaginas');
    }, true)

    $scope.$on('myApp.construirBotoneraPaginas', function() {

        $scope.botoneraPaginas = serverService.getPaginationBar('usuarios', $scope.numPagina, $scope.pages, 2, $scope.nrpp);
    })

    $scope.prettyFieldNames = serverService.getPrettyFieldNames('Usuario').then(function(datos4) {
        datos4['data'].push('acciones');
        $scope.prettyFieldNames = datos4['data'];
    });

    $scope.usuarios = serverService.getPage('Usuario', $scope.numPagina, null, null, $scope.nrpp, null, null, null, null, null, null).then(function(datos3) {
        $scope.usuarios = datos3['list'];

    });

    $scope.fieldNames = serverService.getFieldNames('Usuario').then(function(datos6) {
        $scope.fieldNames = datos6['data'];
        $scope.selectedFilterFieldName = null;
    });


    $scope.$watch('numPagina', function() {
        $scope.$broadcast('myApp.construirPagina');
    }, true)

    $scope.$on('myApp.construirPagina', function() {

        $scope.usuarios = serverService.getPage('Usuario', $scope.numPagina, null, null, $scope.nrpp, null, null, null, null, null, null).then(function(datos3) {
            $scope.usuarios = datos3['list'];

        });

    })


});

moduloUsuario.controller('controlUsuarioView', function($scope, $routeParams, serverService) {
    $scope.back = function() {
        window.history.back();
    };
    $scope.idUsuario = $routeParams.id;
    $scope.cli = serverService.get('Usuario', $scope.idUsuario).then(function(datos4) {

        $scope.cli = datos4;
    });
});


moduloUsuario.controller('MyCtrl2', function($scope) {
    $scope.flores = [{nombre: 'margarita'}, {nombre: 'jazmin'}];
});