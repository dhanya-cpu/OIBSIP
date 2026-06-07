import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Rail Reserve – Online Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setMinimumSize(new Dimension(600, 520));
        setLocationRelativeTo(null);
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 244, 240));

        // ── Header ────────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(26, 26, 24));
        header.setPreferredSize(new Dimension(0, 52));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel brand = new JLabel("🚂  Rail Reserve");
        brand.setFont(new Font("Georgia", Font.BOLD, 16));
        brand.setForeground(Color.WHITE);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, Admin");
        welcome.setFont(new Font("Arial", Font.PLAIN, 12));
        welcome.setForeground(new Color(200, 200, 195));

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.PLAIN, 12));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(60, 60, 55));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(new CompoundBorder(
                new LineBorder(new Color(100, 100, 95), 1, true),
                new EmptyBorder(4, 12, 4, 12)
        ));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> logout());

        userPanel.add(welcome);
        userPanel.add(btnLogout);

        header.add(brand,     BorderLayout.WEST);
        header.add(userPanel, BorderLayout.EAST);

        // ── Tabbed pane ───────────────────────────────────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.PLAIN, 13));
        tabs.setBackground(new Color(245, 244, 240));

        tabs.addTab("🎫  Reservation",  new ReservationPanel());
        tabs.addTab("✖  Cancellation",  new CancellationPanel());

        add(header, BorderLayout.NORTH);
        add(tabs,   BorderLayout.CENTER);

        // ── Status bar ────────────────────────────────────────────────────────
        JLabel statusBar = new JLabel("  Rail Reserve © 2025 – Online Train Reservation System");
        statusBar.setFont(new Font("Arial", Font.PLAIN, 11));
        statusBar.setForeground(new Color(155, 155, 150));
        statusBar.setBorder(new MatteBorder(1, 0, 0, 0, new Color(220, 220, 215)));
        statusBar.setPreferredSize(new Dimension(0, 24));
        add(statusBar, BorderLayout.SOUTH);
    }

    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        }
    }
}
