'use strict';
/* Services */
// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('myApp.services', [])
        .factory('serverService', function($http) {
            function getFilter(filter, filteroperator, filtervalue) {
                var filterParams;
                if (filter) {
                    filterParams = "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
                } else {
                    filterParams = "";
                }
                return filterParams;
            }
            ;
            function getOrder(order, ordervalue) {
                var orderParams;
                if (order) {
                    orderParams = '&order=' + order + '&ordervalue=' + ordervalue;
                } else {
                    orderParams = "";
                }
                return orderParams;
            }
            ;
            return {
                get: function(objeto, numero) {
                    return $http.get('/' + appName + '/' + objeto + '/' + numero + '/get.json').then(function(result) {
                        return result.data;
                    });
                },
                getPage: function(objeto, pagina, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
                    var orderParams = getOrder(order, ordervalue);
                    var filterParams = getFilter(filter, filteroperator, filtervalue);
                    var systemfilterParams = getFilter(systemfilter, systemfilteroperator, systemfiltervalue);
                    //console.log('/' + appName + '/' + objeto + '/' + rpp + '/' + pagina + '/getpage.json?' + filterParams + orderParams + systemfilterParams);
                    return $http.get('/' + appName + '/' + objeto + '/' + rpp + '/' + pagina + '/getpage.json?' + filterParams + orderParams + systemfilterParams).then(function(result) {
                        return result.data;
                    });
                },
                getFieldNames: function(objeto) {
                    return $http.get('/' + appName + '/' + objeto + '/getcolumns.json').then(function(result) {
                        return result.data;
                    });
                },                
                getPrettyFieldNames: function(objeto) {
                    return $http.get('/' + appName + '/' + objeto + '/getprettycolumns.json').then(function(result) {
                        return result.data;
                    });
                },
                getPages: function(objeto, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
                    var filterParams = getFilter(filter, filteroperator, filtervalue);
                    var systemfilterParams = getFilter(systemfilter, systemfilteroperator, systemfiltervalue);
                    return $http.get('/' + appName + '/' + objeto + '/' + rpp + '/getpages.json?' + filterParams + systemfilterParams).then(function(result) {
                        return result.data;
                    });
                }
            };
        })
        .value('version', '0.1');
