package com.example.carservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
@Entity
public class MaintenanceSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceScheduleId;
    @Column(name = "maintenance_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maintenanceDate;

    @Column(name = "time_window")
   //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Enumerated(EnumType.STRING)
    private TimeWindows maintenanceTime;
    @OneToOne
    @JoinColumn(name = "scheduled_technical_inspection_id")
    private TechnicalInspection technicalInspection;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public MaintenanceSchedule() {
    }

    public MaintenanceSchedule(Date maintenanceDate, TimeWindows maintenanceTime, TechnicalInspection technicalInspection, Car car, Client client) {
        this.maintenanceDate = maintenanceDate;
        this.maintenanceTime = maintenanceTime;
        this.technicalInspection = technicalInspection;
        this.car = car;
        this.client = client;
    }

    public Long getMaintenanceScheduleId() {
        return maintenanceScheduleId;
    }

    public void setMaintenanceScheduleId(Long maintenanceScheduleId) {
        this.maintenanceScheduleId = maintenanceScheduleId;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public TimeWindows getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(TimeWindows timeWindow) {
        this.maintenanceTime = timeWindow;
    }

    public TechnicalInspection getTechnicalInspection() {
        return technicalInspection;
    }

    public void setTechnicalInspection(TechnicalInspection technicalInspection) {
        this.technicalInspection = technicalInspection;
    }

    public Car getCar() {
        return car;
    }

    public MaintenanceSchedule(Date maintenanceDate, TimeWindows maintenanceTime) {
        this.maintenanceDate = maintenanceDate;
        this.maintenanceTime = maintenanceTime;
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
