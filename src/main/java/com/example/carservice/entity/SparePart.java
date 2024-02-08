package com.example.carservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Objects;

@Entity
public class SparePart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long sparePartId;

  @Column(name = "part_number")
  private String partNumber;

  private String name;

  @ManyToOne
  @JoinColumn(name = "manufacturer_id")
  private Manufacturer manufacturer;

  public SparePart() {}

  public SparePart(String partNumber, String name, Manufacturer manufacturer) {
    this.partNumber = partNumber;
    this.name = name;
    this.manufacturer = manufacturer;
  }

  public Long getSparePartId() {
    return sparePartId;
  }

  public void setSparePartId(Long sparePartId) {
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

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (!(object instanceof SparePart)) return false;
      SparePart sparePart = (SparePart) object;
      return getSparePartId() == sparePart.getSparePartId()
        && Objects.equals(getPartNumber(), sparePart.getPartNumber())
        && Objects.equals(getName(), sparePart.getName())
        && Objects.equals(getManufacturer(), sparePart.getManufacturer());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSparePartId(), getPartNumber(), getName(), getManufacturer());
  }
}
