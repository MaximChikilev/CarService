package com.example.carservice.dto;

public class ClientCarRecommendedToServiceDTO {
    private ClientCarAVGMileageDTO clientCarAVGMileageDTO;
    private String inspectionName;

    public ClientCarRecommendedToServiceDTO(ClientCarAVGMileageDTO clientCarAVGMileageDTO, String inspectionName) {
        this.clientCarAVGMileageDTO = clientCarAVGMileageDTO;
        this.inspectionName = inspectionName;
    }

    public ClientCarAVGMileageDTO getClientCarAVGMileageDTO() {
        return clientCarAVGMileageDTO;
    }

    public void setClientCarAVGMileageDTO(ClientCarAVGMileageDTO clientCarAVGMileageDTO) {
        this.clientCarAVGMileageDTO = clientCarAVGMileageDTO;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public ClientCarRecommendedToServiceDTO() {
    }
}
