package ui;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import enums.TeamType;
import manager.EmergencyManager;
import manager.TeamManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportPanel extends JPanel {

    // =========================================================
    // MANAGERS
    // =========================================================

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;


    // =========================================================
    // SUMMARY LABELS
    // =========================================================

    private JLabel totalValue;

    private JLabel criticalValue;

    private JLabel pendingValue;

    private JLabel assignedValue;

    private JLabel inProgressValue;

    private JLabel resolvedValue;

    private JLabel availableTeamValue;

    private JLabel busyTeamValue;


    // =========================================================
    // TABLE MODELS
    // =========================================================

    private DefaultTableModel typeModel;

    private DefaultTableModel priorityModel;

    private DefaultTableModel statusModel;

    private DefaultTableModel teamModel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ReportPanel(
            EmergencyManager emergencyManager,
            TeamManager teamManager
    ) {

        this.emergencyManager =
                emergencyManager;

        this.teamManager =
                teamManager;

        buildUI();

        refreshReports();
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
                        20,
                        22,
                        20,
                        22
                )
        );


        add(
                createHeader(),
                BorderLayout.NORTH
        );


        add(
                createReportArea(),
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                Theme.BACKGROUND
        );


        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(
                Theme.BACKGROUND
        );


        JLabel title =
                new JLabel(
                        "REPORTS & STATISTICS"
                );

        title.setFont(
                Theme.PAGE_TITLE_FONT
        );

        title.setForeground(
                Theme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Operational overview of emergencies and response teams."
                );

        subtitle.setFont(
                Theme.NORMAL_FONT
        );

        subtitle.setForeground(
                Theme.MUTED_TEXT
        );


        titlePanel.add(
                title
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        4
                )
        );

        titlePanel.add(
                subtitle
        );


        panel.add(
                titlePanel,
                BorderLayout.WEST
        );


        JButton refreshButton =
                new JButton(
                        "REFRESH"
                );


        Theme.styleHeaderButton(
                refreshButton
        );


        refreshButton.addActionListener(
                e -> refreshReports()
        );


        panel.add(
                refreshButton,
                BorderLayout.EAST
        );


        return panel;
    }


    // =========================================================
    // REPORT AREA
    // =========================================================

    private JPanel createReportArea() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );


        mainPanel.setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // SUMMARY CARDS
        // =====================================================

        JPanel summaryPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                4,
                                12,
                                12
                        )
                );


        summaryPanel.setBackground(
                Theme.BACKGROUND
        );


        totalValue =
                createValueLabel();

        criticalValue =
                createValueLabel();

        pendingValue =
                createValueLabel();

        assignedValue =
                createValueLabel();

        inProgressValue =
                createValueLabel();

        resolvedValue =
                createValueLabel();

        availableTeamValue =
                createValueLabel();

        busyTeamValue =
                createValueLabel();


        summaryPanel.add(
                createSummaryCard(
                        "TOTAL EMERGENCIES",
                        totalValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "CRITICAL",
                        criticalValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "PENDING",
                        pendingValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "ASSIGNED",
                        assignedValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "IN PROGRESS",
                        inProgressValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "RESOLVED",
                        resolvedValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "AVAILABLE TEAMS",
                        availableTeamValue
                )
        );


        summaryPanel.add(
                createSummaryCard(
                        "BUSY TEAMS",
                        busyTeamValue
                )
        );


        mainPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLES
        // =====================================================

        JPanel tablesPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                2,
                                14,
                                14
                        )
                );


        tablesPanel.setBackground(
                Theme.BACKGROUND
        );


        tablesPanel.add(
                createTypeTable()
        );


        tablesPanel.add(
                createPriorityTable()
        );


        tablesPanel.add(
                createStatusTable()
        );


        tablesPanel.add(
                createTeamTable()
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        tablesPanel
                );


        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        14
                );


        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        return mainPanel;
    }


    // =========================================================
    // SUMMARY CARD
    // =========================================================

    private JPanel createSummaryCard(
            String title,
            JLabel value
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                5
                        )
                );


        card.setBackground(
                Theme.ALMOND_CREAM
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                3,
                                0,
                                0,
                                0,
                                Theme.KHAKI_BEIGE
                        ),
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        Theme.KHAKI_BEIGE
                                ),
                                BorderFactory.createEmptyBorder(
                                        10,
                                        10,
                                        10,
                                        10
                                )
                        )
                )
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


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );


        card.add(
                value,
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
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );


        label.setForeground(
                Theme.JET_BLACK
        );


        return label;
    }


    // =========================================================
    // TYPE TABLE
    // =========================================================

    private JPanel createTypeTable() {

        typeModel =
                createTableModel(
                        "Emergency Type",
                        "Count"
                );


        JTable table =
                new JTable(
                        typeModel
                );


        return createTableCard(
                "EMERGENCY TYPE SUMMARY",
                table
        );
    }


    // =========================================================
    // PRIORITY TABLE
    // =========================================================

    private JPanel createPriorityTable() {

        priorityModel =
                createTableModel(
                        "Priority",
                        "Count"
                );


        JTable table =
                new JTable(
                        priorityModel
                );


        return createTableCard(
                "PRIORITY SUMMARY",
                table
        );
    }


    // =========================================================
    // STATUS TABLE
    // =========================================================

    private JPanel createStatusTable() {

        statusModel =
                createTableModel(
                        "Status",
                        "Count"
                );


        JTable table =
                new JTable(
                        statusModel
                );


        return createTableCard(
                "STATUS SUMMARY",
                table
        );
    }


    // =========================================================
    // TEAM TABLE
    // =========================================================

    private JPanel createTeamTable() {

        teamModel =
                new DefaultTableModel(
                        new String[]{
                                "Team Type",
                                "Total",
                                "Available",
                                "Busy"
                        },
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


        JTable table =
                new JTable(
                        teamModel
                );


        return createTableCard(
                "RESPONSE TEAM SUMMARY",
                table
        );
    }


    // =========================================================
    // TABLE MODEL
    // =========================================================

    private DefaultTableModel createTableModel(
            String firstColumn,
            String secondColumn
    ) {

        return new DefaultTableModel(
                new String[]{
                        firstColumn,
                        secondColumn
                },
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
    }


    // =========================================================
    // TABLE CARD
    // =========================================================

    private JPanel createTableCard(
            String title,
            JTable table
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                8
                        )
                );


        card.setBackground(
                Theme.ALMOND_CREAM
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.KHAKI_BEIGE
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );


        titleLabel.setFont(
                Theme.SUBTITLE_FONT
        );


        titleLabel.setForeground(
                Theme.TEXT
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );


        styleTable(
                table
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        table
                );


        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        Theme.STONE_BROWN
                )
        );


        card.add(
                scrollPane,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // REFRESH REPORTS
    // =========================================================

    public void refreshReports() {

        // -----------------------------------------------------
        // Main counts
        // -----------------------------------------------------

        int total =
                emergencyManager
                        .getTotalEmergencies();


        int critical =
                emergencyManager
                        .getCountByPriority(
                                Priority.CRITICAL
                        );


        int pending =
                emergencyManager
                        .getCountByStatus(
                                EmergencyStatus.PENDING
                        );


        int assigned =
                emergencyManager
                        .getCountByStatus(
                                EmergencyStatus.ASSIGNED
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


        int availableTeams =
                teamManager
                        .getAvailableTeamCount();


        int busyTeams =
                teamManager
                        .getBusyTeamCount();


        totalValue.setText(
                String.valueOf(total)
        );


        criticalValue.setText(
                String.valueOf(critical)
        );


        pendingValue.setText(
                String.valueOf(pending)
        );


        assignedValue.setText(
                String.valueOf(assigned)
        );


        inProgressValue.setText(
                String.valueOf(inProgress)
        );


        resolvedValue.setText(
                String.valueOf(resolved)
        );


        availableTeamValue.setText(
                String.valueOf(availableTeams)
        );


        busyTeamValue.setText(
                String.valueOf(busyTeams)
        );


        // -----------------------------------------------------
        // Type table
        // -----------------------------------------------------

        typeModel.setRowCount(
                0
        );


        for (
                EmergencyType type
                : EmergencyType.values()
        ) {

            typeModel.addRow(
                    new Object[]{
                            type,
                            emergencyManager
                                    .getCountByType(
                                    type
                            )
                    }
            );
        }


        // -----------------------------------------------------
        // Priority table
        // -----------------------------------------------------

        priorityModel.setRowCount(
                0
        );


        for (
                Priority priority
                : Priority.values()
        ) {

            priorityModel.addRow(
                    new Object[]{
                            priority,
                            emergencyManager
                                    .getCountByPriority(
                                    priority
                            )
                    }
            );
        }


        // -----------------------------------------------------
        // Status table
        // -----------------------------------------------------

        statusModel.setRowCount(
                0
        );


        for (
                EmergencyStatus status
                : EmergencyStatus.values()
        ) {

            statusModel.addRow(
                    new Object[]{
                            status,
                            emergencyManager
                                    .getCountByStatus(
                                    status
                            )
                    }
            );
        }


        // -----------------------------------------------------
        // Team table
        // -----------------------------------------------------

        teamModel.setRowCount(
                0
        );


        for (
                TeamType teamType
                : TeamType.values()
        ) {

            int totalTeams = 0;

            int available = 0;

            int busy = 0;


            for (
                    model.ResponseTeam team
                    : teamManager.getAllTeams()
            ) {

                if (
                        team.getTeamType()
                                == teamType
                ) {

                    totalTeams++;


                    if (
                            team.isAvailable()
                    ) {

                        available++;

                    } else {

                        busy++;
                    }
                }
            }


            teamModel.addRow(
                    new Object[]{
                            teamType,
                            totalTeams,
                            available,
                            busy
                    }
            );
        }
    }


    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable(
            JTable table
    ) {

        table.setRowHeight(
                27
        );


        table.setFont(
                Theme.NORMAL_FONT
        );


        table.setForeground(
                Theme.TEXT
        );


        table.setBackground(
                Theme.WHITE
        );


        table.setGridColor(
                Theme.KHAKI_BEIGE
        );


        table.setSelectionBackground(
                Theme.KHAKI_BEIGE
        );


        table.setSelectionForeground(
                Theme.BLACK
        );


        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        table.getTableHeader()
                .setFont(
                        Theme.SUBTITLE_FONT
                );


        table.getTableHeader()
                .setBackground(
                        Theme.JET_BLACK
                );


        table.getTableHeader()
                .setForeground(
                        Theme.LIGHT_TEXT
                );


        table.getTableHeader()
                .setReorderingAllowed(
                        false
                );
    }
}