package com.example.carservice.dto;

import java.util.List;

public class FreeDataTimeForInspectionDTO {
  private List<String> freeDateTime;

  public FreeDataTimeForInspectionDTO() {}

  public FreeDataTimeForInspectionDTO(List<String> freeDateTime) {
    this.freeDateTime = freeDateTime;
  }

  public List<String> getFreeDateTime() {
    return freeDateTime;
  }

  public void setFreeDateTime(List<String> freeDateTime) {
    this.freeDateTime = freeDateTime;
  }
}
