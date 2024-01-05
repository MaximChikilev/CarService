package com.example.carservice.entity;

import jakarta.persistence.*;

@Entity
public class Semitrailer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int semitrailerId;
    @Column(name = "lisense_plate_number")
    private String licensePlateNumber;
    @Column(name = "vin_code")
    private String vinCode;
    @Column(name = "manufactere_year")
    private int manufactureYear;
    @OneToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    public Semitrailer() {
    }

    public Semitrailer(String licensePlateNumber, String vinCode, int manufactureYear, Manufacturer manufacturer) {
        this.licensePlateNumber = licensePlateNumber;
        this.vinCode = vinCode;
        this.manufactureYear = manufactureYear;
        this.manufacturer = manufacturer;
    }

    public int getSemitrailerId() {
        return semitrailerId;
    }

    public void setSemitrailerId(int semitrailerId) {
        this.semitrailerId = semitrailerId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
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
}
