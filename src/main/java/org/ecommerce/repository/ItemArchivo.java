package org.ecommerce.repository;
import org.ecommerce.domain.Item;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemArchivo {
    // Atributos
    private static final String ARCHIVO_ITEMS = "item.json";
    private final ObjectMapper mapper;

    // Constructor
    public ItemArchivo() {
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Metodos
    public void saveItem(Item item) throws IOException {
        File archivo = new File(ARCHIVO_ITEMS);
        List<Item> items = new ArrayList<>();
        if (archivo.exists()) {
            items = mapper.readValue(archivo, new TypeReference<List<Item>>() {});
        }
        items.add(item);
        mapper.writeValue(archivo, items);
    }

    public Optional<Item> findByIdItem(int idItem) throws IOException {
        File archivo = new File(ARCHIVO_ITEMS);
        List<Item> items = new ArrayList<>();
        if (archivo.exists()) {
            items = mapper.readValue(archivo, new TypeReference<List<Item>>() {});
            for (Item item : items) {
                if (item.getIdItem() == idItem) {
                    return Optional.of(item);
                }
            }
        }
        return Optional.empty();
    }

    public List<Item> findAll() throws IOException {
        File archivo = new File(ARCHIVO_ITEMS);
        List<Item> items = new ArrayList<>();
        if (archivo.exists()) {
            items = mapper.readValue(archivo, new TypeReference<List<Item>>() {});
        }
        return items;
    }

    public boolean updateItem(Item item) throws IOException {
        File archivo = new File(ARCHIVO_ITEMS);
        List<Item> items = new ArrayList<>();
        if (archivo.exists()) {
            items = mapper.readValue(archivo, new TypeReference<List<Item>>() {});
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getIdItem() == item.getIdItem()) {
                items.set(i, item);
                mapper.writeValue(archivo, items);
                return true;
            }
        }
        return false;
    }

    public boolean deleteByIdItem(int idItem) throws IOException {
        File archivo = new File(ARCHIVO_ITEMS);
        List<Item> items = new ArrayList<>();
        if (archivo.exists()) {
            items = mapper.readValue(archivo, new TypeReference<List<Item>>() {});
            boolean eliminado = items.removeIf(item -> item.getIdItem() == idItem);
            if (eliminado) {
                mapper.writeValue(archivo, items);
            }
            return eliminado;
        }
        return false;
    }

    public int findLastIdItem() throws IOException {
        File archivo = new File(ARCHIVO_ITEMS);
        List<Item> items = new ArrayList<>();
        int maxId = 0;
        if (archivo.exists()) {
            items = mapper.readValue(archivo, new TypeReference<List<Item>>() {});
            for (Item item : items) {
                if (item.getIdItem() > maxId) {
                    maxId = item.getIdItem();
                }
            }
        }
        return maxId;
    }
}
