import java.util.Scanner;

public class Main {

    public static void main (String [] args) {
        //eisagwgi dedomenwn
        Scanner s = new Scanner(System.in);
        Airline airline = new Airline();
        Airplane airplane = new Airplane();
        Airports airports = new Airports();
        Login userlogin = new Login();
        HarvesineDistance hd = new HarvesineDistance();
        System.out.println("Δώσε αριθμό διαθέσιμων αεροπλάνων:");
        airline.setNumberOfAirplanes();
        int airplanesNumber = airline.getNumberOfAirplanes();
        System.out.println("Δώσε με την σειρά: Άυξοντα αριθμό αεροπλάνου, χωρητικότητα καυσίμων, το αεροδρόμιο που βρίσκεται και τις πτήσεις που μπορεί να κάνει:");
        airplane.setAirplaneDetails();
        int[20][4] airplane = get.AirplaneDetails();
        System.out.println("Δώσε τον αριθμό των προορισμών που θες να επισκέπτονται τα αεροπλάνα:");
        airline.setNumberOfDestinations();
        int numberOfDest = airline.getNumberOfDestinations();
        System.out.println("Δώσε με την σειρά: Τις τοποθεσίες που θέλεις να επισκεφτείς και πόσες φορές θέλεις να επισκεφτείς την κάθε τοποθεσία:");
        airline.setWantedLocations();
        int[10][2] locations = get.WantedLocations();
        int[][] KilometersDistance;
        //gemizei pinaka me apostasis apo kathe perioxi se kathe perioxi
        for (int row = 1; int numberOfDest; row++) {
            for (int column = 0; int numberOfDest; column++) {
                KilometersDistance[row][column] = hd.haversine(GeoLocation[row][1], GeoLocation[row][2], GeoLocation[column][1], GeoLocation[column][2]);
            }
        }
        int[][] finalDestinations; //pinakas me tis diadromes pu tha kanei to kathe aeroplano
        for (int plane = 1; int airplanesNumber; plane++) {
            int min = KilometersDistance[0][0];
            int minrow = 0;
            int mincolumn = 0;
            int i = 0;
            finalDestinations[plane][i] = airplane[plane][3]; //to kathe aeroplano ksekinaei apo opou eixe meinei tin proigoumeni fora
            i+=1;
            while airplane[plane][4] != 0 { //oso oi ptiseis pou mporei na kanei to aeroplano den einai 0 tote
                    for (int column = 0; column < KilometersDistance[airplane[plane][3]].length; column++) {
                        if ((KilometersDistance[airplane[plane][3]][column] < min) && (KiliometersDistance[airplane[plane][3]][column] != 0)) { //vriskei min apostasi
                            min = KilometersDistance[airplane[plane][3]][column];
                            minrow = airplane[plane][3];
                            mincolumn = column;
                        }
                    }
                }
                airplane[plane][4] = airplane[plane][4] - 1; //meiwnei tis diadromes pou mporei na kanei to aeroplano
                airplane[plane][3] = mincolumn; //apothikevei to aerodromio pou vriskete to aeroplano sto telos tis diadromis
                finalDestinations[plane][i] = mincolumn; //prosthetei pu pige to aeroplano gia tin diadromi
                i+=1;
                for (int row = 0; row = 10; row++) {
                    if (locations[row][1] = mincolumn) {
                        locations[row][2] = locations[row][2] - 1;//afairei apo kathe perioxi tis apetoumenes diadromes pou prepei na ginoun se auti
                        if (locations[row][2] = 0) {// an ginoun oles oi diadromes se mia perioxi midenizei tis apostaseis tis perioxhs oste na mhn ksanapigenei ekei kamia ptisi
                            for (int i = 0; i < KiliometersDistance[mincolumn].length; i++) {
                                KiliometersDistance[mincolumn][i] = 0;
                                KiliometersDistance[i][mincolumn] = 0;
                            }
                        }
                    }
                    
                }


            } 
            System.out.println("Η τελική διαδρομή του αεροπλάνου"+plane+"είναι:");
            for (int i = 0; i = 3; i++) {
                System.out.println(finalDestinations[1][i]);
            }
        }


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
        

    }
}