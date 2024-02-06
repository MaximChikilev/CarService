package com.example.carservice.dto;

import java.util.List;

public class CarInspectionScheduleDTO {
  private List<String> licensePlateNumbers;
  private FreeDataTimeForInspectionDTO freeDataTimeForInspection;

  public CarInspectionScheduleDTO(
      List<String> licensePlateNumbers, FreeDataTimeForInspectionDTO freeDataTimeForInspection) {
    this.licensePlateNumbers = licensePlateNumbers;
    this.freeDataTimeForInspection = freeDataTimeForInspection;
  }

  public CarInspectionScheduleDTO() {}

  public List<String> getLicensePlateNumbers() {
    return licensePlateNumbers;
  }

  public void setLicensePlateNumbers(List<String> licensePlateNumbers) {
    this.licensePlateNumbers = licensePlateNumbers;
  }

  public FreeDataTimeForInspectionDTO getFreeDataTimeForInspection() {
    return freeDataTimeForInspection;
  }

  public void setFreeDataTimeForInspection(FreeDataTimeForInspectionDTO freeDataTimeForInspection) {
    this.freeDataTimeForInspection = freeDataTimeForInspection;
  }
}
