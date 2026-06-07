import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CancellationPanel extends JPanel {

    private JTextField txtPNR;
    private JLabel     lblMessage;
    private JPanel     resultPanel;
    private JButton    btnConfirm;
    private Ticket     currentTicket;

    // Result labels
    private JLabel valPNR, valName, valTrain, valRoute, valDate, valClass, valStatus;

    public CancellationPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 244, 240));
        setBorder(new EmptyBorder(16, 16, 16, 16));
        buildUI();
    }

    private void buildUI() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 215), 1, true),
                new EmptyBorder(20, 24, 20, 24)
        ));

        // ── Message ──────────────────────────────────────────────────────────
        lblMessage = new JLabel(" ");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMessage.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ── PNR search ────────────────────────────────────────────────────────
        card.add(sectionTitle("Enter PNR Number"));
        card.add(Box.createVerticalStrut(10));

        JPanel searchRow = new JPanel(new BorderLayout(10, 0));
        searchRow.setOpaque(false);
        searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        txtPNR = new JTextField();
        txtPNR.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPNR.setBorder(new CompoundBorder(
                new LineBorder(new Color(180, 180, 175), 1, true),
                new EmptyBorder(5, 8, 5, 8)
        ));
        txtPNR.setToolTipText("e.g. PNR2025001");
        txtPNR.addActionListener(e -> fetchPNR());

        JButton btnSearch = makeBtn("Search", new Color(26, 26, 24), Color.WHITE);
        btnSearch.addActionListener(e -> fetchPNR());

        searchRow.add(txtPNR, BorderLayout.CENTER);
        searchRow.add(btnSearch, BorderLayout.EAST);

        card.add(searchRow);
        card.add(Box.createVerticalStrut(12));
        card.add(lblMessage);

        // ── Result panel ──────────────────────────────────────────────────────
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(new Color(240, 238, 234));
        resultPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(210, 210, 205), 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        resultPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resultPanel.setVisible(false);

        JLabel resultTitle = new JLabel("Booking Details");
        resultTitle.setFont(new Font("Georgia", Font.BOLD, 14));
        resultTitle.setForeground(new Color(26, 26, 24));
        resultPanel.add(resultTitle);
        resultPanel.add(Box.createVerticalStrut(10));

        valPNR    = new JLabel(); valName  = new JLabel();
        valTrain  = new JLabel(); valRoute = new JLabel();
        valDate   = new JLabel(); valClass = new JLabel();
        valStatus = new JLabel();

        resultPanel.add(resultRow("PNR",       valPNR));
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(resultRow("Passenger", valName));
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(resultRow("Train",     valTrain));
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(resultRow("Route",     valRoute));
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(resultRow("Date",      valDate));
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(resultRow("Class",     valClass));
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(resultRow("Status",    valStatus));

        card.add(resultPanel);
        card.add(Box.createVerticalStrut(12));

        // ── Cancel button row ─────────────────────────────────────────────────
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnBack = makeBtn("Back", new Color(240, 238, 234), new Color(26, 26, 24));
        btnConfirm      = makeBtn("OK – Confirm Cancellation", new Color(252, 235, 235), new Color(163, 45, 45));
        btnConfirm.setBorder(new CompoundBorder(
                new LineBorder(new Color(240, 149, 149), 1, true),
                new EmptyBorder(7, 16, 7, 16)
        ));

        btnBack.addActionListener(e -> clearForm());
        btnConfirm.addActionListener(e -> confirmCancel());

        btnRow.add(btnBack);
        btnRow.add(btnConfirm);

        btnConfirm.setVisible(false);
        btnBack.setVisible(false);

        // Store references
        card.add(btnRow);
        card.putClientProperty("btnRow", btnRow);
        card.putClientProperty("btnBack", btnBack);

        card.putClientProperty("btnConfirmRef", btnConfirm);

        add(card, BorderLayout.CENTER);

        // store btnBack so we can toggle it
        btnRow.putClientProperty("btnBack", btnBack);
    }

    private void fetchPNR() {
        String pnr = txtPNR.getText().trim().toUpperCase();
        if (pnr.isEmpty()) {
            setMessage("Please enter a PNR number.", true);
            return;
        }

        Ticket t = Database.findByPNR(pnr);
        if (t == null) {
            setMessage("No booking found for PNR: " + pnr, true);
            resultPanel.setVisible(false);
            hideActionButtons();
            return;
        }
        if ("Cancelled".equals(t.getStatus())) {
            setMessage("This ticket has already been cancelled.", true);
            resultPanel.setVisible(false);
            hideActionButtons();
            return;
        }

        currentTicket = t;
        valPNR.setText(t.getPnr());
        valName.setText(t.getPassengerName() + " (Age: " + t.getAge() + ", " + t.getGender() + ")");
        valTrain.setText(t.getTrainNumber() + " – " + t.getTrainName());
        valRoute.setText(t.getFrom() + " → " + t.getTo());
        valDate.setText(t.getJourneyDate());
        valClass.setText(t.getClassType());
        valStatus.setText(t.getStatus());
        valStatus.setForeground("Confirmed".equals(t.getStatus()) ? new Color(59, 109, 17) : new Color(163, 45, 45));

        resultPanel.setVisible(true);
        showActionButtons();
        lblMessage.setText(" ");
        revalidate();
        repaint();
    }

    private void confirmCancel() {
        if (currentTicket == null) return;
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to cancel ticket " + currentTicket.getPnr() + "?\nThis action cannot be undone.",
                "Confirm Cancellation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (choice == JOptionPane.OK_OPTION) {
            String pnr = currentTicket.getPnr();
            currentTicket.setStatus("Cancelled");
            clearForm();
            setMessage("✔  Ticket " + pnr + " has been successfully cancelled.", false);
        }
    }

    private void clearForm() {
        txtPNR.setText("");
        resultPanel.setVisible(false);
        hideActionButtons();
        currentTicket = null;
    }

    private void showActionButtons() {
        btnConfirm.setVisible(true);
        // find sibling btnBack via parent
        Container parent = btnConfirm.getParent();
        if (parent != null) {
            for (Component c : parent.getComponents()) {
                if (c instanceof JButton && ((JButton) c).getText().equals("Back")) {
                    c.setVisible(true);
                }
            }
        }
    }

    private void hideActionButtons() {
        btnConfirm.setVisible(false);
        Container parent = btnConfirm.getParent();
        if (parent != null) {
            for (Component c : parent.getComponents()) {
                if (c instanceof JButton && ((JButton) c).getText().equals("Back")) {
                    c.setVisible(false);
                }
            }
        }
    }

    private void setMessage(String msg, boolean error) {
        lblMessage.setText(msg);
        lblMessage.setForeground(error ? new Color(163, 45, 45) : new Color(59, 109, 17));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private JLabel sectionTitle(String text) {
        JLabel lbl = new JLabel(text.toUpperCase());
        lbl.setFont(new Font("Arial", Font.BOLD, 10));
        lbl.setForeground(new Color(107, 107, 103));
        lbl.setBorder(new MatteBorder(0, 0, 1, 0, new Color(220, 220, 215)));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JPanel resultRow(String label, JLabel valueLabel) {
        JPanel row = new JPanel(new GridLayout(1, 2));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(107, 107, 103));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 12));
        valueLabel.setForeground(new Color(26, 26, 24));
        row.add(lbl);
        row.add(valueLabel);
        return row;
    }

    private JButton makeBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        return btn;
    }
}
