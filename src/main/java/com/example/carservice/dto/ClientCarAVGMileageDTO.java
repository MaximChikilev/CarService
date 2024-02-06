package com.example.carservice.dto;

public class ClientCarAVGMileageDTO {
  private String firstName;
  private String secondName;
  private String phoneNumber;
  private String email;
  private String licensePlateNumber;
  private int mileage;
  private double avgMileage;

  public ClientCarAVGMileageDTO() {}

  public ClientCarAVGMileageDTO(
      String firstName,
      String secondName,
      String phoneNumber,
      String email,
      String licensePlateNumber,
      int mileage,
      double avgMileage) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.licensePlateNumber = licensePlateNumber;
    this.mileage = mileage;
    this.avgMileage = avgMileage;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLicensePlateNumber() {
    return licensePlateNumber;
  }

  public void setLicensePlateNumber(String licensePlateNumber) {
    this.licensePlateNumber = licensePlateNumber;
  }

  public double getAvgMileage() {
    return avgMileage;
  }

  public void setAvgMileage(double avgMileage) {
    this.avgMileage = avgMileage;
  }

  public int getMileage() {
    return mileage;
  }

  public void setMileage(int mileage) {
    this.mileage = mileage;
  }
}
