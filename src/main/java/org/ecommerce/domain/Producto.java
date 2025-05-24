package org.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Producto {
    // Atributos
    private final int idProducto; // La ID no cambia
    private String nombreProducto;
    private float precioProducto;
    private int stockProducto;

    // Constructor
    public Producto(@JsonProperty("idProducto") int idProducto,
                    @JsonProperty("nombreProducto") String nombreProducto,
                    @JsonProperty("precioProducto") float precioProducto,
                    @JsonProperty("stockProducto") int stockProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
    }

    // Setters
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecioProducto(float precioProducto) {
        this.precioProducto = precioProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    // Getters
    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }
}
