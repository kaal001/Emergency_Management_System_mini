package manager;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import model.Emergency;
import persistence.FileManager;

import java.util.ArrayList;

public class EmergencyManager {

    // =========================================================
    // DATA
    // =========================================================

    private ArrayList<Emergency> emergencies;

    private FileManager fileManager;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public EmergencyManager() {

        this(null);
    }


    // =========================================================
    // CONSTRUCTOR WITH FILE MANAGER
    // =========================================================

    public EmergencyManager(
            FileManager fileManager
    ) {

        this.fileManager =
                fileManager;

        this.emergencies =
                new ArrayList<>();


        /*
         * Load previously saved emergency data.
         */
        if (fileManager != null) {

            ArrayList<Emergency> loadedEmergencies =
                    fileManager.loadEmergencies();


            if (
                    loadedEmergencies != null
            ) {

                emergencies =
                        new ArrayList<>(
                                loadedEmergencies
                        );
            }
        }
    }


    // =========================================================
    // ADD EMERGENCY
    // =========================================================

    public boolean addEmergency(
            Emergency emergency
    ) {

        if (emergency == null) {
            return false;
        }


        /*
         * Generate an ID automatically if necessary.
         */
        if (
                isBlank(
                        emergency.getEmergencyId()
                )
        ) {

            if (emergency.getType() == null) {
                return false;
            }

            emergency.setEmergencyId(
                    generateEmergencyId(
                            emergency.getType()
                    )
            );
        }


        if (emergency.getType() == null) {
            return false;
        }


        if (emergency.getPriority() == null) {
            return false;
        }


        if (
                isBlank(
                        emergency.getLocation()
                )
        ) {

            return false;
        }


        if (
                isBlank(
                        emergency.getDescription()
                )
        ) {

            return false;
        }


        if (
                isBlank(
                        emergency.getDateTime()
                )
        ) {

            return false;
        }


        /*
         * Prevent duplicate emergency IDs.
         */
        if (
                findEmergencyById(
                        emergency.getEmergencyId()
                ) != null
        ) {

            return false;
        }


        emergencies.add(
                emergency
        );


        saveData();


        return true;
    }


    // =========================================================
    // GENERATE EMERGENCY ID
    // =========================================================

    public String generateEmergencyId(
            EmergencyType type
    ) {

        if (type == null) {
            return "EM-001";
        }


        String prefix =
                getEmergencyPrefix(
                        type
                );


        int highestNumber = 0;


        for (
                Emergency emergency
                : emergencies
        ) {

            String id =
                    emergency.getEmergencyId();


            if (id == null) {
                continue;
            }


            if (
                    !id.startsWith(
                            prefix + "-"
                    )
            ) {

                continue;
            }


            try {

                String numberPart =
                        id.substring(
                                prefix.length() + 1
                        );


                int number =
                        Integer.parseInt(
                                numberPart
                        );


                if (
                        number > highestNumber
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


        return prefix
                + "-"
                + String.format(
                "%03d",
                highestNumber + 1
        );
    }


    // =========================================================
    // EMERGENCY PREFIX
    // =========================================================

    private String getEmergencyPrefix(
            EmergencyType type
    ) {

        switch (type) {

            case MEDICAL:
                return "MED";

            case FIRE:
                return "FIR";

            case ROAD_ACCIDENT:
                return "ACC";

            case SECURITY:
                return "SEC";

            case NATURAL_DISASTER:
                return "NAT";

            case GAS_LEAK:
                return "GAS";

            case ELECTRICAL_EMERGENCY:
                return "ELE";

            case BUILDING_COLLAPSE:
                return "BLD";

            case INDUSTRIAL_ACCIDENT:
                return "IND";

            case MISSING_PERSON:
                return "MIS";

            case WATER_FLOOD_EMERGENCY:
                return "FLD";

            default:
                return "EM";
        }
    }


    // =========================================================
    // UPDATE EMERGENCY
    // =========================================================

    public boolean updateEmergency(
            String emergencyId,
            Emergency updatedEmergency
    ) {

        if (
                isBlank(emergencyId)
                        || updatedEmergency == null
        ) {

            return false;
        }


        Emergency existingEmergency =
                findEmergencyById(
                        emergencyId
                );


        if (existingEmergency == null) {
            return false;
        }


        if (updatedEmergency.getType() == null) {
            return false;
        }


        if (updatedEmergency.getPriority() == null) {
            return false;
        }


        if (
                isBlank(
                        updatedEmergency.getLocation()
                )
        ) {

            return false;
        }


        if (
                isBlank(
                        updatedEmergency.getDescription()
                )
        ) {

            return false;
        }


        if (
                isBlank(
                        updatedEmergency.getDateTime()
                )
        ) {

            return false;
        }


        /*
         * ID, status and assigned team are controlled
         * separately by their own workflow.
         */

        existingEmergency.setType(
                updatedEmergency.getType()
        );


        existingEmergency.setPriority(
                updatedEmergency.getPriority()
        );


        existingEmergency.setLocation(
                updatedEmergency.getLocation()
        );


        existingEmergency.setDescription(
                updatedEmergency.getDescription()
        );


        existingEmergency.setDateTime(
                updatedEmergency.getDateTime()
        );


        saveData();


        return true;
    }


    // =========================================================
    // DELETE EMERGENCY
    // =========================================================

    public boolean removeEmergency(
            String emergencyId
    ) {

        Emergency emergency =
                findEmergencyById(
                        emergencyId
                );


        if (emergency == null) {
            return false;
        }


        /*
         * Active emergencies cannot be deleted.
         */

        if (
                emergency.getStatus()
                        == EmergencyStatus.ASSIGNED
                        ||
                        emergency.getStatus()
                                == EmergencyStatus.IN_PROGRESS
        ) {

            return false;
        }


        boolean removed =
                emergencies.remove(
                        emergency
                );


        if (removed) {

            saveData();
        }


        return removed;
    }


    // =========================================================
    // FIND BY ID
    // =========================================================

    public Emergency findEmergencyById(
            String emergencyId
    ) {

        if (isBlank(emergencyId)) {
            return null;
        }


        for (
                Emergency emergency
                : emergencies
        ) {

            if (
                    emergency.getEmergencyId()
                            .equalsIgnoreCase(
                                    emergencyId.trim()
                            )
            ) {

                return emergency;
            }
        }


        return null;
    }


    // =========================================================
    // SEARCH
    // =========================================================

    public ArrayList<Emergency> searchEmergencies(
            String keyword
    ) {

        ArrayList<Emergency> results =
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
                    emergencies
            );

            return results;
        }


        for (
                Emergency emergency
                : emergencies
        ) {

            boolean matches =

                    containsIgnoreCase(
                            emergency.getEmergencyId(),
                            searchText
                    )

                            ||

                            containsIgnoreCase(
                                    emergency.getLocation(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    emergency.getDescription(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    emergency.getType().toString(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    emergency.getPriority().toString(),
                                    searchText
                            )

                            ||

                            containsIgnoreCase(
                                    emergency.getStatus().toString(),
                                    searchText
                            );


            if (matches) {

                results.add(
                        emergency
                );
            }
        }


        return results;
    }


    // =========================================================
    // FILTER BY STATUS
    // =========================================================

    public ArrayList<Emergency> getEmergenciesByStatus(
            EmergencyStatus status
    ) {

        ArrayList<Emergency> results =
                new ArrayList<>();


        if (status == null) {
            return results;
        }


        for (
                Emergency emergency
                : emergencies
        ) {

            if (
                    emergency.getStatus()
                            == status
            ) {

                results.add(
                        emergency
                );
            }
        }


        return results;
    }


    // =========================================================
    // FILTER BY PRIORITY
    // =========================================================

    public ArrayList<Emergency> getEmergenciesByPriority(
            Priority priority
    ) {

        ArrayList<Emergency> results =
                new ArrayList<>();


        if (priority == null) {
            return results;
        }


        for (
                Emergency emergency
                : emergencies
        ) {

            if (
                    emergency.getPriority()
                            == priority
            ) {

                results.add(
                        emergency
                );
            }
        }


        return results;
    }


    // =========================================================
    // FILTER BY TYPE
    // =========================================================

    public ArrayList<Emergency> getEmergenciesByType(
            EmergencyType type
    ) {

        ArrayList<Emergency> results =
                new ArrayList<>();


        if (type == null) {
            return results;
        }


        for (
                Emergency emergency
                : emergencies
        ) {

            if (
                    emergency.getType()
                            == type
            ) {

                results.add(
                        emergency
                );
            }
        }


        return results;
    }


    // =========================================================
    // UPDATE PRIORITY
    // =========================================================

    public boolean updateEmergencyPriority(
            String emergencyId,
            Priority priority
    ) {

        Emergency emergency =
                findEmergencyById(
                        emergencyId
                );


        if (
                emergency == null
                        || priority == null
        ) {

            return false;
        }


        emergency.setPriority(
                priority
        );


        saveData();


        return true;
    }


    // =========================================================
    // UPDATE STATUS
    // =========================================================

    public boolean updateEmergencyStatus(
            String emergencyId,
            EmergencyStatus newStatus
    ) {

        Emergency emergency =
                findEmergencyById(
                        emergencyId
                );


        if (
                emergency == null
                        || newStatus == null
        ) {

            return false;
        }


        EmergencyStatus currentStatus =
                emergency.getStatus();


        if (
                !isValidStatusTransition(
                        currentStatus,
                        newStatus
                )
        ) {

            return false;
        }


        /*
         * ASSIGNED requires an assigned team.
         */

        if (
                newStatus
                        == EmergencyStatus.ASSIGNED
                        &&
                        isBlank(
                                emergency.getAssignedTeamId()
                        )
        ) {

            return false;
        }


        emergency.setStatus(
                newStatus
        );


        saveData();


        return true;
    }


    // =========================================================
    // STATUS TRANSITION
    // =========================================================

    private boolean isValidStatusTransition(
            EmergencyStatus currentStatus,
            EmergencyStatus newStatus
    ) {

        if (
                currentStatus == null
                        || newStatus == null
        ) {

            return false;
        }


        if (
                currentStatus == newStatus
        ) {

            return true;
        }


        switch (currentStatus) {

            case PENDING:

                return
                        newStatus
                                == EmergencyStatus.ASSIGNED
                                ||
                                newStatus
                                        == EmergencyStatus.CANCELLED;


            case ASSIGNED:

                return
                        newStatus
                                == EmergencyStatus.IN_PROGRESS
                                ||
                                newStatus
                                        == EmergencyStatus.CANCELLED;


            case IN_PROGRESS:

                return
                        newStatus
                                == EmergencyStatus.RESOLVED
                                ||
                                newStatus
                                        == EmergencyStatus.CANCELLED;


            case RESOLVED:
            case CANCELLED:

                return false;


            default:

                return false;
        }
    }


    // =========================================================
    // GET ALL
    // =========================================================

    public ArrayList<Emergency> getAllEmergencies() {

        return new ArrayList<>(
                emergencies
        );
    }


    // =========================================================
    // LOAD DATA
    // =========================================================

    public void loadData(
            ArrayList<Emergency> loadedEmergencies
    ) {

        if (loadedEmergencies == null) {

            emergencies =
                    new ArrayList<>();

            return;
        }


        emergencies =
                new ArrayList<>(
                        loadedEmergencies
                );
    }


    // =========================================================
    // SAVE DATA
    // =========================================================

    public void saveData() {

        if (fileManager == null) {

            return;
        }


        fileManager.saveEmergencies(
                new ArrayList<>(
                        emergencies
                )
        );
    }


    // =========================================================
    // COUNTS
    // =========================================================

    public int getTotalEmergencies() {

        return emergencies.size();
    }


    public int getCountByStatus(
            EmergencyStatus status
    ) {

        return getEmergenciesByStatus(
                status
        ).size();
    }


    public int getCountByPriority(
            Priority priority
    ) {

        return getEmergenciesByPriority(
                priority
        ).size();
    }


    public int getCountByType(
            EmergencyType type
    ) {

        return getEmergenciesByType(
                type
        ).size();
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