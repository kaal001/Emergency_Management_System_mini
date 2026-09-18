package app;

import manager.AuthenticationManager;
import ui.LoginFrame;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AuthenticationManager authenticationManager =
                    new AuthenticationManager();

            LoginFrame loginFrame =
                    new LoginFrame(
                            authenticationManager
                    );

            loginFrame.setVisible(true);
        });
    }
}