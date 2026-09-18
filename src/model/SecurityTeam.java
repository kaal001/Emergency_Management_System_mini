package model;

import enums.TeamType;

public class SecurityTeam extends ResponseTeam {

    public SecurityTeam() {
        super();
        setTeamType(TeamType.SECURITY);
    }


    public SecurityTeam(
            String teamId,
            String teamName,
            String contactNumber,
            int memberCount
    ) {
        super(
                teamId,
                teamName,
                TeamType.SECURITY,
                contactNumber,
                memberCount
        );
    }


    @Override
    public void respondToEmergency() {
        System.out.println(
                "Security Team: " + getTeamResponseMessage()
        );
    }
}