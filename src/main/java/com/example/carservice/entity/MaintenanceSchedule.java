package com.example.carservice.entity;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class MaintenanceSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maintenanceScheduleId;
    @Column(name = "maintenance_date")
    private Date maintenanceDate;
    @Column(name = "time_window")
    private TimeWindows timeWindow;
    @OneToOne
    @JoinColumn(name = "scheduled_technical_inspection_id")
    private ScheduledTechnicalInspections scheduledTechnicalInspections;
    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public MaintenanceSchedule() {
    }

    public MaintenanceSchedule(Date maintenanceDate, TimeWindows timeWindow, ScheduledTechnicalInspections scheduledTechnicalInspections, Car car, Client client) {
        this.maintenanceDate = maintenanceDate;
        this.timeWindow = timeWindow;
        this.scheduledTechnicalInspections = scheduledTechnicalInspections;
        this.car = car;
        this.client = client;
    }

    public int getMaintenanceScheduleId() {
        return maintenanceScheduleId;
    }

    public void setMaintenanceScheduleId(int maintenanceScheduleId) {
        this.maintenanceScheduleId = maintenanceScheduleId;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public TimeWindows getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(TimeWindows timeWindow) {
        this.timeWindow = timeWindow;
    }

    public ScheduledTechnicalInspections getScheduledTechnicalInspections() {
        return scheduledTechnicalInspections;
    }

    public void setScheduledTechnicalInspections(ScheduledTechnicalInspections scheduledTechnicalInspections) {
        this.scheduledTechnicalInspections = scheduledTechnicalInspections;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
