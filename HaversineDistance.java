public class HaversineDistance {

    // Μέθοδος για τον υπολογισμό της απόστασης
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        // Ακτίνα της Γης σε χιλιόμετρα
        final double R = 6371.0;
        
        // Μετατροπή μοιρών σε ακτίνια
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        
        // Διαφορές συντεταγμένων
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        
        // Υπολογισμός τύπου Haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        // Τελική απόσταση
        return R * c;
    }
        
    public static void main(String[] args) {
        // Συντεταγμένες Αθήνας
        double lat1 = 37.9838;
        double lon1 = 23.7275;
        
        // Συντεταγμένες Θεσσαλονίκης
        double lat2 = 40.6401;
        double lon2 = 22.9444;
        
        // Υπολογισμός απόστασης
        double distance = haversine(lat1, lon1, lat2, lon2);
        
        // Εμφάνιση αποτελέσματος
        System.out.printf("Η απόσταση μεταξύ Αθήνας και Θεσσαλονίκης είναι %.2f km.%n", distance);
    }
}
        