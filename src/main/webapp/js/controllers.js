'use strict';




/* Controllers */

var modulo01 = angular.module('myApp.controllers', []);

modulo01.controller('MyCtrl1', function($scope, $routeParams, serverService) {

//    $scope.nrpps = [{
//            id: 5,
//            desc: "5 registros"
//        }, {
//            id: 10,
//            desc: "10 registros"
//        }, {
//            id: 50,
//            desc: "50 registros"
//        }, {
//            id: 100,
//            desc: "100 registros"
//        }
//    ];

    //$scope.nrpp = 10;


//    $http({
//        method: 'GET',
//        url: '/cliente/getcolumns.json'  
//                //data: { applicationId: 3 }
//    }).success(function(result) {
//        $scope.fieldNames = result;
//    });



    $scope.numPagina = $routeParams.numpage;
    $scope.nrpp = $routeParams.numrpp;
    $scope.botoneraNrpp = serverService.getNrppBar($scope.numPagina, $scope.nrpp);
    
    $scope.pages = serverService.getPages('cliente', $scope.nrpp, null, null, null, null, null, null).then(function(datos5) {
        $scope.pages = datos5['data'];
    });

    $scope.$watch('pages', function() {
        $scope.$broadcast('myApp.construirBotoneraPaginas');
    }, true)

    $scope.$on('myApp.construirBotoneraPaginas', function() {
        $scope.botoneraPaginas = serverService.getPaginationBar($scope.numPagina, $scope.pages, 2, $scope.nrpp);

    })



    //$scope.nrpp = 5;

//    $scope.cliente = serverService.get('cliente', 2).then(function(datos) {
//        $scope.cliente = datos;
//    });

    $scope.prettyFieldNames = serverService.getPrettyFieldNames('cliente').then(function(datos4) {
        datos4['data'].push('acciones');
        $scope.prettyFieldNames = datos4['data'];
    });

    $scope.clientes = serverService.getPage('cliente', $scope.numPagina, null, null, $scope.nrpp, null, null, null, null, null, null).then(function(datos3) {
        $scope.clientes = datos3['list'];

    });



    $scope.fieldNames = serverService.getFieldNames('cliente').then(function(datos6) {
        $scope.fieldNames = datos6['data'];
        $scope.selectedFilterFieldName = null;
    });

//    $scope.$watch('nrpp', function() {
//        $scope.$broadcast('myApp.eventoClienteInicia');
//    }, true)
//
//    $scope.$on('myApp.eventoClienteInicia', function() {
//
//
//    })

});

//modulo01.controller('MyCtrl1', function($scope, $http) {
// $scope.foos = myService.getFoos().then(function(foos) {
//        $scope.resultado = foos;
//    });
////    $scope.resultado = "vacío";
////    $scope.estado = 0;
////    $scope.pedirajax = function() {
////        $http(
////             "/cliente/1/get.json"
////        ).success(function(data, status, headers, config) {
////            $scope.resultado = data;
////            $scope.estado = status;
////            console.log("----Resultados:");
////            console.log($scope.resultado);
////            console.log($scope.estado);
////        }).error(function(data, status, headers, config) {
////            $scope.resultado = status;
////        });
////    }
//
//
//
//
//});


modulo01.controller('MyCtrl2', function($scope) {
    $scope.flores = [{nombre: 'margarita'}, {nombre: 'jazmin'}];
});