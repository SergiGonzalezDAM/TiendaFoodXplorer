package com.foodxplorer.tiendafoodxplorer.model;

import java.io.Serializable;

/**
 * Created by ALUMNEDAM on 15/05/2017.
 */

public class Pedido implements Serializable {
    private long idPedido;
    private String fechaPedido;
    private long idDireccion;
    private long idEstado;
    private String correo;

    public Pedido() {
    }

    public Pedido(long idPedido, String fechaPedido, long idDireccion, long idEstado, String correo) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.idDireccion = idDireccion;
        this.idEstado = idEstado;
        this.correo = correo;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fechaPedido='" + fechaPedido + '\'' +
                ", idDireccion=" + idDireccion +
                ", idEstado=" + idEstado +
                '}';
    }
}
