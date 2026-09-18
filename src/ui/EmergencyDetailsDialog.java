package ui;

import model.Emergency;

import javax.swing.*;
import java.awt.*;

public class EmergencyDetailsDialog extends JDialog {

    public EmergencyDetailsDialog(
            JFrame parent,
            Emergency emergency
    ) {

        super(
                parent,
                "Emergency Details",
                true
        );

        setSize(
                560,
                540
        );

        setMinimumSize(
                new Dimension(
                        560,
                        540
                )
        );

        setLocationRelativeTo(
                parent
        );

        setLayout(
                new BorderLayout()
        );

        getContentPane().setBackground(
                Theme.BACKGROUND
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                Theme.HEADER
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        24,
                        18,
                        24
                )
        );


        JLabel titleLabel =
                new JLabel(
                        "EMERGENCY DETAILS"
                );

        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.LIGHT_TEXT
        );


        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // MAIN CONTENT
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
                        15,
                        25
                )
        );


        // =====================================================
        // INFORMATION PANEL
        // =====================================================

        JPanel informationPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                4,
                                12,
                                12
                        )
                );

        informationPanel.setBackground(
                Theme.BACKGROUND
        );


        addDetail(
                informationPanel,
                "Emergency ID",
                emergency.getEmergencyId()
        );

        addDetail(
                informationPanel,
                "Type",
                formatValue(
                        emergency.getType()
                )
        );

        addDetail(
                informationPanel,
                "Priority",
                formatValue(
                        emergency.getPriority()
                )
        );

        addDetail(
                informationPanel,
                "Status",
                formatValue(
                        emergency.getStatus()
                )
        );

        addDetail(
                informationPanel,
                "Location",
                emergency.getLocation()
        );

        addDetail(
                informationPanel,
                "Assigned Team",
                emergency.getAssignedTeamId()
                        == null
                        ? "Not Assigned"
                        : emergency.getAssignedTeamId()
        );

        addDetail(
                informationPanel,
                "Date / Time",
                emergency.getDateTime()
        );

        addDetail(
                informationPanel,
                "Record Type",
                "Emergency Incident"
        );


        mainPanel.add(
                informationPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // DESCRIPTION
        // =====================================================

        JPanel descriptionPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                8
                        )
                );

        descriptionPanel.setBackground(
                Theme.BACKGROUND
        );


        JLabel descriptionTitle =
                new JLabel(
                        "Description"
                );

        descriptionTitle.setFont(
                Theme.SECTION_FONT
        );

        descriptionTitle.setForeground(
                Theme.TEXT
        );


        JTextArea descriptionArea =
                new JTextArea();

        descriptionArea.setText(
                emergency.getDescription()
        );

        descriptionArea.setEditable(
                false
        );

        descriptionArea.setLineWrap(
                true
        );

        descriptionArea.setWrapStyleWord(
                true
        );

        descriptionArea.setFont(
                Theme.NORMAL_FONT
        );

        descriptionArea.setForeground(
                Theme.TEXT
        );

        descriptionArea.setBackground(
                Theme.ALMOND_CREAM
        );

        descriptionArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Theme.KHAKI_BEIGE
                        ),
                        BorderFactory.createEmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );


        JScrollPane descriptionScrollPane =
                new JScrollPane(
                        descriptionArea
                );

        descriptionScrollPane.setPreferredSize(
                new Dimension(
                        0,
                        130
                )
        );


        descriptionPanel.add(
                descriptionTitle,
                BorderLayout.NORTH
        );

        descriptionPanel.add(
                descriptionScrollPane,
                BorderLayout.CENTER
        );


        mainPanel.add(
                descriptionPanel,
                BorderLayout.CENTER
        );


        add(
                mainPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BOTTOM BUTTON
        // =====================================================

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottomPanel.setBackground(
                Theme.BACKGROUND
        );

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        20,
                        15,
                        20
                )
        );


        JButton closeButton =
                new JButton(
                        "CLOSE"
                );

        Theme.styleSecondaryButton(
                closeButton
        );


        closeButton.addActionListener(
                e -> dispose()
        );


        bottomPanel.add(
                closeButton
        );


        add(
                bottomPanel,
                BorderLayout.SOUTH
        );
    }


    // =========================================================
    // ADD DETAIL
    // =========================================================

    private void addDetail(
            JPanel panel,
            String labelText,
            String value
    ) {

        JPanel detailPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                3
                        )
                );

        detailPanel.setBackground(
                Theme.BACKGROUND
        );


        JLabel label =
                new JLabel(
                        labelText
                );

        label.setFont(
                Theme.SMALL_FONT
        );

        label.setForeground(
                Theme.MUTED_TEXT
        );


        JLabel valueLabel =
                new JLabel(
                        value == null
                                ? "N/A"
                                : value
                );

        valueLabel.setFont(
                Theme.NORMAL_FONT
        );

        valueLabel.setForeground(
                Theme.TEXT
        );


        detailPanel.add(
                label,
                BorderLayout.NORTH
        );

        detailPanel.add(
                valueLabel,
                BorderLayout.CENTER
        );


        panel.add(
                detailPanel
        );
    }


    // =========================================================
    // FORMAT VALUE
    // =========================================================

    private String formatValue(
            Object value
    ) {

        if (value == null) {
            return "N/A";
        }

        String text =
                value.toString()
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

        for (String word : words) {

            if (word.isEmpty()) {
                continue;
            }

            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );

            if (word.length() > 1) {

                result.append(
                        word.substring(1)
                );
            }

            result.append(" ");
        }

        return result.toString().trim();
    }
}