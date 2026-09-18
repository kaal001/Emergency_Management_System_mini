package ui;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import manager.EmergencyManager;
import model.Emergency;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EmergencyHistoryPanel extends JPanel {

    private final EmergencyManager emergencyManager;

    private JTextField searchField;

    private JComboBox<String> typeFilter;

    private JComboBox<String> priorityFilter;

    private JComboBox<String> statusFilter;

    private JTable historyTable;

    private DefaultTableModel tableModel;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public EmergencyHistoryPanel(
            EmergencyManager emergencyManager
    ) {

        this.emergencyManager =
                emergencyManager;

        setLayout(
                new BorderLayout()
        );

        setBackground(
                Theme.BACKGROUND
        );

        buildUI();

        loadHistory();
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        // =====================================================
        // MAIN WRAPPER
        // =====================================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                15
                        )
                );

        mainPanel.setBackground(
                Theme.BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        25,
                        20,
                        25
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


        JPanel titlePanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                5
                        )
                );

        titlePanel.setBackground(
                Theme.BACKGROUND
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
                        "Review recorded emergency incidents and their final status."
                );

        subtitleLabel.setFont(
                Theme.NORMAL_FONT
        );

        subtitleLabel.setForeground(
                Theme.MUTED_TEXT
        );


        titlePanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        titlePanel.add(
                subtitleLabel,
                BorderLayout.CENTER
        );


        JButton refreshButton =
                new JButton(
                        "REFRESH"
                );

        Theme.styleHeaderButton(
                refreshButton
        );

        refreshButton.addActionListener(
                e -> loadHistory()
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                refreshButton,
                BorderLayout.EAST
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // FILTER PANEL
        // =====================================================

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                10
                        )
                );

        filterPanel.setBackground(
                Theme.BACKGROUND
        );

        filterPanel.setBorder(
                BorderFactory.createLineBorder(
                        Theme.KHAKI_BEIGE
                )
        );


        JLabel searchLabel =
                new JLabel(
                        "Search"
                );

        searchLabel.setFont(
                Theme.NORMAL_FONT
        );

        searchLabel.setForeground(
                Theme.TEXT
        );


        searchField =
                new JTextField(
                        14
                );


        JLabel typeLabel =
                new JLabel(
                        "Type"
                );

        typeLabel.setFont(
                Theme.NORMAL_FONT
        );

        typeLabel.setForeground(
                Theme.TEXT
        );


        typeFilter =
                new JComboBox<>();

        typeFilter.addItem(
                "All Types"
        );

        for (
                EmergencyType type :
                EmergencyType.values()
        ) {

            typeFilter.addItem(
                    formatEmergencyType(
                            type
                    )
            );
        }


        JLabel priorityLabel =
                new JLabel(
                        "Priority"
                );

        priorityLabel.setFont(
                Theme.NORMAL_FONT
        );

        priorityLabel.setForeground(
                Theme.TEXT
        );


        priorityFilter =
                new JComboBox<>();

        priorityFilter.addItem(
                "All Priorities"
        );

        for (
                Priority priority :
                Priority.values()
        ) {

            priorityFilter.addItem(
                    formatPriority(
                            priority
                    )
            );
        }


        JLabel statusLabel =
                new JLabel(
                        "Status"
                );

        statusLabel.setFont(
                Theme.NORMAL_FONT
        );

        statusLabel.setForeground(
                Theme.TEXT
        );


        statusFilter =
                new JComboBox<>();

        statusFilter.addItem(
                "All Statuses"
        );

        for (
                EmergencyStatus status :
                EmergencyStatus.values()
        ) {

            statusFilter.addItem(
                    formatStatus(
                            status
                    )
            );
        }


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


        filterButton.addActionListener(
                e -> applyFilters()
        );


        clearButton.addActionListener(
                e -> clearFilters()
        );


        filterPanel.add(
                searchLabel
        );

        filterPanel.add(
                searchField
        );

        filterPanel.add(
                typeLabel
        );

        filterPanel.add(
                typeFilter
        );

        filterPanel.add(
                priorityLabel
        );

        filterPanel.add(
                priorityFilter
        );

        filterPanel.add(
                statusLabel
        );

        filterPanel.add(
                statusFilter
        );

        filterPanel.add(
                filterButton
        );

        filterPanel.add(
                clearButton
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


        historyTable =
                new JTable(
                        tableModel
                );


        // =====================================================
        // TABLE STYLE
        // =====================================================

        historyTable.setFont(
                Theme.NORMAL_FONT
        );

        historyTable.setRowHeight(
                30
        );

        historyTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        historyTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_LAST_COLUMN
        );

        historyTable.setFillsViewportHeight(
                true
        );


        historyTable.getTableHeader()
                .setFont(
                        Theme.NORMAL_FONT
                );


        historyTable.getTableHeader()
                .setBackground(
                        Theme.SIDEBAR
                );


        historyTable.getTableHeader()
                .setForeground(
                        Theme.LIGHT_TEXT
                );


        historyTable.getTableHeader()
                .setReorderingAllowed(
                        false
                );


        historyTable.setGridColor(
                Theme.KHAKI_BEIGE
        );


        historyTable.setSelectionBackground(
                Theme.KHAKI_BEIGE
        );

        historyTable.setSelectionForeground(
                Theme.BLACK
        );


        // =====================================================
        // CENTER ALIGNMENT
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        historyTable
                .getColumnModel()
                .getColumn(0)
                .setPreferredWidth(
                        120
                );

        historyTable
                .getColumnModel()
                .getColumn(1)
                .setPreferredWidth(
                        145
                );

        historyTable
                .getColumnModel()
                .getColumn(2)
                .setPreferredWidth(
                        100
                );

        historyTable
                .getColumnModel()
                .getColumn(3)
                .setPreferredWidth(
                        180
                );

        historyTable
                .getColumnModel()
                .getColumn(4)
                .setPreferredWidth(
                        120
                );

        historyTable
                .getColumnModel()
                .getColumn(5)
                .setPreferredWidth(
                        130
                );

        historyTable
                .getColumnModel()
                .getColumn(6)
                .setPreferredWidth(
                        145
                );


        historyTable
                .getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        historyTable
                .getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        centerRenderer
                );

        historyTable
                .getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        centerRenderer
                );

        historyTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        centerRenderer
                );

        historyTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        centerRenderer
                );


        JScrollPane scrollPane =
                new JScrollPane(
                        historyTable
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        Theme.KHAKI_BEIGE
                )
        );


        // =====================================================
        // INFORMATION LABEL
        // =====================================================

        JLabel informationLabel =
                new JLabel(
                        "Double-click an emergency to view full details."
                );

        informationLabel.setFont(
                Theme.SMALL_FONT
        );

        informationLabel.setForeground(
                Theme.MUTED_TEXT
        );

        informationLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        3,
                        0,
                        3
                )
        );


        // =====================================================
        // DOUBLE CLICK → DETAILS DIALOG
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

                            openSelectedEmergency();
                        }
                    }
                }
        );


        // =====================================================
        // CENTER CONTENT
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                8
                        )
                );

        centerPanel.setBackground(
                Theme.BACKGROUND
        );


        centerPanel.add(
                filterPanel,
                BorderLayout.NORTH
        );


        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        centerPanel.add(
                informationLabel,
                BorderLayout.SOUTH
        );


        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // LOAD HISTORY
    // =========================================================

    public void loadHistory() {

        ArrayList<Emergency> emergencies =
                emergencyManager
                        .getAllEmergencies();

        displayEmergencies(
                emergencies
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
                        "Not Assigned";
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
    // APPLY FILTERS
    // =========================================================

    private void applyFilters() {

        String keyword =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();


        String selectedType =
                typeFilter
                        .getSelectedItem()
                        .toString();


        String selectedPriority =
                priorityFilter
                        .getSelectedItem()
                        .toString();


        String selectedStatus =
                statusFilter
                        .getSelectedItem()
                        .toString();


        ArrayList<Emergency> filtered =
                new ArrayList<>();


        for (
                Emergency emergency :
                emergencyManager.getAllEmergencies()
        ) {

            boolean matchesKeyword =
                    keyword.isEmpty()
                            || containsIgnoreCase(
                            emergency.getEmergencyId(),
                            keyword
                    )
                            || containsIgnoreCase(
                            emergency.getLocation(),
                            keyword
                    )
                            || containsIgnoreCase(
                            emergency.getDescription(),
                            keyword
                    )
                            || containsIgnoreCase(
                            formatEmergencyType(
                                    emergency.getType()
                            ),
                            keyword
                    );


            boolean matchesType =
                    selectedType.equals(
                            "All Types"
                    )
                            || selectedType.equals(
                            formatEmergencyType(
                                    emergency.getType()
                            )
                    );


            boolean matchesPriority =
                    selectedPriority.equals(
                            "All Priorities"
                    )
                            || selectedPriority.equals(
                            formatPriority(
                                    emergency.getPriority()
                            )
                    );


            boolean matchesStatus =
                    selectedStatus.equals(
                            "All Statuses"
                    )
                            || selectedStatus.equals(
                            formatStatus(
                                    emergency.getStatus()
                            )
                    );


            if (
                    matchesKeyword
                            && matchesType
                            && matchesPriority
                            && matchesStatus
            ) {

                filtered.add(
                        emergency
                );
            }
        }


        displayEmergencies(
                filtered
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
    // OPEN SELECTED EMERGENCY
    // =========================================================

    private void openSelectedEmergency() {

        int selectedRow =
                historyTable.getSelectedRow();


        if (
                selectedRow < 0
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an emergency first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

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


        if (
                emergency == null
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Emergency record could not be found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        JFrame parentFrame =
                (JFrame)
                        SwingUtilities
                                .getWindowAncestor(
                                        this
                                );


        EmergencyDetailsDialog dialog =
                new EmergencyDetailsDialog(
                        parentFrame,
                        emergency
                );


        dialog.setVisible(
                true
        );
    }


    // =========================================================
    // SEARCH HELPER
    // =========================================================

    private boolean containsIgnoreCase(
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

        if (
                type == null
        ) {

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

        if (
                priority == null
        ) {

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

        if (
                status == null
        ) {

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