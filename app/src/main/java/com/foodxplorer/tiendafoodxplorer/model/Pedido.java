package com.foodxplorer.tiendafoodxplorer.model;

/**
 * Created by ALUMNEDAM on 15/05/2017.
 */

public class Pedido {
    private long idPedido;
    private String fechaPedido;
    private long idDireccion;
    private long idEstado;

    public Pedido() {
    }

    public Pedido(long idPedido, String fechaPedido, long idDireccion, long idEstado) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.idDireccion = idDireccion;
        this.idEstado = idEstado;
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
