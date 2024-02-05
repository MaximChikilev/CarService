package com.example.carservice.dto;

public class InspectionDTO {
    private String inspectionName;
    private int mileageToPass;

    public InspectionDTO() {
    }

    public InspectionDTO(String inspectionName, int mileageToPass) {
        this.inspectionName = inspectionName;
        this.mileageToPass = mileageToPass;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public int getMileageToPass() {
        return mileageToPass;
    }

    public void setMileageToPass(int mileageToPass) {
        this.mileageToPass = mileageToPass;
    }
}
