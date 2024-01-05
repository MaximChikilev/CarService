package com.example.carservice.entity;

import jakarta.persistence.*;

@Entity
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sparePartId;
    @Column(name = "part_number")
    private String partNumber;
    private String name;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    public SparePart() {
    }

    public SparePart(String partNumber, String name, Manufacturer manufacturer) {
        this.partNumber = partNumber;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
