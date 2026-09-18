package ui;

import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Admin;
import persistence.FileManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // =========================================================
    // LOGGED-IN ADMIN
    // =========================================================

    private final Admin loggedInAdmin;


    // =========================================================
    // PERSISTENCE
    // =========================================================

    private final FileManager fileManager;


    // =========================================================
    // SHARED MANAGERS
    // The same manager objects are shared across all panels.
    // =========================================================

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;

    private final AssignmentManager assignmentManager;


    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JPanel contentPanel;

    private CardLayout cardLayout;


    // =========================================================
    // ACTUAL PANELS
    // =========================================================

    private DashboardPanel dashboardPanel;

    private ManagementTabbedPanel managementPanel;

    // ADDED: History and Reports panels
    private EmergencyHistoryPanel historyPanel;

    private ReportPanel reportPanel;


    // =========================================================
    // NAVIGATION BUTTONS
    // =========================================================

    private JButton dashboardButton;

    private JButton managementButton;

    private JButton historyButton;

    private JButton reportsButton;


    // =========================================================
    // CURRENT PAGE
    // =========================================================

    private String currentPage =
            DASHBOARD_PAGE;


    // =========================================================
    // PAGE NAMES
    // =========================================================

    private static final String DASHBOARD_PAGE =
            "dashboard";

    private static final String MANAGEMENT_PAGE =
            "management";

    private static final String HISTORY_PAGE =
            "history";

    private static final String REPORTS_PAGE =
            "reports";


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MainFrame(
            Admin loggedInAdmin
    ) {

        this.loggedInAdmin =
                loggedInAdmin;


        // -----------------------------------------------------
        // CREATE ONE SHARED FILE MANAGER
        // -----------------------------------------------------

        this.fileManager =
                new FileManager();


        // -----------------------------------------------------
        // CREATE MANAGERS USING THE SAME FILE MANAGER
        // -----------------------------------------------------

        this.emergencyManager =
                new EmergencyManager(
                        fileManager
                );


        this.teamManager =
                new TeamManager(
                        fileManager
                );


        this.assignmentManager =
                new AssignmentManager(
                        emergencyManager,
                        teamManager,
                        fileManager
                );


        initializeFrame();

        buildUI();
    }


    // =========================================================
    // FRAME SETUP
    // =========================================================

    private void initializeFrame() {

        setTitle(
                Theme.APP_TITLE
        );


        setSize(
                1280,
                760
        );


        setMinimumSize(
                new Dimension(
                        980,
                        620
                )
        );


        setResizable(
                true
        );


        setDefaultCloseOperation(
                JFrame.DO_NOTHING_ON_CLOSE
        );


        setLocationRelativeTo(
                null
        );


        addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent e
                    ) {

                        confirmExit();
                    }
                }
        );
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        setJMenuBar(
                createMenuBar()
        );


        // =====================================================
        // LEFT SIDEBAR
        // =====================================================

        JPanel sidebar =
                createSidebar();


        // =====================================================
        // RIGHT SIDE CENTER AREA
        // =====================================================

        JPanel centerArea =
                new JPanel(
                        new BorderLayout()
                );


        centerArea.setBackground(
                Theme.BACKGROUND
        );


        // -----------------------------------------------------
        // TOP HEADER
        // -----------------------------------------------------

        centerArea.add(
                createHeader(),
                BorderLayout.NORTH
        );


        // =====================================================
        // CARD LAYOUT
        // =====================================================

        cardLayout =
                new CardLayout();


        contentPanel =
                new JPanel(
                        cardLayout
                );


        contentPanel.setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // DASHBOARD PANEL
        // =====================================================

        dashboardPanel =
                new DashboardPanel(
                        emergencyManager,
                        teamManager
                );


        // =====================================================
        // MANAGEMENT PANEL
        // =====================================================

        managementPanel =
                new ManagementTabbedPanel(
                        emergencyManager,
                        teamManager,
                        assignmentManager
                );


        // =====================================================
        // HISTORY PANEL
        // ADDED
        // =====================================================

        historyPanel =
                new EmergencyHistoryPanel(
                        emergencyManager
                );


        // =====================================================
        // REPORTS PANEL
        // ADDED
        // =====================================================

        reportPanel =
                new ReportPanel(
                        emergencyManager,
                        teamManager
                );


        // =====================================================
        // ADD ACTUAL PAGES
        // =====================================================

        contentPanel.add(
                dashboardPanel,
                DASHBOARD_PAGE
        );


        contentPanel.add(
                managementPanel,
                MANAGEMENT_PAGE
        );


        // ADDED: Real History page
        contentPanel.add(
                historyPanel,
                HISTORY_PAGE
        );


        // ADDED: Real Reports page
        contentPanel.add(
                reportPanel,
                REPORTS_PAGE
        );


        centerArea.add(
                contentPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // MAIN FRAME LAYOUT
        // =====================================================

        setLayout(
                new BorderLayout()
        );


        add(
                sidebar,
                BorderLayout.WEST
        );


        add(
                centerArea,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );


        header.setBackground(
                Theme.HEADER
        );


        header.setPreferredSize(
                new Dimension(
                        0,
                        Theme.HEADER_HEIGHT
                )
        );


        // =====================================================
        // APPLICATION TITLE
        // =====================================================

        JLabel applicationTitle =
                new JLabel(
                        "EMERGENCY MANAGEMENT SYSTEM"
                );


        applicationTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );


        applicationTitle.setForeground(
                Theme.LIGHT_TEXT
        );


        applicationTitle.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        28,
                        0,
                        10
                )
        );


        // =====================================================
        // RIGHT HEADER
        // =====================================================

        JPanel rightHeader =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                14,
                                13
                        )
                );


        rightHeader.setBackground(
                Theme.HEADER
        );


        JButton refreshButton =
                new JButton(
                        "REFRESH"
                );


        Theme.styleHeaderButton(
                refreshButton
        );


        refreshButton.addActionListener(
                e -> refreshCurrentPage()
        );


        JLabel adminLabel =
                new JLabel(
                        "Admin: "
                                + loggedInAdmin.getName()
                );


        adminLabel.setFont(
                Theme.NORMAL_FONT
        );


        adminLabel.setForeground(
                Theme.ACCENT
        );


        rightHeader.add(
                refreshButton
        );


        rightHeader.add(
                adminLabel
        );


        header.add(
                applicationTitle,
                BorderLayout.WEST
        );


        header.add(
                rightHeader,
                BorderLayout.EAST
        );


        return header;
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel();


        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );


        sidebar.setBackground(
                Theme.SIDEBAR
        );


        sidebar.setPreferredSize(
                new Dimension(
                        Theme.SIDEBAR_WIDTH,
                        0
                )
        );


        sidebar.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        12,
                        18,
                        12
                )
        );


        // =====================================================
        // ADMIN MENU TITLE
        // =====================================================

        JLabel menuTitle =
                new JLabel(
                        "ADMIN MENU"
                );


        menuTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );


        menuTitle.setForeground(
                Theme.ACCENT
        );


        menuTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        sidebar.add(
                menuTitle
        );


        sidebar.add(
                Box.createVerticalStrut(
                        22
                )
        );


        // =====================================================
        // NAVIGATION BUTTONS
        // =====================================================

        dashboardButton =
                createAdminButton(
                        "DASHBOARD"
                );


        managementButton =
                createAdminButton(
                        "MANAGEMENT"
                );


        historyButton =
                createAdminButton(
                        "HISTORY"
                );


        reportsButton =
                createAdminButton(
                        "REPORTS"
                );


        // -----------------------------------------------------
        // Dashboard
        // -----------------------------------------------------

        dashboardButton.addActionListener(
                e -> showPage(
                        DASHBOARD_PAGE,
                        dashboardButton
                )
        );


        // -----------------------------------------------------
        // Management
        // -----------------------------------------------------

        managementButton.addActionListener(
                e -> showPage(
                        MANAGEMENT_PAGE,
                        managementButton
                )
        );


        // -----------------------------------------------------
        // History
        // -----------------------------------------------------

        historyButton.addActionListener(
                e -> showPage(
                        HISTORY_PAGE,
                        historyButton
                )
        );


        // -----------------------------------------------------
        // Reports
        // -----------------------------------------------------

        reportsButton.addActionListener(
                e -> showPage(
                        REPORTS_PAGE,
                        reportsButton
                )
        );


        // =====================================================
        // ADD NAVIGATION BUTTONS
        // =====================================================

        sidebar.add(
                dashboardButton
        );


        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );


        sidebar.add(
                managementButton
        );


        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );


        sidebar.add(
                historyButton
        );


        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
        );

        sidebar.add(
                reportsButton
        );


        // =====================================================
        // FLEXIBLE EMPTY SPACE
        // Pushes Logout to the bottom.
        // =====================================================

        sidebar.add(
                Box.createVerticalGlue()
        );


        // =====================================================
        // LOGOUT
        // =====================================================

        JButton logoutButton =
                new JButton(
                        "LOGOUT"
                );


        Theme.styleLogoutButton(
                logoutButton
        );


        logoutButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        logoutButton.addActionListener(
                e -> logout()
        );


        sidebar.add(
                logoutButton
        );


        // Dashboard active initially

        setActiveButton(
                dashboardButton
        );


        return sidebar;
    }


    // =========================================================
    // CREATE ADMIN BUTTON
    // =========================================================

    private JButton createAdminButton(
            String text
    ) {

        JButton button =
                new JButton(
                        text
                );


        Theme.styleAdminMenuButton(
                button
        );


        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        return button;
    }


    // =========================================================
    // SHOW PAGE
    // =========================================================

    private void showPage(
            String pageName,
            JButton selectedButton
    ) {

        currentPage =
                pageName;


        setActiveButton(
                selectedButton
        );


        cardLayout.show(
                contentPanel,
                pageName
        );


        // -----------------------------------------------------
        // Refresh the selected module when opened
        // -----------------------------------------------------

        if (
                DASHBOARD_PAGE.equals(
                        pageName
                )
        ) {

            dashboardPanel.refreshDashboard();

        } else if (
                MANAGEMENT_PAGE.equals(
                        pageName
                )
        ) {

            managementPanel.refreshAllTabs();

        } else if (
                HISTORY_PAGE.equals(
                        pageName
                )
        ) {

            // ADDED
            historyPanel.loadHistory();

        } else if (
                REPORTS_PAGE.equals(
                        pageName
                )
        ) {

            // ADDED
            reportPanel.refreshReports();
        }
    }


    // =========================================================
    // ACTIVE SIDEBAR BUTTON
    // =========================================================

    private void setActiveButton(
            JButton selectedButton
    ) {

        Theme.styleAdminMenuButton(
                dashboardButton
        );


        Theme.styleAdminMenuButton(
                managementButton
        );


        Theme.styleAdminMenuButton(
                historyButton
        );


        Theme.styleAdminMenuButton(
                reportsButton
        );


        if (
                selectedButton != null
        ) {

            selectedButton.setBackground(
                    Theme.STONE_BROWN
            );


            selectedButton.setForeground(
                    Theme.LIGHT_TEXT
            );
        }
    }


    // =========================================================
    // REFRESH CURRENT PAGE
    // =========================================================

    private void refreshCurrentPage() {

        if (
                DASHBOARD_PAGE.equals(
                        currentPage
                )
        ) {

            dashboardPanel.refreshDashboard();
        }


        else if (
                MANAGEMENT_PAGE.equals(
                        currentPage
                )
        ) {

            managementPanel.refreshAllTabs();
        }


        else if (
                HISTORY_PAGE.equals(
                        currentPage
                )
        ) {

            // ADDED
            historyPanel.loadHistory();
        }


        else if (
                REPORTS_PAGE.equals(
                        currentPage
                )
        ) {

            // ADDED
            reportPanel.refreshReports();
        }


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // =========================================================
    // HISTORY PLACEHOLDER
    // =========================================================

    /*
    private JPanel createHistoryPlaceholder() {

        return createSimplePlaceholder(
                "EMERGENCY HISTORY",
                "Emergency history module will be added next."
        );
    }
    */


    // =========================================================
    // REPORTS PLACEHOLDER
    // =========================================================

    /*
    private JPanel createReportsPlaceholder() {

        return createSimplePlaceholder(
                "REPORTS & STATISTICS",
                "Reports and statistics module will be added next."
        );
    }
    */


    // =========================================================
    // SIMPLE PLACEHOLDER
    // =========================================================

    /*
    private JPanel createSimplePlaceholder(
            String title,
            String description
    ) {

        JPanel page =
                new JPanel(
                        new GridBagLayout()
                );


        page.setBackground(
                Theme.BACKGROUND
        );


        JPanel box =
                new JPanel();


        box.setLayout(
                new BoxLayout(
                        box,
                        BoxLayout.Y_AXIS
                )
        );


        box.setBackground(
                Theme.ALMOND_CREAM
        );


        box.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.KHAKI_BEIGE
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                45,
                                30,
                                45
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );


        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );


        titleLabel.setForeground(
                Theme.TEXT
        );


        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel descriptionLabel =
                new JLabel(
                        description
                );


        descriptionLabel.setFont(
                Theme.NORMAL_FONT
        );


        descriptionLabel.setForeground(
                Theme.MUTED_TEXT
        );


        descriptionLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        box.add(
                titleLabel
        );


        box.add(
                Box.createVerticalStrut(
                        8
                )
        );


        box.add(
                descriptionLabel
        );


        page.add(
                box
        );


        return page;
    }
    */


    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result
                        == JOptionPane.YES_OPTION
        ) {

            LoginFrame loginFrame =
                    new LoginFrame(
                            new manager.AuthenticationManager()
                    );


            loginFrame.setVisible(
                    true
            );


            dispose();
        }
    }


    // =========================================================
    // EXIT
    // =========================================================

    private void confirmExit() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to exit the application?",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result
                        == JOptionPane.YES_OPTION
        ) {

            System.exit(0);
        }
    }


    // =========================================================
    // MENU BAR
    // =========================================================

    private JMenuBar createMenuBar() {

        JMenuBar menuBar =
                new JMenuBar();


        // =====================================================
        // FILE
        // =====================================================

        JMenu fileMenu =
                new JMenu(
                        "File"
                );


        JMenuItem logoutItem =
                new JMenuItem(
                        "Logout"
                );


        JMenuItem exitItem =
                new JMenuItem(
                        "Exit"
                );


        logoutItem.addActionListener(
                e -> logout()
        );


        exitItem.addActionListener(
                e -> confirmExit()
        );


        fileMenu.add(
                logoutItem
        );


        fileMenu.addSeparator();


        fileMenu.add(
                exitItem
        );


        // =====================================================
        // VIEW
        // =====================================================

        JMenu viewMenu =
                new JMenu(
                        "View"
                );


        JMenuItem dashboardItem =
                new JMenuItem(
                        "Dashboard"
                );


        dashboardItem.addActionListener(
                e -> showPage(
                        DASHBOARD_PAGE,
                        dashboardButton
                )
        );


        viewMenu.add(
                dashboardItem
        );


        // =====================================================
        // HELP
        // =====================================================

        JMenu helpMenu =
                new JMenu(
                        "Help"
                );


        JMenuItem aboutItem =
                new JMenuItem(
                        "About"
                );


        aboutItem.addActionListener(
                e -> showAboutDialog()
        );


        helpMenu.add(
                aboutItem
        );


        // =====================================================
        // ADD MENUS
        // =====================================================

        menuBar.add(
                fileMenu
        );


        menuBar.add(
                viewMenu
        );


        menuBar.add(
                helpMenu
        );


        return menuBar;
    }


    // =========================================================
    // ABOUT
    // =========================================================

    private void showAboutDialog() {

        JOptionPane.showMessageDialog(
                this,
                Theme.APP_TITLE
                        + "\n"
                        + "Java Swing Desktop Application"
                        + "\n"
                        + "Emergency management and coordination system."
                        + "\n\n"
                        + "Version: "
                        + Theme.APP_VERSION,
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}