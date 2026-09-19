package ui;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Assignment;
import model.Emergency;
import model.ResponseTeam;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmergencyManagementPanel extends JPanel {

    // =========================================================
    // SHARED MANAGERS
    // =========================================================

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;

    private final AssignmentManager assignmentManager;


    // =========================================================
    // FORM FIELDS
    // =========================================================

    private JTextField emergencyIdField;

    private JComboBox<EmergencyType> typeComboBox;

    private JComboBox<Priority> priorityComboBox;

    private JTextField locationField;

    private JTextField dateTimeField;

    private JComboBox<EmergencyStatus> statusComboBox;

    private JTextArea descriptionArea;


    // =========================================================
    // SEARCH
    // =========================================================

    private JTextField searchField;


    // =========================================================
    // TABLE
    // =========================================================

    private JTable emergencyTable;

    private DefaultTableModel tableModel;


    // =========================================================
    // CRUD BUTTONS
    // =========================================================

    private JButton addButton;

    private JButton updateButton;

    private JButton deleteButton;

    private JButton clearFormButton;


    // =========================================================
    // WORKFLOW BUTTONS
    // =========================================================

    private JButton startResponseButton;

    private JButton resolveButton;

    private JButton cancelEmergencyButton;


    // =========================================================
    // SELECTED EMERGENCY
    // =========================================================

    private Emergency selectedEmergency;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public EmergencyManagementPanel(
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


        setLayout(
                new BorderLayout()
        );

        setBackground(
                Theme.BACKGROUND
        );


        buildUI();

        loadEmergencies();
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        // =====================================================
        // PAGE HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(false);

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        18,
                        8,
                        18
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel(
                        "EMERGENCY MANAGEMENT"
                );

        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.TEXT
        );

        JLabel subtitleLabel =
                new JLabel(
                        "Record and manage emergency incidents."
                );

        subtitleLabel.setFont(
                Theme.SMALL_FONT
        );

        subtitleLabel.setForeground(
                Theme.MUTED_TEXT
        );

        titlePanel.add(
                titleLabel
        );

        titlePanel.add(
                Box.createVerticalStrut(2)
        );

        titlePanel.add(
                subtitleLabel
        );

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        JPanel livePanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                6,
                                4
                        )
                );

        livePanel.setOpaque(false);

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
                        "LIVE INCIDENT CONTROL"
                );

        liveLabel.setFont(
                Theme.SMALL_FONT
        );

        liveLabel.setForeground(
                Theme.MUTED_TEXT
        );

        livePanel.add(liveDot);
        livePanel.add(liveLabel);

        headerPanel.add(
                livePanel,
                BorderLayout.EAST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // MAIN SPLIT PANE
        // =====================================================

        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        createFormPanel(),
                        createTablePanel()
                );

        splitPane.setResizeWeight(
                0.40
        );

        splitPane.setDividerLocation(
                360
        );

        splitPane.setDividerSize(
                7
        );

        splitPane.setContinuousLayout(
                true
        );

        splitPane.setBorder(null);

        // Critical for responsive resizing: neither side gets a
        // large preferred minimum width that can push the other side away.
        splitPane.getLeftComponent().setMinimumSize(
                new Dimension(280, 0)
        );

        splitPane.getRightComponent().setMinimumSize(
                new Dimension(260, 0)
        );

        add(
                splitPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM ACTION AREA
        // =====================================================

        add(
                createBottomActionPanel(),
                BorderLayout.SOUTH
        );
    }


    // =========================================================
    // FORM PANEL
    // =========================================================

    private JPanel createFormPanel() {

        // =====================================================
        // OUTER CARD
        // =====================================================

        JPanel outerPanel =
                new JPanel(
                        new BorderLayout(0, 6)
                );

        outerPanel.setBackground(
                Theme.ALMOND_CREAM
        );

        outerPanel.setMinimumSize(
                new Dimension(280, 0)
        );

        outerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        Theme.createRoundedBorder(
                                Theme.KHAKI_BEIGE,
                                14,
                                1,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );


        JLabel formTitle =
                new JLabel(
                        "EMERGENCY DETAILS"
                );

        formTitle.setFont(
                Theme.SECTION_FONT
        );

        formTitle.setForeground(
                Theme.TEXT
        );

        formTitle.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        3,
                        5,
                        3
                )
        );

        outerPanel.add(
                formTitle,
                BorderLayout.NORTH
        );


        // =====================================================
        // ACTUAL FORM CONTENT
        // =====================================================

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setOpaque(false);


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(4, 4, 4, 4);

        gbc.anchor =
                GridBagConstraints.NORTHWEST;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =====================================================
        // EMERGENCY ID
        // =====================================================

        JLabel idLabel =
                new JLabel(
                        "Emergency ID"
                );

        emergencyIdField =
                new JTextField();

        emergencyIdField.setEditable(false);

        styleTextField(
                emergencyIdField
        );

        addFormRow(
                formPanel,
                gbc,
                0,
                idLabel,
                emergencyIdField
        );


        // =====================================================
        // EMERGENCY TYPE
        // =====================================================

        JLabel typeLabel =
                new JLabel(
                        "Emergency Type"
                );

        typeComboBox =
                new JComboBox<>(
                        EmergencyType.values()
                );

        styleComboBox(
                typeComboBox
        );

        typeComboBox.addActionListener(
                e -> updateGeneratedId()
        );

        addFormRow(
                formPanel,
                gbc,
                1,
                typeLabel,
                typeComboBox
        );


        // =====================================================
        // PRIORITY
        // =====================================================

        JLabel priorityLabel =
                new JLabel(
                        "Priority"
                );

        priorityComboBox =
                new JComboBox<>(
                        Priority.values()
                );

        styleComboBox(
                priorityComboBox
        );

        addFormRow(
                formPanel,
                gbc,
                2,
                priorityLabel,
                priorityComboBox
        );


        // =====================================================
        // LOCATION
        // =====================================================

        JLabel locationLabel =
                new JLabel(
                        "Location"
                );

        locationField =
                new JTextField();

        styleTextField(
                locationField
        );

        addFormRow(
                formPanel,
                gbc,
                3,
                locationLabel,
                locationField
        );


        // =====================================================
        // DATE / TIME
        // =====================================================

        JLabel dateTimeLabel =
                new JLabel(
                        "Date / Time"
                );

        dateTimeField =
                new JTextField();

        dateTimeField.setEditable(false);

        styleTextField(
                dateTimeField
        );

        addFormRow(
                formPanel,
                gbc,
                4,
                dateTimeLabel,
                dateTimeField
        );


        // =====================================================
        // STATUS
        // =====================================================

        JLabel statusLabel =
                new JLabel(
                        "Status"
                );

        statusComboBox =
                new JComboBox<>(
                        EmergencyStatus.values()
                );

        statusComboBox.setEnabled(false);

        styleComboBox(
                statusComboBox
        );

        addFormRow(
                formPanel,
                gbc,
                5,
                statusLabel,
                statusComboBox
        );


        // =====================================================
        // DESCRIPTION
        // =====================================================

        JLabel descriptionLabel =
                new JLabel(
                        "Description"
                );

        descriptionArea =
                new JTextArea(
                        5,
                        20
                );

        descriptionArea.setFont(
                Theme.NORMAL_FONT
        );

        descriptionArea.setForeground(
                Theme.TEXT
        );

        descriptionArea.setBackground(
                Theme.WHITE
        );

        descriptionArea.setLineWrap(
                true
        );

        descriptionArea.setWrapStyleWord(
                true
        );

        descriptionArea.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        6,
                        5,
                        6
                )
        );

        JScrollPane descriptionScrollPane =
                new JScrollPane(
                        descriptionArea
                );

        descriptionScrollPane.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        10,
                        1,
                        0
                )
        );

        descriptionScrollPane.setPreferredSize(
                new Dimension(
                        0,
                        85
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        formPanel.add(
                descriptionLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;

        formPanel.add(
                descriptionScrollPane,
                gbc
        );


        // =====================================================
        // VIEWPORT
        // =====================================================

        JScrollPane formScrollPane =
                new JScrollPane(
                        formPanel
                );

        formScrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        formScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        formScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        formScrollPane.getViewport()
                .setOpaque(false);

        formScrollPane.setOpaque(false);


        outerPanel.add(
                formScrollPane,
                BorderLayout.CENTER
        );

        return outerPanel;
    }


    // =========================================================
    // TABLE PANEL
    // =========================================================

    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(0, 8)
                );

        panel.setBackground(
                Theme.BACKGROUND
        );

        panel.setMinimumSize(
                new Dimension(260, 0)
        );

        // =====================================================
        // SEARCH AREA
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(7, 0)
                );

        searchPanel.setOpaque(false);

        JLabel searchLabel =
                new JLabel("SEARCH");

        searchLabel.setFont(
                Theme.SMALL_FONT
        );

        searchLabel.setForeground(
                Theme.TEXT
        );

        searchField =
                new JTextField();

        styleTextField(searchField);

        searchField.setPreferredSize(
                new Dimension(170, 34)
        );

        JPanel searchInputPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        searchInputPanel.setOpaque(false);

        searchInputPanel.add(searchField);

        JButton searchButton =
                new JButton("SEARCH");

        Theme.stylePrimaryButton(searchButton);

        JButton clearSearchButton =
                new JButton("CLEAR");

        Theme.styleSecondaryButton(clearSearchButton);

        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchInputPanel,
                BorderLayout.CENTER
        );

        JPanel searchButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                5,
                                0
                        )
                );

        searchButtons.setOpaque(false);

        searchButtons.add(searchButton);
        searchButtons.add(clearSearchButton);

        searchPanel.add(
                searchButtons,
                BorderLayout.EAST
        );

        panel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Emergency ID",
                "Type",
                "Priority",
                "Location",
                "Status",
                "Assigned Team",
                "Date / Time"
        };

        tableModel =
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

        emergencyTable =
                new JTable(
                        tableModel
                );

        Theme.styleTable(emergencyTable);

        emergencyTable.setRowHeight(29);

        emergencyTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        emergencyTable.setFillsViewportHeight(true);

        emergencyTable.getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane tableScrollPane =
                new JScrollPane(
                        emergencyTable
                );

        tableScrollPane.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        12,
                        1,
                        1
                )
        );

        tableScrollPane.getViewport()
                .setBackground(
                        Theme.WHITE
                );

        panel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // SEARCH ACTIONS
        // =====================================================

        searchButton.addActionListener(
                e -> searchEmergencies()
        );

        searchField.addActionListener(
                e -> searchEmergencies()
        );

        clearSearchButton.addActionListener(
                e -> {

                    searchField.setText("");

                    loadEmergencies();
                }
        );

        // =====================================================
        // TABLE ROW SELECTION
        // =====================================================

        emergencyTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                loadSelectedEmergency();
                            }
                        }
                );

        return panel;
    }


    // =========================================================
    // BOTTOM ACTION PANEL
    // =========================================================

    private JPanel createBottomActionPanel() {

        JPanel wrapper =
                new JPanel(
                        new BorderLayout(
                                0,
                                4
                        )
                );

        wrapper.setBackground(
                Theme.BACKGROUND
        );

        wrapper.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        18,
                        10,
                        18
                )
        );


        // =====================================================
        // RESPONSE WORKFLOW
        // =====================================================

        JPanel workflowPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        workflowPanel.setBackground(
                Theme.BACKGROUND
        );


        JLabel workflowLabel =
                new JLabel(
                        "RESPONSE WORKFLOW"
                );

        workflowLabel.setFont(
                Theme.SMALL_FONT
        );

        workflowLabel.setForeground(
                Theme.MUTED_TEXT
        );


        startResponseButton =
                new JButton(
                        "START RESPONSE"
                );

        Theme.stylePrimaryButton(
                startResponseButton
        );


        resolveButton =
                new JButton(
                        "RESOLVE"
                );

        Theme.stylePrimaryButton(
                resolveButton
        );


        cancelEmergencyButton =
                new JButton(
                        "CANCEL EMERGENCY"
                );

        Theme.styleDangerButton(
                cancelEmergencyButton
        );


        workflowPanel.add(
                workflowLabel
        );

        workflowPanel.add(
                startResponseButton
        );

        workflowPanel.add(
                resolveButton
        );

        workflowPanel.add(
                cancelEmergencyButton
        );


        wrapper.add(
                workflowPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CRUD ROW - ALWAYS AT THE BOTTOM
        // =====================================================

        JPanel crudPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        crudPanel.setBackground(
                Theme.BACKGROUND
        );


        addButton =
                new JButton(
                        "ADD"
                );

        Theme.stylePrimaryButton(
                addButton
        );


        updateButton =
                new JButton(
                        "UPDATE"
                );

        Theme.styleSecondaryButton(
                updateButton
        );


        deleteButton =
                new JButton(
                        "DELETE"
                );

        Theme.styleDangerButton(
                deleteButton
        );


        clearFormButton =
                new JButton(
                        "CLEAR FORM"
                );

        Theme.styleSecondaryButton(
                clearFormButton
        );


        crudPanel.add(
                addButton
        );

        crudPanel.add(
                updateButton
        );

        crudPanel.add(
                deleteButton
        );

        crudPanel.add(
                clearFormButton
        );


        wrapper.add(
                crudPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addButton.addActionListener(
                e -> addEmergency()
        );

        updateButton.addActionListener(
                e -> updateEmergency()
        );

        deleteButton.addActionListener(
                e -> deleteEmergency()
        );

        clearFormButton.addActionListener(
                e -> clearForm()
        );

        startResponseButton.addActionListener(
                e -> startResponse()
        );

        resolveButton.addActionListener(
                e -> resolveEmergency()
        );

        cancelEmergencyButton.addActionListener(
                e -> cancelEmergency()
        );

        updateWorkflowButtons();

        return wrapper;
    }


    // =========================================================
    // ADD EMERGENCY
    // =========================================================

    private void addEmergency() {

        EmergencyType type =
                (EmergencyType)
                        typeComboBox
                                .getSelectedItem();


        Priority priority =
                (Priority)
                        priorityComboBox
                                .getSelectedItem();


        String location =
                locationField
                        .getText()
                        .trim();


        String description =
                descriptionArea
                        .getText()
                        .trim();


        if (type == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency type.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (priority == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a priority.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (location.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the emergency location.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            locationField.requestFocus();

            return;
        }


        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the emergency description.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            descriptionArea.requestFocus();

            return;
        }


        String emergencyId =
                emergencyManager
                        .generateEmergencyId(
                                type
                        );


        String dateTime =
                new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm"
                ).format(
                        new Date()
                );


        Emergency emergency =
                new Emergency(
                        emergencyId,
                        type,
                        priority,
                        location,
                        description,
                        dateTime
                );


        boolean added =
                emergencyManager.addEmergency(
                        emergency
                );


        if (!added) {

            JOptionPane.showMessageDialog(
                    this,
                    "Emergency could not be added.\n"
                            + "The generated ID may already exist.",
                    "Add Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // =====================================================
        // AUTOMATIC CRITICAL ASSIGNMENT
        // =====================================================

        boolean automaticallyAssigned =
                false;

        String assignedTeamId =
                null;


        if (
                priority == Priority.CRITICAL
                        && assignmentManager != null
                        && teamManager != null
        ) {

            ArrayList<ResponseTeam> suitableTeams =
                    teamManager.findSuitableTeams(
                            emergency.getType()
                    );


            if (!suitableTeams.isEmpty()) {

                ResponseTeam team =
                        suitableTeams.get(0);


                Assignment createdAssignment =
                        assignmentManager.assignTeam(
                                emergencyId,
                                team.getTeamId(),
                                dateTime,
                                ""
                        );

                automaticallyAssigned =
                        createdAssignment != null;

                if (automaticallyAssigned) {

                    assignedTeamId =
                            team.getTeamId();
                }
            }
        }


        if (automaticallyAssigned) {

            emergencyManager.saveData();

            teamManager.saveData();

        } else {

            emergencyManager.saveData();
        }
        // =====================================================
        // RESULT MESSAGE
        // =====================================================

        String message;

        if (automaticallyAssigned) {

            message =
                    "Emergency added successfully.\n\n"
                            + "Emergency ID: "
                            + emergencyId
                            + "\n"
                            + "Priority: "
                            + priority
                            + "\n"
                            + "Automatically Assigned Team: "
                            + assignedTeamId
                            + "\n"
                            + "Status: ASSIGNED";

        } else if (
                priority == Priority.CRITICAL
        ) {

            message =
                    "Critical emergency added successfully.\n\n"
                            + "Emergency ID: "
                            + emergencyId
                            + "\n"
                            + "No suitable available team was found.\n"
                            + "Status: PENDING";

        } else {

            message =
                    "Emergency added successfully.\n\n"
                            + "Emergency ID: "
                            + emergencyId
                            + "\n"
                            + "Status: PENDING";
        }


        JOptionPane.showMessageDialog(
                this,
                message,
                "Emergency Added",
                JOptionPane.INFORMATION_MESSAGE
        );


        clearForm();

        loadEmergencies();
    }


    // =========================================================
    // UPDATE EMERGENCY
    // =========================================================

    private void updateEmergency() {

        if (selectedEmergency == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        EmergencyStatus currentStatus =
                selectedEmergency.getStatus();


        if (
                currentStatus == EmergencyStatus.RESOLVED
                        || currentStatus == EmergencyStatus.CANCELLED
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Resolved or cancelled emergencies cannot be edited.",
                    "Update Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        EmergencyType newType =
                (EmergencyType)
                        typeComboBox
                                .getSelectedItem();


        Priority newPriority =
                (Priority)
                        priorityComboBox
                                .getSelectedItem();


        String newLocation =
                locationField
                        .getText()
                        .trim();


        String newDescription =
                descriptionArea
                        .getText()
                        .trim();


        if (newType == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency type.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (newPriority == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a priority.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (newLocation.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the emergency location.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (newDescription.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the emergency description.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // =====================================================
        // UPDATE MODEL
        // =====================================================

        selectedEmergency.setType(
                newType
        );

        selectedEmergency.setPriority(
                newPriority
        );

        selectedEmergency.setLocation(
                newLocation
        );

        selectedEmergency.setDescription(
                newDescription
        );


        emergencyManager.saveData();


        JOptionPane.showMessageDialog(
                this,
                "Emergency updated successfully.",
                "Update Successful",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadEmergencies();


        selectEmergencyInTable(
                selectedEmergency.getEmergencyId()
        );
    }


    // =========================================================
    // DELETE EMERGENCY
    // =========================================================

    private void deleteEmergency() {

        if (selectedEmergency == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        EmergencyStatus status =
                selectedEmergency.getStatus();


        if (
                status == EmergencyStatus.ASSIGNED
                        || status == EmergencyStatus.IN_PROGRESS
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This emergency cannot be deleted while response work is active.",
                    "Delete Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete emergency "
                                + selectedEmergency.getEmergencyId()
                                + "?",
                        "Delete Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                result != JOptionPane.YES_OPTION
        ) {

            return;
        }


        String emergencyId =
                selectedEmergency
                        .getEmergencyId();


        emergencyManager.removeEmergency(
                emergencyId
        );


        emergencyManager.saveData();


        JOptionPane.showMessageDialog(
                this,
                "Emergency deleted successfully.",
                "Delete Successful",
                JOptionPane.INFORMATION_MESSAGE
        );


        clearForm();

        loadEmergencies();
    }


    // =========================================================
    // START RESPONSE
    // =========================================================

    private void startResponse() {

        if (selectedEmergency == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (
                selectedEmergency.getStatus()
                        != EmergencyStatus.ASSIGNED
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only assigned emergencies can be started.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        boolean updated =
                emergencyManager.updateEmergencyStatus(
                        selectedEmergency.getEmergencyId(),
                        EmergencyStatus.IN_PROGRESS
                );


        if (!updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "The response could not be started.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        saveAllRelatedData();


        JOptionPane.showMessageDialog(
                this,
                "Emergency response has started.\n\n"
                        + "Emergency: "
                        + selectedEmergency.getEmergencyId()
                        + "\n"
                        + "Status: IN PROGRESS",
                "Response Started",
                JOptionPane.INFORMATION_MESSAGE
        );


        refreshAfterWorkflowChange();
    }


    // =========================================================
    // RESOLVE EMERGENCY
    // =========================================================

    private void resolveEmergency() {

        if (selectedEmergency == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (
                selectedEmergency.getStatus()
                        != EmergencyStatus.IN_PROGRESS
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Only an emergency that is IN PROGRESS can be resolved.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Mark emergency "
                                + selectedEmergency.getEmergencyId()
                                + " as RESOLVED?",
                        "Resolve Emergency",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );


        if (
                result != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean updated =
                emergencyManager.updateEmergencyStatus(
                        selectedEmergency.getEmergencyId(),
                        EmergencyStatus.RESOLVED
                );


        if (!updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "The emergency could not be resolved.",
                    "Resolve Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        /*
         * RESOLVED status also releases the assigned team
         * inside EmergencyManager.
         *
         * saveAllRelatedData() persists:
         * - emergency
         * - team
         * - assignment
         */
        saveAllRelatedData();


        JOptionPane.showMessageDialog(
                this,
                "Emergency resolved successfully.\n\n"
                        + "Emergency: "
                        + selectedEmergency.getEmergencyId()
                        + "\n"
                        + "Status: RESOLVED\n"
                        + "Assigned team is now AVAILABLE.",
                "Emergency Resolved",
                JOptionPane.INFORMATION_MESSAGE
        );


        refreshAfterWorkflowChange();
    }


    // =========================================================
    // CANCEL EMERGENCY
    // =========================================================

    private void cancelEmergency() {

        if (selectedEmergency == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        EmergencyStatus status =
                selectedEmergency.getStatus();


        if (
                status == EmergencyStatus.RESOLVED
                        || status == EmergencyStatus.CANCELLED
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "This emergency can no longer be cancelled.",
                    "Action Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Cancel emergency "
                                + selectedEmergency.getEmergencyId()
                                + "?",
                        "Cancel Emergency",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                result != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean updated =
                emergencyManager.updateEmergencyStatus(
                        selectedEmergency.getEmergencyId(),
                        EmergencyStatus.CANCELLED
                );


        if (!updated) {

            JOptionPane.showMessageDialog(
                    this,
                    "The emergency could not be cancelled.",
                    "Cancel Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        saveAllRelatedData();


        JOptionPane.showMessageDialog(
                this,
                "Emergency cancelled successfully.",
                "Emergency Cancelled",
                JOptionPane.INFORMATION_MESSAGE
        );


        refreshAfterWorkflowChange();
    }


    // =========================================================
    // SAVE ALL RELATED DATA
    // =========================================================

    private void saveAllRelatedData() {

        emergencyManager.saveData();

        if (teamManager != null) {
            teamManager.saveData();
        }
    }


    // =========================================================
    // REFRESH AFTER WORKFLOW
    // =========================================================

    private void refreshAfterWorkflowChange() {

        String emergencyId =
                selectedEmergency != null
                        ? selectedEmergency.getEmergencyId()
                        : null;


        loadEmergencies();


        if (emergencyId != null) {

            selectEmergencyInTable(
                    emergencyId
            );
        }
    }


    // =========================================================
    // LOAD TABLE
    // =========================================================

    public void loadEmergencies() {

        displayEmergencies(
                emergencyManager
                        .getAllEmergencies()
        );
    }


    // =========================================================
    // DISPLAY EMERGENCIES
    // =========================================================

    private void displayEmergencies(
            ArrayList<Emergency> emergencies
    ) {

        tableModel.setRowCount(
                0
        );

        selectedEmergency =
                null;

        updateWorkflowButtons();


        for (
                Emergency emergency :
                emergencies
        ) {

            String assignedTeam =
                    emergency.getAssignedTeamId();


            if (
                    assignedTeam == null
                            || assignedTeam.trim().isEmpty()
            ) {

                assignedTeam =
                        "-";
            }


            tableModel.addRow(
                    new Object[]{

                            emergency.getEmergencyId(),

                            formatEmergencyType(
                                    emergency.getType()
                            ),

                            formatPriority(
                                    emergency.getPriority()
                            ),

                            emergency.getLocation(),

                            formatStatus(
                                    emergency.getStatus()
                            ),

                            assignedTeam,

                            emergency.getDateTime()
                    }
            );
        }
    }


    // =========================================================
    // SEARCH EMERGENCIES
    // =========================================================

    private void searchEmergencies() {

        String keyword =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        if (keyword.isEmpty()) {

            loadEmergencies();

            return;
        }


        ArrayList<Emergency> results =
                new ArrayList<>();


        for (
                Emergency emergency :
                emergencyManager.getAllEmergencies()
        ) {

            String assignedTeam =
                    emergency.getAssignedTeamId();


            if (assignedTeam == null) {
                assignedTeam = "";
            }


            boolean matches =
                    contains(
                            emergency.getEmergencyId(),
                            keyword
                    )
                            || contains(
                            emergency.getLocation(),
                            keyword
                    )
                            || contains(
                            emergency.getDescription(),
                            keyword
                    )
                            || contains(
                            formatEmergencyType(
                                    emergency.getType()
                            ),
                            keyword
                    )
                            || contains(
                            formatPriority(
                                    emergency.getPriority()
                            ),
                            keyword
                    )
                            || contains(
                            formatStatus(
                                    emergency.getStatus()
                            ),
                            keyword
                    )
                            || contains(
                            assignedTeam,
                            keyword
                    );


            if (matches) {

                results.add(
                        emergency
                );
            }
        }


        displayEmergencies(
                results
        );


        if (
                results.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "No matching emergency was found.",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    // =========================================================
    // LOAD SELECTED EMERGENCY
    // =========================================================

    private void loadSelectedEmergency() {

        int selectedRow =
                emergencyTable.getSelectedRow();


        if (selectedRow < 0) {

            selectedEmergency =
                    null;

            updateWorkflowButtons();

            return;
        }


        String emergencyId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();


        selectedEmergency =
                emergencyManager
                        .findEmergencyById(
                                emergencyId
                        );


        if (
                selectedEmergency == null
        ) {

            return;
        }


        emergencyIdField.setText(
                selectedEmergency
                        .getEmergencyId()
        );


        typeComboBox.setSelectedItem(
                selectedEmergency
                        .getType()
        );


        priorityComboBox.setSelectedItem(
                selectedEmergency
                        .getPriority()
        );


        locationField.setText(
                selectedEmergency
                        .getLocation()
        );


        dateTimeField.setText(
                selectedEmergency
                        .getDateTime()
        );


        statusComboBox.setSelectedItem(
                selectedEmergency
                        .getStatus()
        );


        descriptionArea.setText(
                selectedEmergency
                        .getDescription()
        );


        updateGeneratedIdDisplay();

        updateWorkflowButtons();
    }


    // =========================================================
    // SELECT ROW BY ID
    // =========================================================

    private void selectEmergencyInTable(
            String emergencyId
    ) {

        if (emergencyId == null) {
            return;
        }


        for (
                int i = 0;
                i < tableModel.getRowCount();
                i++
        ) {

            String tableId =
                    tableModel
                            .getValueAt(
                                    i,
                                    0
                            )
                            .toString();


            if (
                    tableId.equalsIgnoreCase(
                            emergencyId
                    )
            ) {

                emergencyTable
                        .setRowSelectionInterval(
                                i,
                                i
                        );

                emergencyTable
                        .scrollRectToVisible(
                                emergencyTable
                                        .getCellRect(
                                                i,
                                                0,
                                                true
                                        )
                        );

                break;
            }
        }
    }


    // =========================================================
    // UPDATE GENERATED ID
    // =========================================================

    private void updateGeneratedId() {

        if (
                selectedEmergency != null
                        && emergencyTable
                        .getSelectedRow() >= 0
        ) {

            return;
        }


        EmergencyType type =
                (EmergencyType)
                        typeComboBox
                                .getSelectedItem();


        if (type == null) {

            emergencyIdField.setText(
                    ""
            );

            return;
        }


        String generatedId =
                emergencyManager
                        .generateEmergencyId(
                                type
                        );


        emergencyIdField.setText(
                generatedId
        );


        dateTimeField.setText(
                new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm"
                ).format(
                        new Date()
                )
        );


        statusComboBox.setSelectedItem(
                EmergencyStatus.PENDING
        );
    }


    // =========================================================
    // KEEP ID WHEN EXISTING RECORD IS SELECTED
    // =========================================================

    private void updateGeneratedIdDisplay() {

        if (
                selectedEmergency != null
        ) {

            emergencyIdField.setText(
                    selectedEmergency
                            .getEmergencyId()
            );
        }
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        selectedEmergency =
                null;


        emergencyTable.clearSelection();


        typeComboBox.setSelectedIndex(
                0
        );


        priorityComboBox.setSelectedIndex(
                0
        );


        locationField.setText(
                ""
        );


        descriptionArea.setText(
                ""
        );


        statusComboBox.setSelectedItem(
                EmergencyStatus.PENDING
        );


        dateTimeField.setText(
                new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm"
                ).format(
                        new Date()
                )
        );


        updateGeneratedId();


        updateWorkflowButtons();
    }


    // =========================================================
    // WORKFLOW BUTTON STATES
    // =========================================================

    private void updateWorkflowButtons() {

        if (
                startResponseButton == null
                        || resolveButton == null
                        || cancelEmergencyButton == null
        ) {

            return;
        }


        startResponseButton.setEnabled(
                false
        );


        resolveButton.setEnabled(
                false
        );


        cancelEmergencyButton.setEnabled(
                false
        );


        if (
                selectedEmergency == null
        ) {

            return;
        }


        EmergencyStatus status =
                selectedEmergency.getStatus();


        if (
                status == EmergencyStatus.ASSIGNED
        ) {

            startResponseButton.setEnabled(
                    true
            );


            cancelEmergencyButton.setEnabled(
                    true
            );
        }


        else if (
                status == EmergencyStatus.IN_PROGRESS
        ) {

            resolveButton.setEnabled(
                    true
            );


            cancelEmergencyButton.setEnabled(
                    true
            );
        }


        else if (
                status == EmergencyStatus.PENDING
        ) {

            cancelEmergencyButton.setEnabled(
                    true
            );
        }
    }


    // =========================================================
    // FORM ROW HELPER
    // =========================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            JLabel label,
            JComponent component
    ) {

        gbc.gridx = 0;

        gbc.gridy = row;

        gbc.weightx = 0.0;

        gbc.weighty = 0.0;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        panel.add(
                label,
                gbc
        );


        gbc.gridx = 1;

        gbc.gridy = row;

        gbc.weightx = 1.0;

        gbc.weighty = 0.0;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        panel.add(
                component,
                gbc
        );
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

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.STONE_BROWN
                        ),
                        BorderFactory.createEmptyBorder(
                                6,
                                8,
                                6,
                                8
                        )
                )
        );
    }


    // =========================================================
    // COMBO BOX STYLE
    // =========================================================

    private void styleComboBox(
            JComboBox<?> comboBox
    ) {

        comboBox.setFont(
                Theme.NORMAL_FONT
        );

        comboBox.setForeground(
                Theme.TEXT
        );

        comboBox.setBackground(
                Theme.WHITE
        );

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        Theme.STONE_BROWN
                )
        );
    }


    // =========================================================
    // SEARCH HELPER
    // =========================================================

    private boolean contains(
            String value,
            String keyword
    ) {

        if (
                value == null
                        || keyword == null
        ) {

            return false;
        }


        return value
                .toLowerCase()
                .contains(
                        keyword
                );
    }


    // =========================================================
    // FORMAT EMERGENCY TYPE
    // =========================================================

    private String formatEmergencyType(
            EmergencyType type
    ) {

        if (type == null) {
            return "N/A";
        }


        return formatEnumText(
                type.toString()
        );
    }


    // =========================================================
    // FORMAT PRIORITY
    // =========================================================

    private String formatPriority(
            Priority priority
    ) {

        if (priority == null) {
            return "N/A";
        }


        return formatEnumText(
                priority.toString()
        );
    }


    // =========================================================
    // FORMAT STATUS
    // =========================================================

    private String formatStatus(
            EmergencyStatus status
    ) {

        if (status == null) {
            return "N/A";
        }


        return formatEnumText(
                status.toString()
        );
    }


    // =========================================================
    // FORMAT ENUM TEXT
    // =========================================================

    private String formatEnumText(
            String value
    ) {

        String text =
                value
                        .replace(
                                "_",
                                " "
                        )
                        .toLowerCase();


        String[] words =
                text.split(
                        " "
                );


        StringBuilder result =
                new StringBuilder();


        for (
                String word :
                words
        ) {

            if (
                    word.isEmpty()
            ) {

                continue;
            }


            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );


            if (
                    word.length() > 1
            ) {

                result.append(
                        word.substring(1)
                );
            }


            result.append(
                    " "
            );
        }


        return result
                .toString()
                .trim();
    }
}

