import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static volatile int numberOfAirplanes = -1;  // Αριθμός αεροπλάνων
    public static volatile int numberOfDestinations = -1;  // Αριθμός προορισμών
    private static final Object lock = new Object();  // Κοινό lock για συγχρονισμό

    public static void main (String [] args) {
        //eisagwgi dedomenwn
        Scanner s = new Scanner(System.in);
        Airline airline = new Airline();
        Airplane airplane = new Airplane();
        Airport airports = new Airport();
        HaversineDistance hd = new HaversineDistance();
        //μενού για register και login
        Login loginSystem = new Login();
        Register registerSystem = new Register(loginSystem);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Pick an option");
            int choice = scanner.nextInt();
            scanner.nextLine();
  
            switch (choice) {
                 case 1:
                      System.out.print("Enter the name of the company: ");
                      String username = scanner.nextLine();
                      System.out.print("Enter the password: ");
                      String password = scanner.nextLine();
                      loginSystem.authenticate(username, password);
                      break;
                 case 2: 
                      registerSystem.createUsernameAndPassword();
                      break;
                 case 3:
                      System.out.println("Exit from the system");
                      scanner.close();
                      System.exit(0);
                 default:
                      System.out.println("Unvalid option. Try again.");
            } 
         }       
        
         // Εμφανίζεται το παράθυρο για τα αεροπλάνα
        OptionPaneDemoNumberOfAirplanes demoAirplanes = new OptionPaneDemoNumberOfAirplanes(null);
        demoAirplanes.mainImpl();
        
        demoAirplanes.setInputListener(input -> {
            synchronized (lock) {
                numberOfAirplanes = input; // Αποθηκεύουμε την απάντηση
                System.out.println("Ο αριθμός αεροπλάνων που αποθηκεύτηκε είναι: " + numberOfAirplanes);
                airline.setNumberOfAirplanes(numberOfAirplanes); 
                lock.notifyAll(); // Ενημερώνουμε ότι ολοκληρώθηκε η εισαγωγή
            }
        });
        
        //// Thread που περιμένει να απαντηθεί το πρώτο παράθυρο (αεροπλάνα) και μετά ανοίγει το δεύτερο (προορισμοί)
        Thread destinationsThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    while (numberOfAirplanes == -1) {
                        System.out.println("Αναμονή για την εισαγωγή αριθμού αεροπλάνων...");
                        lock.wait(); // Περιμένει μέχρι να απαντηθεί το πρώτο παράθυρο
                    }

                    // Μόλις απαντηθεί, ανοίγει το δεύτερο παράθυρο
                    OptionPaneDemoNumberOfDestinations demoDestinations = new OptionPaneDemoNumberOfDestinations(null);
                    demoDestinations.mainImpl();
                    demoDestinations.setInputListener(input -> {
                        synchronized (lock) {
                            numberOfDestinations = input; // Αποθηκεύουμε την απάντηση
                            System.out.println("Ο αριθμός προορισμών που αποθηκεύτηκε είναι: " + numberOfDestinations);
                            airline.setNumberOfDestinations(numberOfDestinations);
                        }
                    });
                } catch (InterruptedException e) {
                    System.out.println("Το thread διακόπηκε.");
                }
            }
        });
        destinationsThread.start(); // Ξεκινάμε το thread για τους προορισμούς
    }
}


        //!!!Εδω μπαίνει ο κώδικας interface για Airplane Details σε μια επαναληψη for για όσα αεροπλάνα εχουμε.
       // Αυτά τα 3 να σβηστουν οταν μπει το αντιστοιχο κομματι  System.out.println("Δώσε με την σειρά: Άυξοντα αριθμό αεροπλάνου, χωρητικότητα καυσίμων, το αεροδρόμιο που βρίσκεται και τις πτήσεις που μπορεί να κάνει:");
        airplane.setAirplaneDetails();
        int[20][4] airplane = get.AirplaneDetails();
        System.out.println("Δώσε τον αριθμό των προορισμών που θες να επισκέπτονται τα αεροπλάνα:");
        airline.setNumberOfDestinations();
        int numberOfDest = airline.getNumberOfDestinations();

    
        //!!!Εδω μπαίνει ο κώδικας interface για τους προορισμούς
        // Να σβηστουν οταν μπει το αντιστοιχο κομματι 
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

    }
}
}