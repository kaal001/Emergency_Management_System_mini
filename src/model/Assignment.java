package model;

import java.io.Serializable;

public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    // =========================================================
    // FIELDS
    // =========================================================

    private String assignmentId;
    private String emergencyId;
    private String teamId;
    private String assignedTime;
    private String notes;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Assignment() {
        this.assignmentId = "";
        this.emergencyId = "";
        this.teamId = "";
        this.assignedTime = "";
        this.notes = "";
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public Assignment(
            String assignmentId,
            String emergencyId,
            String teamId,
            String assignedTime,
            String notes
    ) {
        this.assignmentId = assignmentId;
        this.emergencyId = emergencyId;
        this.teamId = teamId;
        this.assignedTime = assignedTime;
        this.notes = notes;
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }


    public String getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(String emergencyId) {
        this.emergencyId = emergencyId;
    }


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }


    public String getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(String assignedTime) {
        this.assignedTime = assignedTime;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {
        return assignmentId
                + " - "
                + emergencyId
                + " - "
                + teamId;
    }
}