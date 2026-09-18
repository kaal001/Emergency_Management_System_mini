package ui;

import enums.EmergencyStatus;
import enums.Priority;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Emergency;

import javax.swing.*;
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
        // TITLE
        // =====================================================

        JPanel titlePanel =
                new JPanel(
                        new BorderLayout()
                );

        titlePanel.setBackground(
                Theme.BACKGROUND
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
                        "Monitor emergency activity and response operations."
                );

        subtitleLabel.setFont(
                Theme.NORMAL_FONT
        );

        subtitleLabel.setForeground(
                Theme.MUTED_TEXT
        );


        JPanel titleText =
                new JPanel();

        titleText.setLayout(
                new BoxLayout(
                        titleText,
                        BoxLayout.Y_AXIS
                )
        );

        titleText.setBackground(
                Theme.BACKGROUND
        );

        titleText.add(
                titleLabel
        );

        titleText.add(
                Box.createVerticalStrut(4)
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
                        "REFRESH"
                );

        Theme.styleHeaderButton(
                refreshButton
        );

        refreshButton.addActionListener(
                e -> refreshDashboard()
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
                        totalValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "PENDING",
                        pendingValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "CRITICAL",
                        criticalValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "IN PROGRESS",
                        inProgressValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "RESOLVED",
                        resolvedValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "UNASSIGNED",
                        unassignedValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "AVAILABLE TEAMS",
                        availableTeamsValueLabel
                )
        );

        statisticsPanel.add(
                createStatCard(
                        "BUSY TEAMS",
                        busyTeamsValueLabel
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
                                8
                        )
                );

        recentPanel.setBackground(
                Theme.BACKGROUND
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


        recentPanel.add(
                recentTitle,
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
                BorderFactory.createLineBorder(
                        Theme.STONE_BROWN
                )
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
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                0,
                                6
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
                                        12,
                                        14,
                                        12,
                                        14
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
                valueLabel,
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
                        28
                )
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

            if (emergency.getAssignedTeamId()
                    == null
                    || emergency.getAssignedTeamId()
                    .trim()
                    .isEmpty()) {

                unassigned++;
            }
        }


        int availableTeams =
                teamManager
                        .getAvailableTeamCount();

        int busyTeams =
                teamManager
                        .getBusyTeamCount();


        totalValueLabel.setText(
                String.valueOf(total)
        );

        pendingValueLabel.setText(
                String.valueOf(pending)
        );

        criticalValueLabel.setText(
                String.valueOf(critical)
        );

        inProgressValueLabel.setText(
                String.valueOf(inProgress)
        );

        resolvedValueLabel.setText(
                String.valueOf(resolved)
        );

        unassignedValueLabel.setText(
                String.valueOf(unassigned)
        );

        availableTeamsValueLabel.setText(
                String.valueOf(availableTeams)
        );

        busyTeamsValueLabel.setText(
                String.valueOf(busyTeams)
        );


        loadRecentEmergencies();
    }


    // =========================================================
    // LOAD RECENT EMERGENCIES
    // =========================================================

    private void loadRecentEmergencies() {

        recentTableModel.setRowCount(0);


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


            if (assignedTeam == null
                    || assignedTeam.trim().isEmpty()) {

                assignedTeam = "-";
            }


            recentTableModel.addRow(
                    new Object[]{
                            emergency.getEmergencyId(),
                            emergency.getType(),
                            emergency.getPriority(),
                            emergency.getLocation(),
                            emergency.getStatus(),
                            assignedTeam
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
                29
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