package net.rafaelaznar.bean;

import java.util.Date;

public class CompraBean {

    private Integer id = 0;
    private Integer id_Usuario = 0;
    private Integer id_producto = 0;
    private Integer cantidad = 0;
    private Date fecha = new Date();
    private Integer id_factura = 0;

    public CompraBean() {

    }

    public CompraBean(Integer intId) {
        this.id = intId;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(Integer id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId_factura() {
        return id_factura;
    }

    public void setId_factura(Integer id_factura) {
        this.id_factura = id_factura;
    }

}
