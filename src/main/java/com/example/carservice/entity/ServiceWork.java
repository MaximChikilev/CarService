package com.example.carservice.entity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ServiceWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceWorkId;
    private String name;
    @Column(name = "duration_in_minutes")
    private int durationInMinutes;
    @OneToMany
    @JoinColumn(name = "service_work_id")
    private List<SparePart> spareParts = new ArrayList<>();

    public ServiceWork() {
    }

    public ServiceWork(String name, int durationInMinutes, List<SparePart> spareParts) {
        this.name = name;
        this.durationInMinutes = durationInMinutes;
        this.spareParts = spareParts;
    }

    public int getServiceWorkId() {
        return serviceWorkId;
    }

    public void setServiceWorkId(int serviceWorkId) {
        this.serviceWorkId = serviceWorkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    public void addSparePart(SparePart sparePart) {
        if (!spareParts.contains(sparePart)) {
            spareParts.add(sparePart);
        }
    }
}
