package com.entidad.entidad.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entidad.entidad.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    /*
     * @Query("SELECT o.user.id, COUNT(o.id) AS orderCount " +
     * "FROM Orders o " +
     * "GROUP BY o.user.id " +
     * "ORDER BY orderCount DESC")
     * List<Object[]> findTopFrequentCustomers(Pageable pageable);
     */

    @Query("SELECT o.user.id, COUNT(o) as orderCount FROM Orders o GROUP BY o.user.id ORDER BY orderCount DESC")
    List<Object[]> findTopFrequentCustomers(Pageable pageable);

}
