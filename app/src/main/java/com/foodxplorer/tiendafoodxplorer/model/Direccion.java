package com.foodxplorer.tiendafoodxplorer.model;

/**
 * Created by ALUMNEDAM on 24/05/2017.
 */

public class Direccion {
    private long idDireccion;
    private String calle;
    private String piso;
    private String poblacion;
    private String codPostal;

    public Direccion() {
    }

    public Direccion(long idDireccion, String calle, String piso, String poblacion, String codPostal) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.piso = piso;
        this.poblacion = poblacion;
        this.codPostal = codPostal;
    }

    public long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "idDireccion=" + idDireccion +
                ", calle='" + calle + '\'' +
                ", piso='" + piso + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", codPostal='" + codPostal + '\'' +
                '}';
    }
}
