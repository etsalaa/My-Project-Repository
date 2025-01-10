public class Airport2 {
    private String name; // Όνομα αεροδρομίου
    private String latitude; // Γεωγραφικό πλάτος
    private String longitude; // Γεωγραφικό μήκος
    private int visits; // Μέγιστη χωρητικότητα αεροπλάνων

    public Airport2() {}

    public Airport2(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Κατασκευαστής
    public Airport2(String name, String latitude, String longitude, int visits) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
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
