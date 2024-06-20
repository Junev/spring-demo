package com.example.mytask.entity;

public class EquipTimeLong {
    private String equipmentId;
    private String equipmentClassId;
    private String propertyId;
    private String propertyName;
    private String propertyValue;
    private String propertyFlag;
    private String processValueCondition;
    private Long processValue;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentClassId() {
        return equipmentClassId;
    }

    public void setEquipmentClassId(String equipmentClassId) {
        this.equipmentClassId = equipmentClassId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyFlag() {
        return propertyFlag;
    }

    public void setPropertyFlag(String propertyFlag) {
        this.propertyFlag = propertyFlag;
    }

    public String getProcessValueCondition() {
        return processValueCondition;
    }

    public void setProcessValueCondition(String processValueCondition) {
        this.processValueCondition = processValueCondition;
    }

    public Long getProcessValue() {
        return processValue;
    }

    public void setProcessValue(Long processValue) {
        this.processValue = processValue;
    }
}
