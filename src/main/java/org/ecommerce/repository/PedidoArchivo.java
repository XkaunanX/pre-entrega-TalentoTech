package org.ecommerce.repository;
import org.ecommerce.domain.Pedido;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoArchivo {
    // Atributos
    private static final String ARCHIVO_PEDIDOS = "pedido.json";
    private final ObjectMapper mapper;

    // Constructor
    public PedidoArchivo() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Metodos
    public void savePedido(Pedido pedido) throws IOException {
        File archivo = new File(ARCHIVO_PEDIDOS);
        List<Pedido> pedidos = new ArrayList<>();
        if (archivo.exists()) {
            pedidos = mapper.readValue(archivo, new TypeReference<List<Pedido>>() {});
        }
        pedidos.add(pedido);
        mapper.writeValue(archivo, pedidos);
    }

    public Optional<Pedido> findByIdPedido(int idPedido) throws IOException {
        File archivo = new File(ARCHIVO_PEDIDOS);
        List<Pedido> pedidos = new ArrayList<>();
        if (archivo.exists()) {
            pedidos = mapper.readValue(archivo, new TypeReference<List<Pedido>>() {});
            for (Pedido pedido : pedidos) {
                if (pedido.getIdPedido() == idPedido) {
                    return Optional.of(pedido);
                }
            }
        }
        return Optional.empty();
    }

    public List<Pedido> findAll() throws IOException {
        File archivo = new File(ARCHIVO_PEDIDOS);
        List<Pedido> pedidos = new ArrayList<>();
        if (archivo.exists()) {
            try {
                pedidos = mapper.readValue(archivo, new TypeReference<List<Pedido>>() {});
            } catch (IOException e) {
                System.err.println("Error leyendo el archivo de pedidos:");
                e.printStackTrace(); // Te muestra la traza del error en consola
                throw e; // Opcional: relanza la excepción para manejarla en otra capa
            }
        }
        return pedidos;
    }

    public boolean updatePedido(Pedido pedido) throws IOException {
        File archivo = new File(ARCHIVO_PEDIDOS);
        List<Pedido> pedidos = new ArrayList<>();
        if (archivo.exists()) {
            pedidos = mapper.readValue(archivo, new TypeReference<List<Pedido>>() {});
        }
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getIdPedido() == pedido.getIdPedido()) {
                pedidos.set(i, pedido);
                mapper.writeValue(archivo, pedidos);
                return true;
            }
        }
        return false;
    }

    public int findLastIdPedido() throws IOException {
        File archivo = new File(ARCHIVO_PEDIDOS);
        List<Pedido> pedidos = new ArrayList<>();
        int maxId = 0;

        if (archivo.exists() && archivo.length() > 0) {
            try {
                pedidos = mapper.readValue(archivo, new TypeReference<List<Pedido>>() {});
            } catch (Exception e) {
                System.err.println("Error leyendo pedidos: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }

            for (Pedido pedido : pedidos) {
                if (pedido.getIdPedido() > maxId) {
                    maxId = pedido.getIdPedido();
                }
            }
        }
        return maxId;
    }
}
