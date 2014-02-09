'use strict';

var appName='AjaxStockUniDaoSpring';



// Declare app level module which depends on filters, and services
angular.module('myApp', [
    'ngRoute',
    'myApp.filters',
    'myApp.services',
    'myApp.directives',
    'myApp.controllers',
     'ngSanitize' //http://stackoverflow.com/questions/9381926/insert-html-into-view-using-angularjs
]).
        config(['$routeProvider', function($routeProvider) {
                $routeProvider.when('/cliente/:numpage/:numrpp', {templateUrl: 'partials/cliente.jsp', controller: 'MyCtrl1'});
                $routeProvider.when('/view2', {templateUrl: 'partials/partial2.jsp', controller: 'MyCtrl2'});
                $routeProvider.otherwise({redirectTo: '/view1'});
            }]);
