package model;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;

import java.io.Serializable;

public class Emergency implements Serializable {

    private static final long serialVersionUID = 1L;

    // =========================================================
    // FIELDS
    // =========================================================

    private String emergencyId;
    private EmergencyType type;
    private Priority priority;
    private String location;
    private String description;
    private String dateTime;
    private EmergencyStatus status;
    private String assignedTeamId;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Emergency() {
        this.emergencyId = "";
        this.type = null;
        this.priority = null;
        this.location = "";
        this.description = "";
        this.dateTime = "";
        this.status = EmergencyStatus.PENDING;
        this.assignedTeamId = null;
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public Emergency(
            String emergencyId,
            EmergencyType type,
            Priority priority,
            String location,
            String description,
            String dateTime
    ) {
        this.emergencyId = emergencyId;
        this.type = type;
        this.priority = priority;
        this.location = location;
        this.description = description;
        this.dateTime = dateTime;
        this.status = EmergencyStatus.PENDING;
        this.assignedTeamId = null;
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(String emergencyId) {
        this.emergencyId = emergencyId;
    }


    public EmergencyType getType() {
        return type;
    }

    public void setType(EmergencyType type) {
        this.type = type;
    }


    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    public EmergencyStatus getStatus() {
        return status;
    }

    public void setStatus(EmergencyStatus status) {
        this.status = status;
    }


    public String getAssignedTeamId() {
        return assignedTeamId;
    }

    public void setAssignedTeamId(String assignedTeamId) {
        this.assignedTeamId = assignedTeamId;
    }


    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {
        return emergencyId + " - " + type + " - " + priority;
    }
}