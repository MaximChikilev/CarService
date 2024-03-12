package com.example.carservice.dto;

public class ConnectionsWithOtherEntityDTO {
    private String entityName;
    private Long connectionsCount;

    public ConnectionsWithOtherEntityDTO(String entityName, Long connectionsCount) {
        this.entityName = entityName;
        this.connectionsCount = connectionsCount;
    }

    public ConnectionsWithOtherEntityDTO() {
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getConnectionsCount() {
        return connectionsCount;
    }

    public void setConnectionsCount(Long connectionsCount) {
        this.connectionsCount = connectionsCount;
    }
}
