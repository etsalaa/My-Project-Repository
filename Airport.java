public class Airport {
    private String name; // Όνομα αεροδρομίου
    private double latitude; // Γεωγραφικό πλάτος
    private double longitude; // Γεωγραφικό μήκος
    private int capacity; // Μέγιστη χωρητικότητα αεροπλάνων

    // Κατασκευαστής
    public Airport(String name, double latitude, double longitude, int capacity) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Μέθοδος για εμφάνιση πληροφοριών
    public void displayInfo() {
        System.out.printf("Αεροδρόμιο: %s, Συντεταγμένες: (%.2f, %.2f), Χωρητικότητα: %d%n",
                name, latitude, longitude, capacity);
    }
}
