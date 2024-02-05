package com.example.carservice.dto;

import com.example.carservice.entity.TimeWindows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarDateTimeChoiceDTO {
    private String selectedLicensePlateNumber;
    private String selectedDateTime;

    public CarDateTimeChoiceDTO() {
    }

    public CarDateTimeChoiceDTO(String selectedLicensePlateNumber, String selectedDateTime) {
        this.selectedLicensePlateNumber = selectedLicensePlateNumber;
        this.selectedDateTime = selectedDateTime;
    }

    public String getSelectedLicensePlateNumber() {
        return selectedLicensePlateNumber;
    }

    public void setSelectedLicensePlateNumber(String selectedLicensePlateNumber) {
        this.selectedLicensePlateNumber = selectedLicensePlateNumber;
    }

    public String getSelectedDateTime() {
        return selectedDateTime;
    }

    public void setSelectedDateTime(String selectedDateTime) {
        this.selectedDateTime = selectedDateTime;
    }

    public TimeWindows getTimeWindowFromChoice() {
        int length = selectedDateTime.length();
        String timeWindow = selectedDateTime.substring(length - 6, length - 1);
        return TimeWindows.getByHours(timeWindow);
    }

    public Date getDateFromChoice() throws ParseException {
        String pattern = "dd.MM.yyyy";
        String stringDate = selectedDateTime.substring(0, selectedDateTime.length() - 7);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(stringDate);
        System.out.println(date);
        return date;
    }
}
