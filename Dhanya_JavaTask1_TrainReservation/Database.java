import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    // ── Train data ──────────────────────────────────────────────────────────
    public static final Map<String, String> TRAINS = new HashMap<>();
    static {
        TRAINS.put("12345", "Chennai Express");
        TRAINS.put("22691", "Rajdhani Express");
        TRAINS.put("16101", "Boat Mail");
        TRAINS.put("11028", "Chennai Mail");
    }

    // ── Station list ────────────────────────────────────────────────────────
    public static final String[] STATIONS = {
        "Chennai Central", "Mumbai CST", "New Delhi",
        "Kolkata Howrah", "Bangalore City", "Hyderabad Deccan",
        "Ahmedabad", "Pune", "Jaipur", "Lucknow"
    };

    // ── Class types ─────────────────────────────────────────────────────────
    public static final String[] CLASSES = {
        "Sleeper (SL)", "Third AC (3A)", "Second AC (2A)",
        "First AC (1A)", "Chair Car (CC)", "Executive Class (EC)"
    };

    // ── Ticket storage ──────────────────────────────────────────────────────
    private static final List<Ticket> tickets = new ArrayList<>();

    static {
        // Pre-loaded sample tickets
        tickets.add(new Ticket("PNR2025001", "Arun Sharma",   35, "Male",   "9876543210",
                "12345", "Chennai Express",  "New Delhi",       "Chennai Central",   "2025-07-15", "Second AC (2A)", "Confirmed"));
        tickets.add(new Ticket("PNR2025002", "Priya Nair",    28, "Female", "9123456780",
                "22691", "Rajdhani Express", "Mumbai CST",      "New Delhi",         "2025-07-20", "Third AC (3A)",  "Confirmed"));
        tickets.add(new Ticket("PNR2025003", "Karthik R",     42, "Male",   "9988776655",
                "16101", "Boat Mail",        "Chennai Central", "Kolkata Howrah",    "2025-06-10", "Sleeper (SL)",   "Confirmed"));
    }

    // ── Methods ─────────────────────────────────────────────────────────────
    public static void addTicket(Ticket t) {
        tickets.add(t);
    }

    public static Ticket findByPNR(String pnr) {
        for (Ticket t : tickets) {
            if (t.getPnr().equalsIgnoreCase(pnr)) return t;
        }
        return null;
    }

    public static List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public static String generatePNR() {
        return "PNR" + System.currentTimeMillis() % 10_000_000L;
    }

    // ── Auth ─────────────────────────────────────────────────────────────────
    public static boolean authenticate(String userId, String password) {
        return "admin".equals(userId) && "1234".equals(password);
    }
}
