package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.OrderForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderFormRepository extends JpaRepository<OrderForm,Long> {
}
