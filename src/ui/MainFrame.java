package ui;

import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Admin;
import persistence.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
    // HEADER COMPONENTS
    // =========================================================

    private JLabel currentPageLabel;

    private JLabel systemStatusDot;

    private JButton refreshButton;

    private Timer statusPulseTimer;


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

        this.fileManager =
                new FileManager();

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

        startSystemPulse();
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
                new WindowAdapter() {

                    @Override
                    public void windowClosing(
                            WindowEvent e
                    ) {

                        confirmExit();
                    }
                }
        );
    }


    // =========================================================
    // BUILD COMPLETE UI
    // =========================================================

    private void buildUI() {

        setJMenuBar(
                createMenuBar()
        );


        // =====================================================
        // SIDEBAR
        // =====================================================

        JPanel sidebar =
                createSidebar();


        // =====================================================
        // MAIN CENTER AREA
        // =====================================================

        JPanel centerArea =
                new JPanel(
                        new BorderLayout()
                );

        centerArea.setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // HEADER
        // =====================================================

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
        // DASHBOARD
        // =====================================================

        dashboardPanel =
                new DashboardPanel(
                        emergencyManager,
                        teamManager
                );


        // =====================================================
        // MANAGEMENT
        // =====================================================

        managementPanel =
                new ManagementTabbedPanel(
                        emergencyManager,
                        teamManager,
                        assignmentManager
                );


        // =====================================================
        // HISTORY
        // =====================================================

        historyPanel =
                new EmergencyHistoryPanel(
                        emergencyManager
                );


        // =====================================================
        // REPORTS
        // =====================================================

        reportPanel =
                new ReportPanel(
                        emergencyManager,
                        teamManager
                );


        // =====================================================
        // REGISTER PAGES
        // =====================================================

        contentPanel.add(
                dashboardPanel,
                DASHBOARD_PAGE
        );

        contentPanel.add(
                managementPanel,
                MANAGEMENT_PAGE
        );

        contentPanel.add(
                historyPanel,
                HISTORY_PAGE
        );

        contentPanel.add(
                reportPanel,
                REPORTS_PAGE
        );


        centerArea.add(
                contentPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // FINAL FRAME LAYOUT
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

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        24,
                        8,
                        20
                )
        );


        // =====================================================
        // LEFT TITLE AREA
        // =====================================================

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(
                false
        );


        JLabel applicationTitle =
                new JLabel(
                        "EMERGENCY MANAGEMENT SYSTEM"
                );

        applicationTitle.setFont(
                Theme.TITLE_FONT
        );

        applicationTitle.setForeground(
                Theme.LIGHT_TEXT
        );


        currentPageLabel =
                new JLabel(
                        "DASHBOARD"
                );

        currentPageLabel.setFont(
                Theme.SMALL_FONT
        );

        currentPageLabel.setForeground(
                Theme.ACCENT
        );


        titlePanel.add(
                applicationTitle
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        2
                )
        );

        titlePanel.add(
                currentPageLabel
        );


        // =====================================================
        // RIGHT HEADER AREA
        // =====================================================

        JPanel rightHeader =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        rightHeader.setOpaque(
                false
        );


        // SYSTEM STATUS

        JPanel systemStatusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                5,
                                0
                        )
                );

        systemStatusPanel.setOpaque(
                true
        );

        systemStatusPanel.setBackground(
                new Color(
                        Theme.STONE_BROWN.getRed(),
                        Theme.STONE_BROWN.getGreen(),
                        Theme.STONE_BROWN.getBlue(),
                        80
                )
        );

        systemStatusPanel.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        14,
                        1,
                        5
                )
        );


        systemStatusDot =
                new JLabel(
                        "●"
                );

        systemStatusDot.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        systemStatusDot.setForeground(
                Theme.ACCENT
        );


        JLabel systemStatusLabel =
                new JLabel(
                        "SYSTEM ONLINE"
                );

        systemStatusLabel.setFont(
                Theme.SMALL_FONT
        );

        systemStatusLabel.setForeground(
                Theme.LIGHT_TEXT
        );


        systemStatusPanel.add(
                systemStatusDot
        );

        systemStatusPanel.add(
                systemStatusLabel
        );


        // ADMIN

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


        // REFRESH BUTTON

        refreshButton =
                new JButton(
                        "REFRESH"
                );

        Theme.styleHeaderButton(
                refreshButton
        );

        refreshButton.addActionListener(
                e -> {

                    refreshCurrentPage();

                    animateRefreshButton();
                }
        );


        rightHeader.add(
                systemStatusPanel
        );

        rightHeader.add(
                adminLabel
        );

        rightHeader.add(
                refreshButton
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                rightHeader,
                BorderLayout.EAST
        );


        return header;
    }


    // =========================================================
    // SYSTEM STATUS PULSE
    // =========================================================

    private void startSystemPulse() {

        statusPulseTimer =
                new Timer(
                        850,
                        e -> {

                            if (
                                    systemStatusDot
                                            .getForeground()
                                            .equals(
                                                    Theme.ACCENT
                                            )
                            ) {

                                systemStatusDot.setForeground(
                                        Theme.KHAKI_BEIGE
                                                .brighter()
                                );

                            } else {

                                systemStatusDot.setForeground(
                                        Theme.ACCENT
                                );
                            }
                        }
                );

        statusPulseTimer.start();
    }


    // =========================================================
    // REFRESH BUTTON FEEDBACK
    // =========================================================

    private void animateRefreshButton() {

        if (refreshButton == null) {
            return;
        }

        String originalText =
                "REFRESH";

        refreshButton.setText(
                "REFRESHED"
        );

        Timer timer =
                new Timer(
                        900,
                        e ->
                                refreshButton.setText(
                                        originalText
                                )
                );

        timer.setRepeats(
                false
        );

        timer.start();
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
                        24,
                        14,
                        18,
                        14
                )
        );


        // =====================================================
        // BRAND
        // =====================================================

        JLabel brandLabel =
                new JLabel(
                        "EMS"
                );

        brandLabel.setFont(
                Theme.SUBTITLE_FONT
        );

        brandLabel.setForeground(
                Theme.ACCENT
        );

        brandLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                brandLabel
        );


        JLabel versionLabel =
                new JLabel(
                        Theme.APP_VERSION
                );

        versionLabel.setFont(
                Theme.SMALL_FONT
        );

        versionLabel.setForeground(
                Theme.MUTED_TEXT
        );

        versionLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                Box.createVerticalStrut(
                        3
                )
        );

        sidebar.add(
                versionLabel
        );


        sidebar.add(
                Box.createVerticalStrut(
                        26
                )
        );


        // =====================================================
        // NAVIGATION TITLE
        // =====================================================

        JLabel menuTitle =
                new JLabel(
                        "ADMIN NAVIGATION"
                );

        menuTitle.setFont(
                Theme.SMALL_FONT
        );

        menuTitle.setForeground(
                Theme.MUTED_TEXT
        );

        menuTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(
                menuTitle
        );


        sidebar.add(
                Box.createVerticalStrut(
                        14
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


        dashboardButton.addActionListener(
                e ->
                        showPage(
                                DASHBOARD_PAGE,
                                dashboardButton
                        )
        );

        managementButton.addActionListener(
                e ->
                        showPage(
                                MANAGEMENT_PAGE,
                                managementButton
                        )
        );

        historyButton.addActionListener(
                e ->
                        showPage(
                                HISTORY_PAGE,
                                historyButton
                        )
        );

        reportsButton.addActionListener(
                e ->
                        showPage(
                                REPORTS_PAGE,
                                reportsButton
                        )
        );


        sidebar.add(
                dashboardButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        10
                )
        );

        sidebar.add(
                managementButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        10
                )
        );

        sidebar.add(
                historyButton
        );

        sidebar.add(
                Box.createVerticalStrut(
                        10
                )
        );

        sidebar.add(
                reportsButton
        );


        // =====================================================
        // SPACER
        // =====================================================

        sidebar.add(
                Box.createVerticalGlue()
        );


        // =====================================================
        // QUICK SYSTEM INFO
        // =====================================================

        JPanel infoPanel =
                new JPanel();

        infoPanel.setLayout(
                new BoxLayout(
                        infoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        infoPanel.setOpaque(
                true
        );

        infoPanel.setBackground(
                new Color(
                        Theme.BLACK.getRed(),
                        Theme.BLACK.getGreen(),
                        Theme.BLACK.getBlue(),
                        80
                )
        );

        infoPanel.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        14,
                        1,
                        8
                )
        );

        infoPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel modeLabel =
                new JLabel(
                        "ADMIN MODE"
                );

        modeLabel.setFont(
                Theme.SMALL_FONT
        );

        modeLabel.setForeground(
                Theme.ACCENT
        );

        modeLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel storageLabel =
                new JLabel(
                        "LOCAL SERIALIZATION"
                );

        storageLabel.setFont(
                Theme.SMALL_FONT
        );

        storageLabel.setForeground(
                Theme.LIGHT_TEXT
        );

        storageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        infoPanel.add(
                modeLabel
        );

        infoPanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        infoPanel.add(
                storageLabel
        );


        sidebar.add(
                infoPanel
        );


        sidebar.add(
                Box.createVerticalStrut(
                        12
                )
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


        // =====================================================
        // INITIAL ACTIVE PAGE
        // =====================================================

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

        updatePageIndicator(
                pageName
        );

        cardLayout.show(
                contentPanel,
                pageName
        );


        // =====================================================
        // REFRESH SELECTED MODULE
        // =====================================================

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

            historyPanel.loadHistory();

        } else if (
                REPORTS_PAGE.equals(
                        pageName
                )
        ) {

            reportPanel.refreshReports();
        }


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // =========================================================
    // PAGE INDICATOR
    // =========================================================

    private void updatePageIndicator(
            String pageName
    ) {

        if (currentPageLabel == null) {
            return;
        }

        String text;

        if (
                DASHBOARD_PAGE.equals(
                        pageName
                )
        ) {

            text =
                    "DASHBOARD";

        } else if (
                MANAGEMENT_PAGE.equals(
                        pageName
                )
        ) {

            text =
                    "MANAGEMENT";

        } else if (
                HISTORY_PAGE.equals(
                        pageName
                )
        ) {

            text =
                    "HISTORY";

        } else {

            text =
                    "REPORTS";
        }

        currentPageLabel.setText(
                text
        );

        currentPageLabel.setForeground(
                Theme.ACCENT
        );
    }


    // =========================================================
    // ACTIVE SIDEBAR BUTTON
    // =========================================================

    private void setActiveButton(
            JButton selectedButton
    ) {

        Theme.setActiveMenuButton(
                dashboardButton,
                selectedButton == dashboardButton
        );

        Theme.setActiveMenuButton(
                managementButton,
                selectedButton == managementButton
        );

        Theme.setActiveMenuButton(
                historyButton,
                selectedButton == historyButton
        );

        Theme.setActiveMenuButton(
                reportsButton,
                selectedButton == reportsButton
        );
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

        } else if (
                MANAGEMENT_PAGE.equals(
                        currentPage
                )
        ) {

            managementPanel.refreshAllTabs();

        } else if (
                HISTORY_PAGE.equals(
                        currentPage
                )
        ) {

            historyPanel.loadHistory();

        } else if (
                REPORTS_PAGE.equals(
                        currentPage
                )
        ) {

            reportPanel.refreshReports();
        }

        contentPanel.revalidate();

        contentPanel.repaint();
    }


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

            if (
                    statusPulseTimer != null
            ) {

                statusPulseTimer.stop();
            }

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

            if (
                    statusPulseTimer != null
            ) {

                statusPulseTimer.stop();
            }

            System.exit(
                    0
            );
        }
    }


    // =========================================================
    // MENU BAR
    // =========================================================

    private JMenuBar createMenuBar() {

        JMenuBar menuBar =
                new JMenuBar();

        menuBar.setBackground(
                Theme.HEADER
        );

        menuBar.setBorder(
                BorderFactory.createEmptyBorder(
                        3,
                        8,
                        3,
                        8
                )
        );


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

        JMenuItem managementItem =
                new JMenuItem(
                        "Management"
                );

        JMenuItem historyItem =
                new JMenuItem(
                        "History"
                );

        JMenuItem reportsItem =
                new JMenuItem(
                        "Reports"
                );


        dashboardItem.addActionListener(
                e ->
                        showPage(
                                DASHBOARD_PAGE,
                                dashboardButton
                        )
        );

        managementItem.addActionListener(
                e ->
                        showPage(
                                MANAGEMENT_PAGE,
                                managementButton
                        )
        );

        historyItem.addActionListener(
                e ->
                        showPage(
                                HISTORY_PAGE,
                                historyButton
                        )
        );

        reportsItem.addActionListener(
                e ->
                        showPage(
                                REPORTS_PAGE,
                                reportsButton
                        )
        );


        viewMenu.add(
                dashboardItem
        );

        viewMenu.add(
                managementItem
        );

        viewMenu.add(
                historyItem
        );

        viewMenu.add(
                reportsItem
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