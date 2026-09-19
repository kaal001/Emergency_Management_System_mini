package ui;

import enums.EmergencyStatus;
import enums.EmergencyType;
import enums.Priority;
import model.Emergency;

import javax.swing.*;
import java.awt.*;

public class EmergencyDetailsDialog extends JDialog {

    // =========================================================
    // DATA
    // =========================================================

    private final Emergency emergency;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public EmergencyDetailsDialog(
            JFrame owner,
            Emergency emergency
    ) {

        super(
                owner,
                "Emergency Details",
                true
        );

        this.emergency = emergency;

        initializeDialog();

        buildUI();
    }


    // =========================================================
    // DIALOG SETUP
    // =========================================================

    private void initializeDialog() {

        setSize(
                610,
                650
        );

        setMinimumSize(
                new Dimension(
                        610,
                        650
                )
        );

        setResizable(
                false
        );

        setDefaultCloseOperation(
                JDialog.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(
                getOwner()
        );

        getContentPane().setBackground(
                Theme.BACKGROUND
        );
    }


    // =========================================================
    // BUILD UI
    // =========================================================

    private void buildUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
                                14
                        )
                );

        mainPanel.setBackground(
                Theme.BACKGROUND
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        22,
                        18,
                        22
                )
        );


        // =====================================================
        // HEADER
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


        JLabel moduleLabel =
                new JLabel(
                        "EMERGENCY RECORD"
                );

        moduleLabel.setFont(
                Theme.SMALL_FONT
        );

        moduleLabel.setForeground(
                Theme.STONE_BROWN
        );


        JLabel titleLabel =
                new JLabel(
                        "EMERGENCY DETAILS"
                );

        titleLabel.setFont(
                Theme.PAGE_TITLE_FONT
        );

        titleLabel.setForeground(
                Theme.TEXT
        );


        titlePanel.add(
                moduleLabel
        );

        titlePanel.add(
                Box.createVerticalStrut(
                        3
                )
        );

        titlePanel.add(
                titleLabel
        );


        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );


        // =====================================================
        // CURRENT STATUS
        // =====================================================

        JPanel statusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                6,
                                5
                        )
                );

        statusPanel.setOpaque(
                false
        );


        JLabel statusDot =
                new JLabel(
                        "●"
                );

        statusDot.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        statusDot.setForeground(
                Theme.ACCENT
        );


        JLabel statusText =
                new JLabel(
                        formatStatus(
                                emergency.getStatus()
                        )
                );

        statusText.setFont(
                Theme.NORMAL_FONT
        );

        statusText.setForeground(
                Theme.STONE_BROWN
        );


        statusPanel.add(
                statusDot
        );

        statusPanel.add(
                statusText
        );


        headerPanel.add(
                statusPanel,
                BorderLayout.EAST
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CONTENT PANEL
        // =====================================================

        JPanel contentPanel =
                new JPanel();

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        contentPanel.setOpaque(
                false
        );


        // =====================================================
        // INCIDENT INFORMATION CARD
        // =====================================================

        JPanel detailsCard =
                createCard();

        detailsCard.setLayout(
                new BorderLayout(
                        0,
                        12
                )
        );


        JLabel detailsTitle =
                new JLabel(
                        "INCIDENT INFORMATION"
                );

        detailsTitle.setFont(
                Theme.SECTION_FONT
        );

        detailsTitle.setForeground(
                Theme.TEXT
        );


        detailsCard.add(
                detailsTitle,
                BorderLayout.NORTH
        );


        JPanel informationPanel =
                new JPanel(
                        new GridBagLayout()
                );

        informationPanel.setOpaque(
                false
        );


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        6,
                        4,
                        6,
                        4
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;


        // =====================================================
        // INFORMATION
        // =====================================================

        addInfoRow(
                informationPanel,
                gbc,
                0,
                "EMERGENCY ID",
                safeText(
                        emergency.getEmergencyId()
                )
        );


        addInfoRow(
                informationPanel,
                gbc,
                1,
                "TYPE",
                formatEmergencyType(
                        emergency.getType()
                )
        );


        addInfoRow(
                informationPanel,
                gbc,
                2,
                "PRIORITY",
                formatPriority(
                        emergency.getPriority()
                )
        );


        addInfoRow(
                informationPanel,
                gbc,
                3,
                "STATUS",
                formatStatus(
                        emergency.getStatus()
                )
        );


        addInfoRow(
                informationPanel,
                gbc,
                4,
                "LOCATION",
                safeText(
                        emergency.getLocation()
                )
        );


        String assignedTeam =
                emergency.getAssignedTeamId();

        if (
                assignedTeam == null
                        || assignedTeam
                        .trim()
                        .isEmpty()
        ) {

            assignedTeam =
                    "Not Assigned";
        }


        addInfoRow(
                informationPanel,
                gbc,
                5,
                "ASSIGNED TEAM",
                assignedTeam
        );


        addInfoRow(
                informationPanel,
                gbc,
                6,
                "DATE / TIME",
                safeText(
                        emergency.getDateTime()
                )
        );


        detailsCard.add(
                informationPanel,
                BorderLayout.CENTER
        );


        contentPanel.add(
                detailsCard
        );


        // =====================================================
        // GAP
        // =====================================================

        contentPanel.add(
                Box.createVerticalStrut(
                        14
                )
        );


        // =====================================================
        // DESCRIPTION CARD
        // =====================================================

        JPanel descriptionCard =
                createCard();

        descriptionCard.setLayout(
                new BorderLayout(
                        0,
                        9
                )
        );


        JLabel descriptionTitle =
                new JLabel(
                        "DESCRIPTION"
                );

        descriptionTitle.setFont(
                Theme.SECTION_FONT
        );

        descriptionTitle.setForeground(
                Theme.TEXT
        );


        descriptionCard.add(
                descriptionTitle,
                BorderLayout.NORTH
        );


        JTextArea descriptionArea =
                new JTextArea(
                        safeText(
                                emergency.getDescription()
                        )
                );

        descriptionArea.setEditable(
                false
        );

        descriptionArea.setFocusable(
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
                Theme.WHITE
        );

        descriptionArea.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );

        descriptionScroll.setBorder(
                Theme.createRoundedBorder(
                        Theme.STONE_BROWN,
                        11,
                        1,
                        1
                )
        );

        descriptionScroll.setPreferredSize(
                new Dimension(
                        0,
                        125
                )
        );


        descriptionCard.add(
                descriptionScroll,
                BorderLayout.CENTER
        );


        contentPanel.add(
                descriptionCard
        );


        // =====================================================
        // CONTENT SCROLL
        // =====================================================

        JScrollPane contentScroll =
                new JScrollPane(
                        contentPanel
                );

        contentScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        contentScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        contentScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        contentScroll.getViewport()
                .setOpaque(
                        false
                );


        mainPanel.add(
                contentScroll,
                BorderLayout.CENTER
        );


        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footerPanel =
                new JPanel(
                        new BorderLayout()
                );

        footerPanel.setOpaque(
                false
        );


        JLabel footerLabel =
                new JLabel(
                        "READ-ONLY INCIDENT VIEW"
                );

        footerLabel.setFont(
                Theme.SMALL_FONT
        );

        footerLabel.setForeground(
                Theme.MUTED_TEXT
        );


        JButton closeButton =
                new JButton(
                        "CLOSE"
                );

        Theme.styleHeaderButton(
                closeButton
        );


        closeButton.addActionListener(
                e -> dispose()
        );


        footerPanel.add(
                footerLabel,
                BorderLayout.WEST
        );

        footerPanel.add(
                closeButton,
                BorderLayout.EAST
        );


        mainPanel.add(
                footerPanel,
                BorderLayout.SOUTH
        );


        // =====================================================
        // FINAL
        // =====================================================

        setContentPane(
                mainPanel
        );

        getRootPane().setDefaultButton(
                closeButton
        );
    }


    // =========================================================
    // CREATE CARD
    // =========================================================

    private JPanel createCard() {

        JPanel card =
                new RoundedPanel();

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        Theme.createRoundedBorder(
                                Theme.KHAKI_BEIGE,
                                16,
                                1,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                17,
                                15,
                                17
                        )
                )
        );

        return card;
    }


    // =========================================================
    // INFO ROW
    // =========================================================

    private void addInfoRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            String valueText
    ) {

        gbc.gridy =
                row;

        gbc.gridx =
                0;

        gbc.weightx =
                0.0;


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

        label.setPreferredSize(
                new Dimension(
                        125,
                        27
                )
        );


        panel.add(
                label,
                gbc
        );


        gbc.gridx =
                1;

        gbc.weightx =
                1.0;


        JLabel value =
                new JLabel(
                        valueText
                );

        value.setFont(
                Theme.NORMAL_FONT
        );

        value.setForeground(
                Theme.TEXT
        );


        panel.add(
                value,
                gbc
        );
    }


    // =========================================================
    // FORMAT TYPE
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


        return capitalizeWords(
                text
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
    // FORMAT STATUS
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


                // -------------------------------------------------
                // SHADOW
                // -------------------------------------------------

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


                // -------------------------------------------------
                // BODY
                // -------------------------------------------------

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