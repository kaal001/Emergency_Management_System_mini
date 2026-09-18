package ui;

import enums.TeamType;
import manager.TeamManager;
import model.AmbulanceTeam;
import model.FireTeam;
import model.RescueTeam;
import model.ResponseTeam;
import model.SecurityTeam;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TeamManagementPanel extends JPanel {

    // =========================================================
    // MANAGER
    // =========================================================

    private final TeamManager teamManager;


    // =========================================================
    // FORM FIELDS
    // =========================================================

    private JTextField teamIdField;
    private JTextField teamNameField;
    private JComboBox<TeamType> teamTypeComboBox;
    private JTextField contactNumberField;
    private JSpinner memberCountSpinner;
    private JCheckBox availableCheckBox;


    // =========================================================
    // SEARCH
    // =========================================================

    private JTextField searchField;


    // =========================================================
    // TABLE
    // =========================================================

    private JTable teamTable;
    private DefaultTableModel tableModel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TeamManagementPanel(
            TeamManager teamManager
    ) {

        this.teamManager =
                teamManager;

        buildUI();

        loadTeams();
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
                        "RESPONSE TEAM MANAGEMENT"
                );

        title.setFont(
                Theme.PAGE_TITLE_FONT
        );

        title.setForeground(
                Theme.TEXT
        );


        JLabel subtitle =
                new JLabel(
                        "Manage ambulance, fire, rescue and security teams."
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
                        "TEAM DETAILS"
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
        // TEAM ID
        // =====================================================

        teamIdField =
                new JTextField();

        styleTextField(
                teamIdField
        );

        addFormRow(
                form,
                gbc,
                0,
                "Team ID",
                teamIdField
        );


        // =====================================================
        // TEAM NAME
        // =====================================================

        teamNameField =
                new JTextField();

        styleTextField(
                teamNameField
        );

        addFormRow(
                form,
                gbc,
                1,
                "Team Name",
                teamNameField
        );


        // =====================================================
        // TEAM TYPE
        // =====================================================

        teamTypeComboBox =
                new JComboBox<>(
                        TeamType.values()
                );

        styleComboBox(
                teamTypeComboBox
        );

        addFormRow(
                form,
                gbc,
                2,
                "Team Type",
                teamTypeComboBox
        );


        // =====================================================
        // CONTACT NUMBER
        // =====================================================

        contactNumberField =
                new JTextField();

        styleTextField(
                contactNumberField
        );

        addFormRow(
                form,
                gbc,
                3,
                "Contact Number",
                contactNumberField
        );


        // =====================================================
        // MEMBER COUNT
        // =====================================================

        memberCountSpinner =
                new JSpinner(
                        new SpinnerNumberModel(
                                1,
                                1,
                                100,
                                1
                        )
                );

        memberCountSpinner.setFont(
                Theme.NORMAL_FONT
        );

        addFormRow(
                form,
                gbc,
                4,
                "Members",
                memberCountSpinner
        );


        // =====================================================
        // AVAILABILITY
        // =====================================================

        availableCheckBox =
                new JCheckBox(
                        "Available"
                );

        availableCheckBox.setFont(
                Theme.NORMAL_FONT
        );

        availableCheckBox.setForeground(
                Theme.TEXT
        );

        availableCheckBox.setBackground(
                Theme.ALMOND_CREAM
        );

        availableCheckBox.setSelected(
                true
        );


        addFormRow(
                form,
                gbc,
                5,
                "Status",
                availableCheckBox
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
                e -> searchTeams()
        );


        clearSearchButton.addActionListener(
                e -> {

                    searchField.setText(
                            ""
                    );

                    loadTeams();
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


        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                5,
                                0
                        )
                );

        buttons.setBackground(
                Theme.BACKGROUND
        );

        buttons.add(
                searchButton
        );

        buttons.add(
                clearSearchButton
        );


        searchPanel.add(
                buttons,
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
                "Team ID",
                "Team Name",
                "Type",
                "Contact",
                "Members",
                "Availability"
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


        teamTable =
                new JTable(
                        tableModel
                );


        styleTable(
                teamTable
        );


        teamTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                selectTeam();
                            }
                        }
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        teamTable
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
                e -> addTeam()
        );

        updateButton.addActionListener(
                e -> updateTeam()
        );

        deleteButton.addActionListener(
                e -> deleteTeam()
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
    // ADD TEAM
    // =========================================================

    private void addTeam() {

        String id =
                teamIdField
                        .getText()
                        .trim();

        String name =
                teamNameField
                        .getText()
                        .trim();

        String contact =
                contactNumberField
                        .getText()
                        .trim();

        TeamType type =
                (TeamType)
                        teamTypeComboBox
                                .getSelectedItem();

        int memberCount =
                (Integer)
                        memberCountSpinner
                                .getValue();


        if (!validateForm(
                id,
                name,
                contact,
                type,
                memberCount
        )) {

            return;
        }


        ResponseTeam team =
                createTeam(
                        id,
                        name,
                        type,
                        contact,
                        memberCount
                );


        team.setAvailable(
                availableCheckBox.isSelected()
        );


        boolean success =
                teamManager.addTeam(
                        team
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not add team."
                            + "\nCheck the Team ID and information.",
                    "Add Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Response team added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadTeams();

        clearForm();
    }


    // =========================================================
    // CREATE CONCRETE TEAM
    // =========================================================

    private ResponseTeam createTeam(
            String id,
            String name,
            TeamType type,
            String contact,
            int memberCount
    ) {

        switch (type) {

            case AMBULANCE:

                return new AmbulanceTeam(
                        id,
                        name,
                        contact,
                        memberCount
                );

            case FIRE:

                return new FireTeam(
                        id,
                        name,
                        contact,
                        memberCount
                );

            case RESCUE:

                return new RescueTeam(
                        id,
                        name,
                        contact,
                        memberCount
                );

            case SECURITY:

                return new SecurityTeam(
                        id,
                        name,
                        contact,
                        memberCount
                );

            default:

                return new RescueTeam(
                        id,
                        name,
                        contact,
                        memberCount
                );
        }
    }


    // =========================================================
    // UPDATE TEAM
    // =========================================================

    private void updateTeam() {

        String id =
                teamIdField
                        .getText()
                        .trim();

        String name =
                teamNameField
                        .getText()
                        .trim();

        String contact =
                contactNumberField
                        .getText()
                        .trim();

        TeamType type =
                (TeamType)
                        teamTypeComboBox
                                .getSelectedItem();

        int memberCount =
                (Integer)
                        memberCountSpinner
                                .getValue();


        if (id.isEmpty()) {

            showValidationMessage(
                    "Select a team from the table first."
            );

            return;
        }


        if (!validateForm(
                id,
                name,
                contact,
                type,
                memberCount
        )) {

            return;
        }


        boolean success =
                teamManager.updateTeam(
                        id,
                        name,
                        contact,
                        memberCount,
                        availableCheckBox.isSelected()
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not update team.",
                    "Update Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Response team updated successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadTeams();

        clearForm();
    }


    // =========================================================
    // DELETE TEAM
    // =========================================================

    private void deleteTeam() {

        String id =
                teamIdField
                        .getText()
                        .trim();


        if (id.isEmpty()) {

            showValidationMessage(
                    "Select a team from the table first."
            );

            return;
        }


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + "Team: "
                                + id
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
                teamManager.removeTeam(
                        id
                );


        if (!success) {

            JOptionPane.showMessageDialog(
                    this,
                    "This team cannot be deleted."
                            + "\nA busy team cannot be removed.",
                    "Delete Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JOptionPane.showMessageDialog(
                this,
                "Response team deleted successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );


        loadTeams();

        clearForm();
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void searchTeams() {

        String keyword =
                searchField
                        .getText()
                        .trim();


        if (keyword.isEmpty()) {

            loadTeams();

            return;
        }


        ArrayList<ResponseTeam> results =
                teamManager.searchTeams(
                        keyword
                );


        loadTable(
                results
        );
    }


    // =========================================================
    // LOAD TEAMS
    // =========================================================

    public void loadTeams() {

        loadTable(
                teamManager.getAllTeams()
        );
    }


    // =========================================================
    // LOAD TABLE
    // =========================================================

    private void loadTable(
            ArrayList<ResponseTeam> teams
    ) {

        tableModel.setRowCount(
                0
        );


        for (
                ResponseTeam team
                : teams
        ) {

            tableModel.addRow(
                    new Object[]{
                            team.getTeamId(),
                            team.getTeamName(),
                            team.getTeamType(),
                            team.getContactNumber(),
                            team.getMemberCount(),
                            team.isAvailable()
                                    ? "AVAILABLE"
                                    : "BUSY"
                    }
            );
        }
    }


    // =========================================================
    // SELECT TEAM
    // =========================================================

    private void selectTeam() {

        int row =
                teamTable.getSelectedRow();


        if (row < 0) {
            return;
        }


        String teamId =
                tableModel
                        .getValueAt(
                                row,
                                0
                        )
                        .toString();


        ResponseTeam team =
                teamManager.findTeamById(
                        teamId
                );


        if (team == null) {
            return;
        }


        teamIdField.setText(
                team.getTeamId()
        );

        teamNameField.setText(
                team.getTeamName()
        );

        teamTypeComboBox.setSelectedItem(
                team.getTeamType()
        );

        contactNumberField.setText(
                team.getContactNumber()
        );

        memberCountSpinner.setValue(
                team.getMemberCount()
        );

        availableCheckBox.setSelected(
                team.isAvailable()
        );


        /*
         * The team type determines the actual concrete
         * object class, so we do not allow changing it
         * through update.
         */
        teamTypeComboBox.setEnabled(
                false
        );
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        teamIdField.setText(
                ""
        );

        teamNameField.setText(
                ""
        );

        teamTypeComboBox.setEnabled(
                true
        );

        teamTypeComboBox.setSelectedIndex(
                0
        );

        contactNumberField.setText(
                ""
        );

        memberCountSpinner.setValue(
                1
        );

        availableCheckBox.setSelected(
                true
        );

        teamTable.clearSelection();

        teamIdField.requestFocus();
    }


    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateForm(
            String id,
            String name,
            String contact,
            TeamType type,
            int memberCount
    ) {

        if (id.isEmpty()) {

            showValidationMessage(
                    "Team ID is required."
            );

            return false;
        }


        if (name.isEmpty()) {

            showValidationMessage(
                    "Team name is required."
            );

            return false;
        }


        if (type == null) {

            showValidationMessage(
                    "Please select a team type."
            );

            return false;
        }


        if (contact.isEmpty()) {

            showValidationMessage(
                    "Contact number is required."
            );

            return false;
        }


        if (memberCount <= 0) {

            showValidationMessage(
                    "Member count must be greater than zero."
            );

            return false;
        }


        return true;
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