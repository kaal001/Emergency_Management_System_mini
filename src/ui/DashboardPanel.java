package ui;

import enums.EmergencyStatus;
import enums.Priority;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Emergency;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DashboardPanel extends JPanel {

    // =========================================================
    // MANAGERS
    // =========================================================

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JLabel totalValueLabel;

    private JLabel pendingValueLabel;

    private JLabel criticalValueLabel;

    private JLabel inProgressValueLabel;

    private JLabel resolvedValueLabel;

    private JLabel unassignedValueLabel;

    private JLabel availableTeamsValueLabel;

    private JLabel busyTeamsValueLabel;

    private DefaultTableModel recentTableModel;

    private final ArrayList<Timer> animationTimers =
            new ArrayList<>();


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DashboardPanel(
            EmergencyManager emergencyManager,
            TeamManager teamManager
    ) {

        this.emergencyManager =
                emergencyManager;

        this.teamManager =
                teamManager;

        buildUI();

        refreshDashboard();
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        setLayout(
                new BorderLayout(
                        0,
                        18
                )
        );

        setBackground(
                Theme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        26,
                        22,
                        26
                )
        );


        // =====================================================
        // TITLE AREA
        // =====================================================

        JPanel titlePanel =
                new JPanel(
                        new BorderLayout()
                );

        titlePanel.setBackground(
                Theme.BACKGROUND
        );


        JPanel titleText =
                new JPanel();

        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        titleText.setOpaque(
                false
        );


        JLabel titleLabel =
                new JLabel(
                        "ADMIN DASHBOARD"
                );

        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.TEXT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "MONITOR EMERGENCY ACTIVITY // RESPONSE OPERATIONS"
                );

        subtitleLabel.setFont(
                Theme.SMALL_FONT
        );

        subtitleLabel.setForeground(
                Theme.MUTED_TEXT
        );


        titleText.add(
                titleLabel
        );

        titleText.add(
                Box.createVerticalStrut(
                        4
                )
        );

        titleText.add(
                subtitleLabel
        );


        titlePanel.add(
                titleText,
                BorderLayout.WEST
        );


        JButton refreshButton =
                new JButton(
                        "REFRESH DATA"
                );

        Theme.styleHeaderButton(
                refreshButton
        );

        refreshButton.addActionListener(
                e -> {

                    refreshDashboard();

                    refreshButton.setText(
                            "UPDATED ✓"
                    );

                    Timer timer =
                            new Timer(
                                    900,
                                    event ->
                                            refreshButton.setText(
                                                    "REFRESH DATA"
                                            )
                            );

                    timer.setRepeats(
                            false
                    );

                    timer.start();
                }
        );


        titlePanel.add(
                refreshButton,
                BorderLayout.EAST
        );


        add(
                titlePanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );

        centerPanel.setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // STATISTICS
        // =====================================================

        JPanel statisticsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                14,
                                14
                        )
                );

        statisticsPanel.setBackground(
                Theme.BACKGROUND
        );


        totalValueLabel =
                createValueLabel();

        pendingValueLabel =
                createValueLabel();

        criticalValueLabel =
                createValueLabel();

        inProgressValueLabel =
                createValueLabel();

        resolvedValueLabel =
                createValueLabel();

        unassignedValueLabel =
                createValueLabel();

        availableTeamsValueLabel =
                createValueLabel();

        busyTeamsValueLabel =
                createValueLabel();


        statisticsPanel.add(
                createStatCard(
                        "TOTAL EMERGENCIES",
                        totalValueLabel,
                        Theme.KHAKI_BEIGE
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "PENDING",
                        pendingValueLabel,
                        Theme.KHAKI_BEIGE
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "CRITICAL",
                        criticalValueLabel,
                        Theme.STONE_BROWN
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "IN PROGRESS",
                        inProgressValueLabel,
                        Theme.JET_BLACK
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "RESOLVED",
                        resolvedValueLabel,
                        Theme.STONE_BROWN
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "UNASSIGNED",
                        unassignedValueLabel,
                        Theme.KHAKI_BEIGE
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "AVAILABLE TEAMS",
                        availableTeamsValueLabel,
                        Theme.KHAKI_BEIGE
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "BUSY TEAMS",
                        busyTeamsValueLabel,
                        Theme.STONE_BROWN
                )
        );


        centerPanel.add(
                statisticsPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // RECENT EMERGENCIES
        // =====================================================

        JPanel recentPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );

        recentPanel.setBackground(
                Theme.BACKGROUND
        );


        JPanel recentHeader =
                new JPanel(
                        new BorderLayout()
                );

        recentHeader.setOpaque(
                false
        );


        JLabel recentTitle =
                new JLabel(
                        "RECENT EMERGENCIES"
                );

        recentTitle.setFont(
                Theme.SECTION_FONT
        );

        recentTitle.setForeground(
                Theme.TEXT
        );


        JLabel recentHint =
                new JLabel(
                        "Latest 10 records"
                );

        recentHint.setFont(
                Theme.SMALL_FONT
        );

        recentHint.setForeground(
                Theme.MUTED_TEXT
        );


        recentHeader.add(
                recentTitle,
                BorderLayout.WEST
        );

        recentHeader.add(
                recentHint,
                BorderLayout.EAST
        );


        recentPanel.add(
                recentHeader,
                BorderLayout.NORTH
        );


        String[] columns = {
                "Emergency ID",
                "Type",
                "Priority",
                "Location",
                "Status",
                "Assigned Team"
        };


        recentTableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };


        JTable recentTable =
                new JTable(
                        recentTableModel
                );


        styleTable(
                recentTable
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        recentTable
                );

        scrollPane.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        16,
                        1,
                        1
                )
        );

        scrollPane.getViewport()
                .setBackground(
                        Theme.WHITE
                );


        recentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        centerPanel.add(
                recentPanel,
                BorderLayout.CENTER
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // CREATE STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            JLabel valueLabel,
            Color accentColor
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                5
                        )
                ) {

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
                            // SUBTLE SHADOW
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
                                    3,
                                    4,
                                    getWidth() - 3,
                                    getHeight() - 4,
                                    18,
                                    18
                            );


                            // =================================================
                            // CARD BODY
                            // =================================================

                            g2.setColor(
                                    Theme.ALMOND_CREAM
                            );

                            g2.fillRoundRect(
                                    0,
                                    0,
                                    getWidth() - 4,
                                    getHeight() - 4,
                                    18,
                                    18
                            );

                        } finally {

                            g2.dispose();
                        }


                        super.paintComponent(
                                graphics
                        );
                    }
                };


        card.setOpaque(
                false
        );


        card.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        16,
                        14,
                        16
                )
        );


        // =====================================================
        // ACCENT BAR
        // =====================================================

        JPanel accentBar =
                new JPanel();

        accentBar.setBackground(
                accentColor
        );

        accentBar.setPreferredSize(
                new Dimension(
                        0,
                        4
                )
        );


        JPanel cardContent =
                new JPanel(
                        new BorderLayout(
                                0,
                                4
                        )
                );

        cardContent.setOpaque(
                false
        );


        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                Theme.SMALL_FONT
        );

        titleLabel.setForeground(
                Theme.MUTED_TEXT
        );


        cardContent.add(
                titleLabel,
                BorderLayout.NORTH
        );

        cardContent.add(
                valueLabel,
                BorderLayout.CENTER
        );


        card.add(
                accentBar,
                BorderLayout.NORTH
        );

        card.add(
                cardContent,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // VALUE LABEL
    // =========================================================

    private JLabel createValueLabel() {

        JLabel label =
                new JLabel(
                        "0",
                        SwingConstants.CENTER
                );

        label.setFont(
                Theme.STAT_FONT
        );

        label.setForeground(
                Theme.JET_BLACK
        );

        return label;
    }


    // =========================================================
    // REFRESH DASHBOARD
    // =========================================================

    public void refreshDashboard() {

        stopAnimations();


        int total =
                emergencyManager
                        .getTotalEmergencies();


        int pending =
                emergencyManager
                        .getCountByStatus(
                                EmergencyStatus.PENDING
                        );


        int critical =
                emergencyManager
                        .getCountByPriority(
                                Priority.CRITICAL
                        );


        int inProgress =
                emergencyManager
                        .getCountByStatus(
                                EmergencyStatus.IN_PROGRESS
                        );


        int resolved =
                emergencyManager
                        .getCountByStatus(
                                EmergencyStatus.RESOLVED
                        );


        int unassigned =
                0;


        for (
                Emergency emergency
                : emergencyManager.getAllEmergencies()
        ) {

            if (
                    emergency.getAssignedTeamId()
                            == null
                            || emergency
                            .getAssignedTeamId()
                            .trim()
                            .isEmpty()
            ) {

                unassigned++;
            }
        }


        int availableTeams =
                teamManager
                        .getAvailableTeamCount();


        int busyTeams =
                teamManager
                        .getBusyTeamCount();


        // =====================================================
        // ANIMATED VALUES
        // =====================================================

        animateValue(
                totalValueLabel,
                total
        );

        animateValue(
                pendingValueLabel,
                pending
        );

        animateValue(
                criticalValueLabel,
                critical
        );

        animateValue(
                inProgressValueLabel,
                inProgress
        );

        animateValue(
                resolvedValueLabel,
                resolved
        );

        animateValue(
                unassignedValueLabel,
                unassigned
        );

        animateValue(
                availableTeamsValueLabel,
                availableTeams
        );

        animateValue(
                busyTeamsValueLabel,
                busyTeams
        );


        loadRecentEmergencies();
    }


    // =========================================================
    // ANIMATED COUNTER
    // =========================================================

    private void animateValue(
            JLabel label,
            int target
    ) {

        final int[] current =
                {0};

        final int duration =
                450;

        final int interval =
                20;

        final int steps =
                duration / interval;


        Timer timer =
                new Timer(
                        interval,
                        null
                );


        timer.addActionListener(
                e -> {

                    current[0]++;


                    double progress =
                            Math.min(
                                    1.0,
                                    (double) current[0]
                                            / steps
                            );


                    // =================================================
                    // SMOOTH EASING
                    // =================================================

                    double eased =
                            1.0
                                    - Math.pow(
                                    1.0 - progress,
                                    3
                            );


                    int value =
                            (int)
                                    Math.round(
                                            target * eased
                                    );


                    label.setText(
                            String.valueOf(
                                    value
                            )
                    );


                    if (
                            current[0]
                                    >= steps
                    ) {

                        label.setText(
                                String.valueOf(
                                        target
                                )
                        );

                        timer.stop();
                    }
                }
        );


        animationTimers.add(
                timer
        );


        timer.start();
    }


    // =========================================================
    // STOP ACTIVE ANIMATIONS
    // =========================================================

    private void stopAnimations() {

        for (
                Timer timer
                : animationTimers
        ) {

            if (
                    timer != null
                            && timer.isRunning()
            ) {

                timer.stop();
            }
        }


        animationTimers.clear();
    }


    // =========================================================
    // LOAD RECENT EMERGENCIES
    // =========================================================

    private void loadRecentEmergencies() {

        recentTableModel.setRowCount(
                0
        );


        ArrayList<Emergency> emergencies =
                emergencyManager
                        .getAllEmergencies();


        int start =
                Math.max(
                        0,
                        emergencies.size() - 10
                );


        for (
                int i = start;
                i < emergencies.size();
                i++
        ) {

            Emergency emergency =
                    emergencies.get(i);


            String assignedTeam =
                    emergency.getAssignedTeamId();


            if (
                    assignedTeam == null
                            || assignedTeam
                            .trim()
                            .isEmpty()
            ) {

                assignedTeam =
                        "-";
            }


            recentTableModel.addRow(
                    new Object[]{
                            emergency.getEmergencyId(),

                            formatType(
                                    emergency
                                            .getType()
                            ),

                            formatPriority(
                                    emergency
                                            .getPriority()
                            ),

                            emergency.getLocation(),

                            formatStatus(
                                    emergency
                                            .getStatus()
                            ),

                            assignedTeam
                    }
            );
        }
    }


    // =========================================================
    // FORMAT TYPE
    // =========================================================

    private String formatType(
            Object type
    ) {

        if (
                type == null
        ) {

            return "-";
        }


        String text =
                type.toString()
                        .replace(
                                "_",
                                " "
                        );


        String[] parts =
                text.toLowerCase()
                        .split(
                                " "
                        );


        StringBuilder result =
                new StringBuilder();


        for (
                String part
                : parts
        ) {

            if (
                    part.isEmpty()
            ) {

                continue;
            }


            result.append(
                    Character.toUpperCase(
                            part.charAt(0)
                    )
            );


            if (
                    part.length() > 1
            ) {

                result.append(
                        part.substring(
                                1
                        )
                );
            }


            result.append(
                    " "
            );
        }


        return result.toString()
                .trim();
    }


    // =========================================================
    // FORMAT PRIORITY
    // =========================================================

    private String formatPriority(
            Object priority
    ) {

        return formatEnumValue(
                priority
        );
    }


    // =========================================================
    // FORMAT STATUS
    // =========================================================

    private String formatStatus(
            Object status
    ) {

        return formatEnumValue(
                status
        );
    }


    // =========================================================
    // GENERIC ENUM FORMAT
    // =========================================================

    private String formatEnumValue(
            Object value
    ) {

        if (
                value == null
        ) {

            return "-";
        }


        String text =
                value.toString()
                        .replace(
                                "_",
                                " "
                        )
                        .toLowerCase();


        String[] parts =
                text.split(
                        " "
                );


        StringBuilder result =
                new StringBuilder();


        for (
                String part
                : parts
        ) {

            if (
                    part.isEmpty()
            ) {

                continue;
            }


            result.append(
                    Character.toUpperCase(
                            part.charAt(0)
                    )
            );


            if (
                    part.length() > 1
            ) {

                result.append(
                        part.substring(
                                1
                        )
                );
            }


            result.append(
                    " "
            );
        }


        return result.toString()
                .trim();
    }


    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable(
            JTable table
    ) {

        Theme.styleTable(
                table
        );


        if (
                table.getColumnCount()
                        > 2
        ) {

            table.getColumnModel()
                    .getColumn(2)
                    .setCellRenderer(
                            new PriorityRenderer()
                    );
        }


        if (
                table.getColumnCount()
                        > 4
        ) {

            table.getColumnModel()
                    .getColumn(4)
                    .setCellRenderer(
                            new StatusRenderer()
                    );
        }


        if (
                table.getColumnCount()
                        > 1
        ) {

            table.getColumnModel()
                    .getColumn(1)
                    .setCellRenderer(
                            new DefaultTableCellRenderer() {

                                @Override
                                public Component
                                getTableCellRendererComponent(
                                        JTable table,
                                        Object value,
                                        boolean selected,
                                        boolean focused,
                                        int row,
                                        int column
                                ) {

                                    Component component =
                                            super
                                                    .getTableCellRendererComponent(
                                                            table,
                                                            value,
                                                            selected,
                                                            focused,
                                                            row,
                                                            column
                                                    );


                                    setHorizontalAlignment(
                                            SwingConstants.LEFT
                                    );


                                    if (
                                            !selected
                                    ) {

                                        setBackground(
                                                row % 2 == 0
                                                        ? Theme.WHITE
                                                        : Theme.ALMOND_CREAM
                                        );

                                        setForeground(
                                                Theme.TEXT
                                        );
                                    }


                                    return component;
                                }
                            }
                    );
        }
    }


    // =========================================================
    // PRIORITY RENDERER
    // =========================================================

    private static class PriorityRenderer
            extends DefaultTableCellRenderer {


        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column
        ) {

            Component component =
                    super
                            .getTableCellRendererComponent(
                                    table,
                                    value,
                                    selected,
                                    focused,
                                    row,
                                    column
                            );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            if (
                    !selected
            ) {

                String text =
                        value == null
                                ? ""
                                : value.toString()
                                .toUpperCase();


                if (
                        text.contains(
                                "CRITICAL"
                        )
                ) {

                    setBackground(
                            Theme.STONE_BROWN
                    );

                    setForeground(
                            Theme.LIGHT_TEXT
                    );


                } else if (
                        text.contains(
                                "HIGH"
                        )
                ) {

                    setBackground(
                            Theme.KHAKI_BEIGE
                    );

                    setForeground(
                            Theme.BLACK
                    );


                } else {

                    setBackground(
                            row % 2 == 0
                                    ? Theme.WHITE
                                    : Theme.ALMOND_CREAM
                    );

                    setForeground(
                            Theme.TEXT
                    );
                }
            }


            return component;
        }
    }


    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private static class StatusRenderer
            extends DefaultTableCellRenderer {


        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column
        ) {

            Component component =
                    super
                            .getTableCellRendererComponent(
                                    table,
                                    value,
                                    selected,
                                    focused,
                                    row,
                                    column
                            );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            if (
                    !selected
            ) {

                String text =
                        value == null
                                ? ""
                                : value.toString()
                                .toUpperCase();


                if (
                        text.contains(
                                "IN PROGRESS"
                        )
                ) {

                    setBackground(
                            Theme.JET_BLACK
                    );

                    setForeground(
                            Theme.LIGHT_TEXT
                    );


                } else if (
                        text.contains(
                                "ASSIGNED"
                        )
                ) {

                    setBackground(
                            Theme.KHAKI_BEIGE
                    );

                    setForeground(
                            Theme.BLACK
                    );


                } else if (
                        text.contains(
                                "RESOLVED"
                        )
                ) {

                    setBackground(
                            Theme.STONE_BROWN
                    );

                    setForeground(
                            Theme.LIGHT_TEXT
                    );


                } else {

                    setBackground(
                            row % 2 == 0
                                    ? Theme.WHITE
                                    : Theme.ALMOND_CREAM
                    );

                    setForeground(
                            Theme.TEXT
                    );
                }
            }


            return component;
        }
    }


    // =========================================================
    // CLEANUP
    // =========================================================

    @Override
    public void removeNotify() {

        stopAnimations();

        super.removeNotify();
    }
}