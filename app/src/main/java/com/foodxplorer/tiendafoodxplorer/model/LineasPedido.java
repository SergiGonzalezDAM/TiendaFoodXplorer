package com.foodxplorer.tiendafoodxplorer.model;

/**
 * Created by ALUMNEDAM on 24/05/2017.
 */

public class LineasPedido {
    private long idPedido;
    private long idProducto;
    private int cantidad;
    private double precio;
    private int iva;

    public LineasPedido() {
    }

    public LineasPedido(long idPedido, long idProducto, int cantidad, double precio, int iva) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.iva = iva;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return "LineasPedido{" +
                "idPedido=" + idPedido +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", iva=" + iva +
                '}';
    }
}
