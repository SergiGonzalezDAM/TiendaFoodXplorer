package com.foodxplorer.tiendafoodxplorer.model;

import android.graphics.drawable.Drawable;

public class Producto
{

    private Drawable imagenProducto;
    private String Nombre;
    private int idProducto;
    String linkImagen;
    private double precio;
    int iva;
    double ofertaProducto;
    int tipoProducto;
    int activo;
    String descripcion;



    public Producto(Drawable imagenProducto, String nombreProducto, int idProducto, double precio, String descripcion)
    {
        this.Nombre = nombreProducto;
        this.imagenProducto = imagenProducto;
        this.idProducto = idProducto;
        this.precio = precio;
        this.descripcion = descripcion;
    }
    public Producto()
    {
        System.out.println("hola");
    }
    public Producto(int idProducto, String nombreProducto, String descripcion, double precio, int iva, double ofertaProducto, int activo, int tipoProducto, String linkImagen)
    {
        this.Nombre = nombreProducto;
        this.linkImagen = linkImagen;
        this.iva = iva;
        this.activo = activo;
        this.tipoProducto = tipoProducto;
        this.ofertaProducto = ofertaProducto;
        this.idProducto = idProducto;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Producto(Drawable imagenProducto, String nombreProducto, double precio, String descripcion)
    {
        this.Nombre = nombreProducto;
        this.imagenProducto = imagenProducto;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Drawable getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(Drawable imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getLinkImagen() {
        return linkImagen;
    }

    public void setLinkImagen(String linkImagen) {
        this.linkImagen = linkImagen;
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

    public double getOfertaProducto() {
        return ofertaProducto;
    }

    public void setOfertaProducto(double ofertaProducto) {
        this.ofertaProducto = ofertaProducto;
    }

    public int getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(int tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "imagenProducto=" + imagenProducto +
                ", Nombre='" + Nombre + '\'' +
                ", idProducto=" + idProducto +
                ", linkImagen='" + linkImagen + '\'' +
                ", precio=" + precio +
                ", iva=" + iva +
                ", ofertaProducto=" + ofertaProducto +
                ", tipoProducto=" + tipoProducto +
                ", activo=" + activo +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}