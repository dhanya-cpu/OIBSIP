public class Ticket {
    private String pnr;
    private String passengerName;
    private int age;
    private String gender;
    private String phone;
    private String trainNumber;
    private String trainName;
    private String from;
    private String to;
    private String journeyDate;
    private String classType;
    private String status;

    public Ticket(String pnr, String passengerName, int age, String gender,
                  String phone, String trainNumber, String trainName,
                  String from, String to, String journeyDate,
                  String classType, String status) {
        this.pnr           = pnr;
        this.passengerName = passengerName;
        this.age           = age;
        this.gender        = gender;
        this.phone         = phone;
        this.trainNumber   = trainNumber;
        this.trainName     = trainName;
        this.from          = from;
        this.to            = to;
        this.journeyDate   = journeyDate;
        this.classType     = classType;
        this.status        = status;
    }

    // Getters
    public String getPnr()           { return pnr; }
    public String getPassengerName() { return passengerName; }
    public int    getAge()           { return age; }
    public String getGender()        { return gender; }
    public String getPhone()         { return phone; }
    public String getTrainNumber()   { return trainNumber; }
    public String getTrainName()     { return trainName; }
    public String getFrom()          { return from; }
    public String getTo()            { return to; }
    public String getJourneyDate()   { return journeyDate; }
    public String getClassType()     { return classType; }
    public String getStatus()        { return status; }

    // Setter
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format(
            "PNR: %s | %s | Train: %s (%s) | %s → %s | Date: %s | Class: %s | Status: %s",
            pnr, passengerName, trainNumber, trainName, from, to, journeyDate, classType, status
        );
    }
}
