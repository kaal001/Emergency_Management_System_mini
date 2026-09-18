package manager;

import enums.EmergencyType;
import enums.TeamType;
import model.AmbulanceTeam;
import model.FireTeam;
import model.RescueTeam;
import model.ResponseTeam;
import model.SecurityTeam;
import persistence.FileManager;

import java.util.ArrayList;

public class TeamManager {

    // =========================================================
    // DATA
    // =========================================================

    private ArrayList<ResponseTeam> teams;

    private FileManager fileManager;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public TeamManager() {

        this(null);
    }


    // =========================================================
    // CONSTRUCTOR WITH FILE MANAGER
    // =========================================================

    public TeamManager(
            FileManager fileManager
    ) {

        this.fileManager =
                fileManager;

        this.teams =
                new ArrayList<>();


        /*
         * If a FileManager is available, try to load
         * previously saved teams.
         *
         * If no saved teams exist, create the default
         * teams for the first run.
         */
        if (fileManager != null) {

            ArrayList<ResponseTeam> loadedTeams =
                    fileManager.loadTeams();


            if (
                    loadedTeams != null
                            &&
                            !loadedTeams.isEmpty()
            ) {

                teams =
                        new ArrayList<>(
                                loadedTeams
                        );

            } else {

                createDefaultTeams();
            }

        } else {

            createDefaultTeams();
        }
    }


    // =========================================================
    // ADD TEAM
    // =========================================================

    public boolean addTeam(
            ResponseTeam team
    ) {

        if (team == null) {
            return false;
        }


        if (isBlank(
                team.getTeamId()
        )) {

            return false;
        }


        if (isBlank(
                team.getTeamName()
        )) {

            return false;
        }


        if (team.getTeamType() == null) {

            return false;
        }


        if (isBlank(
                team.getContactNumber()
        )) {

            return false;
        }


        if (team.getMemberCount() <= 0) {

            return false;
        }


        if (
                findTeamById(
                        team.getTeamId()
                ) != null
        ) {

            return false;
        }


        teams.add(
                team
        );


        saveData();


        return true;
    }


    // =========================================================
    // CREATE DEFAULT TEAMS
    // =========================================================

    private void createDefaultTeams() {

        /*
         * Do not use addTeam() here because every call
         * would separately write the file.
         *
         * We first build the default list and save once.
         */


        teams.add(
                new AmbulanceTeam(
                        "AT-001",
                        "Central Ambulance Team",
                        "01711111111",
                        4
                )
        );


        teams.add(
                new FireTeam(
                        "FT-001",
                        "Central Fire Team",
                        "01722222222",
                        6
                )
        );


        teams.add(
                new RescueTeam(
                        "RT-001",
                        "Central Rescue Team",
                        "01733333333",
                        5
                )
        );


        teams.add(
                new SecurityTeam(
                        "ST-001",
                        "Central Security Team",
                        "01744444444",
                        8
                )
        );


        saveData();
    }


    // =========================================================
    // UPDATE TEAM
    // =========================================================

    public boolean updateTeam(
            String teamId,
            String teamName,
            String contactNumber,
            int memberCount,
            boolean available
    ) {

        ResponseTeam team =
                findTeamById(
                        teamId
                );


        if (team == null) {

            return false;
        }


        if (isBlank(
                teamName
        )) {

            return false;
        }


        if (isBlank(
                contactNumber
        )) {

            return false;
        }


        if (memberCount <= 0) {

            return false;
        }


        /*
         * Team type is intentionally not changed.
         *
         * Team type determines the actual subclass:
         *
         * AmbulanceTeam
         * FireTeam
         * RescueTeam
         * SecurityTeam
         */

        team.setTeamName(
                teamName
        );


        team.setContactNumber(
                contactNumber
        );


        team.setMemberCount(
                memberCount
        );


        team.setAvailable(
                available
        );


        saveData();


        return true;
    }


    // =========================================================
    // DELETE TEAM
    // =========================================================

    public boolean removeTeam(
            String teamId
    ) {

        ResponseTeam team =
                findTeamById(
                        teamId
                );


        if (team == null) {

            return false;
        }


        /*
         * A busy team may currently be assigned
         * to an emergency.
         */

        if (!team.isAvailable()) {

            return false;
        }


        boolean removed =
                teams.remove(
                        team
                );


        if (removed) {

            saveData();
        }


        return removed;
    }


    // =========================================================
    // FIND TEAM BY ID
    // =========================================================

    public ResponseTeam findTeamById(
            String teamId
    ) {

        if (isBlank(
                teamId
        )) {

            return null;
        }


        for (
                ResponseTeam team
                : teams
        ) {

            if (
                    team.getTeamId()
                            .equalsIgnoreCase(
                                    teamId.trim()
                            )
            ) {

                return team;
            }
        }


        return null;
    }


    // =========================================================
    // SEARCH
    // =========================================================

    public ArrayList<ResponseTeam> searchTeams(
            String keyword
    ) {

        ArrayList<ResponseTeam> results =
                new ArrayList<>();


        if (keyword == null) {

            return results;
        }


        String searchText =
                keyword
                        .trim()
                        .toLowerCase();


        if (searchText.isEmpty()) {

            results.addAll(
                    teams
            );

            return results;
        }


        for (
                ResponseTeam team
                : teams
        ) {

            boolean matches =

                    containsIgnoreCase(
                            team.getTeamId(),
                            searchText
                    )

                            ||

                            containsIgnoreCase(
                                    team.getTeamName(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    team.getContactNumber(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    team.getTeamType()
                                            .toString(),
                                    searchText
                            );


            if (matches) {

                results.add(
                        team
                );
            }
        }


        return results;
    }


    // =========================================================
    // GET ALL TEAMS
    // =========================================================

    public ArrayList<ResponseTeam> getAllTeams() {

        return new ArrayList<>(
                teams
        );
    }


    // =========================================================
    // LOAD DATA
    // =========================================================

    public void loadData(
            ArrayList<ResponseTeam> loadedTeams
    ) {

        if (
                loadedTeams == null
                        ||
                        loadedTeams.isEmpty()
        ) {

            teams =
                    new ArrayList<>();

            createDefaultTeams();

            return;
        }


        teams =
                new ArrayList<>(
                        loadedTeams
                );
    }


    // =========================================================
    // SAVE DATA
    // =========================================================

    public void saveData() {

        if (fileManager == null) {

            return;
        }


        fileManager.saveTeams(
                new ArrayList<>(
                        teams
                )
        );
    }


    // =========================================================
    // GET AVAILABLE TEAMS
    // =========================================================

    public ArrayList<ResponseTeam> getAvailableTeams() {

        ArrayList<ResponseTeam> results =
                new ArrayList<>();


        for (
                ResponseTeam team
                : teams
        ) {

            if (team.isAvailable()) {

                results.add(
                        team
                );
            }
        }


        return results;
    }


    // =========================================================
    // GET BUSY TEAMS
    // =========================================================

    public ArrayList<ResponseTeam> getBusyTeams() {

        ArrayList<ResponseTeam> results =
                new ArrayList<>();


        for (
                ResponseTeam team
                : teams
        ) {

            if (!team.isAvailable()) {

                results.add(
                        team
                );
            }
        }


        return results;
    }


    // =========================================================
    // GET TEAMS BY TYPE
    // =========================================================

    public ArrayList<ResponseTeam> getTeamsByType(
            TeamType teamType
    ) {

        ArrayList<ResponseTeam> results =
                new ArrayList<>();


        if (teamType == null) {

            return results;
        }


        for (
                ResponseTeam team
                : teams
        ) {

            if (
                    team.getTeamType()
                            == teamType
            ) {

                results.add(
                        team
                );
            }
        }


        return results;
    }


    // =========================================================
    // FIND SUITABLE TEAMS
    // =========================================================

    public ArrayList<ResponseTeam> findSuitableTeams(
            EmergencyType emergencyType
    ) {

        ArrayList<ResponseTeam> results =
                new ArrayList<>();


        TeamType requiredType =
                getRequiredTeamType(
                        emergencyType
                );


        if (requiredType == null) {

            return results;
        }


        for (
                ResponseTeam team
                : teams
        ) {

            if (
                    team.isAvailable()
                            &&
                            team.getTeamType()
                                    == requiredType
            ) {

                results.add(
                        team
                );
            }
        }


        return results;
    }


    // =========================================================
    // CHECK TEAM SUITABILITY
    // =========================================================

    public boolean isTeamSuitable(
            EmergencyType emergencyType,
            ResponseTeam team
    ) {

        if (
                emergencyType == null
                        || team == null
        ) {

            return false;
        }


        TeamType requiredType =
                getRequiredTeamType(
                        emergencyType
                );


        return
                requiredType != null
                        &&
                        team.getTeamType()
                                == requiredType;
    }


    // =========================================================
    // EMERGENCY TYPE → TEAM TYPE
    // =========================================================

    public TeamType getRequiredTeamType(
            EmergencyType emergencyType
    ) {

        if (emergencyType == null) {

            return null;
        }


        switch (emergencyType) {

            case MEDICAL:

                return TeamType.AMBULANCE;


            case FIRE:
            case GAS_LEAK:

                return TeamType.FIRE;


            case ROAD_ACCIDENT:
            case ELECTRICAL_EMERGENCY:
            case BUILDING_COLLAPSE:
            case INDUSTRIAL_ACCIDENT:
            case NATURAL_DISASTER:
            case WATER_FLOOD_EMERGENCY:

                return TeamType.RESCUE;


            case SECURITY:
            case MISSING_PERSON:

                return TeamType.SECURITY;


            default:

                return null;
        }
    }


    // =========================================================
    // COUNTS
    // =========================================================

    public int getTotalTeams() {

        return teams.size();
    }


    public int getAvailableTeamCount() {

        return getAvailableTeams().size();
    }


    public int getBusyTeamCount() {

        return getBusyTeams().size();
    }


    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private boolean isBlank(
            String value
    ) {

        return value == null
                || value.trim().isEmpty();
    }


    private boolean containsIgnoreCase(
            String value,
            String searchText
    ) {

        if (value == null) {

            return false;
        }


        return value
                .toLowerCase()
                .contains(
                        searchText
                );
    }
}