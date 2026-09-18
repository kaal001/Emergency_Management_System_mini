package ui;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Emergency;
import model.ResponseTeam;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmergencyManagementPanel
        extends JPanel {

    // =========================================================
    // MANAGERS
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

    private JTextArea descriptionArea;

    private JTextField dateTimeField;

    private JComboBox<EmergencyStatus> statusComboBox;


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

        buildUI();

        loadEmergencies();

        clearForm();
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        setLayout(
                new BorderLayout(
                        0,
                        15
                )
        );

        setBackground(
                Theme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );


        add(
                createPageHeader(),
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN MANAGEMENT AREA
        // =====================================================

        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        createFormScrollPane(),
                        createTablePanel()
                );


        splitPane.setDividerLocation(
                420
        );


        splitPane.setResizeWeight(
                0.34
        );


        splitPane.setContinuousLayout(
                true
        );


        splitPane.setBorder(
                null
        );


        add(
                splitPane,
                BorderLayout.CENTER
        );


        add(
                createActionBar(),
                BorderLayout.SOUTH
        );
    }


    // =========================================================
    // PAGE HEADER
    // =========================================================

    private JPanel createPageHeader() {

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
                        "EMERGENCY MANAGEMENT"
                );

        title.setFont(
                Theme.PAGE_TITLE_FONT
        );

        title.setForeground(
                Theme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Record and manage emergency incidents."
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
                        3
                )
        );


        titlePanel.add(
                subtitle
        );


        panel.add(
                titlePanel,
                BorderLayout.WEST
        );


        return panel;
    }


    // =========================================================
    // FORM SCROLL PANE
    // =========================================================

    private JScrollPane createFormScrollPane() {

        JPanel outerPanel =
                createFormPanel();


        JScrollPane scrollPane =
                new JScrollPane(
                        outerPanel
                );


        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        Theme.KHAKI_BEIGE
                )
        );


        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        14
                );


        return scrollPane;
    }


    // =========================================================
    // FORM PANEL
    // =========================================================

    private JPanel createFormPanel() {

        JPanel outerPanel =
                new JPanel(
                        new BorderLayout()
                );


        outerPanel.setBackground(
                Theme.ALMOND_CREAM
        );


        outerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        18,
                        18,
                        18
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


        outerPanel.add(
                formTitle,
                BorderLayout.NORTH
        );


        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );


        form.setBackground(
                Theme.ALMOND_CREAM
        );


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        8,
                        5,
                        8,
                        5
                );


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        gbc.anchor =
                GridBagConstraints.NORTH;


        // =====================================================
        // ID
        // =====================================================

        emergencyIdField =
                new JTextField();


        styleTextField(
                emergencyIdField
        );


        emergencyIdField.setEditable(
                false
        );


        emergencyIdField.setBackground(
                new Color(
                        0xDDD2C5
                )
        );


        addFormRow(
                form,
                gbc,
                0,
                "Emergency ID",
                emergencyIdField
        );


        // =====================================================
        // TYPE
        // =====================================================

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
                form,
                gbc,
                1,
                "Emergency Type",
                typeComboBox
        );


        // =====================================================
        // PRIORITY
        // =====================================================

        priorityComboBox =
                new JComboBox<>(
                        Priority.values()
                );


        styleComboBox(
                priorityComboBox
        );


        priorityComboBox.setSelectedItem(
                Priority.HIGH
        );


        addFormRow(
                form,
                gbc,
                2,
                "Priority",
                priorityComboBox
        );


        // =====================================================
        // LOCATION
        // =====================================================

        locationField =
                new JTextField();


        styleTextField(
                locationField
        );


        locationField.setToolTipText(
                "Example: Building A, Floor 2, Room 204"
        );


        addFormRow(
                form,
                gbc,
                3,
                "Location",
                locationField
        );


        // =====================================================
        // DATE / TIME
        // =====================================================

        dateTimeField =
                new JTextField();


        styleTextField(
                dateTimeField
        );


        dateTimeField.setEditable(
                false
        );


        dateTimeField.setBackground(
                new Color(
                        0xDDD2C5
                )
        );


        addFormRow(
                form,
                gbc,
                4,
                "Date / Time",
                dateTimeField
        );


        // =====================================================
        // STATUS
        // =====================================================

        statusComboBox =
                new JComboBox<>(
                        EmergencyStatus.values()
                );


        styleComboBox(
                statusComboBox
        );


        statusComboBox.setEnabled(
                false
        );


        addFormRow(
                form,
                gbc,
                5,
                "Status",
                statusComboBox
        );


        // =====================================================
        // DESCRIPTION
        // =====================================================

        descriptionArea =
                new JTextArea(
                        6,
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


        descriptionArea.setMargin(
                new Insets(
                        7,
                        7,
                        7,
                        7
                )
        );


        descriptionArea.setBorder(
                BorderFactory.createLineBorder(
                        Theme.STONE_BROWN
                )
        );


        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );


        descriptionScroll.setPreferredSize(
                new Dimension(
                        220,
                        135
                )
        );


        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.35;
        gbc.weighty = 1.0;


        JLabel descriptionLabel =
                new JLabel(
                        "Description"
                );


        descriptionLabel.setFont(
                Theme.SUBTITLE_FONT
        );


        descriptionLabel.setForeground(
                Theme.TEXT
        );


        form.add(
                descriptionLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 0.65;
        gbc.weighty = 1.0;


        gbc.fill =
                GridBagConstraints.BOTH;


        form.add(
                descriptionScroll,
                gbc
        );


        outerPanel.add(
                form,
                BorderLayout.CENTER
        );


        return outerPanel;
    }


    // =========================================================
    // FORM ROW
    // =========================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            Component component
    ) {

        JLabel label =
                new JLabel(
                        labelText
                );


        label.setFont(
                Theme.SUBTITLE_FONT
        );


        label.setForeground(
                Theme.TEXT
        );


        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.35;
        gbc.weighty = 0;


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        panel.add(
                label,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 0.65;


        panel.add(
                component,
                gbc
        );
    }


    // =========================================================
    // TABLE PANEL
    // =========================================================

    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                10
                        )
                );


        panel.setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // SEARCH
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );


        searchPanel.setBackground(
                Theme.BACKGROUND
        );


        JLabel searchLabel =
                new JLabel(
                        "SEARCH"
                );


        searchLabel.setFont(
                Theme.SUBTITLE_FONT
        );


        searchLabel.setForeground(
                Theme.TEXT
        );


        searchField =
                new JTextField();


        styleTextField(
                searchField
        );


        JButton searchButton =
                new JButton(
                        "SEARCH"
                );


        Theme.stylePrimaryButton(
                searchButton
        );


        JButton clearSearchButton =
                new JButton(
                        "CLEAR"
                );


        Theme.styleSecondaryButton(
                clearSearchButton
        );


        searchButton.addActionListener(
                e -> searchEmergencies()
        );


        clearSearchButton.addActionListener(
                e -> {

                    searchField.setText(
                            ""
                    );


                    loadEmergencies();
                }
        );


        searchPanel.add(
                searchLabel,
                BorderLayout.WEST
        );


        searchPanel.add(
                searchField,
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


        searchButtons.setBackground(
                Theme.BACKGROUND
        );


        searchButtons.add(
                searchButton
        );


        searchButtons.add(
                clearSearchButton
        );


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


        styleTable(
                emergencyTable
        );


        emergencyTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (
                                    !e.getValueIsAdjusting()
                            ) {

                                selectEmergency();
                            }
                        }
                );


        JScrollPane tableScroll =
                new JScrollPane(
                        emergencyTable
                );


        tableScroll.setBorder(
                BorderFactory.createLineBorder(
                        Theme.STONE_BROWN
                )
        );


        panel.add(
                tableScroll,
                BorderLayout.CENTER
        );


        return panel;
    }


    // =========================================================
    // ACTION BAR
    // =========================================================

    private JPanel createActionBar() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );


        panel.setBackground(
                Theme.BACKGROUND
        );


        JButton addButton =
                new JButton(
                        "ADD"
                );


        Theme.stylePrimaryButton(
                addButton
        );


        JButton updateButton =
                new JButton(
                        "UPDATE"
                );


        Theme.styleSecondaryButton(
                updateButton
        );


        JButton deleteButton =
                new JButton(
                        "DELETE"
                );


        Theme.styleDangerButton(
                deleteButton
        );


        JButton clearButton =
                new JButton(
                        "CLEAR FORM"
                );


        Theme.styleSecondaryButton(
                clearButton
        );


        addButton.addActionListener(
                e -> addEmergency()
        );


        updateButton.addActionListener(
                e -> updateEmergency()
        );


        deleteButton.addActionListener(
                e -> deleteEmergency()
        );


        clearButton.addActionListener(
                e -> clearForm()
        );


        panel.add(
                addButton
        );


        panel.add(
                updateButton
        );


        panel.add(
                deleteButton
        );


        panel.add(
                clearButton
        );


        return panel;
    }


    // =========================================================
    // UPDATE GENERATED ID
    // =========================================================

    private void updateGeneratedId() {

        /*
         * Do not change the ID while an existing row is selected.
         */
        if (
                emergencyTable != null
                        &&
                        emergencyTable.getSelectedRow() >= 0
        ) {

            return;
        }


        EmergencyType selectedType =
                (EmergencyType)
                        typeComboBox
                                .getSelectedItem();


        if (selectedType == null) {
            return;
        }


        emergencyIdField.setText(
                emergencyManager
                        .generateEmergencyId(
                                selectedType
                        )
        );
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


        String dateTime =
                dateTimeField
                        .getText()
                        .trim();


        if (
                type == null
                        ||
                        priority == null
                        ||
                        location.isEmpty()
                        ||
                        description.isEmpty()
        ) {

            if (type == null) {

                showValidationMessage(
                        "Please select an emergency type."
                );

                return;
            }


            if (priority == null) {

                showValidationMessage(
                        "Please select a priority."
                );

                return;
            }


            if (location.isEmpty()) {

                showValidationMessage(
                        "Location is required."
                );

                locationField.requestFocus();

                return;
            }


            if (description.isEmpty()) {

                showValidationMessage(
                        "Description is required."
                );

                descriptionArea.requestFocus();

                return;
            }
        }


        /*
         * Date/time is generated automatically.
         */
        dateTime =
                getCurrentDateTime();


        String generatedId =
                emergencyManager
                        .generateEmergencyId(
                                type
                        );


        Emergency emergency =
                new Emergency(
                        generatedId,
                        type,
                        priority,
                        location,
                        description,
                        dateTime
                );


        boolean success =
                emergencyManager.addEmergency(
                        emergency
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Emergency could not be added.",
                    "Add Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        String message =
                "Emergency added successfully."
                        + "\nEmergency ID: "
                        + generatedId;


        /*
         * Automatic assignment for CRITICAL emergencies.
         */
        if (
                priority == Priority.CRITICAL
                        &&
                        assignmentManager != null
        ) {

            ArrayList<ResponseTeam> suitableTeams =
                    teamManager
                            .findSuitableTeams(
                                    type
                            );


            if (
                    !suitableTeams.isEmpty()
            ) {

                ResponseTeam selectedTeam =
                        suitableTeams.get(0);


                AssignmentManagerResult:
                {
                    String assignmentId =
                            assignmentManager
                                    .generateAssignmentId();


                    model.Assignment assignment =
                            assignmentManager.assignTeam(
                                    emergency.getEmergencyId(),
                                    selectedTeam.getTeamId(),
                                    dateTime,
                                    "Automatically assigned because priority is CRITICAL."
                            );


                    if (assignment != null) {

                        message =
                                message
                                        + "\n\n"
                                        + "Critical emergency detected."
                                        + "\n"
                                        + "Team automatically assigned: "
                                        + selectedTeam.getTeamName()
                                        + "\nAssignment ID: "
                                        + assignment.getAssignmentId();
                    }

                }
            } else {

                message =
                        message
                                + "\n\n"
                                + "No suitable team is currently available."
                                + "\nEmergency remains PENDING.";
            }
        }


        JOptionPane.showMessageDialog(
                this,
                message,
                "Emergency Added",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadEmergencies();

        clearForm();
    }


    // =========================================================
    // UPDATE EMERGENCY
    // =========================================================

    private void updateEmergency() {

        String id =
                emergencyIdField
                        .getText()
                        .trim();


        if (id.isEmpty()) {

            showValidationMessage(
                    "Select an emergency from the table first."
            );

            return;
        }


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


        String dateTime =
                dateTimeField
                        .getText()
                        .trim();


        if (
                type == null
                        ||
                        priority == null
                        ||
                        location.isEmpty()
                        ||
                        description.isEmpty()
        ) {

            if (type == null) {

                showValidationMessage(
                        "Please select an emergency type."
                );

                return;
            }


            if (priority == null) {

                showValidationMessage(
                        "Please select a priority."
                );

                return;
            }


            if (location.isEmpty()) {

                showValidationMessage(
                        "Location is required."
                );

                return;
            }


            if (description.isEmpty()) {

                showValidationMessage(
                        "Description is required."
                );

                return;
            }
        }


        Emergency updatedEmergency =
                new Emergency(
                        id,
                        type,
                        priority,
                        location,
                        description,
                        dateTime
                );


        boolean success =
                emergencyManager.updateEmergency(
                        id,
                        updatedEmergency
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Emergency could not be updated.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Emergency updated successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadEmergencies();

        clearForm();
    }


    // =========================================================
    // DELETE EMERGENCY
    // =========================================================

    private void deleteEmergency() {

        String id =
                emergencyIdField
                        .getText()
                        .trim();


        if (id.isEmpty()) {

            showValidationMessage(
                    "Select an emergency from the table first."
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + "Emergency: "
                                + id
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                result
                        != JOptionPane.YES_OPTION
        ) {

            return;
        }


        boolean success =
                emergencyManager.removeEmergency(
                        id
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "This emergency cannot be deleted."
                            + "\nActive assigned or in-progress "
                            + "emergencies cannot be removed.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Emergency deleted successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadEmergencies();

        clearForm();
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void searchEmergencies() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadEmergencies();

            return;
        }


        ArrayList<Emergency> results =
                emergencyManager
                        .searchEmergencies(
                                keyword
                        );


        loadTable(
                results
        );
    }


    // =========================================================
    // LOAD EMERGENCIES
    // =========================================================

    public void loadEmergencies() {

        loadTable(
                emergencyManager
                        .getAllEmergencies()
        );
    }


    // =========================================================
    // LOAD TABLE
    // =========================================================

    private void loadTable(
            ArrayList<Emergency> emergencies
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                Emergency emergency
                : emergencies
        ) {

            String assignedTeam =
                    emergency.getAssignedTeamId();


            if (
                    assignedTeam == null
                            ||
                            assignedTeam.trim().isEmpty()
            ) {

                assignedTeam = "-";
            }


            tableModel.addRow(
                    new Object[]{
                            emergency.getEmergencyId(),
                            emergency.getType(),
                            emergency.getPriority(),
                            emergency.getLocation(),
                            emergency.getStatus(),
                            assignedTeam,
                            emergency.getDateTime()
                    }
            );
        }
    }


    // =========================================================
    // SELECT EMERGENCY
    // =========================================================

    private void selectEmergency() {

        int selectedRow =
                emergencyTable
                        .getSelectedRow();


        if (selectedRow < 0) {
            return;
        }


        String emergencyId =
                tableModel
                        .getValueAt(
                                selectedRow,
                                0
                        )
                        .toString();


        Emergency emergency =
                emergencyManager
                        .findEmergencyById(
                                emergencyId
                        );


        if (emergency == null) {
            return;
        }


        emergencyIdField.setText(
                emergency.getEmergencyId()
        );


        typeComboBox.setSelectedItem(
                emergency.getType()
        );


        priorityComboBox.setSelectedItem(
                emergency.getPriority()
        );


        locationField.setText(
                emergency.getLocation()
        );


        descriptionArea.setText(
                emergency.getDescription()
        );


        dateTimeField.setText(
                emergency.getDateTime()
        );


        statusComboBox.setSelectedItem(
                emergency.getStatus()
        );
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        emergencyTable.clearSelection();


        typeComboBox.setSelectedIndex(
                0
        );


        priorityComboBox.setSelectedItem(
                Priority.HIGH
        );


        locationField.setText(
                ""
        );


        descriptionArea.setText(
                ""
        );


        dateTimeField.setText(
                getCurrentDateTime()
        );


        statusComboBox.setSelectedItem(
                EmergencyStatus.PENDING
        );


        updateGeneratedId();


        emergencyIdField.requestFocus();
    }


    // =========================================================
    // CURRENT DATE / TIME
    // =========================================================

    private String getCurrentDateTime() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm"
                );


        return LocalDateTime
                .now()
                .format(
                        formatter
                );
    }


    // =========================================================
    // VALIDATION MESSAGE
    // =========================================================

    private void showValidationMessage(
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.WARNING_MESSAGE
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
                                7,
                                8,
                                7,
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
    }


    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable(
            JTable table
    ) {

        table.setRowHeight(
                30
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