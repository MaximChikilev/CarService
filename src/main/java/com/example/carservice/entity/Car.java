package com.example.carservice.entity;

import javax.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    @Column(name = "licence_plate_number")
    private String licensePlateNumber;
    private String model;
    @Column(name = "vin_code")
    private String vinCode;
    @Column(name = "manufacture_year")
    private int manufactureYear;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    private int mileage;

    public Car() {
    }

    public Car(String licensePlateNumber, String model, String vinCode, int manufactureYear, Manufacturer manufacturer, int mileage) {
        this.licensePlateNumber = licensePlateNumber;
        this.model = model;
        this.vinCode = vinCode;
        this.manufactureYear = manufactureYear;
        this.manufacturer = manufacturer;
        this.mileage = mileage;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
