public class Airport {
    private String name; // Όνομα αεροδρομίου
    private double latitude; // Γεωγραφικό πλάτος
    private double longitude; // Γεωγραφικό μήκος
    private int visits; // Μέγιστη χωρητικότητα αεροπλάνων

    public Airport() {}

    public Airport(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Κατασκευαστής
    public Airport(String name, double latitude, double longitude, int visits) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visits = visits;
    }

    // Getters και Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVisits() {
        return visits;
    }

    public void setCapacity(int capacity) {
        this.visits = visits;
    }

    // Μέθοδος για εμφάνιση πληροφοριών
    public void displayInfo() {
        System.out.printf("Αεροδρόμιο: %s, Συντεταγμένες: (%.2f, %.2f), Χωρητικότητα: %d%n",
                name, latitude, longitude, visits);
    }
}
