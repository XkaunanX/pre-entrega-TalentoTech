package org.ecommerce.repository;
import org.ecommerce.domain.Producto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoArchivo {
    // Atributos
    private static final String ARCHIVO_PRODUCTOS = "producto.json";
    private final ObjectMapper mapper;

    // Constructor
    public ProductoArchivo() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Metodos
    public void saveProducto(Producto producto) throws IOException {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        List<Producto> productos = new ArrayList<>();
        if (archivo.exists()) {
            productos = mapper.readValue(archivo, new TypeReference<List<Producto>>() {});
        }
        productos.add(producto);
        mapper.writeValue(archivo, productos);
    }

    public Optional<Producto> findByIdProducto(int idProducto) throws IOException {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        List<Producto> productos = new ArrayList<>();
        if (archivo.exists()) {
            productos = mapper.readValue(archivo, new TypeReference<List<Producto>>() {});

            for (Producto producto : productos) {
                if (producto.getIdProducto() == idProducto) {
                    return Optional.of(producto);
                }
            }
        }
        return Optional.empty();
    }

    public List<Producto> findAll() throws IOException {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        List<Producto> productos = new ArrayList<>();
        if (archivo.exists()) {
            productos = mapper.readValue(archivo, new TypeReference<List<Producto>>() {});
        }
        return productos;
    }

    public boolean updateProducto(Producto producto) throws IOException {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        List<Producto> productos = new ArrayList<>();
        if (archivo.exists()) {
            productos = mapper.readValue(archivo, new TypeReference<List<Producto>>() {});
        }
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == producto.getIdProducto()) {
                productos.set(i, producto);
                mapper.writeValue(archivo, productos);
                return true;
            }
        }
        return false;
    }

    public void deleteByIdProducto(int idProducto) throws IOException {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        List<Producto> productos = new ArrayList<>();
        if (archivo.exists()) {
            productos = mapper.readValue(archivo, new TypeReference<List<Producto>>() {});
            // Filtrar la lista para eliminar el producto con idProducto
            productos.removeIf(producto -> producto.getIdProducto() == idProducto);
            mapper.writeValue(archivo, productos);
        }
    }

    public int findLastIdProducto() throws IOException {
        File archivo = new File(ARCHIVO_PRODUCTOS);
        List<Producto> productos = new ArrayList<>();
        int maxId = 0;
        if (archivo.exists()) {
            productos = mapper.readValue(archivo, new TypeReference<List<Producto>>() {});
            for (Producto producto : productos) {
                if (producto.getIdProducto() > maxId) {
                    maxId = producto.getIdProducto();
                }
            }
        }
        return maxId;
    }
}
