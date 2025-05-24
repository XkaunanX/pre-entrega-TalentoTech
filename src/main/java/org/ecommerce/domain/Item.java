package org.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    // Atributos
    private final int idItem;
    private int cantidadProducto;
    private Producto producto;

    @JsonBackReference // Evita ciclos infinitos al serializar
    private Pedido pedido;

    // Constructor
    @JsonCreator
    public Item(
            @JsonProperty("idItem") int idItem,
            @JsonProperty("cantidadProducto") int cantidadProducto,
            @JsonProperty("producto") Producto producto){
        this.idItem = idItem;
        this.cantidadProducto = cantidadProducto;
        this.producto = producto;
    }

    // Setters
    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    // Getters
    public int getIdItem() {
        return idItem;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    @JsonProperty("idPedido")
    public Integer getIdPedido() {
        return (pedido != null) ? pedido.getIdPedido() : null;
    }

    // Metodos
    public float getSubTotal() {
        return producto.getPrecioProducto() * cantidadProducto;
    }
}
