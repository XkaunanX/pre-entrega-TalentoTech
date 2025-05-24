package org.ecommerce.services;
import org.ecommerce.domain.Producto;
import org.ecommerce.repository.ProductoArchivo;

import java.io.IOException; // Manejo de excepciones
import java.util.List;
import java.util.Optional;

// Ya se que los RuntimeException no se propagan hacia arriba
public class ProductoService {
    // Atributos
    private final ProductoArchivo productoArchivo;

    // Constructor
    public ProductoService(){
        // Repositorio
        this.productoArchivo = new ProductoArchivo();
    }

    // Metodos
    public void buildProducto(String nombre, float precio, int stock){
        try {
            int ultimaId = productoArchivo.findLastIdProducto();
            int nuevaId = ultimaId + 1;
            Producto nuevoProducto = new Producto(nuevaId, nombre, precio, stock);
            productoArchivo.saveProducto(nuevoProducto);
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e);
        }
    }

    public Optional<Producto> searchProducto(int idProducto){
        try {
            return productoArchivo.findByIdProducto(idProducto);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + idProducto, e);
        }
    }

    public boolean editProducto(int idProducto, String nuevoNombre, float nuevoPrecio, int nuevoStock) {
        try {
            Optional<Producto> optProducto = productoArchivo.findByIdProducto(idProducto);
            if (optProducto.isEmpty()) {
                return false;
            }
            Producto producto = optProducto.get();
            producto.setNombreProducto(nuevoNombre);
            producto.setPrecioProducto(nuevoPrecio);
            producto.setStockProducto(nuevoStock);
            return productoArchivo.updateProducto(producto);
        } catch (IOException e) {
            throw new RuntimeException("Error al editar producto con ID: " + idProducto, e);
        }
    }

    public void removeProducto(int idProducto){
        try {
            productoArchivo.deleteByIdProducto(idProducto);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + idProducto, e);
        }
    }

    public List<Producto> allProductos() {
        try {
            return productoArchivo.findAll();
        } catch (IOException e) {
            throw new RuntimeException("Error: ", e);
        }
    }
}
