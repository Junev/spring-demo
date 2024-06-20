package com.example.mytask.entity;

public class PropertyValue {
    private String propertyId;
    private Object value;
    private Object preValue;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getPreValue() {
        return preValue;
    }

    public void setPreValue(Object preValue) {
        this.preValue = preValue;
    }
}
