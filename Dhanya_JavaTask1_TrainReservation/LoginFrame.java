import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {

    private JTextField     txtUserId;
    private JPasswordField txtPassword;
    private JLabel         lblMessage;

    public LoginFrame() {
        setTitle("Rail Reserve – Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    private void buildUI() {
        // ── Outer panel (dark background) ───────────────────────────────────
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(new Color(26, 26, 24));
        setContentPane(outer);

        // ── Card panel ──────────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 215), 1, true),
                new EmptyBorder(30, 36, 30, 36)
        ));
        card.setMaximumSize(new Dimension(320, 340));

        // ── Icon + title ─────────────────────────────────────────────────────
        JLabel iconLabel = new JLabel(" ", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Rail Reserve", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 20));
        title.setForeground(new Color(26, 26, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Online Train Reservation System", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setForeground(new Color(107, 107, 103));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Fields ───────────────────────────────────────────────────────────
        JLabel lblId = makeLabel("Login ID");
        txtUserId = makeTextField("Enter your login ID");

        JLabel lblPwd = makeLabel("Password");
        txtPassword = new JPasswordField();
        styleField(txtPassword);
        txtPassword.setToolTipText("Enter your password");

        // ── Message label ────────────────────────────────────────────────────
        lblMessage = new JLabel(" ");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMessage.setForeground(new Color(163, 45, 45));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Sign In button ────────────────────────────────────────────────────
        JButton btnLogin = new JButton("Sign In");
        styleButton(btnLogin, new Color(26, 26, 24), Color.WHITE);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnLogin.addActionListener(e -> doLogin());

        // ── Hint ─────────────────────────────────────────────────────────────
        JLabel hint = new JLabel("Demo: admin / 1234", SwingConstants.CENTER);
        hint.setFont(new Font("Arial", Font.ITALIC, 11));
        hint.setForeground(new Color(155, 155, 150));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Enter key on password ─────────────────────────────────────────────
        txtPassword.addActionListener(e -> doLogin());
        txtUserId.addActionListener(e -> txtPassword.requestFocus());

        // ── Assemble card ─────────────────────────────────────────────────────
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));
        card.add(lblId);
        card.add(Box.createVerticalStrut(4));
        card.add(txtUserId);
        card.add(Box.createVerticalStrut(12));
        card.add(lblPwd);
        card.add(Box.createVerticalStrut(4));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(8));
        card.add(lblMessage);
        card.add(Box.createVerticalStrut(8));
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(12));
        card.add(hint);

        outer.add(card);
    }

    private void doLogin() {
        String userId   = txtUserId.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (Database.authenticate(userId, password)) {
            dispose();
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        } else {
            lblMessage.setText("Invalid login ID or password.");
            txtPassword.setText("");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        lbl.setForeground(new Color(107, 107, 103));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField field = new JTextField();
        styleField(field);
        field.setToolTipText(placeholder);
        return field;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(180, 180, 175), 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
    }
}
