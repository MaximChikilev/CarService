package com.example.carservice.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
public class GpsTrackerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne@JoinColumn(name = "car_id")
    private Car car;
    private Date date;
    private int mileage;

    public GpsTrackerData(Car car, Date date, int mileage) {
        this.car = car;
        this.date = date;
        this.mileage = mileage;
    }

    public GpsTrackerData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
