package model;

import enums.TeamType;

import java.io.Serializable;

public abstract class ResponseTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    // =========================================================
    // FIELDS
    // =========================================================

    private String teamId;
    private String teamName;
    private TeamType teamType;
    private String contactNumber;
    private int memberCount;
    private boolean available;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public ResponseTeam() {
        this.teamId = "";
        this.teamName = "";
        this.teamType = null;
        this.contactNumber = "";
        this.memberCount = 0;
        this.available = true;
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public ResponseTeam(
            String teamId,
            String teamName,
            TeamType teamType,
            String contactNumber,
            int memberCount
    ) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamType = teamType;
        this.contactNumber = contactNumber;
        this.memberCount = memberCount;
        this.available = true;
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }


    public TeamType getTeamType() {
        return teamType;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }


    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }


    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    // =========================================================
    // PROTECTED METHOD
    // =========================================================

    protected String getTeamResponseMessage() {
        return "Team " + teamName
                + " is responding to the emergency.";
    }


    // =========================================================
    // ABSTRACT METHOD
    // =========================================================

    public abstract void respondToEmergency();


    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {
        return teamId + " - " + teamName;
    }
}