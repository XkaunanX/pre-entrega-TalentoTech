package org.ecommerce.services;
import org.ecommerce.domain.Item;
import org.ecommerce.domain.Pedido;
import org.ecommerce.domain.Producto;
import org.ecommerce.repository.PedidoArchivo;
import org.ecommerce.repository.ProductoArchivo;
import org.ecommerce.repository.ItemArchivo;
import org.ecommerce.exception.StockInsuficienteException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PedidoService {
    // Atributos
    private final PedidoArchivo pedidoArchivo;
    private final ProductoArchivo productoArchivo;
    private final ItemArchivo itemArchivo;

    // Constructor
    public PedidoService(){
        this.pedidoArchivo = new PedidoArchivo();
        this.productoArchivo = new ProductoArchivo();
        this.itemArchivo = new ItemArchivo();
    }

    // Metodos
    public void buildPedido(){
        try {
            int ultimaId = pedidoArchivo.findLastIdPedido();
            int nuevaId = ultimaId + 1;
            Pedido nuevoPedido = new Pedido(nuevaId);
            pedidoArchivo.savePedido(nuevoPedido);
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e);
        }
    }

    public Optional<Pedido> searchPedido(int idPedido){
        try {
            return pedidoArchivo.findByIdPedido(idPedido);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + idPedido, e);
        }
    }

    public void includeItem(int idPedido, int idProducto, int cantidad) {
        try {
            Optional<Pedido> optionalPedido = pedidoArchivo.findByIdPedido(idPedido);
            Optional<Producto> optionalProducto = productoArchivo.findByIdProducto(idProducto);
            if (optionalPedido.isEmpty()) {
                throw new RuntimeException("Pedido con ID " + idPedido + " no encontrado.");
            }
            if (optionalProducto.isEmpty()) {
                throw new RuntimeException("Producto con ID " + idProducto + " no encontrado.");
            }
            Pedido pedido = optionalPedido.get();
            Producto producto = optionalProducto.get();
            int nuevaId = itemArchivo.findLastIdItem() + 1;
            Item nuevoItem = new Item(nuevaId, cantidad, producto);
            nuevoItem.setPedido(pedido);
            pedido.addItem(nuevoItem);
            // Persistir el pedido actualizado
            pedidoArchivo.updatePedido(pedido);
            itemArchivo.saveItem(nuevoItem);
        } catch (IOException e) {
            throw new RuntimeException("Error al incluir item en pedido", e);
        }
    }

    public void confirmPedido(int idPedido) {
        try {
            Optional<Pedido> optionalPedido = pedidoArchivo.findByIdPedido(idPedido);
            if (optionalPedido.isEmpty()) {
                throw new RuntimeException("Pedido con ID " + idPedido + " no encontrado.");
            }
            Pedido pedido = optionalPedido.get();
            List<Item> items = getItems(pedido);

            // Agrupar cantidades por producto
            Map<Integer, Integer> cantidadTotalPorProducto = new HashMap<>();
            Map<Integer, Producto> productosMap = new HashMap<>();

            for (Item item : items) {
                Producto producto = item.getProducto();
                int idProducto = producto.getIdProducto();
                cantidadTotalPorProducto.put(idProducto,
                        cantidadTotalPorProducto.getOrDefault(idProducto, 0) + item.getCantidadProducto());
                productosMap.putIfAbsent(idProducto, producto);
            }

            // Verificar stock
            for (Map.Entry<Integer, Integer> entry : cantidadTotalPorProducto.entrySet()) {
                int idProducto = entry.getKey();
                int cantidadTotal = entry.getValue();
                Producto producto = productosMap.get(idProducto);
                if (producto.getStockProducto() < cantidadTotal) {
                    throw new StockInsuficienteException("Stock insuficiente para el producto con ID "
                            + idProducto + ". Disponible: " + producto.getStockProducto()
                            + ", requerido: " + cantidadTotal);
                }
            }

            // Descontar stock y actualizar productos
            for (Map.Entry<Integer, Integer> entry : cantidadTotalPorProducto.entrySet()) {
                int idProducto = entry.getKey();
                int cantidadTotal = entry.getValue();
                Producto producto = productosMap.get(idProducto);
                int nuevoStock = producto.getStockProducto() - cantidadTotal;
                producto.setStockProducto(nuevoStock);
                productoArchivo.updateProducto(producto);
            }

            // Confirmar pedido
            pedido.confirmar();
            pedidoArchivo.updatePedido(pedido);
        } catch (IOException e) {
            throw new RuntimeException("Error al confirmar pedido con ID " + idPedido, e);
        }
    }

    private static List<Item> getItems(Pedido pedido) {
        List<Item> items = pedido.getItems();

        // Verificar si hay stock suficiente para todos los items
        for (Item item : items) {
            Producto producto = item.getProducto();
            if (producto == null) {
                throw new RuntimeException("Producto asociado al item " + item.getIdItem() + " no encontrado.");
            }
            if (producto.getStockProducto() < item.getCantidadProducto()) {
                throw new RuntimeException("Stock insuficiente para el producto con ID " + producto.getIdProducto());
            }
        }
        return items;
    }


    public float calculateTotalPedido(int idPedido) {
        try {
            Optional<Pedido> optionalPedido = pedidoArchivo.findByIdPedido(idPedido);
            if (optionalPedido.isEmpty()) {
                throw new RuntimeException("Pedido con ID " + idPedido + " no encontrado.");
            }
            Pedido pedido = optionalPedido.get();
            float total = 0.0f;
            for (Item item : pedido.getItems()) {
                Producto producto = item.getProducto();
                total += producto.getPrecioProducto() * item.getCantidadProducto();
            }
            return total;
        } catch (IOException e) {
            throw new RuntimeException("Error al calcular total del pedido con ID " + idPedido, e);
        }
    }

    public List<Pedido> allPedidos(){
        try {
            return pedidoArchivo.findAll();
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e);
        }
    }
}
