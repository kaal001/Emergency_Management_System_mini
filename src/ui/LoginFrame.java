package ui;

import manager.AuthenticationManager;
import model.Admin;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    // =========================================================
    // FIELDS
    // =========================================================

    private final AuthenticationManager authenticationManager;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginFrame(
            AuthenticationManager authenticationManager
    ) {

        this.authenticationManager =
                authenticationManager;

        initializeFrame();
        buildUI();
    }


    // =========================================================
    // FRAME SETUP
    // =========================================================

    private void initializeFrame() {

        setTitle(
                Theme.APP_TITLE + " - Login"
        );

        // Compact login window
        setSize(
                430,
                340
        );

        setResizable(false);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        getContentPane().setBackground(
                Theme.BACKGROUND
        );
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                12,
                                12
                        )
                );

        mainPanel.setBackground(
                Theme.BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        28,
                        22,
                        28
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setOpaque(false);


        JLabel titleLabel =
                new JLabel(
                        "EMERGENCY MANAGEMENT SYSTEM"
                );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        titleLabel.setForeground(
                Theme.JET_BLACK
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "Administrator Login"
                );

        subtitleLabel.setFont(
                Theme.SMALL_FONT
        );

        subtitleLabel.setForeground(
                Theme.STONE_BROWN
        );

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        headerPanel.add(
                titleLabel
        );

        headerPanel.add(
                Box.createVerticalStrut(4)
        );

        headerPanel.add(
                subtitleLabel
        );


        // =====================================================
        // FORM
        // =====================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6,
                        4,
                        6,
                        4
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        JLabel usernameLabel =
                new JLabel("Username:");

        usernameLabel.setFont(
                Theme.SUBTITLE_FONT
        );

        usernameLabel.setForeground(
                Theme.TEXT
        );


        usernameField =
                new JTextField();

        styleTextField(
                usernameField
        );


        JLabel passwordLabel =
                new JLabel("Password:");

        passwordLabel.setFont(
                Theme.SUBTITLE_FONT
        );

        passwordLabel.setForeground(
                Theme.TEXT
        );


        passwordField =
                new JPasswordField();

        styleTextField(
                passwordField
        );


        // Username

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        formPanel.add(
                usernameLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                usernameField,
                gbc
        );


        // Password

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        formPanel.add(
                passwordLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                passwordField,
                gbc
        );


        // =====================================================
        // BOTTOM AREA
        // =====================================================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );

        bottomPanel.setOpaque(false);


        loginButton =
                new JButton(
                        "LOGIN"
                );

        stylePrimaryButton(
                loginButton
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.addActionListener(
                e -> performLogin()
        );


        JLabel hintLabel =
                new JLabel(
                        "Default login: admin / admin123"
                );

        hintLabel.setFont(
                Theme.SMALL_FONT
        );

        hintLabel.setForeground(
                Theme.STONE_BROWN
        );

        hintLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        bottomPanel.add(
                loginButton
        );

        bottomPanel.add(
                Box.createVerticalStrut(8)
        );

        bottomPanel.add(
                hintLabel
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        setContentPane(
                mainPanel
        );

        getRootPane().setDefaultButton(
                loginButton
        );
    }


    // =========================================================
    // LOGIN
    // =========================================================

    private void performLogin() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );


        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            usernameField.requestFocus();

            return;
        }


        if (password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            passwordField.requestFocus();

            return;
        }


        Admin admin =
                authenticationManager.authenticate(
                        username,
                        password
                );


        if (admin != null) {

            MainFrame mainFrame =
                    new MainFrame(admin);

            mainFrame.setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");

            passwordField.requestFocus();
        }
    }


    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                Theme.NORMAL_FONT
        );

        field.setForeground(
                Theme.TEXT
        );

        field.setBackground(
                Color.WHITE
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.STONE_BROWN
                        ),
                        BorderFactory.createEmptyBorder(
                                6,
                                8,
                                6,
                                8
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(
                        220,
                        34
                )
        );
    }


    // =========================================================
    // PRIMARY BUTTON STYLE
    // =========================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setFont(
                Theme.SUBTITLE_FONT
        );

        button.setForeground(
                Theme.ALMOND_CREAM
        );

        button.setBackground(
                Theme.JET_BLACK
        );

        button.setFocusPainted(
                false
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.STONE_BROWN
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                30,
                                8,
                                30
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }
}