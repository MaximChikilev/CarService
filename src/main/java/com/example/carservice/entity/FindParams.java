package com.example.carservice.entity;

public class FindParams {
  private String fieldName = "";
  private String value = "";

  public FindParams() {}

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldsName) {
    this.fieldName = fieldsName;
  }
}
