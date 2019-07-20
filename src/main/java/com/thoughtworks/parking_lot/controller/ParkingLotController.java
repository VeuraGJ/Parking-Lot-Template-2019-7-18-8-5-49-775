package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.Car;
import com.thoughtworks.parking_lot.entity.OrderForm;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderFormRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private OrderFormRepository orderFormRepository;
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
    public String getParkingLotEmptyPositionNum(@PathVariable long parkingLotId) {
        ParkingLot parkingLot=parkingLotRepository.findById(parkingLotId).orElse(null);
        List<OrderForm> orderForms =parkingLot.getOrderForms().stream()
                .filter(orderForm -> orderForm.getStatus().equals("on"))
                .collect(Collectors.toList());
        if(parkingLot.getCapacity()- orderForms.size()==0){
            return "停车场已经满";
        }
        return String.valueOf(parkingLot.getCapacity()- orderForms.size());
    }
    @PostMapping("/parking-lots/{parkingLotId}/orderForm-forms")
    public ResponseEntity<ParkingLot> addOrderForm(@PathVariable long parkingLotId,@RequestBody OrderForm orderForm){
        ParkingLot parkingLot =parkingLotRepository.findById(parkingLotId).orElse(null);
        orderForm.setCreateTime(new Date());
        parkingLot.getOrderForms().add(orderForm);
        return ResponseEntity.ok(parkingLotRepository.save(parkingLot));
    }
    @PutMapping("/parking-lots/{parkingLotId}/order-forms")
    public ResponseEntity<OrderForm> updateOrderForm(@PathVariable long parkingLotId, @RequestBody Car car){
        ParkingLot parkingLot =parkingLotRepository.findById(parkingLotId).orElse(null);
        OrderForm updateorder = parkingLot.getOrderForms().stream()
                .filter(orderForm -> orderForm.getStatus().equals("on") && orderForm.getCar().getId().equals(car.getId()))
                .findFirst().orElse(null);
        if(updateorder != null){
            updateorder.setEndTime(new Date());
            updateorder.setStatus("closed");
            updateorder = orderFormRepository.save(updateorder);
        }
        return ResponseEntity.ok(updateorder);
    }
}
