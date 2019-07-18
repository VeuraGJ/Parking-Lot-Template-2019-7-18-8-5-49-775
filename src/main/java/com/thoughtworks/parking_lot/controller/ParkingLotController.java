package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @PostMapping("/parking-lots")
    public ResponseEntity<ParkingLot> addParkingLot(@RequestBody ParkingLot parkingLot){
        return ResponseEntity.ok(parkingLotRepository.save(parkingLot));
    }
//    @DeleteMapping("/parking-lots/{parkingLotId}")
//    public ResponseEntity<ParkingLot> deleteParkingLot(@PathVariable long parkingLotId){
//        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
//        return ResponseEntity.ok(parkingLotRepository.save(parkingLot));
//    }
}
