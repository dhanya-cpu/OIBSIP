# 🚆 Rail Reserve – Online Train Reservation System

A Java Swing desktop application for managing train ticket reservations and cancellations. Built as part of the **OIBSIP (Oasis Infobyte Internship Program)**.

---

## 📌 Features

- **Secure Login** – Admin authentication before accessing the system
- **Train Reservation** – Book tickets by entering passenger and journey details
- **PNR Generation** – Unique PNR number generated automatically for each booking
- **Ticket Cancellation** – Search by PNR and cancel confirmed bookings
- **Pre-loaded Sample Data** – Three sample tickets available on launch for testing
- **Clean UI** – Minimal and modern Java Swing interface

---

## 🖥️ Screenshots

> _Login Screen → Dashboard → Reservation Tab → Cancellation Tab_

---

## 🗂️ Project Structure

```
ReservationSystem/
├── Main.java               # Entry point – launches the login window
├── LoginFrame.java         # Login screen UI and authentication
├── MainFrame.java          # Main dashboard with tabbed navigation
├── ReservationPanel.java   # Reservation form and booking logic
├── CancellationPanel.java  # PNR search and ticket cancellation
├── Ticket.java             # Ticket data model
├── Database.java           # In-memory data store (trains, stations, tickets)
└── README.md
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher installed
- `java` and `javac` available in your system PATH

### Steps

```bash
# Navigate to the project folder
cd path/to/ReservationSystem

# Compile all Java files
javac *.java

# Run the application
java Main
```

### Login Credentials
| Field    | Value   |
|----------|---------|
| Login ID | `admin` |
| Password | `1234`  |

---

## 📋 How to Use

### Making a Reservation
1. Log in with the admin credentials
2. Go to the **Reservation** tab
3. Fill in passenger details (Name, Age, Gender, Phone)
4. Select Train Number, From/To stations, Journey Date, and Class
5. Click **Insert** – your PNR will be displayed on success

### Cancelling a Ticket
1. Go to the **Cancellation** tab
2. Enter the PNR number (e.g., `PNR2025001`) and click **Search**
3. Review the booking details shown
4. Click **OK – Confirm Cancellation** to cancel the ticket

### Sample PNRs (pre-loaded)
| PNR         | Passenger     | Train             | Route                          |
|-------------|---------------|-------------------|--------------------------------|
| PNR2025001  | Arun Sharma   | Chennai Express   | New Delhi → Chennai Central    |
| PNR2025002  | Priya Nair    | Rajdhani Express  | Mumbai CST → New Delhi         |
| PNR2025003  | Karthik R     | Boat Mail         | Chennai Central → Kolkata Howrah |

---

## 🛠️ Tech Stack

| Technology | Details              |
|------------|----------------------|
| Language   | Java                 |
| UI Library | Java Swing (AWT)     |
| Storage    | In-memory (runtime)  |
| JDK        | Java 8+              |

---

## 📦 Available Trains

| Train No | Train Name        |
|----------|-------------------|
| 12345    | Chennai Express   |
| 22691    | Rajdhani Express  |
| 16101    | Boat Mail         |
| 11028    | Chennai Mail      |

---

## 🏫 Internship

This project was developed as a task for the **Oasis Infobyte Internship Program (OIBSIP)**.

---

## 📄 License

This project is open source and free to use for educational purposes.
