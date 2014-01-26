//MODELO
var objeto = function(clase, ContextPath) {
    //contexto privado
    function getFilter(filter, filteroperator, filtervalue) {
        if (filter) {
            filterParams = "&filter=" + filter + "&filteroperator=" + filteroperator + "&filtervalue=" + filtervalue;
        } else {
            filterParams = "";
        }
        return filterParams;
    }
    function getOrder(order, ordervalue) {
        if (order) {
            orderParams = '&order=' + order + '&ordervalue=' + ordervalue;
        } else {
            orderParams = "";
        }
        return orderParams;
    }
    var urlDatos = ContextPath + '/' + clase;
    return {
        //contexto público (interface)
        getName: function() {
            return clase;
        },
        getPrettyFieldNames: function() {
            $.when(ajaxCallSync(urlDatos + '/getprettycolumns', 'GET', '')).done(function(data) {
                prettyFieldNames = data['data'];
            });
            return prettyFieldNames;
        },
        getPrettyFieldNamesAcciones: function() {
            $.when(ajaxCallSync(urlDatos + '/getprettycolumns', 'GET', '')).done(function(data) {
                prettyFieldNames = data['data'];
                prettyFieldNames.push("acciones");

            });
            return prettyFieldNames;
        },
        getCountFields: function() {
            $.when(ajaxCallSync(urlDatos + '/getcolumns', 'GET', '')).done(function(data) {
                countFields = data['data'].length;
            });
            return countFields;
        },
        getFieldNames: function() {
            $.when(ajaxCallSync(urlDatos + '/getcolumns', 'GET', '')).done(function(data) {
                fieldNames = data['data'];
            });
            return fieldNames;
        },
        getPage: function(pagina, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
            orderParams = getOrder(order, ordervalue);
            filterParams = getFilter(filter, filteroperator, filtervalue);
            systemfilterParams = getFilter(systemfilter, systemfilteroperator, systemfiltervalue);
            $.when(ajaxCallSync(urlDatos + '/' + rpp + '/' + pagina + 'getpage.json?' + filterParams + orderParams + systemfilterParams, 'GET', '')).done(function(data) {
                pagina_objs = data;
            });
            return pagina_objs;
        },
        getPages: function(rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
            filterParams = getFilter(filter, filteroperator, filtervalue);
            systemfilterParams = getFilter(systemfilter, systemfilteroperator, systemfiltervalue);
            $.when(ajaxCallSync(urlDatos + '/' + rpp + '/' + 'getpages.json?' + filterParams + systemfilterParams, 'GET', '')).done(function(data) {
                pages = data['data'];
            });
            return pages;
        },
        getRegisters: function(filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
            filterParams = getFilter(filter, filteroperator, filtervalue);
            systemfilterParams = getFilter(systemfilter, systemfilteroperator, systemfiltervalue);
            $.when(ajaxCallSync(urlDatos + '/getregisters.json?' + filterParams + systemfilterParams, 'GET', '')).done(function(data) {
                regs = data['data'];
            });
            return regs;
        },
        getOne: function(id) {
            $.when(ajaxCallSync(urlDatos + '/' + id + '/get.json', 'GET', '')).done(function(data) {
                one = data;
            });
            return one;
        },
        saveOne: function(jsonfile) {
            $.when(ajaxCallSync(urlDatos + '/save.json', 'GET', jsonfile)).done(function(data) {
                feedback = data;
            });
            return feedback;
        },
        removeOne: function(id) {
            $.when(ajaxCallSync(urlDatos + '/' + id + '/remove.json' + id, 'GET', '')).done(function(data) {
                feedback = data;
            });
            return feedback;
        }
    };
};
//VISTA
var vista = function(objeto, ContextPath) {
    //contexto privado
    var link = "#";
    var neighborhood = 2;
    var urlDatos = ContextPath + '/' + objeto.getName() ;
            return {
                //contexto público (interface)
                getObject: function() {
                    return objeto;
                },
                getLoading: function() {
                    return '<img src="img/ajax-loading.gif" alt="cargando..." />';
                },
                getPageLinks: function(page_number, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
                    page_number = parseInt(page_number);
                    total_pages = parseInt(objeto.getPages(rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue));
                    neighborhood = parseInt(neighborhood);
                    vector = "<div class=\"pagination\"><ul>";
                    if (page_number > 1)
                        vector += ("<li><a class=\"pagination_link\" id=\"" + (page_number - 1) + "\" href=\"" + link + (page_number - 1) + "\">prev</a></li>");
                    if (page_number > neighborhood + 1)
                        vector += ("<li><a class=\"pagination_link\" id=\"1\" href=\"" + link + "1\">1</a></li>");
                    if (page_number > neighborhood + 2)
                        vector += ("<li>" + "<a href=\"#\">...</a>" + "</li>");
                    for (i = (page_number - neighborhood); i <= (page_number + neighborhood); i++) {
                        if (i >= 1 && i <= total_pages) {
                            if (page_number == i) {
                                vector += ("<li class=\"active\"><a class=\"pagination_link\" id=\"" + i + "\" href=\"" + link + i + "\">" + i + "</a></li>");
                            }
                            else
                                vector += ("<li><a class=\"pagination_link\" id=\"" + i + "\" href=\"" + link + i + "\">" + i + "</a></li>");
                        }
                    }
                    if (page_number < total_pages - (neighborhood + 1))
                        vector += ("<li>" + "<a href=\"#\">...</a>" + "</li>");
                    if (page_number < total_pages - (neighborhood))
                        vector += ("<li><a class=\"pagination_link\" id=\"" + total_pages + "\" href=\"" + link + total_pages + "\">" + total_pages + "</a></li>");
                    if (page_number < total_pages)
                        vector += ("<li><a class=\"pagination_link\"  id=\"" + (page_number + 1) + "\" href=\"" + link + (page_number + 1) + "\">next</a></li>");
                    vector += "</ul></div>";
                    return vector;
                },
                getPageTable: function(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue, botonera) {
                    var tabla = "<table class=\"table table table-striped table-condensed\">";
                    if (objeto.getPrettyFieldNamesAcciones() != null) {
                        tabla += '<tr>';
                        $.each(objeto.getPrettyFieldNamesAcciones(), function(index, value) {
                            tabla += '<th>' + value;
                            tabla += '<a class="orderAsc' + index + '" href="#"><i class="icon-arrow-up"></i></a>';
                            tabla += '<a class="orderDesc' + index + '" href="#"><i class="icon-arrow-down"></i></a>';
                            tabla += '</th>';
                        });
                        tabla += '</tr>';
                    }
                    page = objeto.getPage(pag, order, ordervalue, rpp, filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue)['list'];
                    $.each(page, function(index, value) {
                        tabla += '<tr>';

                        $.each(objeto.getFieldNames(), function(index, valor) {
                            if (/id_/.test(valor)) {
                                $.when(ajaxCallSync(ContextPath + '/json?ob=' + valor.split("_")[1] + '&op=get&id=' + value[valor], 'GET', '')).done(function(data) {

                                    contador = 0;
                                    add_tabla = "";
                                    for (key in data) {
                                        if (contador == 0)
                                            add_tabla = '<td>id=' + data[key] + '(no existe)</td>';
                                        if (contador == 1)
                                            add_tabla = '<td>' + data[key] + '</td>';
                                        contador++;
                                    }
                                    if (contador == 0) {
                                        add_tabla = '<td>' + value[valor] + ' #error</td>';
                                    }
                                    tabla += add_tabla;
                                });
                            } else {
                                switch (value[valor]) {
                                    case true:
                                        tabla += '<td><i class="icon-ok"></i></td>';
                                        break;
                                    case false:
                                        tabla += '<td><i class="icon-remove"></i></td>';
                                        break;
                                    default:
                                        tabla += '<td>' + value[valor] + '</td>';
                                }
                            }
                        });
                        tabla += '<td><div class="btn-toolbar"><div class="btn-group">';

                        $.each(botonera, function(indice, valor) {
                            tabla += '<a class="' + valor.class + '" id=' + value.id + ' href="#"><i class="' + valor.icon + '"></i> ' + valor.text + '</a>';
                        });
                        tabla += '</div></div></td>';
                        tabla += '</tr>';
                    });
                    tabla += "</table>";
                    return tabla;
                },
                getObjectTable: function(id) {
                    cabecera = objeto.getPrettyFieldNames();
                    datos = objeto.getOne(id);
                    var tabla = "<table class=\"table table table-bordered table-condensed\">";
                    $.each(objeto.getFieldNames(), function(index, valor) {
                        switch (datos[valor]) {
                            case true:
                                tabla += '<tr><td><strong>' + cabecera[index] + '</strong></td><td><i class="icon-ok"></i></td></tr>';
                                break;
                            case false:
                                tabla += '<tr><td><strong>' + cabecera[index] + '</strong></td><td><i class="icon-remove"></i></td></tr>';
                                break;
                            default:
                                tabla += '<tr><td><strong>' + cabecera[index] + '</strong></td><td>' + datos[valor] + '</td></tr>';
                        }
                    });
                    tabla += '</table>';
                    return tabla;
                },
                getEmptyList: function() {
                    $.when(ajaxCallSync(urlDatos + '/list.html', 'GET', '')).done(function(data) {
                        form = data;
                    });
                    return form;
                },
                getEmptyForm: function() {
                    $.when(ajaxCallSync(urlDatos + '/form.html', 'GET', '')).done(function(data) {
                        form = data;
                    });
                    return form;
                },
                doFillForm: function(id) {
                    campos = objeto.getFieldNames();
                    datos = objeto.getOne(id);
                    $.each(campos, function(index, valor) {
                        var a = true;
                        switch (datos[campos[index]]) {
                            case true:
                                $('#' + campos[index]).attr("checked", "checked");
                                break;
                            case false:
                                break;
                            default:
                                $('#' + campos[index]).val(datos[campos[index]]);
                        }

                    });
                },
                getRegistersInfo: function(filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue) {
                    regs = this.getObject().getRegisters(filter, filteroperator, filtervalue, systemfilter, systemfilteroperator, systemfiltervalue);
                    return "<p>Mostrando una consulta de " + regs + " registros.</p>";
                },
                getOrderInfo: function(order, ordervalue) {
                    if (order) {
                        strOrder = "<p>Contenido ordenado por " + order + " (" + ordervalue + ') <a href="#" id="linkQuitarOrden">(Quitar orden)</a></p>';
                    } else {
                        strOrder = "<p>Contenido no ordenado</p>";
                    }
                    return strOrder;
                },
                getFilterInfo: function(filter, filteroperator, filtervalue) {
                    if (filter) {
                        strFilter = "<p>Contenido filtrado (" + filter + " " + filteroperator + " " + filtervalue + ') <a href="#" id="linkQuitarFiltro">(Quitar filtro)</a></p>';
                    } else {
                        strFilter = "<p>Contenido no filtrado</p>";
                    }
                    return strFilter;
                }
            };

};


