package org.ecommerce.controller;
import org.ecommerce.domain.Pedido;
import org.ecommerce.services.PedidoService;

import java.util.Optional;
import java.util.List;

public class PedidoController {
    // Atributos
    private final PedidoService pedidoService;

    // Constructor
    public PedidoController() {
        this.pedidoService = new PedidoService();
    }

    // Metodos
    // POST /pedidos
    // Crea un nuevo pedido
    public void crearPedido() {
        pedidoService.buildPedido();
    }

    // GET /pedidos
    // Retorna todos los pedidos
    public List<Pedido> listarPedidos() {
        return pedidoService.allPedidos();
    }

    // GET /pedidos/{id}
    // Busca un pedido por su ID
    public Optional<Pedido> obtenerPedidoPorId(int idPedido) {
        return pedidoService.searchPedido(idPedido);
    }

    // PUT /pedidos/{idPedido}/items
    // Agregar un item al pedido
    public void agregarItem(int idPedido, int idProducto, int cantidad) {
        pedidoService.includeItem(idPedido, idProducto, cantidad);
    }

    // PUT /pedidos/{id}/confirmar
    // Confirma un pedido si todos los productos tienen stock suficiente
    public void confirmarPedido(int idPedido){
        pedidoService.confirmPedido(idPedido);
    }

    // GET /pedidos/{id}/total
    // Devuelve el total a pagar por un pedido
    public float totalPedido(int idPedido){
        return pedidoService.calculateTotalPedido(idPedido);
    }
}
