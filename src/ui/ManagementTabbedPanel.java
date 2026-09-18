package ui;

import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;

import javax.swing.*;
import java.awt.*;

public class ManagementTabbedPanel extends JPanel {

    // =========================================================
    // MANAGERS
    // =========================================================

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;

    private final AssignmentManager assignmentManager;


    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTabbedPane tabbedPane;

    private EmergencyManagementPanel emergencyPanel;

    private TeamManagementPanel teamPanel;

    private AssignmentManagementPanel assignmentPanel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ManagementTabbedPanel(
            EmergencyManager emergencyManager,
            TeamManager teamManager,
            AssignmentManager assignmentManager
    ) {

        this.emergencyManager =
                emergencyManager;

        this.teamManager =
                teamManager;

        this.assignmentManager =
                assignmentManager;

        buildUI();
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        setLayout(
                new BorderLayout()
        );

        setBackground(
                Theme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        16,
                        12,
                        16
                )
        );


        // =====================================================
        // PAGE HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                Theme.BACKGROUND
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        5,
                        12,
                        5
                )
        );


        JLabel titleLabel =
                new JLabel(
                        "MANAGEMENT"
                );

        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.TEXT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "Manage emergencies, response teams and assignments."
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


        headerPanel.add(
                titleText,
                BorderLayout.WEST
        );


        // =====================================================
        // TABBED PANE
        // =====================================================

        tabbedPane =
                new JTabbedPane();

        tabbedPane.setFont(
                Theme.SUBTITLE_FONT
        );

        tabbedPane.setBackground(
                Theme.BACKGROUND
        );

        tabbedPane.setForeground(
                Theme.TEXT
        );

        tabbedPane.setBorder(
                BorderFactory.createLineBorder(
                        Theme.KHAKI_BEIGE
                )
        );


        // =====================================================
        // CREATE MANAGEMENT PANELS
        // =====================================================

        emergencyPanel =
                new EmergencyManagementPanel(
                        emergencyManager,
                        teamManager,
                        assignmentManager
                );


        teamPanel =
                new TeamManagementPanel(
                        teamManager
                );


        assignmentPanel =
                new AssignmentManagementPanel(
                        assignmentManager,
                        emergencyManager,
                        teamManager
                );


        // =====================================================
        // ADD TABS
        // =====================================================

        tabbedPane.addTab(
                "Emergencies",
                emergencyPanel
        );


        tabbedPane.addTab(
                "Response Teams",
                teamPanel
        );


        tabbedPane.addTab(
                "Assignments",
                assignmentPanel
        );


        // =====================================================
        // TAB CHANGE LISTENER
        // =====================================================

        tabbedPane.addChangeListener(
                e -> refreshSelectedTab()
        );


        // =====================================================
        // ADD TO PANEL
        // =====================================================

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        add(
                tabbedPane,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // REFRESH SELECTED TAB
    // =========================================================

    private void refreshSelectedTab() {

        int selectedIndex =
                tabbedPane.getSelectedIndex();


        if (selectedIndex == 0) {

            emergencyPanel.loadEmergencies();

        } else if (selectedIndex == 1) {

            teamPanel.loadTeams();

        } else if (selectedIndex == 2) {

            assignmentPanel.refreshData();
        }
    }


    // =========================================================
    // PUBLIC REFRESH
    // =========================================================

    public void refreshAllTabs() {

        emergencyPanel.loadEmergencies();

        teamPanel.loadTeams();

        assignmentPanel.refreshData();
    }
}