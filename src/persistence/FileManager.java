package persistence;

import model.Admin;
import model.Assignment;
import model.Emergency;
import model.ResponseTeam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {

    // =========================================================
    // DATA FOLDER
    // =========================================================

    private static final String DATA_FOLDER =
            "data";


    // =========================================================
    // FILE NAMES
    // =========================================================

    private static final String ADMINS_FILE =
            DATA_FOLDER
                    + File.separator
                    + "admins.dat";

    private static final String EMERGENCIES_FILE =
            DATA_FOLDER
                    + File.separator
                    + "emergencies.dat";

    private static final String TEAMS_FILE =
            DATA_FOLDER
                    + File.separator
                    + "teams.dat";

    private static final String ASSIGNMENTS_FILE =
            DATA_FOLDER
                    + File.separator
                    + "assignments.dat";


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public FileManager() {

        createDataFolder();
    }


    // =========================================================
    // CREATE DATA FOLDER
    // =========================================================

    private void createDataFolder() {

        File folder =
                new File(
                        DATA_FOLDER
                );

        if (!folder.exists()) {

            folder.mkdirs();
        }
    }


    // =========================================================
    // ADMIN SAVE
    // =========================================================

    public void saveAdmins(
            ArrayList<Admin> admins
    ) {

        saveObject(
                ADMINS_FILE,
                admins
        );
    }


    // =========================================================
    // ADMIN LOAD
    // =========================================================

    public ArrayList<Admin> loadAdmins() {

        Object object =
                loadObject(
                        ADMINS_FILE
                );

        if (
                object instanceof ArrayList<?>
        ) {

            ArrayList<?> list =
                    (ArrayList<?>) object;

            ArrayList<Admin> result =
                    new ArrayList<>();

            for (
                    Object item
                    : list
            ) {

                if (
                        item instanceof Admin
                ) {

                    result.add(
                            (Admin) item
                    );
                }
            }

            return result;
        }

        return new ArrayList<>();
    }


    // =========================================================
    // EMERGENCY SAVE
    // =========================================================

    public void saveEmergencies(
            ArrayList<Emergency> emergencies
    ) {

        saveObject(
                EMERGENCIES_FILE,
                emergencies
        );
    }


    // =========================================================
    // EMERGENCY LOAD
    // =========================================================

    public ArrayList<Emergency> loadEmergencies() {

        Object object =
                loadObject(
                        EMERGENCIES_FILE
                );

        if (
                object instanceof ArrayList<?>
        ) {

            ArrayList<?> list =
                    (ArrayList<?>) object;

            ArrayList<Emergency> result =
                    new ArrayList<>();

            for (
                    Object item
                    : list
            ) {

                if (
                        item instanceof Emergency
                ) {

                    result.add(
                            (Emergency) item
                    );
                }
            }

            return result;
        }

        return new ArrayList<>();
    }


    // =========================================================
    // TEAM SAVE
    // =========================================================

    public void saveTeams(
            ArrayList<ResponseTeam> teams
    ) {

        saveObject(
                TEAMS_FILE,
                teams
        );
    }


    // =========================================================
    // TEAM LOAD
    // =========================================================

    public ArrayList<ResponseTeam> loadTeams() {

        Object object =
                loadObject(
                        TEAMS_FILE
                );

        if (
                object instanceof ArrayList<?>
        ) {

            ArrayList<?> list =
                    (ArrayList<?>) object;

            ArrayList<ResponseTeam> result =
                    new ArrayList<>();

            for (
                    Object item
                    : list
            ) {

                if (
                        item instanceof ResponseTeam
                ) {

                    result.add(
                            (ResponseTeam) item
                    );
                }
            }

            return result;
        }

        return new ArrayList<>();
    }


    // =========================================================
    // ASSIGNMENT SAVE
    // =========================================================

    public void saveAssignments(
            ArrayList<Assignment> assignments
    ) {

        saveObject(
                ASSIGNMENTS_FILE,
                assignments
        );
    }


    // =========================================================
    // ASSIGNMENT LOAD
    // =========================================================

    public ArrayList<Assignment> loadAssignments() {

        Object object =
                loadObject(
                        ASSIGNMENTS_FILE
                );

        if (
                object instanceof ArrayList<?>
        ) {

            ArrayList<?> list =
                    (ArrayList<?>) object;

            ArrayList<Assignment> result =
                    new ArrayList<>();

            for (
                    Object item
                    : list
            ) {

                if (
                        item instanceof Assignment
                ) {

                    result.add(
                            (Assignment) item
                    );
                }
            }

            return result;
        }

        return new ArrayList<>();
    }


    // =========================================================
    // GENERIC SAVE
    // =========================================================

    private void saveObject(
            String filePath,
            Object object
    ) {

        createDataFolder();

        try (
                FileOutputStream outputStream =
                        new FileOutputStream(
                                filePath
                        );

                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(
                                outputStream
                        )
        ) {

            objectOutputStream.writeObject(
                    object
            );

        } catch (
                IOException e
        ) {

            System.out.println(
                    "Error saving data: "
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // GENERIC LOAD
    // =========================================================

    private Object loadObject(
            String filePath
    ) {

        File file =
                new File(
                        filePath
                );


        if (!file.exists()) {

            return null;
        }


        try (
                FileInputStream inputStream =
                        new FileInputStream(
                                file
                        );

                ObjectInputStream objectInputStream =
                        new ObjectInputStream(
                                inputStream
                        )
        ) {

            return objectInputStream.readObject();

        } catch (
                IOException
                | ClassNotFoundException e
        ) {

            System.out.println(
                    "Error loading data: "
                            + e.getMessage()
            );

            return null;
        }
    }
}