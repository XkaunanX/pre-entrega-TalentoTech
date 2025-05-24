package org.ecommerce.controller;
import org.ecommerce.domain.Producto;
import org.ecommerce.services.ProductoService;

import java.util.Optional;
import java.util.List;

public class ProductoController {
    // Atributos
    private final ProductoService productoService;

    // Constructor
    public ProductoController() {
        this.productoService = new ProductoService();
    }

    // Metodos -> Llamadas a service

    // Crear un producto (POST)
    public void crearProducto(String nombre, float precio, int stock) {
        productoService.buildProducto(nombre, precio, stock);
    }

    // Obtener todos los productos (GET)
    public List<Producto> listarProductos() {
         return productoService.allProductos();
    }

    // Obtener producto por id (GET)
    public Optional<Producto> obtenerProductoPorId(int idProducto) {
        return productoService.searchProducto(idProducto);
    }

    // Actualizar producto (PUT)
    public boolean actualizarProducto(int idProducto, String nuevoNombre, float nuevoPrecio, int nuevoStock) {
        return productoService.editProducto(idProducto, nuevoNombre, nuevoPrecio, nuevoStock);
    }

    // Eliminar producto por id (DELETE)
    public void eliminarProducto(int idProducto) {
        productoService.removeProducto(idProducto);
    }
}
