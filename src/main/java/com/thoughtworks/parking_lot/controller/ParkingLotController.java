package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.OrderForm;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
            List<ParkingLot> parkingLots = new ArrayList<>();
            parkingLotRepository.findAll().iterator().forEachRemaining(parkingLots::add);
            return ResponseEntity.ok(parkingLots);
        } else {
            return ResponseEntity.ok(parkingLotRepository.findAll(PageRequest.of(page, pageSize)));
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
    @GetMapping("/parking-lots/{parkingLotId}/empty-position")
    @ResponseBody
    public Integer getParkingLotEmptyPositionNum(@PathVariable long parkingLotId) {
        ParkingLot parkingLot=parkingLotRepository.findById(parkingLotId).orElse(null);
        List<OrderForm> orderForms=parkingLot.getOrderForms().stream()
                .filter(orderForm -> orderForm.getStatus().equals("on"))
                .collect(Collectors.toList());
        return parkingLot.getCapacity()-orderForms.size();

    }

}
