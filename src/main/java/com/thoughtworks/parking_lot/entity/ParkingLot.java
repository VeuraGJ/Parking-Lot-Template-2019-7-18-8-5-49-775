package com.thoughtworks.parking_lot.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String name;
    @Min(0)
    private int capacity;
    private String location;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderForm> orderForms;

    public ParkingLot() {
    }

    public ParkingLot(String name, int capacity, String location) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public ParkingLot(String name, @Min(0) int capacity, String location, List<OrderForm> orderForms) {
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.orderForms = orderForms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public List<OrderForm> getOrderForms() {
        return orderForms;
    }

    public void setOrderForms(List<OrderForm> orderForms) {
        this.orderForms = orderForms;
    }
}
