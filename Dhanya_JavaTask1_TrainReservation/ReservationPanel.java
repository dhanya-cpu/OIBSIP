import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class ReservationPanel extends JPanel {

    private JTextField  txtName, txtAge, txtPhone, txtTrainName;
    private JComboBox<String> cmbGender, cmbTrainNo, cmbFrom, cmbTo, cmbClass;
    private JTextField  txtDate;
    private JLabel      lblMessage;

    public ReservationPanel() {
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

        // ── Passenger Details ─────────────────────────────────────────────────
        card.add(sectionTitle("Passenger Details"));
        card.add(Box.createVerticalStrut(8));

        JPanel row1 = twoColRow(
                labeled("Full Name", txtName    = textField()),
                labeled("Age",       txtAge     = textField())
        );
        card.add(row1);
        card.add(Box.createVerticalStrut(10));

        cmbGender = comboBox(new String[]{"", "Male", "Female", "Other"});
        JPanel row2 = twoColRow(
                labeled("Gender",       cmbGender),
                labeled("Phone Number", txtPhone = textField())
        );
        card.add(row2);
        card.add(Box.createVerticalStrut(16));

        // ── Journey Details ───────────────────────────────────────────────────
        card.add(sectionTitle("Journey Details"));
        card.add(Box.createVerticalStrut(8));

        String[] trainNos = new String[]{"", "12345", "22691", "16101", "11028"};
        cmbTrainNo = comboBox(trainNos);
        txtTrainName = textField();
        txtTrainName.setEditable(false);
        txtTrainName.setBackground(new Color(240, 238, 234));
        txtTrainName.setForeground(new Color(107, 107, 103));
        cmbTrainNo.addActionListener(e -> fillTrainName());

        JPanel row3 = twoColRow(
                labeled("Train Number", cmbTrainNo),
                labeled("Train Name",   txtTrainName)
        );
        card.add(row3);
        card.add(Box.createVerticalStrut(10));

        String[] stationItems = prependBlank(Database.STATIONS);
        cmbFrom = comboBox(stationItems);
        cmbTo   = comboBox(stationItems);
        JPanel row4 = twoColRow(
                labeled("From", cmbFrom),
                labeled("To (Destination)", cmbTo)
        );
        card.add(row4);
        card.add(Box.createVerticalStrut(10));

        txtDate = textField();
        txtDate.setToolTipText("YYYY-MM-DD");
        txtDate.setText(LocalDate.now().plusDays(1).toString());

        String[] classItems = prependBlank(Database.CLASSES);
        cmbClass = comboBox(classItems);
        JPanel row5 = twoColRow(
                labeled("Date of Journey (YYYY-MM-DD)", txtDate),
                labeled("Class Type", cmbClass)
        );
        card.add(row5);
        card.add(Box.createVerticalStrut(16));

        // ── Message + Buttons ─────────────────────────────────────────────────
        card.add(lblMessage);
        card.add(Box.createVerticalStrut(8));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnClear  = makeBtn("Clear",  new Color(240, 238, 234), new Color(26,26,24));
        JButton btnInsert = makeBtn("Insert", new Color(26, 26, 24),    Color.WHITE);
        btnClear.addActionListener(e -> clearForm());
        btnInsert.addActionListener(e -> insertReservation());

        btnRow.add(btnClear);
        btnRow.add(btnInsert);
        card.add(btnRow);

        add(card, BorderLayout.CENTER);
    }

    private void fillTrainName() {
        String no = (String) cmbTrainNo.getSelectedItem();
        txtTrainName.setText(no != null ? Database.TRAINS.getOrDefault(no, "") : "");
    }

    private void insertReservation() {
        String name   = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String phone  = txtPhone.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();
        String train  = (String) cmbTrainNo.getSelectedItem();
        String from   = (String) cmbFrom.getSelectedItem();
        String to     = (String) cmbTo.getSelectedItem();
        String date   = txtDate.getText().trim();
        String cls    = (String) cmbClass.getSelectedItem();

        // Validation
        if (name.isEmpty() || ageStr.isEmpty() || phone.isEmpty() ||
            gender == null || gender.isEmpty() ||
            train  == null || train.isEmpty()  ||
            from   == null || from.isEmpty()   ||
            to     == null || to.isEmpty()     ||
            date.isEmpty() ||
            cls    == null || cls.isEmpty()) {
            setMessage("Please fill in all fields before inserting.", true);
            return;
        }
        if (from.equals(to)) {
            setMessage("Origin and destination cannot be the same.", true);
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 1 || age > 120) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            setMessage("Please enter a valid age (1–120).", true);
            return;
        }

        String pnr = Database.generatePNR();
        Ticket t   = new Ticket(pnr, name, age, gender, phone, train,
                                Database.TRAINS.get(train), from, to, date, cls, "Confirmed");
        Database.addTicket(t);
        setMessage("✔  Reservation successful! Your PNR: " + pnr, false);
        clearForm();
    }

    private void clearForm() {
        txtName.setText("");
        txtAge.setText("");
        txtPhone.setText("");
        cmbGender.setSelectedIndex(0);
        cmbTrainNo.setSelectedIndex(0);
        txtTrainName.setText("");
        cmbFrom.setSelectedIndex(0);
        cmbTo.setSelectedIndex(0);
        txtDate.setText(LocalDate.now().plusDays(1).toString());
        cmbClass.setSelectedIndex(0);
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

    private JTextField textField() {
        JTextField f = new JTextField();
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setBorder(new CompoundBorder(
                new LineBorder(new Color(180, 180, 175), 1, true),
                new EmptyBorder(5, 8, 5, 8)
        ));
        f.setPreferredSize(new Dimension(0, 32));
        return f;
    }

    private JComboBox<String> comboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Arial", Font.PLAIN, 13));
        cb.setBorder(new LineBorder(new Color(180, 180, 175), 1, true));
        cb.setPreferredSize(new Dimension(0, 32));
        return cb;
    }

    private JPanel labeled(String labelText, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Arial", Font.PLAIN, 11));
        lbl.setForeground(new Color(107, 107, 103));
        p.add(lbl, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JPanel twoColRow(JPanel left, JPanel right) {
        JPanel row = new JPanel(new GridLayout(1, 2, 12, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(left);
        row.add(right);
        return row;
    }

    private JButton makeBtn(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        return btn;
    }

    private String[] prependBlank(String[] arr) {
        String[] result = new String[arr.length + 1];
        result[0] = "";
        System.arraycopy(arr, 0, result, 1, arr.length);
        return result;
    }
}
