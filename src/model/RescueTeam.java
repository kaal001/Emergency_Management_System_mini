package model;

import enums.TeamType;

public class RescueTeam extends ResponseTeam {

    public RescueTeam() {
        super();
        setTeamType(TeamType.RESCUE);
    }


    public RescueTeam(
            String teamId,
            String teamName,
            String contactNumber,
            int memberCount
    ) {
        super(
                teamId,
                teamName,
                TeamType.RESCUE,
                contactNumber,
                memberCount
        );
    }


    @Override
    public void respondToEmergency() {
        System.out.println(
                "Rescue Team: " + getTeamResponseMessage()
        );
    }
}