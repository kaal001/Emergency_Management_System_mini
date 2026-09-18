package ui;

import javax.swing.*;
import java.awt.*;

public final class Theme {

    // =========================================================
    // COLOR PALETTE
    // =========================================================

    public static final Color BLACK =
            new Color(0x0A0908);

    public static final Color JET_BLACK =
            new Color(0x22333B);

    public static final Color ALMOND_CREAM =
            new Color(0xEAE0D5);

    public static final Color KHAKI_BEIGE =
            new Color(0xC6AC8F);

    public static final Color STONE_BROWN =
            new Color(0x5E503F);

    public static final Color WHITE =
            Color.WHITE;


    // =========================================================
    // SEMANTIC COLORS
    // =========================================================

    public static final Color BACKGROUND =
            ALMOND_CREAM;

    public static final Color SIDEBAR =
            JET_BLACK;

    public static final Color HEADER =
            BLACK;

    public static final Color TEXT =
            BLACK;

    public static final Color LIGHT_TEXT =
            ALMOND_CREAM;

    public static final Color MUTED_TEXT =
            STONE_BROWN;

    public static final Color ACCENT =
            KHAKI_BEIGE;

    public static final Color BORDER =
            STONE_BROWN;


    // =========================================================
    // APPLICATION INFORMATION
    // =========================================================

    public static final String APP_TITLE =
            "Emergency Management System";

    public static final String APP_SHORT_TITLE =
            "EMS";

    public static final String APP_VERSION =
            "v1.0";


    // =========================================================
    // DIMENSIONS
    // =========================================================

    public static final int SIDEBAR_WIDTH =
            235;

    public static final int HEADER_HEIGHT =
            78;


    // =========================================================
    // FONTS
    // =========================================================

    public static final Font TITLE_FONT =
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    26
            );

    public static final Font PAGE_TITLE_FONT =
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    24
            );

    public static final Font SECTION_FONT =
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    16
            );

    public static final Font SUBTITLE_FONT =
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            );

    public static final Font NORMAL_FONT =
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    13
            );

    public static final Font SMALL_FONT =
            new Font(
                    "SansSerif",
                    Font.PLAIN,
                    11
            );

    public static final Font BUTTON_FONT =
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    13
            );


    // =========================================================
    // ADMIN MENU BUTTON
    // =========================================================

    public static void styleAdminMenuButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                BLACK
        );

        button.setBackground(
                ACCENT
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setContentAreaFilled(
                true
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        button.setPreferredSize(
                new Dimension(
                        200,
                        40
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHoverEffect(
                button,
                ACCENT,
                ALMOND_CREAM
        );
    }


    // =========================================================
    // LOGOUT BUTTON
    // =========================================================

    public static void styleLogoutButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                LIGHT_TEXT
        );

        button.setBackground(
                BLACK
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setContentAreaFilled(
                true
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        button.setPreferredSize(
                new Dimension(
                        200,
                        40
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHoverEffect(
                button,
                BLACK,
                STONE_BROWN
        );
    }


    // =========================================================
    // HEADER BUTTON
    // =========================================================

    public static void styleHeaderButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                BLACK
        );

        button.setBackground(
                ACCENT
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setContentAreaFilled(
                true
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        9,
                        20,
                        9,
                        20
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHoverEffect(
                button,
                ACCENT,
                ALMOND_CREAM
        );
    }


    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    public static void stylePrimaryButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                LIGHT_TEXT
        );

        button.setBackground(
                JET_BLACK
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setContentAreaFilled(
                true
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                STONE_BROWN
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                18,
                                8,
                                18
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHoverEffect(
                button,
                JET_BLACK,
                STONE_BROWN
        );
    }


    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    public static void styleSecondaryButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                BLACK
        );

        button.setBackground(
                ALMOND_CREAM
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                STONE_BROWN
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                18,
                                8,
                                18
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHoverEffect(
                button,
                ALMOND_CREAM,
                KHAKI_BEIGE
        );
    }


    // =========================================================
    // DANGER BUTTON
    // =========================================================

    public static void styleDangerButton(
            JButton button
    ) {

        button.setFont(
                BUTTON_FONT
        );

        button.setForeground(
                LIGHT_TEXT
        );

        button.setBackground(
                STONE_BROWN
        );

        button.setFocusPainted(
                false
        );

        button.setOpaque(
                true
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        18,
                        8,
                        18
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addHoverEffect(
                button,
                STONE_BROWN,
                BLACK
        );
    }


    // =========================================================
    // HOVER EFFECT
    // =========================================================

    private static void addHoverEffect(
            JButton button,
            Color normalColor,
            Color hoverColor
    ) {

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                hoverColor
                        );
                    }


                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                normalColor
                        );
                    }
                }
        );
    }


    // =========================================================
    // PREVENT OBJECT CREATION
    // =========================================================

    private Theme() {
    }
}