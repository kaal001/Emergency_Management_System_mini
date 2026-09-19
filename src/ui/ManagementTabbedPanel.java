package ui;

import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ManagementTabbedPanel extends JPanel {

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;

    private final AssignmentManager assignmentManager;

    private JTabbedPane tabbedPane;

    private EmergencyManagementPanel emergencyPanel;

    private TeamManagementPanel teamPanel;

    private AssignmentManagementPanel assignmentPanel;

    private JLabel selectedModuleLabel;

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

    private void buildUI() {

        setLayout(
                new BorderLayout(
                        0,
                        10
                )
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
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(false);

        JPanel titleArea =
                new JPanel();

        titleArea.setLayout(
                new BoxLayout(
                        titleArea,
                        BoxLayout.Y_AXIS
                )
        );

        titleArea.setOpaque(false);

        JLabel eyebrow =
                new JLabel(
                        "CONTROL MODULES"
                );

        eyebrow.setFont(
                Theme.SMALL_FONT
        );

        eyebrow.setForeground(
                Theme.STONE_BROWN
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

        titleArea.add(eyebrow);
        titleArea.add(Box.createVerticalStrut(2));
        titleArea.add(titleLabel);
        titleArea.add(Box.createVerticalStrut(3));
        titleArea.add(subtitleLabel);

        headerPanel.add(
                titleArea,
                BorderLayout.WEST
        );

        JPanel headerStatus =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                8
                        )
                );

        headerStatus.setOpaque(false);

        JLabel liveDot =
                new JLabel("●");

        liveDot.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        liveDot.setForeground(
                Theme.ACCENT
        );

        JLabel liveLabel =
                new JLabel(
                        "LIVE DATA"
                );

        liveLabel.setFont(
                Theme.SMALL_FONT
        );

        liveLabel.setForeground(
                Theme.MUTED_TEXT
        );

        headerStatus.add(liveDot);
        headerStatus.add(liveLabel);

        headerPanel.add(
                headerStatus,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABBED PANE
        // =====================================================

        tabbedPane =
                new JTabbedPane(
                        JTabbedPane.TOP
                );

        tabbedPane.setFont(
                Theme.BUTTON_FONT
        );

        tabbedPane.setForeground(
                Theme.TEXT
        );

        tabbedPane.setBackground(
                Theme.BACKGROUND
        );

        tabbedPane.setOpaque(false);

        tabbedPane.setTabLayoutPolicy(
                JTabbedPane.SCROLL_TAB_LAYOUT
        );

        tabbedPane.setMinimumSize(
                new Dimension(
                        0,
                        0
                )
        );

        tabbedPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        tabbedPane.setUI(
                new ModernTabbedPaneUI()
        );

        // =====================================================
        // CHILD PANELS
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

        emergencyPanel.setMinimumSize(new Dimension(0, 0));
        teamPanel.setMinimumSize(new Dimension(0, 0));
        assignmentPanel.setMinimumSize(new Dimension(0, 0));

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

        tabbedPane.setTabComponentAt(
                0,
                createTabHeader(
                        "EMERGENCIES"
                )
        );

        tabbedPane.setTabComponentAt(
                1,
                createTabHeader(
                        "RESPONSE TEAMS"
                )
        );

        tabbedPane.setTabComponentAt(
                2,
                createTabHeader(
                        "ASSIGNMENTS"
                )
        );

        tabbedPane.addChangeListener(
                e -> {

                    refreshSelectedTab();

                    updateSelectedModuleLabel();

                    tabbedPane.repaint();
                }
        );

        add(
                tabbedPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setOpaque(false);

        selectedModuleLabel =
                new JLabel(
                        "ACTIVE MODULE: EMERGENCIES"
                );

        selectedModuleLabel.setFont(
                Theme.SMALL_FONT
        );

        selectedModuleLabel.setForeground(
                Theme.MUTED_TEXT
        );

        footer.add(
                selectedModuleLabel,
                BorderLayout.WEST
        );

        JLabel hint =
                new JLabel(
                        "Select a record to edit or manage its workflow."
                );

        footer.setBorder(
                BorderFactory.createEmptyBorder(
                        2,
                        2,
                        0,
                        2
                )
        );

        hint.setFont(
                Theme.SMALL_FONT
        );

        hint.setForeground(
                Theme.MUTED_TEXT
        );

        footer.add(
                hint,
                BorderLayout.EAST
        );

        add(
                footer,
                BorderLayout.SOUTH
        );
    }

    private JPanel createTabHeader(
            String title
    ) {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                7,
                                6
                        )
                );

        panel.setOpaque(false);

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                Theme.BUTTON_FONT
        );

        titleLabel.setForeground(
                Theme.TEXT
        );

        panel.add(
                titleLabel
        );

        return panel;
    }

    private void updateSelectedModuleLabel() {

        int index =
                tabbedPane.getSelectedIndex();

        if (index == 0) {

            selectedModuleLabel.setText(
                    "ACTIVE MODULE: EMERGENCIES"
            );

        } else if (index == 1) {

            selectedModuleLabel.setText(
                    "ACTIVE MODULE: RESPONSE TEAMS"
            );

        } else if (index == 2) {

            selectedModuleLabel.setText(
                    "ACTIVE MODULE: ASSIGNMENTS"
            );
        }
    }

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

    public void refreshAllTabs() {

        emergencyPanel.loadEmergencies();

        teamPanel.loadTeams();

        assignmentPanel.refreshData();

        updateSelectedModuleLabel();
    }

    // =========================================================
    // MODERN TAB UI
    // =========================================================

    private static class ModernTabbedPaneUI
            extends BasicTabbedPaneUI {

        @Override
        protected void installDefaults() {

            super.installDefaults();

            tabAreaInsets =
                    new Insets(
                            0,
                            0,
                            6,
                            0
                    );

            contentBorderInsets =
                    new Insets(
                            1,
                            1,
                            1,
                            1
                    );
        }

        @Override
        protected void paintTabBackground(
                Graphics g,
                int tabPlacement,
                int tabIndex,
                int x,
                int y,
                int w,
                int h,
                boolean isSelected
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            try {

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                if (isSelected) {

                    g2.setColor(
                            Theme.KHAKI_BEIGE
                    );

                } else {

                    g2.setColor(
                            new Color(
                                    Theme.JET_BLACK.getRed(),
                                    Theme.JET_BLACK.getGreen(),
                                    Theme.JET_BLACK.getBlue(),
                                    24
                            )
                    );
                }

                g2.fillRoundRect(
                        x + 3,
                        y + 3,
                        Math.max(1, w - 6),
                        Math.max(1, h - 5),
                        14,
                        14
                );

            } finally {

                g2.dispose();
            }
        }

        @Override
        protected void paintContentBorder(
                Graphics g,
                int tabPlacement,
                int selectedIndex
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            try {

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(
                        Theme.KHAKI_BEIGE
                );

                g2.drawRoundRect(
                        1,
                        1,
                        Math.max(1, tabPane.getWidth() - 3),
                        Math.max(1, tabPane.getHeight() - 3),
                        18,
                        18
                );

            } finally {

                g2.dispose();
            }
        }

        @Override
        protected void paintFocusIndicator(
                Graphics g,
                int tabPlacement,
                Rectangle[] rects,
                int tabIndex,
                Rectangle iconRect,
                Rectangle textRect,
                boolean isSelected
        ) {
            // Intentionally disabled for a cleaner modern look.
        }
    }
}
