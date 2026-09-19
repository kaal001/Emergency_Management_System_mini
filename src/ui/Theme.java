package ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class Theme {

    // =========================================================
    // COLOR PALETTE
    // Existing project palette - intentionally preserved
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
            84;

    public static final int CORNER_RADIUS =
            18;


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

    public static final Font STAT_FONT =
            new Font(
                    "SansSerif",
                    Font.BOLD,
                    28
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
                LIGHT_TEXT
        );

        button.setBackground(
                SIDEBAR
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
                createRoundedBorder(
                        new Color(
                                KHAKI_BEIGE.getRed(),
                                KHAKI_BEIGE.getGreen(),
                                KHAKI_BEIGE.getBlue(),
                                70
                        ),
                        14,
                        1,
                        8
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        button.setPreferredSize(
                new Dimension(
                        205,
                        44
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.putClientProperty(
                "emsActive",
                false
        );

        addHoverEffect(
                button,
                SIDEBAR,
                STONE_BROWN
        );
    }


    // =========================================================
    // ACTIVE ADMIN MENU BUTTON
    // =========================================================

    public static void setActiveMenuButton(
            JButton button,
            boolean active
    ) {

        button.putClientProperty(
                "emsActive",
                active
        );

        if (active) {

            button.setBackground(
                    STONE_BROWN
            );

            button.setForeground(
                    LIGHT_TEXT
            );

        } else {

            button.setBackground(
                    SIDEBAR
            );

            button.setForeground(
                    LIGHT_TEXT
            );
        }
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
                createRoundedBorder(
                        STONE_BROWN,
                        14,
                        1,
                        8
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        button.setPreferredSize(
                new Dimension(
                        205,
                        44
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
                createRoundedBorder(
                        ALMOND_CREAM,
                        12,
                        1,
                        7
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
                createRoundedBorder(
                        STONE_BROWN,
                        12,
                        1,
                        8
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

        button.setContentAreaFilled(
                true
        );

        button.setBorder(
                createRoundedBorder(
                        STONE_BROWN,
                        12,
                        1,
                        8
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

        button.setContentAreaFilled(
                true
        );

        button.setBorder(
                createRoundedBorder(
                        BLACK,
                        12,
                        1,
                        8
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
    // ROUNDED BORDER
    // =========================================================

    public static javax.swing.border.Border createRoundedBorder(
            Color color,
            int radius,
            int thickness,
            int padding
    ) {

        return BorderFactory.createCompoundBorder(
                new RoundedLineBorder(
                        color,
                        radius,
                        thickness
                ),
                BorderFactory.createEmptyBorder(
                        padding,
                        padding,
                        padding,
                        padding
                )
        );
    }


    // =========================================================
    // ROUNDED BORDER IMPLEMENTATION
    // =========================================================

    private static class RoundedLineBorder
            extends AbstractBorder {

        private final Color color;
        private final int radius;
        private final int thickness;

        public RoundedLineBorder(
                Color color,
                int radius,
                int thickness
        ) {

            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(
                Component component,
                Graphics graphics,
                int x,
                int y,
                int width,
                int height
        ) {

            Graphics2D g2 =
                    (Graphics2D) graphics.create();

            try {

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(
                        color
                );

                g2.setStroke(
                        new BasicStroke(
                                thickness
                        )
                );

                int offset =
                        thickness / 2;

                g2.drawRoundRect(
                        x + offset,
                        y + offset,
                        width - thickness,
                        height - thickness,
                        radius,
                        radius
                );

            } finally {

                g2.dispose();
            }
        }

        @Override
        public Insets getBorderInsets(
                Component component
        ) {

            return new Insets(
                    thickness + 2,
                    thickness + 2,
                    thickness + 2,
                    thickness + 2
            );
        }

        @Override
        public boolean isBorderOpaque() {

            return false;
        }
    }


    // =========================================================
    // ANIMATED HOVER EFFECT
    // =========================================================

    private static void addHoverEffect(
            JButton button,
            Color normalColor,
            Color hoverColor
    ) {

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        boolean active =
                                Boolean.TRUE.equals(
                                        button.getClientProperty(
                                                "emsActive"
                                        )
                                );

                        Color target =
                                active
                                        ? KHAKI_BEIGE
                                        : hoverColor;

                        animateButtonColor(
                                button,
                                target
                        );
                    }


                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        boolean active =
                                Boolean.TRUE.equals(
                                        button.getClientProperty(
                                                "emsActive"
                                        )
                                );

                        Color target =
                                active
                                        ? STONE_BROWN
                                        : normalColor;

                        animateButtonColor(
                                button,
                                target
                        );
                    }
                }
        );
    }


    // =========================================================
    // BUTTON COLOR ANIMATION
    // =========================================================

    private static void animateButtonColor(
            JButton button,
            Color target
    ) {

        Object existing =
                button.getClientProperty(
                        "emsHoverTimer"
                );

        if (existing instanceof Timer) {

            ((Timer) existing).stop();
        }

        Color start =
                button.getBackground();

        final int steps =
                8;

        final int[] step =
                {0};

        Timer timer =
                new Timer(
                        18,
                        null
                );

        timer.addActionListener(
                e -> {

                    step[0]++;

                    float progress =
                            Math.min(
                                    1.0f,
                                    (float) step[0]
                                            / steps
                            );

                    Color current =
                            interpolate(
                                    start,
                                    target,
                                    progress
                            );

                    button.setBackground(
                            current
                    );

                    if (step[0] >= steps) {

                        button.setBackground(
                                target
                        );

                        timer.stop();
                    }
                }
        );

        button.putClientProperty(
                "emsHoverTimer",
                timer
        );

        timer.start();
    }


    // =========================================================
    // COLOR INTERPOLATION
    // =========================================================

    private static Color interpolate(
            Color start,
            Color end,
            float progress
    ) {

        int red =
                (int) (
                        start.getRed()
                                + (
                                end.getRed()
                                        - start.getRed()
                        ) * progress
                );

        int green =
                (int) (
                        start.getGreen()
                                + (
                                end.getGreen()
                                        - start.getGreen()
                        ) * progress
                );

        int blue =
                (int) (
                        start.getBlue()
                                + (
                                end.getBlue()
                                        - start.getBlue()
                        ) * progress
                );

        return new Color(
                red,
                green,
                blue
        );
    }


    // =========================================================
    // TABLE STYLE
    // Reusable across future UI panels
    // =========================================================

    public static void styleTable(
            JTable table
    ) {

        table.setRowHeight(
                32
        );

        table.setFont(
                NORMAL_FONT
        );

        table.setForeground(
                TEXT
        );

        table.setBackground(
                WHITE
        );

        table.setGridColor(
                new Color(
                        KHAKI_BEIGE.getRed(),
                        KHAKI_BEIGE.getGreen(),
                        KHAKI_BEIGE.getBlue(),
                        75
                )
        );

        table.setSelectionBackground(
                STONE_BROWN
        );

        table.setSelectionForeground(
                LIGHT_TEXT
        );

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.setFillsViewportHeight(
                true
        );

        table.setShowVerticalLines(
                false
        );

        table.setShowHorizontalLines(
                true
        );

        table.setIntercellSpacing(
                new Dimension(
                        0,
                        1
                )
        );

        JTableHeaderStyle(
                table
        );
    }


    // =========================================================
    // TABLE HEADER STYLE
    // =========================================================

    private static void JTableHeaderStyle(
            JTable table
    ) {

        table.getTableHeader()
                .setFont(
                        SUBTITLE_FONT
                );

        table.getTableHeader()
                .setBackground(
                        JET_BLACK
                );

        table.getTableHeader()
                .setForeground(
                        LIGHT_TEXT
                );

        table.getTableHeader()
                .setReorderingAllowed(
                        false
                );

        table.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                38
                        )
                );
    }


    // =========================================================
    // PREVENT OBJECT CREATION
    // =========================================================

    private Theme() {
    }
}
