package com.example.carservice.dto;

import com.example.carservice.entity.TimeWindows;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FreeDataTimeForInspectionDTO {
    private List<String> freeDateTime;


    public FreeDataTimeForInspectionDTO() {
    }

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
