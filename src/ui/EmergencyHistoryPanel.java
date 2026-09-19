package ui;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import manager.EmergencyManager;
import model.Emergency;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EmergencyHistoryPanel extends JPanel {

    // =========================================================
    // MANAGER
    // =========================================================

    private final EmergencyManager emergencyManager;


    // =========================================================
    // FILTER COMPONENTS
    // =========================================================

    private JTextField searchField;

    private JComboBox<String> typeFilter;

    private JComboBox<String> priorityFilter;

    private JComboBox<String> statusFilter;


    // =========================================================
    // TABLE
    // =========================================================

    private JTable historyTable;

    private DefaultTableModel tableModel;


    // =========================================================
    // STATUS / INFORMATION
    // =========================================================

    private JLabel resultCountLabel;

    private JLabel selectedRecordLabel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public EmergencyHistoryPanel(
            EmergencyManager emergencyManager
    ) {

        this.emergencyManager =
                emergencyManager;

        buildUI();

        loadHistory();
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        setLayout(
                new BorderLayout(
                        0,
                        16
                )
        );

        setBackground(
                Theme.BACKGROUND
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        24,
                        20,
                        24
                )
        );


        // =====================================================
        // PAGE HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(
                false
        );


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


        JLabel titleLabel =
                new JLabel(
                        "EMERGENCY HISTORY"
                );

        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.TEXT
        );


        JLabel subtitleLabel =
                new JLabel(
                        "SEARCH, FILTER AND REVIEW RECORDED EMERGENCIES"
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
                Box.createVerticalStrut(
                        4
                )
        );

        titlePanel.add(
                subtitleLabel
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );


        JPanel headerStatusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        headerStatusPanel.setOpaque(
                false
        );


        JLabel moduleDot =
                new JLabel(
                        "●"
                );

        moduleDot.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        moduleDot.setForeground(
                Theme.ACCENT
        );


        JLabel moduleLabel =
                new JLabel(
                        "HISTORY MODULE"
                );

        moduleLabel.setFont(
                Theme.SMALL_FONT
        );

        moduleLabel.setForeground(
                Theme.STONE_BROWN
        );


        headerStatusPanel.add(
                moduleDot
        );

        headerStatusPanel.add(
                moduleLabel
        );


        headerPanel.add(
                headerStatusPanel,
                BorderLayout.EAST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                12
                        )
                );

        centerPanel.setOpaque(
                false
        );


        // =====================================================
        // FILTER CARD
        // =====================================================

        JPanel filterCard =
                new RoundedPanel();

        filterCard.setLayout(
                new BorderLayout(
                        0,
                        10
                )
        );

        filterCard.setBorder(
                BorderFactory.createCompoundBorder(
                        Theme.createRoundedBorder(
                                Theme.KHAKI_BEIGE,
                                16,
                                1,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                13,
                                15,
                                13,
                                15
                        )
                )
        );


        JLabel filterTitle =
                new JLabel(
                        "HISTORY FILTERS"
                );

        filterTitle.setFont(
                Theme.SECTION_FONT
        );

        filterTitle.setForeground(
                Theme.TEXT
        );


        filterCard.add(
                filterTitle,
                BorderLayout.NORTH
        );


        JPanel filterPanel =
                new JPanel(
                        new GridBagLayout()
                );

        filterPanel.setOpaque(
                false
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        4,
                        5,
                        4,
                        5
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // =====================================================
        // SEARCH
        // =====================================================

        JLabel searchLabel =
                new JLabel(
                        "SEARCH"
                );

        searchLabel.setFont(
                Theme.SMALL_FONT
        );

        searchLabel.setForeground(
                Theme.TEXT
        );


        searchField =
                new JTextField();

        styleTextField(
                searchField
        );

        searchField.setPreferredSize(
                new Dimension(
                        230,
                        36
                )
        );


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        filterPanel.add(
                searchLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        filterPanel.add(
                searchField,
                gbc
        );


        // =====================================================
        // TYPE
        // =====================================================

        JLabel typeLabel =
                new JLabel(
                        "TYPE"
                );

        typeLabel.setFont(
                Theme.SMALL_FONT
        );

        typeLabel.setForeground(
                Theme.TEXT
        );


        typeFilter =
                new JComboBox<>();

        styleComboBox(
                typeFilter
        );


        typeFilter.addItem(
                "ALL TYPES"
        );


        for (
                EmergencyType type
                : EmergencyType.values()
        ) {

            typeFilter.addItem(
                    formatEmergencyType(
                            type
                    )
            );
        }


        gbc.gridx = 2;
        gbc.weightx = 0;

        filterPanel.add(
                typeLabel,
                gbc
        );


        gbc.gridx = 3;
        gbc.weightx = 0.7;

        filterPanel.add(
                typeFilter,
                gbc
        );


        // =====================================================
        // PRIORITY
        // =====================================================

        JLabel priorityLabel =
                new JLabel(
                        "PRIORITY"
                );

        priorityLabel.setFont(
                Theme.SMALL_FONT
        );

        priorityLabel.setForeground(
                Theme.TEXT
        );


        priorityFilter =
                new JComboBox<>();

        styleComboBox(
                priorityFilter
        );


        priorityFilter.addItem(
                "ALL PRIORITIES"
        );


        for (
                Priority priority
                : Priority.values()
        ) {

            priorityFilter.addItem(
                    formatPriority(
                            priority
                    )
            );
        }


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        filterPanel.add(
                priorityLabel,
                gbc
        );


        gbc.gridx = 1;
        gbc.weightx = 1;

        filterPanel.add(
                priorityFilter,
                gbc
        );


        // =====================================================
        // STATUS
        // =====================================================

        JLabel statusLabel =
                new JLabel(
                        "STATUS"
                );

        statusLabel.setFont(
                Theme.SMALL_FONT
        );

        statusLabel.setForeground(
                Theme.TEXT
        );


        statusFilter =
                new JComboBox<>();

        styleComboBox(
                statusFilter
        );


        statusFilter.addItem(
                "ALL STATUSES"
        );


        for (
                EmergencyStatus status
                : EmergencyStatus.values()
        ) {

            statusFilter.addItem(
                    formatStatus(
                            status
                    )
            );
        }


        gbc.gridx = 2;
        gbc.weightx = 0;

        filterPanel.add(
                statusLabel,
                gbc
        );


        gbc.gridx = 3;
        gbc.weightx = 0.7;

        filterPanel.add(
                statusFilter,
                gbc
        );


        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                6,
                                0
                        )
                );

        actionPanel.setOpaque(
                false
        );


        JButton filterButton =
                new JButton(
                        "FILTER"
                );

        Theme.stylePrimaryButton(
                filterButton
        );


        JButton clearButton =
                new JButton(
                        "CLEAR"
                );

        Theme.styleSecondaryButton(
                clearButton
        );


        JButton refreshButton =
                new JButton(
                        "REFRESH"
                );

        Theme.styleHeaderButton(
                refreshButton
        );


        filterButton.addActionListener(
                e -> searchHistory()
        );


        clearButton.addActionListener(
                e -> clearFilters()
        );


        refreshButton.addActionListener(
                e -> {

                    loadHistory();

                    refreshButton.setText(
                            "UPDATED ✓"
                    );

                    Timer timer =
                            new Timer(
                                    850,
                                    event ->
                                            refreshButton.setText(
                                                    "REFRESH"
                                            )
                            );

                    timer.setRepeats(
                            false
                    );

                    timer.start();
                }
        );


        actionPanel.add(
                filterButton
        );

        actionPanel.add(
                clearButton
        );

        actionPanel.add(
                refreshButton
        );


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1;


        filterPanel.add(
                actionPanel,
                gbc
        );


        filterCard.add(
                filterPanel,
                BorderLayout.CENTER
        );


        centerPanel.add(
                filterCard,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE CARD
        // =====================================================

        JPanel tableCard =
                new RoundedPanel();

        tableCard.setLayout(
                new BorderLayout(
                        0,
                        10
                )
        );


        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        Theme.createRoundedBorder(
                                Theme.KHAKI_BEIGE,
                                16,
                                1,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                13,
                                13,
                                11,
                                13
                        )
                )
        );


        JPanel tableHeader =
                new JPanel(
                        new BorderLayout()
                );

        tableHeader.setOpaque(
                false
        );


        JLabel recordsLabel =
                new JLabel(
                        "EMERGENCY RECORDS"
                );

        recordsLabel.setFont(
                Theme.SECTION_FONT
        );

        recordsLabel.setForeground(
                Theme.TEXT
        );


        resultCountLabel =
                new JLabel(
                        "0 records"
                );

        resultCountLabel.setFont(
                Theme.SMALL_FONT
        );

        resultCountLabel.setForeground(
                Theme.MUTED_TEXT
        );


        tableHeader.add(
                recordsLabel,
                BorderLayout.WEST
        );

        tableHeader.add(
                resultCountLabel,
                BorderLayout.EAST
        );


        tableCard.add(
                tableHeader,
                BorderLayout.NORTH
        );


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


        historyTable =
                new JTable(
                        tableModel
                );


        styleTable(
                historyTable
        );


        historyTable.setRowHeight(
                30
        );


        historyTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        historyTable.setAutoCreateRowSorter(
                true
        );


        historyTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        105
                );

        historyTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        120
                );

        historyTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        85
                );

        historyTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        145
                );

        historyTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        105
                );

        historyTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        120
                );

        historyTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(
                        135
                );


        JScrollPane tableScrollPane =
                new JScrollPane(
                        historyTable
                );


        tableScrollPane.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        14,
                        1,
                        1
                )
        );


        tableScrollPane.getViewport()
                .setBackground(
                        Theme.WHITE
                );


        tableCard.add(
                tableScrollPane,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM ACTION BAR
        // =====================================================

        JPanel bottomBar =
                new JPanel(
                        new BorderLayout()
                );

        bottomBar.setOpaque(
                false
        );


        selectedRecordLabel =
                new JLabel(
                        "Select a record to view details."
                );

        selectedRecordLabel.setFont(
                Theme.SMALL_FONT
        );

        selectedRecordLabel.setForeground(
                Theme.MUTED_TEXT
        );


        JButton detailsButton =
                new JButton(
                        "VIEW DETAILS"
                );

        Theme.stylePrimaryButton(
                detailsButton
        );


        detailsButton.addActionListener(
                e -> showDetails()
        );


        bottomBar.add(
                selectedRecordLabel,
                BorderLayout.WEST
        );

        bottomBar.add(
                detailsButton,
                BorderLayout.EAST
        );


        tableCard.add(
                bottomBar,
                BorderLayout.SOUTH
        );


        centerPanel.add(
                tableCard,
                BorderLayout.CENTER
        );


        add(
                centerPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // SELECTION LISTENER
        // =====================================================

        historyTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (
                                    !e.getValueIsAdjusting()
                            ) {

                                updateSelectedRecord();
                            }
                        }
                );


        // =====================================================
        // DOUBLE CLICK
        // =====================================================

        historyTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        if (
                                e.getClickCount() == 2
                                        && SwingUtilities
                                        .isLeftMouseButton(e)
                        ) {

                            showDetails();
                        }
                    }
                }
        );


        // =====================================================
        // LIVE SEARCH
        // =====================================================

        searchField
                .getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {

                                searchHistory();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {

                                searchHistory();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {

                                searchHistory();
                            }
                        }
                );


        // =====================================================
        // FILTER CHANGE EVENTS
        // =====================================================

        typeFilter.addActionListener(
                e -> searchHistory()
        );

        priorityFilter.addActionListener(
                e -> searchHistory()
        );

        statusFilter.addActionListener(
                e -> searchHistory()
        );
    }


    // =========================================================
    // LOAD HISTORY
    // =========================================================

    public void loadHistory() {

        ArrayList<Emergency> emergencies =
                emergencyManager
                        .getAllEmergencies();


        loadHistory(
                emergencies
        );
    }


    // =========================================================
    // LOAD PROVIDED RECORDS
    // =========================================================

    private void loadHistory(
            ArrayList<Emergency> emergencies
    ) {

        tableModel.setRowCount(
                0
        );


        if (
                emergencies == null
        ) {

            resultCountLabel.setText(
                    "0 records"
            );

            selectedRecordLabel.setText(
                    "No records available."
            );

            return;
        }


        for (
                Emergency emergency
                : emergencies
        ) {

            if (
                    emergency == null
            ) {

                continue;
            }


            String assignedTeam =
                    emergency.getAssignedTeamId();


            if (
                    assignedTeam == null
                            || assignedTeam
                            .trim()
                            .isEmpty()
            ) {

                assignedTeam =
                        "-";
            }


            tableModel.addRow(
                    new Object[]{

                            emergency
                                    .getEmergencyId(),

                            formatEmergencyType(
                                    emergency
                                            .getType()
                            ),

                            formatPriority(
                                    emergency
                                            .getPriority()
                            ),

                            safeText(
                                    emergency
                                            .getLocation()
                            ),

                            formatStatus(
                                    emergency
                                            .getStatus()
                            ),

                            assignedTeam,

                            safeText(
                                    emergency
                                            .getDateTime()
                            )
                    }
            );
        }


        resultCountLabel.setText(
                tableModel.getRowCount()
                        + " records"
        );


        historyTable.clearSelection();


        selectedRecordLabel.setText(
                "Select a record to view details."
        );
    }


    // =========================================================
    // SEARCH / FILTER
    // =========================================================

    private void searchHistory() {

        if (
                tableModel == null
        ) {

            return;
        }


        String keyword =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        String selectedType =
                typeFilter
                        .getSelectedItem()
                        == null
                        ? "ALL TYPES"
                        : typeFilter
                        .getSelectedItem()
                        .toString();


        String selectedPriority =
                priorityFilter
                        .getSelectedItem()
                        == null
                        ? "ALL PRIORITIES"
                        : priorityFilter
                        .getSelectedItem()
                        .toString();


        String selectedStatus =
                statusFilter
                        .getSelectedItem()
                        == null
                        ? "ALL STATUSES"
                        : statusFilter
                        .getSelectedItem()
                        .toString();


        ArrayList<Emergency> result =
                new ArrayList<>();


        for (
                Emergency emergency
                : emergencyManager.getAllEmergencies()
        ) {

            if (
                    emergency == null
            ) {

                continue;
            }


            String id =
                    safeLower(
                            emergency
                                    .getEmergencyId()
                    );


            String location =
                    safeLower(
                            emergency
                                    .getLocation()
                    );


            String description =
                    safeLower(
                            emergency
                                    .getDescription()
                    );


            String assignedTeam =
                    safeLower(
                            emergency
                                    .getAssignedTeamId()
                    );


            boolean keywordMatch =
                    keyword.isEmpty()
                            || id.contains(keyword)
                            || location.contains(keyword)
                            || description.contains(keyword)
                            || assignedTeam.contains(keyword);


            boolean typeMatch =
                    selectedType.equals(
                            "ALL TYPES"
                    )
                            || formatEmergencyType(
                            emergency
                                    .getType()
                    ).equalsIgnoreCase(
                            selectedType
                    );


            boolean priorityMatch =
                    selectedPriority.equals(
                            "ALL PRIORITIES"
                    )
                            || formatPriority(
                            emergency
                                    .getPriority()
                    ).equalsIgnoreCase(
                            selectedPriority
                    );


            boolean statusMatch =
                    selectedStatus.equals(
                            "ALL STATUSES"
                    )
                            || formatStatus(
                            emergency
                                    .getStatus()
                    ).equalsIgnoreCase(
                            selectedStatus
                    );


            if (
                    keywordMatch
                            && typeMatch
                            && priorityMatch
                            && statusMatch
            ) {

                result.add(
                        emergency
                );
            }
        }


        loadHistory(
                result
        );
    }


    // =========================================================
    // CLEAR FILTERS
    // =========================================================

    private void clearFilters() {

        searchField.setText(
                ""
        );


        typeFilter.setSelectedIndex(
                0
        );


        priorityFilter.setSelectedIndex(
                0
        );


        statusFilter.setSelectedIndex(
                0
        );


        loadHistory();
    }


    // =========================================================
    // UPDATE SELECTED RECORD
    // =========================================================

    private void updateSelectedRecord() {

        int selectedRow =
                historyTable.getSelectedRow();


        if (
                selectedRow == -1
        ) {

            selectedRecordLabel.setText(
                    "Select a record to view details."
            );

            return;
        }


        int modelRow =
                historyTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        String emergencyId =
                tableModel
                        .getValueAt(
                                modelRow,
                                0
                        )
                        .toString();


        Emergency emergency =
                emergencyManager
                        .findEmergencyById(
                                emergencyId
                        );


        if (
                emergency == null
        ) {

            selectedRecordLabel.setText(
                    "Record not found."
            );

            return;
        }


        selectedRecordLabel.setText(
                "Selected: "
                        + emergency.getEmergencyId()
                        + "  |  "
                        + formatEmergencyType(
                        emergency.getType()
                )
                        + "  |  "
                        + formatStatus(
                        emergency.getStatus()
                )
        );
    }


    // =========================================================
    // SHOW DETAILS
    // =========================================================

    private void showDetails() {

        int selectedRow =
                historyTable.getSelectedRow();


        if (
                selectedRow == -1
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        int modelRow =
                historyTable
                        .convertRowIndexToModel(
                                selectedRow
                        );


        String emergencyId =
                tableModel
                        .getValueAt(
                                modelRow,
                                0
                        )
                        .toString();


        Emergency emergency =
                emergencyManager
                        .findEmergencyById(
                                emergencyId
                        );


        if (
                emergency == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "The selected emergency could not be found.",
                    "Record Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        EmergencyDetailsDialog dialog =
                new EmergencyDetailsDialog(
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        emergency
                );

        dialog.setVisible(
                true
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

        field.setCaretColor(
                Theme.STONE_BROWN
        );

        field.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        11,
                        1,
                        7
                )
        );
    }


    // =========================================================
    // COMBO BOX STYLE
    // =========================================================

    private void styleComboBox(
            JComboBox<String> comboBox
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
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        11,
                        1,
                        4
                )
        );
    }


    // =========================================================
    // TABLE STYLE
    // =========================================================

    private void styleTable(
            JTable table
    ) {

        Theme.styleTable(
                table
        );


        if (
                table.getColumnCount()
                        > 2
        ) {

            table.getColumnModel()
                    .getColumn(2)
                    .setCellRenderer(
                            new PriorityRenderer()
                    );
        }


        if (
                table.getColumnCount()
                        > 4
        ) {

            table.getColumnModel()
                    .getColumn(4)
                    .setCellRenderer(
                            new StatusRenderer()
                    );
        }


        if (
                table.getColumnCount()
                        > 1
        ) {

            table.getColumnModel()
                    .getColumn(1)
                    .setCellRenderer(
                            new DefaultTableCellRenderer() {

                                @Override
                                public Component
                                getTableCellRendererComponent(
                                        JTable table,
                                        Object value,
                                        boolean selected,
                                        boolean focused,
                                        int row,
                                        int column
                                ) {

                                    Component component =
                                            super
                                                    .getTableCellRendererComponent(
                                                            table,
                                                            value,
                                                            selected,
                                                            focused,
                                                            row,
                                                            column
                                                    );


                                    setHorizontalAlignment(
                                            SwingConstants.LEFT
                                    );


                                    if (
                                            !selected
                                    ) {

                                        setBackground(
                                                row % 2 == 0
                                                        ? Theme.WHITE
                                                        : Theme.ALMOND_CREAM
                                        );

                                        setForeground(
                                                Theme.TEXT
                                        );
                                    }


                                    return component;
                                }
                            }
                    );
        }
    }


    // =========================================================
    // PRIORITY RENDERER
    // =========================================================

    private static class PriorityRenderer
            extends DefaultTableCellRenderer {


        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column
        ) {

            Component component =
                    super
                            .getTableCellRendererComponent(
                                    table,
                                    value,
                                    selected,
                                    focused,
                                    row,
                                    column
                            );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            if (
                    !selected
            ) {

                String text =
                        value == null
                                ? ""
                                : value
                                .toString()
                                .toUpperCase();


                if (
                        text.contains(
                                "CRITICAL"
                        )
                ) {

                    setBackground(
                            Theme.STONE_BROWN
                    );

                    setForeground(
                            Theme.LIGHT_TEXT
                    );


                } else if (
                        text.contains(
                                "HIGH"
                        )
                ) {

                    setBackground(
                            Theme.KHAKI_BEIGE
                    );

                    setForeground(
                            Theme.BLACK
                    );


                } else {

                    setBackground(
                            row % 2 == 0
                                    ? Theme.WHITE
                                    : Theme.ALMOND_CREAM
                    );

                    setForeground(
                            Theme.TEXT
                    );
                }
            }


            return component;
        }
    }


    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private static class StatusRenderer
            extends DefaultTableCellRenderer {


        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column
        ) {

            Component component =
                    super
                            .getTableCellRendererComponent(
                                    table,
                                    value,
                                    selected,
                                    focused,
                                    row,
                                    column
                            );


            setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            if (
                    !selected
            ) {

                String text =
                        value == null
                                ? ""
                                : value
                                .toString()
                                .toUpperCase();


                if (
                        text.contains(
                                "IN PROGRESS"
                        )
                ) {

                    setBackground(
                            Theme.JET_BLACK
                    );

                    setForeground(
                            Theme.LIGHT_TEXT
                    );


                } else if (
                        text.contains(
                                "ASSIGNED"
                        )
                ) {

                    setBackground(
                            Theme.KHAKI_BEIGE
                    );

                    setForeground(
                            Theme.BLACK
                    );


                } else if (
                        text.contains(
                                "RESOLVED"
                        )
                ) {

                    setBackground(
                            Theme.STONE_BROWN
                    );

                    setForeground(
                            Theme.LIGHT_TEXT
                    );


                } else {

                    setBackground(
                            row % 2 == 0
                                    ? Theme.WHITE
                                    : Theme.ALMOND_CREAM
                    );

                    setForeground(
                            Theme.TEXT
                    );
                }
            }


            return component;
        }
    }


    // =========================================================
    // EMERGENCY TYPE FORMATTER
    // =========================================================

    private String formatEmergencyType(
            EmergencyType type
    ) {

        if (
                type == null
        ) {

            return "-";
        }


        String text =
                type.toString()
                        .replace(
                                "_",
                                " "
                        )
                        .toLowerCase();


        String[] parts =
                text.split(
                        " "
                );


        StringBuilder result =
                new StringBuilder();


        for (
                String part
                : parts
        ) {

            if (
                    part.isEmpty()
            ) {

                continue;
            }


            result.append(
                    Character.toUpperCase(
                            part.charAt(0)
                    )
            );


            if (
                    part.length() > 1
            ) {

                result.append(
                        part.substring(
                                1
                        )
                );
            }


            result.append(
                    " "
            );
        }


        return result.toString()
                .trim();
    }


    // =========================================================
    // PRIORITY FORMATTER
    // =========================================================

    private String formatPriority(
            Priority priority
    ) {

        if (
                priority == null
        ) {

            return "-";
        }


        String text =
                priority.toString()
                        .replace(
                                "_",
                                " "
                        )
                        .toLowerCase();


        return capitalizeWords(
                text
        );
    }


    // =========================================================
    // STATUS FORMATTER
    // =========================================================

    private String formatStatus(
            EmergencyStatus status
    ) {

        if (
                status == null
        ) {

            return "-";
        }


        String text =
                status.toString()
                        .replace(
                                "_",
                                " "
                        )
                        .toLowerCase();


        return capitalizeWords(
                text
        );
    }


    // =========================================================
    // CAPITALIZE WORDS
    // =========================================================

    private String capitalizeWords(
            String text
    ) {

        String[] parts =
                text.split(
                        " "
                );


        StringBuilder result =
                new StringBuilder();


        for (
                String part
                : parts
        ) {

            if (
                    part.isEmpty()
            ) {

                continue;
            }


            result.append(
                    Character.toUpperCase(
                            part.charAt(0)
                    )
            );


            if (
                    part.length() > 1
            ) {

                result.append(
                        part.substring(
                                1
                        )
                );
            }


            result.append(
                    " "
            );
        }


        return result.toString()
                .trim();
    }


    // =========================================================
    // SAFE TEXT
    // =========================================================

    private String safeText(
            String text
    ) {

        if (
                text == null
                        || text.trim().isEmpty()
        ) {

            return "-";
        }


        return text.trim();
    }


    // =========================================================
    // SAFE LOWERCASE
    // =========================================================

    private String safeLower(
            String text
    ) {

        if (
                text == null
        ) {

            return "";
        }


        return text
                .trim()
                .toLowerCase();
    }


    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    private static class RoundedPanel
            extends JPanel {


        public RoundedPanel() {

            setOpaque(
                    false
            );
        }


        @Override
        protected void paintComponent(
                Graphics graphics
        ) {

            Graphics2D g2 =
                    (Graphics2D)
                            graphics.create();


            try {

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );


                // =================================================
                // SHADOW
                // =================================================

                g2.setColor(
                        new Color(
                                Theme.BLACK.getRed(),
                                Theme.BLACK.getGreen(),
                                Theme.BLACK.getBlue(),
                                16
                        )
                );


                g2.fillRoundRect(
                        3,
                        4,
                        getWidth() - 4,
                        getHeight() - 4,
                        18,
                        18
                );


                // =================================================
                // PANEL BODY
                // =================================================

                g2.setColor(
                        Theme.ALMOND_CREAM
                );


                g2.fillRoundRect(
                        0,
                        0,
                        getWidth() - 4,
                        getHeight() - 4,
                        18,
                        18
                );

            } finally {

                g2.dispose();
            }


            super.paintComponent(
                    graphics
            );
        }
    }
}