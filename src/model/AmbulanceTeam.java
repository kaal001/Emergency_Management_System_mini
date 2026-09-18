package model;

import enums.TeamType;

public class AmbulanceTeam extends ResponseTeam {

    public AmbulanceTeam() {
        super();
        setTeamType(TeamType.AMBULANCE);
    }


    public AmbulanceTeam(
            String teamId,
            String teamName,
            String contactNumber,
            int memberCount
    ) {
        super(
                teamId,
                teamName,
                TeamType.AMBULANCE,
                contactNumber,
                memberCount
        );
    }


    @Override
    public void respondToEmergency() {
        System.out.println(
                "Ambulance Team: " + getTeamResponseMessage()
        );
    }
}