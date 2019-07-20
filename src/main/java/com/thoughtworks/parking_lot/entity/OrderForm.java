package com.thoughtworks.parking_lot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderForm {
    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    private String status="on";
    @ManyToOne(cascade = CascadeType.ALL)
    private Car car;

    public OrderForm() {
    }

    public OrderForm(Date createTime, Car car) {
        this.createTime = createTime;
        this.car = car;
    }

    public OrderForm(Car car) {
        this.car = car;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
