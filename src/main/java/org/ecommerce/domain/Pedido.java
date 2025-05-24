package org.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {
    // Atributos
    private final int idPedido;
    private final LocalDate fechaPedido;
    @JsonManagedReference
    private ArrayList<Item> items;
    private boolean confirmado;

    // Constructor Jackson
    @JsonCreator
    public Pedido(
            @JsonProperty("idPedido") int idPedido,
            @JsonProperty("fechaPedido") LocalDate fechaPedido,
            @JsonProperty("items") ArrayList<Item> items,
            @JsonProperty("confirmado") boolean confirmado) {
        this.idPedido = idPedido;
        this.fechaPedido = (fechaPedido != null) ? fechaPedido : LocalDate.now();
        this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        this.confirmado = confirmado;
    }

    // Constructor
    public Pedido(int id) {
        this.idPedido = id;
        this.fechaPedido = LocalDate.now();
        this.items = new ArrayList<>();
        this.confirmado = false;
    }

    // Getters
    public int getIdPedido() {
        return idPedido;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    // Metodos
    public void addItem(Item item){
        items.add(item);
    }

    public void deleteItem(int idItem) {
        items.removeIf(item -> item.getIdItem() == idItem);
    }

    public void confirmar() {
        this.confirmado = true;
    }

    public float getTotal() {
        float total = 0f;
        for (Item item : items) {
            total += item.getSubTotal();
        }
        return total;
    }
}
