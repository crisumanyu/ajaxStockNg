'use strict';
function getNrppBar(page_number, nrpp) {
    var link = '#/cliente/';
    var vector = "<div class=\"pagination\"><ul>";
    if (nrpp == 5)
        vector += "<li class=\"active\" >";
    else
        vector += "<li> ";
    vector += "<a class=\"nrpp\" id=\"nrrp5\" href=\"" + link + page_number + "/5" + "\">5</a></li>";
    if (nrpp == 10)
        vector += "<li class=\"active\" >";
    else
        vector += "<li> ";
    vector += "<a class=\"nrpp\" id=\"nrrp10\" href=\"" + link + page_number + "/10" + "\">10</a></li>";
    if (nrpp == 20)
        vector += "<li class=\"active\" >";
    else
        vector += "<li> ";
    vector += "<a class=\"nrpp\" id=\"nrrp20\" href=\"" + link + page_number + "/20" + "\">20</a></li>";
    if (nrpp == 50)
        vector += "<li class=\"active\" >";
    else
        vector += "<li> ";
    ;
    vector += "<a class=\"nrpp\" id=\"nrrp50\" href=\"" + link + page_number + "/50" + "\">50</a></li>";
    if (nrpp == 100)
        vector += "<li class=\"active\" >";
    else
        vector += "<li> ";
    vector += "<a class=\"nrpp\" id=\"nrrp100\" href=\"" + link + page_number + "/100" + "\">100</a></li>";
    // http://localhost:8081/AjaxStockUniDaoSpring/index.jsp#/cliente/4/nrpp
    vector += "</ul></div>";
    return vector;
}
function getPaginationBar(page_number, total_pages, neighborhood, nrpp) {
    page_number = parseInt(page_number);
    total_pages = parseInt(total_pages);
    neighborhood = parseInt(neighborhood);
    var link = '#/cliente/';
    var vector = "<div class=\"pagination\"><ul>";
    if (page_number > 1)
        vector += ("<li><a class=\"pagination_link\" id=\"" + (page_number - 1) + "\" href=\"" + link + (page_number - 1) + "/" + nrpp + "\">prev</a></li>");
    if (page_number > neighborhood + 1)
        vector += ("<li><a class=\"pagination_link\" id=\"1\" href=\"" + link + "/" + nrpp + "1\">1</a></li>");
    if (page_number > neighborhood + 2)
        vector += ("<li>" + "<a href=\"#\">...</a>" + "</li>");
    for (var i = (page_number - neighborhood); i <= (page_number + neighborhood); i++) {
        if (i >= 1 && i <= total_pages) {
            if (page_number == i) {
                vector += ("<li class=\"active\"><a class=\"pagination_link\" id=\"" + i + "\" href=\"" + link + i + "/" + nrpp + "\">" + i + "</a></li>");
            }
            else
                vector += ("<li><a class=\"pagination_link\" id=\"" + i + "\" href=\"" + link + i + "/" + nrpp + "\">" + i + "</a></li>");
        }
    }
    if (page_number < total_pages - (neighborhood + 1))
        vector += ("<li>" + "<a href=\"#\">...</a>" + "</li>");
    if (page_number < total_pages - (neighborhood))
        vector += ("<li><a class=\"pagination_link\" id=\"" + total_pages + "\" href=\"" + link + total_pages + "/" + nrpp + "\">" + total_pages + "</a></li>");
    if (page_number < total_pages)
        vector += ("<li><a class=\"pagination_link\"  id=\"" + (page_number + 1) + "\" href=\"" + link + (page_number + 1) + "/" + nrpp + "\">next</a></li>");
    vector += "</ul></div>";
    return vector;
}
;


/* Controllers */

var modulo01 = angular.module('myApp.controllers', []);

modulo01.controller('MyCtrl1', function($scope, $routeParams, serverService) {

    $scope.nrpps = [{
            id: 5,
            desc: "5 registros"
        }, {
            id: 10,
            desc: "10 registros"
        }, {
            id: 50,
            desc: "50 registros"
        }, {
            id: 100,
            desc: "100 registros"
        }
    ];

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
    $scope.botoneraNrpp = getNrppBar($scope.numPagina, $scope.nrpp);

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

    $scope.pages = serverService.getPages('cliente', $scope.nrpp, null, null, null, null, null, null).then(function(datos5) {
        $scope.pages = datos5['data'];
        $scope.botoneraPaginas = getPaginationBar($scope.numPagina, $scope.pages, 2, $scope.nrpp);
    });



    $scope.fieldNames = serverService.getFieldNames('cliente').then(function(datos6) {
        $scope.fieldNames = datos6['data'];
        $scope.selectedFilterFieldName = null;
    });

    $scope.$watch('nrpp', function() {
        $scope.$broadcast('myApp.eventoClienteInicia');
    }, true)

    $scope.$on('myApp.eventoClienteInicia', function() {


    })

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