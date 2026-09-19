package ui;

import manager.AuthenticationManager;
import model.Admin;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    // =========================================================
    // AUTHENTICATION
    // =========================================================

    private final AuthenticationManager authenticationManager;


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTextField usernameField;

    private JPasswordField passwordField;

    private JButton loginButton;

    private JLabel systemDot;

    private Timer pulseTimer;


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

        startPulseAnimation();
    }


    // =========================================================
    // FRAME SETUP
    // =========================================================

    private void initializeFrame() {

        setTitle(
                Theme.APP_TITLE + " - Login"
        );

        setSize(
                470,
                400
        );

        setResizable(
                false
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(
                null
        );

        getContentPane().setBackground(
                Theme.BACKGROUND
        );
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel background =
                new JPanel(
                        new GridBagLayout()
                );

        background.setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // LOGIN CARD
        // =====================================================

        JPanel card =
                new RoundedCard();

        card.setLayout(
                new BorderLayout(
                        0,
                        16
                )
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        30,
                        24,
                        30
                )
        );

        card.setPreferredSize(
                new Dimension(
                        410,
                        340
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setOpaque(
                false
        );


        JLabel systemLabel =
                new JLabel(
                        "EMS // COMMAND CENTER"
                );

        systemLabel.setFont(
                Theme.SMALL_FONT
        );

        systemLabel.setForeground(
                Theme.STONE_BROWN
        );

        systemLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel titleLabel =
                new JLabel(
                        "ADMINISTRATOR LOGIN"
                );

        titleLabel.setFont(
                Theme.TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.JET_BLACK
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "Secure access to emergency operations"
                );

        subtitleLabel.setFont(
                Theme.SMALL_FONT
        );

        subtitleLabel.setForeground(
                Theme.MUTED_TEXT
        );

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        // =====================================================
        // SYSTEM STATUS
        // =====================================================

        JPanel systemRow =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                5,
                                0
                        )
                );

        systemRow.setOpaque(
                false
        );


        systemDot =
                new JLabel(
                        "●"
                );

        systemDot.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        systemDot.setForeground(
                Theme.ACCENT
        );


        JLabel onlineLabel =
                new JLabel(
                        "SYSTEM READY"
                );

        onlineLabel.setFont(
                Theme.SMALL_FONT
        );

        onlineLabel.setForeground(
                Theme.STONE_BROWN
        );


        systemRow.add(
                systemDot
        );

        systemRow.add(
                onlineLabel
        );


        // =====================================================
        // BUILD HEADER
        // =====================================================

        header.add(
                systemLabel
        );

        header.add(
                Box.createVerticalStrut(
                        5
                )
        );

        header.add(
                titleLabel
        );

        header.add(
                Box.createVerticalStrut(
                        4
                )
        );

        header.add(
                subtitleLabel
        );

        header.add(
                Box.createVerticalStrut(
                        7
                )
        );

        header.add(
                systemRow
        );


        card.add(
                header,
                BorderLayout.NORTH
        );


        // =====================================================
        // FORM
        // =====================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(
                false
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        7,
                        4,
                        7,
                        4
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx =
                1.0;


        JLabel usernameLabel =
                createFormLabel(
                        "USERNAME"
                );


        usernameField =
                new JTextField();

        styleTextField(
                usernameField
        );


        JLabel passwordLabel =
                createFormLabel(
                        "PASSWORD"
                );


        passwordField =
                new JPasswordField();

        styleTextField(
                passwordField
        );


        // =====================================================
        // USERNAME
        // =====================================================

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


        // =====================================================
        // PASSWORD
        // =====================================================

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


        card.add(
                formPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM ACTION
        // =====================================================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );

        bottomPanel.setOpaque(
                false
        );


        loginButton =
                new JButton(
                        "LOGIN"
                );

        Theme.stylePrimaryButton(
                loginButton
        );

        loginButton.setFont(
                Theme.BUTTON_FONT
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setPreferredSize(
                new Dimension(
                        180,
                        42
                )
        );

        loginButton.setMaximumSize(
                new Dimension(
                        280,
                        42
                )
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
                Theme.MUTED_TEXT
        );

        hintLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        bottomPanel.add(
                loginButton
        );

        bottomPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );

        bottomPanel.add(
                hintLabel
        );


        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // CENTER CARD
        // =====================================================

        background.add(
                card
        );


        setContentPane(
                background
        );


        // =====================================================
        // ENTER = LOGIN
        // =====================================================

        getRootPane().setDefaultButton(
                loginButton
        );
    }


    // =========================================================
    // FORM LABEL
    // =========================================================

    private JLabel createFormLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                Theme.SMALL_FONT
        );

        label.setForeground(
                Theme.TEXT
        );

        return label;
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
                Theme.WHITE
        );

        field.setCaretColor(
                Theme.STONE_BROWN
        );

        field.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        12,
                        1,
                        7
                )
        );

        field.setPreferredSize(
                new Dimension(
                        230,
                        38
                )
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


        // =====================================================
        // USERNAME VALIDATION
        // =====================================================

        if (
                username.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            usernameField.requestFocus();

            return;
        }


        // =====================================================
        // PASSWORD VALIDATION
        // =====================================================

        if (
                password.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            passwordField.requestFocus();

            return;
        }


        // =====================================================
        // AUTHENTICATION
        // =====================================================

        Admin admin =
                authenticationManager.authenticate(
                        username,
                        password
                );


        // =====================================================
        // SUCCESS
        // =====================================================

        if (
                admin != null
        ) {

            stopPulseAnimation();


            loginButton.setText(
                    "ACCESS GRANTED ✓"
            );


            Timer timer =
                    new Timer(
                            550,
                            e -> {

                                MainFrame mainFrame =
                                        new MainFrame(
                                                admin
                                        );

                                mainFrame.setVisible(
                                        true
                                );

                                dispose();
                            }
                    );


            timer.setRepeats(
                    false
            );


            timer.start();


        } else {

            // =================================================
            // FAILURE
            // =================================================

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );


            passwordField.setText(
                    ""
            );


            passwordField.requestFocus();
        }
    }


    // =========================================================
    // SYSTEM PULSE ANIMATION
    // =========================================================

    private void startPulseAnimation() {

        pulseTimer =
                new Timer(
                        800,
                        e -> {

                            if (
                                    systemDot
                                            .getForeground()
                                            .equals(
                                                    Theme.ACCENT
                                            )
                            ) {

                                systemDot.setForeground(
                                        Theme.KHAKI_BEIGE
                                                .brighter()
                                );

                            } else {

                                systemDot.setForeground(
                                        Theme.ACCENT
                                );
                            }
                        }
                );


        pulseTimer.start();
    }


    // =========================================================
    // STOP ANIMATION
    // =========================================================

    private void stopPulseAnimation() {

        if (
                pulseTimer != null
        ) {

            pulseTimer.stop();
        }
    }


    // =========================================================
    // DISPOSE
    // =========================================================

    @Override
    public void dispose() {

        stopPulseAnimation();

        super.dispose();
    }


    // =========================================================
    // ROUNDED LOGIN CARD
    // =========================================================

    private static class RoundedCard
            extends JPanel {


        public RoundedCard() {

            setOpaque(
                    false
            );

            setBorder(
                    BorderFactory.createEmptyBorder(
                            1,
                            1,
                            1,
                            1
                    )
            );
        }


        @Override
        protected void paintComponent(
                Graphics graphics
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            graphics.create();


            try {

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );


                // =================================================
                // SHADOW
                // =================================================

                g2.setColor(
                        new Color(
                                Theme.BLACK.getRed(),
                                Theme.BLACK.getGreen(),
                                Theme.BLACK.getBlue(),
                                18
                        )
                );


                g2.fillRoundRect(
                        4,
                        5,
                        getWidth() - 5,
                        getHeight() - 5,
                        22,
                        22
                );


                // =================================================
                // CARD
                // =================================================

                g2.setColor(
                        Theme.ALMOND_CREAM
                );


                g2.fillRoundRect(
                        0,
                        0,
                        getWidth() - 5,
                        getHeight() - 5,
                        22,
                        22
                );


                // =================================================
                // BORDER
                // =================================================

                g2.setColor(
                        Theme.KHAKI_BEIGE
                );


                g2.setStroke(
                        new BasicStroke(
                                1.2f
                        )
                );


                g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 5,
                        getHeight() - 5,
                        22,
                        22
                );


            } finally {

                g2.dispose();
            }


            super.paintComponent(
                    graphics
            );
        }
    }
}