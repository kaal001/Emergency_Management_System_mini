package ui;

import manager.AssignmentManager;
import manager.EmergencyManager;
import manager.TeamManager;
import model.Assignment;
import model.Emergency;
import model.ResponseTeam;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AssignmentManagementPanel extends JPanel {

    // =========================================================
    // MANAGERS
    // =========================================================

    private final AssignmentManager assignmentManager;

    private final EmergencyManager emergencyManager;

    private final TeamManager teamManager;


    // =========================================================
    // FORM FIELDS
    // =========================================================

    private JTextField assignmentIdField;

    private JComboBox<String> emergencyComboBox;

    private JComboBox<String> teamComboBox;

    private JTextField assignedTimeField;

    private JTextArea notesArea;


    // =========================================================
    // SEARCH
    // =========================================================

    private JTextField searchField;


    // =========================================================
    // TABLE
    // =========================================================

    private JTable assignmentTable;

    private DefaultTableModel tableModel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AssignmentManagementPanel(
            AssignmentManager assignmentManager,
            EmergencyManager emergencyManager,
            TeamManager teamManager
    ) {

        this.assignmentManager =
                assignmentManager;

        this.emergencyManager =
                emergencyManager;

        this.teamManager =
                teamManager;

        buildUI();

        refreshData();
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
                        20,
                        22,
                        20,
                        22
                )
        );


        add(
                createPageHeader(),
                BorderLayout.NORTH
        );


        JSplitPane splitPane =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        createFormPanel(),
                        createTablePanel()
                );

        splitPane.setDividerLocation(
                360
        );

        splitPane.setResizeWeight(
                0.30
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
                        "ASSIGNMENT MANAGEMENT"
                );

        title.setFont(
                Theme.PAGE_TITLE_FONT
        );

        title.setForeground(
                Theme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Assign suitable response teams to emergency cases."
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
                Box.createVerticalStrut(4)
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
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.KHAKI_BEIGE
                        ),
                        BorderFactory.createEmptyBorder(
                                16,
                                16,
                                16,
                                16
                        )
                )
        );


        JLabel formTitle =
                new JLabel(
                        "ASSIGNMENT DETAILS"
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


        // =====================================================
        // ASSIGNMENT ID
        // =====================================================

        assignmentIdField =
                new JTextField();

        styleTextField(
                assignmentIdField
        );

        assignmentIdField.setEditable(
                false
        );

        addFormRow(
                form,
                gbc,
                0,
                "Assignment ID",
                assignmentIdField
        );


        // =====================================================
        // EMERGENCY
        // =====================================================

        emergencyComboBox =
                new JComboBox<>();

        styleComboBox(
                emergencyComboBox
        );

        addFormRow(
                form,
                gbc,
                1,
                "Emergency",
                emergencyComboBox
        );


        // =====================================================
        // TEAM
        // =====================================================

        teamComboBox =
                new JComboBox<>();

        styleComboBox(
                teamComboBox
        );

        addFormRow(
                form,
                gbc,
                2,
                "Response Team",
                teamComboBox
        );


        // =====================================================
        // ASSIGNED TIME
        // =====================================================

        assignedTimeField =
                new JTextField();

        styleTextField(
                assignedTimeField
        );

        addFormRow(
                form,
                gbc,
                3,
                "Assigned Time",
                assignedTimeField
        );


        // =====================================================
        // NOTES
        // =====================================================

        notesArea =
                new JTextArea(
                        5,
                        20
                );

        notesArea.setFont(
                Theme.NORMAL_FONT
        );

        notesArea.setLineWrap(
                true
        );

        notesArea.setWrapStyleWord(
                true
        );

        notesArea.setForeground(
                Theme.TEXT
        );

        notesArea.setBackground(
                Theme.WHITE
        );

        notesArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.STONE_BROWN
                        ),
                        BorderFactory.createEmptyBorder(
                                6,
                                7,
                                6,
                                7
                        )
                )
        );


        JScrollPane notesScroll =
                new JScrollPane(
                        notesArea
                );

        notesScroll.setPreferredSize(
                new Dimension(
                        200,
                        110
                )
        );


        addFormRow(
                form,
                gbc,
                4,
                "Notes",
                notesScroll
        );


        // =====================================================
        // INFO
        // =====================================================

        JLabel infoLabel =
                new JLabel(
                        "<html><b>Assignment rule:</b><br>"
                                + "Emergency must be PENDING and "
                                + "the selected team must be AVAILABLE "
                                + "and suitable.</html>"
                );

        infoLabel.setFont(
                Theme.SMALL_FONT
        );

        infoLabel.setForeground(
                Theme.MUTED_TEXT
        );


        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        form.add(
                infoLabel,
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
                e -> searchAssignments()
        );


        clearSearchButton.addActionListener(
                e -> {

                    searchField.setText(
                            ""
                    );

                    loadAssignments();
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
                "Assignment ID",
                "Emergency ID",
                "Team ID",
                "Assigned Time",
                "Notes"
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


        assignmentTable =
                new JTable(
                        tableModel
                );


        styleTable(
                assignmentTable
        );


        assignmentTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                selectAssignment();
                            }
                        }
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        assignmentTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        Theme.STONE_BROWN
                )
        );


        panel.add(
                scrollPane,
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
                        "ASSIGN"
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
                e -> addAssignment()
        );

        updateButton.addActionListener(
                e -> updateAssignment()
        );

        deleteButton.addActionListener(
                e -> deleteAssignment()
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
    // REFRESH DATA
    // =========================================================

    public void refreshData() {

        loadEmergencyComboBox();

        loadTeamComboBox();

        loadAssignments();

        clearForm();
    }


    // =========================================================
    // LOAD EMERGENCY COMBO BOX
    // =========================================================

    private void loadEmergencyComboBox() {

        emergencyComboBox.removeAllItems();


        ArrayList<Emergency> emergencies =
                emergencyManager
                        .getAllEmergencies();


        for (
                Emergency emergency
                : emergencies
        ) {

            emergencyComboBox.addItem(
                    emergency.getEmergencyId()
            );
        }
    }


    // =========================================================
    // LOAD TEAM COMBO BOX
    // =========================================================

    private void loadTeamComboBox() {

        teamComboBox.removeAllItems();


        ArrayList<ResponseTeam> teams =
                teamManager.getAllTeams();


        for (
                ResponseTeam team
                : teams
        ) {

            teamComboBox.addItem(
                    team.getTeamId()
            );
        }
    }


    // =========================================================
    // ADD ASSIGNMENT
    // =========================================================

    private void addAssignment() {

        String emergencyId =
                getSelectedEmergencyId();

        String teamId =
                getSelectedTeamId();

        String assignedTime =
                assignedTimeField
                        .getText()
                        .trim();

        String notes =
                notesArea
                        .getText()
                        .trim();


        if (emergencyId == null) {

            showValidationMessage(
                    "Please select an emergency."
            );

            return;
        }


        if (teamId == null) {

            showValidationMessage(
                    "Please select a response team."
            );

            return;
        }


        if (assignedTime.isEmpty()) {

            showValidationMessage(
                    "Assigned time is required."
            );

            return;
        }


        Assignment assignment =
                assignmentManager.assignTeam(
                        emergencyId,
                        teamId,
                        assignedTime,
                        notes
                );


        if (assignment == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment could not be created."
                            + "\nMake sure the emergency is PENDING, "
                            + "the team is AVAILABLE and the team "
                            + "is suitable for the emergency.",
                    "Assignment Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Team assigned successfully.\n"
                        + "Assignment ID: "
                        + assignment.getAssignmentId(),
                "Assignment Successful",
                JOptionPane.INFORMATION_MESSAGE
        );


        refreshData();
    }


    // =========================================================
    // UPDATE ASSIGNMENT
    // =========================================================

    private void updateAssignment() {

        String assignmentId =
                assignmentIdField
                        .getText()
                        .trim();


        if (assignmentId.isEmpty()) {

            showValidationMessage(
                    "Select an assignment from the table first."
            );

            return;
        }


        String teamId =
                getSelectedTeamId();

        String assignedTime =
                assignedTimeField
                        .getText()
                        .trim();

        String notes =
                notesArea
                        .getText()
                        .trim();


        if (teamId == null) {

            showValidationMessage(
                    "Please select a response team."
            );

            return;
        }


        if (assignedTime.isEmpty()) {

            showValidationMessage(
                    "Assigned time is required."
            );

            return;
        }


        boolean success =
                assignmentManager.updateAssignment(
                        assignmentId,
                        teamId,
                        assignedTime,
                        notes
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment could not be updated."
                            + "\nThe new team may be busy or "
                            + "not suitable for the emergency.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Assignment updated successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        refreshData();
    }


    // =========================================================
    // DELETE ASSIGNMENT
    // =========================================================

    private void deleteAssignment() {

        String assignmentId =
                assignmentIdField
                        .getText()
                        .trim();


        if (assignmentId.isEmpty()) {

            showValidationMessage(
                    "Select an assignment from the table first."
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + "Assignment: "
                                + assignmentId
                                + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (result
                != JOptionPane.YES_OPTION) {

            return;
        }


        boolean success =
                assignmentManager.removeAssignment(
                        assignmentId
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment could not be deleted."
                            + "\nAn in-progress emergency "
                            + "cannot have its assignment removed.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Assignment deleted successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        refreshData();
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void searchAssignments() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadAssignments();

            return;
        }


        ArrayList<Assignment> results =
                assignmentManager
                        .searchAssignments(
                                keyword
                        );


        loadTable(
                results
        );
    }


    // =========================================================
    // LOAD ASSIGNMENTS
    // =========================================================

    public void loadAssignments() {

        loadTable(
                assignmentManager
                        .getAllAssignments()
        );
    }


    // =========================================================
    // LOAD TABLE
    // =========================================================

    private void loadTable(
            ArrayList<Assignment> assignments
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                Assignment assignment
                : assignments
        ) {

            tableModel.addRow(
                    new Object[]{
                            assignment.getAssignmentId(),
                            assignment.getEmergencyId(),
                            assignment.getTeamId(),
                            assignment.getAssignedTime(),
                            assignment.getNotes()
                    }
            );
        }
    }


    // =========================================================
    // SELECT ASSIGNMENT
    // =========================================================

    private void selectAssignment() {

        int row =
                assignmentTable
                        .getSelectedRow();


        if (row < 0) {
            return;
        }


        String assignmentId =
                tableModel
                        .getValueAt(
                                row,
                                0
                        )
                        .toString();


        Assignment assignment =
                assignmentManager
                        .findAssignmentById(
                                assignmentId
                        );


        if (assignment == null) {
            return;
        }


        assignmentIdField.setText(
                assignment.getAssignmentId()
        );


        emergencyComboBox.setSelectedItem(
                assignment.getEmergencyId()
        );


        teamComboBox.setSelectedItem(
                assignment.getTeamId()
        );


        assignedTimeField.setText(
                assignment.getAssignedTime()
        );


        notesArea.setText(
                assignment.getNotes()
        );


        /*
         * Emergency is not changed during assignment update.
         * The assignment belongs to the same emergency.
         */
        emergencyComboBox.setEnabled(
                false
        );
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        assignmentIdField.setText(
                ""
        );


        emergencyComboBox.setEnabled(
                true
        );


        if (
                emergencyComboBox.getItemCount()
                        > 0
        ) {

            emergencyComboBox.setSelectedIndex(
                    0
            );
        }


        if (
                teamComboBox.getItemCount()
                        > 0
        ) {

            teamComboBox.setSelectedIndex(
                    0
            );
        }


        assignedTimeField.setText(
                getCurrentDateTime()
        );


        notesArea.setText(
                ""
        );


        assignmentTable.clearSelection();
    }


    // =========================================================
    // GET SELECTED EMERGENCY
    // =========================================================

    private String getSelectedEmergencyId() {

        Object value =
                emergencyComboBox
                        .getSelectedItem();


        if (value == null) {
            return null;
        }


        return value.toString();
    }


    // =========================================================
    // GET SELECTED TEAM
    // =========================================================

    private String getSelectedTeamId() {

        Object value =
                teamComboBox
                        .getSelectedItem();


        if (value == null) {
            return null;
        }


        return value.toString();
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
                .format(formatter);
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