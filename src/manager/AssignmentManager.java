package manager;

import enums.EmergencyStatus;
import model.Assignment;
import model.Emergency;
import model.ResponseTeam;
import persistence.FileManager;

import java.util.ArrayList;

public class AssignmentManager {

    // =========================================================
    // DATA
    // =========================================================

    private ArrayList<Assignment> assignments;


    // =========================================================
    // RELATED MANAGERS
    // =========================================================

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;


    // =========================================================
    // PERSISTENCE
    // =========================================================

    private FileManager fileManager;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public AssignmentManager(
            EmergencyManager emergencyManager,
            TeamManager teamManager
    ) {

        this(
                emergencyManager,
                teamManager,
                null
        );
    }


    // =========================================================
    // CONSTRUCTOR WITH FILE MANAGER
    // =========================================================

    public AssignmentManager(
            EmergencyManager emergencyManager,
            TeamManager teamManager,
            FileManager fileManager
    ) {

        this.emergencyManager =
                emergencyManager;

        this.teamManager =
                teamManager;

        this.fileManager =
                fileManager;

        this.assignments =
                new ArrayList<>();


        /*
         * Load saved assignments if persistence
         * is available.
         */

        if (fileManager != null) {

            ArrayList<Assignment> loadedAssignments =
                    fileManager.loadAssignments();


            if (
                    loadedAssignments != null
                            &&
                            !loadedAssignments.isEmpty()
            ) {

                assignments =
                        new ArrayList<>(
                                loadedAssignments
                        );
            }
        }
    }


    // =========================================================
    // ADD ASSIGNMENT
    // =========================================================

    public boolean addAssignment(
            Assignment assignment
    ) {

        if (assignment == null) {

            return false;
        }


        // -----------------------------------------------------
        // Generate ID if necessary
        // -----------------------------------------------------

        if (
                isBlank(
                        assignment.getAssignmentId()
                )
        ) {

            assignment.setAssignmentId(
                    generateAssignmentId()
            );
        }


        // -----------------------------------------------------
        // Duplicate ID
        // -----------------------------------------------------

        if (
                findAssignmentById(
                        assignment.getAssignmentId()
                ) != null
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Find emergency and team
        // -----------------------------------------------------

        Emergency emergency =
                emergencyManager
                        .findEmergencyById(
                                assignment.getEmergencyId()
                        );


        ResponseTeam team =
                teamManager
                        .findTeamById(
                                assignment.getTeamId()
                        );


        if (
                emergency == null
                        || team == null
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Emergency must be pending
        // -----------------------------------------------------

        if (
                emergency.getStatus()
                        != EmergencyStatus.PENDING
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Team must be available
        // -----------------------------------------------------

        if (!team.isAvailable()) {

            return false;
        }


        // -----------------------------------------------------
        // Team must be suitable
        // -----------------------------------------------------

        if (
                !teamManager.isTeamSuitable(
                        emergency.getType(),
                        team
                )
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Assigned time required
        // -----------------------------------------------------

        if (
                isBlank(
                        assignment.getAssignedTime()
                )
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Store assignment
        // -----------------------------------------------------

        assignments.add(
                assignment
        );


        // -----------------------------------------------------
        // Update emergency
        // -----------------------------------------------------

        emergency.setAssignedTeamId(
                team.getTeamId()
        );


        emergency.setStatus(
                EmergencyStatus.ASSIGNED
        );


        // -----------------------------------------------------
        // Update team
        // -----------------------------------------------------

        team.setAvailable(
                false
        );


        // -----------------------------------------------------
        // Save all related data
        // -----------------------------------------------------

        saveAllData();


        return true;
    }


    // =========================================================
    // ASSIGN TEAM
    // =========================================================

    public Assignment assignTeam(
            String emergencyId,
            String teamId,
            String assignedTime,
            String notes
    ) {

        String assignmentId =
                generateAssignmentId();


        Assignment assignment =
                new Assignment(
                        assignmentId,
                        emergencyId,
                        teamId,
                        assignedTime,
                        notes
                );


        if (
                addAssignment(
                        assignment
                )
        ) {

            return assignment;
        }


        return null;
    }


    // =========================================================
    // UPDATE ASSIGNMENT
    // =========================================================

    public boolean updateAssignment(
            String assignmentId,
            String newTeamId,
            String assignedTime,
            String notes
    ) {

        Assignment assignment =
                findAssignmentById(
                        assignmentId
                );


        if (assignment == null) {

            return false;
        }


        Emergency emergency =
                emergencyManager
                        .findEmergencyById(
                                assignment.getEmergencyId()
                        );


        ResponseTeam oldTeam =
                teamManager
                        .findTeamById(
                                assignment.getTeamId()
                        );


        ResponseTeam newTeam =
                teamManager
                        .findTeamById(
                                newTeamId
                        );


        if (
                emergency == null
                        ||
                        oldTeam == null
                        ||
                        newTeam == null
        ) {

            return false;
        }


        if (
                isBlank(
                        assignedTime
                )
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Team is being changed
        // -----------------------------------------------------

        if (
                !newTeam.getTeamId()
                        .equalsIgnoreCase(
                                oldTeam.getTeamId()
                        )
        ) {

            if (
                    !newTeam.isAvailable()
            ) {

                return false;
            }


            if (
                    !teamManager.isTeamSuitable(
                            emergency.getType(),
                            newTeam
                    )
            ) {

                return false;
            }


            // Release old team

            oldTeam.setAvailable(
                    true
            );


            // Occupy new team

            newTeam.setAvailable(
                    false
            );


            // Update emergency

            emergency.setAssignedTeamId(
                    newTeam.getTeamId()
            );


            // Update assignment

            assignment.setTeamId(
                    newTeam.getTeamId()
            );
        }


        assignment.setAssignedTime(
                assignedTime
        );


        assignment.setNotes(
                notes == null
                        ? ""
                        : notes
        );


        saveAllData();


        return true;
    }


    // =========================================================
    // DELETE ASSIGNMENT
    // =========================================================

    public boolean removeAssignment(
            String assignmentId
    ) {

        Assignment assignment =
                findAssignmentById(
                        assignmentId
                );


        if (assignment == null) {

            return false;
        }


        Emergency emergency =
                emergencyManager
                        .findEmergencyById(
                                assignment.getEmergencyId()
                        );


        ResponseTeam team =
                teamManager
                        .findTeamById(
                                assignment.getTeamId()
                        );


        if (
                emergency == null
                        || team == null
        ) {

            return false;
        }


        // -----------------------------------------------------
        // In-progress assignment cannot be deleted
        // -----------------------------------------------------

        if (
                emergency.getStatus()
                        == EmergencyStatus.IN_PROGRESS
        ) {

            return false;
        }


        // -----------------------------------------------------
        // Release assigned team
        // -----------------------------------------------------

        if (
                emergency.getStatus()
                        == EmergencyStatus.ASSIGNED
        ) {

            team.setAvailable(
                    true
            );


            emergency.setAssignedTeamId(
                    null
            );


            emergency.setStatus(
                    EmergencyStatus.PENDING
            );
        }


        boolean removed =
                assignments.remove(
                        assignment
                );


        if (removed) {

            saveAllData();
        }


        return removed;
    }


    // =========================================================
    // FIND ASSIGNMENT BY ID
    // =========================================================

    public Assignment findAssignmentById(
            String assignmentId
    ) {

        if (
                isBlank(
                        assignmentId
                )
        ) {

            return null;
        }


        for (
                Assignment assignment
                : assignments
        ) {

            if (
                    assignment.getAssignmentId()
                            .equalsIgnoreCase(
                                    assignmentId.trim()
                            )
            ) {

                return assignment;
            }
        }


        return null;
    }


    // =========================================================
    // SEARCH ASSIGNMENTS
    // =========================================================

    public ArrayList<Assignment> searchAssignments(
            String keyword
    ) {

        ArrayList<Assignment> results =
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
                    assignments
            );

            return results;
        }


        for (
                Assignment assignment
                : assignments
        ) {

            boolean matches =

                    containsIgnoreCase(
                            assignment.getAssignmentId(),
                            searchText
                    )

                            ||

                            containsIgnoreCase(
                                    assignment.getEmergencyId(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    assignment.getTeamId(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    assignment.getAssignedTime(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    assignment.getNotes(),
                                    searchText
                            );


            if (matches) {

                results.add(
                        assignment
                );
            }
        }


        return results;
    }


    // =========================================================
    // GET ALL ASSIGNMENTS
    // =========================================================

    public ArrayList<Assignment> getAllAssignments() {

        return new ArrayList<>(
                assignments
        );
    }


    // =========================================================
    // LOAD DATA
    // =========================================================

    public void loadData(
            ArrayList<Assignment> loadedAssignments
    ) {

        if (
                loadedAssignments == null
        ) {

            assignments =
                    new ArrayList<>();

            return;
        }


        assignments =
                new ArrayList<>(
                        loadedAssignments
                );
    }


    // =========================================================
    // SAVE DATA
    // =========================================================

    public void saveData() {

        if (fileManager == null) {

            return;
        }


        fileManager.saveAssignments(
                new ArrayList<>(
                        assignments
                )
        );
    }


    // =========================================================
    // SAVE ALL RELATED DATA
    // =========================================================

    private void saveAllData() {

        if (fileManager == null) {

            return;
        }


        /*
         * Assignment changes also change:
         *
         * 1. Assignment data
         * 2. Emergency status/team
         * 3. Team availability
         *
         * Therefore all three files are saved together.
         */

        fileManager.saveAssignments(
                new ArrayList<>(
                        assignments
                )
        );


        fileManager.saveEmergencies(
                emergencyManager
                        .getAllEmergencies()
        );


        fileManager.saveTeams(
                teamManager
                        .getAllTeams()
        );
    }


    // =========================================================
    // GENERATE ASSIGNMENT ID
    // =========================================================

    public String generateAssignmentId() {

        int highestNumber =
                1000;


        for (
                Assignment assignment
                : assignments
        ) {

            String id =
                    assignment.getAssignmentId();


            if (id == null) {
                continue;
            }


            if (
                    !id.startsWith(
                            "AS-"
                    )
            ) {

                continue;
            }


            try {

                String numberPart =
                        id.substring(
                                3
                        );


                int number =
                        Integer.parseInt(
                                numberPart
                        );


                if (
                        number
                                > highestNumber
                ) {

                    highestNumber =
                            number;
                }


            } catch (
                    NumberFormatException ignored
            ) {

                // Ignore invalid IDs.
            }
        }


        return "AS-"
                + (
                highestNumber + 1
        );
    }


    // =========================================================
    // COUNT
    // =========================================================

    public int getTotalAssignments() {

        return assignments.size();
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