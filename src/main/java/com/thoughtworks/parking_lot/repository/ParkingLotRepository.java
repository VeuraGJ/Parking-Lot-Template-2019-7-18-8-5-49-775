package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingLotRepository extends CrudRepository<ParkingLot,Long> {
    List<ParkingLot> findAll(Pageable pageable);
}
