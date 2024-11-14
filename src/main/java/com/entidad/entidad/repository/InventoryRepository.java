package com.entidad.entidad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entidad.entidad.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}