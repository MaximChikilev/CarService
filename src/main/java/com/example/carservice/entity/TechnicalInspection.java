package com.example.carservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TechnicalInspection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long inspectionsId;

  private String name;

  @Column(name = "mileage_to_pass")
  private int mileageToPass;

  @ManyToMany
  @JoinColumn(name = "inspection_id")
  List<ServiceWork> serviceWorks = new ArrayList<>();

  @Column(name = "duration_in_minutes")
  private int durationInMinutes;

  public TechnicalInspection() {}

  public TechnicalInspection(
      String name, int mileageToPass, List<ServiceWork> serviceWorks, int durationInMinutes) {
    this.name = name;
    this.mileageToPass = mileageToPass;
    this.serviceWorks = serviceWorks;
    this.durationInMinutes = durationInMinutes;
  }

  public Long getInspectionsId() {
    return inspectionsId;
  }

  public void setInspectionsId(Long inspectionsId) {
    this.inspectionsId = inspectionsId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMileageToPass() {
    return mileageToPass;
  }

  public void setMileageToPass(int mileageToPass) {
    this.mileageToPass = mileageToPass;
  }

  public List<ServiceWork> getServiceWorks() {
    return serviceWorks;
  }

  public void setServiceWorks(List<ServiceWork> serviceWorks) {
    this.serviceWorks = serviceWorks;
  }

  public void addServiceWork(ServiceWork serviceWork) {
    if (!serviceWorks.contains(serviceWork)) serviceWorks.add(serviceWork);
  }

  public int getDurationInMinutes() {
    return durationInMinutes;
  }

  public void setDurationInMinutes(int durationInMinutes) {
    this.durationInMinutes = durationInMinutes;
  }

  public void setDurationInMinutes() {
    durationInMinutes = 0;
    if (serviceWorks != null) {
      for (ServiceWork sw : serviceWorks) {
        durationInMinutes += sw.getDurationInMinutes();
      }
    }
  }
}
