package model;

import enums.TeamType;

public class FireTeam extends ResponseTeam {

    public FireTeam() {
        super();
        setTeamType(TeamType.FIRE);
    }


    public FireTeam(
            String teamId,
            String teamName,
            String contactNumber,
            int memberCount
    ) {
        super(
                teamId,
                teamName,
                TeamType.FIRE,
                contactNumber,
                memberCount
        );
    }


    @Override
    public void respondToEmergency() {
        System.out.println(
                "Fire Team: " + getTeamResponseMessage()
        );
    }
}