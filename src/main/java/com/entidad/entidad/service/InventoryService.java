package com.entidad.entidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entidad.entidad.model.Inventory;
import com.entidad.entidad.repository.InventoryRepository;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new IllegalArgumentException("El inventario con el ID especificado no existe");
        }
        inventoryRepository.deleteById(id);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El inventario con el ID especificado no existe"));
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Inventory saveInventory(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventario no puede ser nulo");
        }
        if (inventory.getProduct() == null) {
            throw new IllegalArgumentException("Producto no puede ser nulo");
        }
        if (inventory.getProduct().getId() == null) {
            throw new IllegalArgumentException("ID de producto no puede ser nulo");
        }
        if (inventory.getQuantity() == null) {
            throw new IllegalArgumentException("Cantidad no puede ser nula");
        }
        if (inventory.getQuantity() < 0) {
            throw new IllegalArgumentException("Cantidad no puede ser negativa");
        }

        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long id, Inventory inventory) {
        if (!inventoryRepository.existsById(id)) {
            throw new IllegalArgumentException("El inventario con el ID especificado no existe");
        }
        inventory.setId(id);
        return inventoryRepository.save(inventory);
    }

}
