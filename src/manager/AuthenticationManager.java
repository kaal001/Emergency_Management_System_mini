package manager;

import model.Admin;

import java.util.ArrayList;

public class AuthenticationManager {

    // =========================================================
    // DATA
    // =========================================================

    private ArrayList<Admin> admins;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AuthenticationManager() {
        admins = new ArrayList<>();

        createDefaultAdmin();
    }


    // =========================================================
    // CONSTRUCTOR WITH EXISTING DATA
    // =========================================================

    public AuthenticationManager(
            ArrayList<Admin> admins
    ) {

        if (admins == null) {
            this.admins = new ArrayList<>();
        } else {
            this.admins = admins;
        }

        /*
         * If no admin exists, create the default
         * administrator account.
         */
        if (this.admins.isEmpty()) {
            createDefaultAdmin();
        }
    }


    // =========================================================
    // AUTHENTICATE
    // =========================================================

    public Admin authenticate(
            String username,
            String password
    ) {

        if (isBlank(username)
                || isBlank(password)) {

            return null;
        }

        String enteredUsername =
                username.trim();

        for (Admin admin : admins) {

            if (admin.getUsername()
                    .equals(enteredUsername)
                    &&
                    admin.getPassword()
                            .equals(password)) {

                return admin;
            }
        }

        return null;
    }


    // =========================================================
    // ADD ADMIN
    // =========================================================

    public boolean addAdmin(Admin admin) {

        if (admin == null) {
            return false;
        }

        if (isBlank(admin.getAdminId())
                || isBlank(admin.getName())
                || isBlank(admin.getUsername())
                || isBlank(admin.getPassword())) {

            return false;
        }

        if (findAdminByUsername(
                admin.getUsername()
        ) != null) {

            return false;
        }

        admins.add(admin);

        return true;
    }


    // =========================================================
    // FIND ADMIN BY USERNAME
    // =========================================================

    public Admin findAdminByUsername(
            String username
    ) {

        if (isBlank(username)) {
            return null;
        }

        for (Admin admin : admins) {

            if (admin.getUsername()
                    .equalsIgnoreCase(
                            username.trim()
                    )) {

                return admin;
            }
        }

        return null;
    }


    // =========================================================
    // CREATE DEFAULT ADMIN
    // =========================================================

    public void createDefaultAdmin() {

        if (!admins.isEmpty()) {
            return;
        }

        Admin defaultAdmin =
                new Admin(
                        "A001",
                        "System Administrator",
                        "admin",
                        "admin123",
                        "admin@ems.com",
                        "01700000000"
                );

        admins.add(defaultAdmin);
    }


    // =========================================================
    // GET ALL ADMINS
    // =========================================================

    public ArrayList<Admin> getAllAdmins() {

        return new ArrayList<>(admins);
    }


    // =========================================================
    // SET ADMINS
    // =========================================================

    public void setAdmins(
            ArrayList<Admin> admins
    ) {

        if (admins == null) {
            this.admins = new ArrayList<>();
        } else {
            this.admins = admins;
        }

        if (this.admins.isEmpty()) {
            createDefaultAdmin();
        }
    }


    // =========================================================
    // PRIVATE HELPER
    // =========================================================

    private boolean isBlank(String value) {

        return value == null
                || value.trim().isEmpty();
    }
}