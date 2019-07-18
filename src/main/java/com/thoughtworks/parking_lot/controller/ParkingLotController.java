package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @PostMapping("/parking-lots")
    public ResponseEntity<ParkingLot> addParkingLot(@RequestBody ParkingLot parkingLot){
        return ResponseEntity.ok(parkingLotRepository.save(parkingLot));
    }
    @DeleteMapping("/parking-lots/{parkingLotId}")
    public ResponseEntity<ParkingLot> deleteParkingLot(@PathVariable long parkingLotId){
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        parkingLotRepository.deleteById(parkingLotId);
        return ResponseEntity.ok(parkingLot);
    }
    @GetMapping("/parking-lots")
    public ResponseEntity<List<ParkingLot>>getParkingLots(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "0") int pageSize) {
        if (page == 0 || pageSize == 0) {
            return ResponseEntity.ok(parkingLotRepository.findAll());
        } else {
            Page<ParkingLot> parkingLotPage = parkingLotRepository.findAll(PageRequest.of(page, pageSize));
            return ResponseEntity.ok(parkingLotPage.get().collect(Collectors.toList()));
        }
    }
    @GetMapping("/parking-lots/{parkingLotId}")
    public ResponseEntity<ParkingLot> getParkingLotById(@PathVariable long parkingLotId) {
        ParkingLot parkingLot=parkingLotRepository.findById(parkingLotId).orElse(null);
        return ResponseEntity.ok(parkingLot);

    }
    @PutMapping("/parking-lots/{parkingLotId}")
    public ResponseEntity<ParkingLot> updateParkingLot(@PathVariable long parkingLotId,@RequestBody ParkingLot parkingLot) {
        parkingLot.setId(parkingLotId);
        return ResponseEntity.ok(parkingLotRepository.save(parkingLot));

    }

}
