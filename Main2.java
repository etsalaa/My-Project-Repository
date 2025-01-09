import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
         // Δημιουργία πίνακα αεροδρομίων (αντικείμενα τύπου Airport)
         Airport[] airports = {
            new Airport("Αθήνα", 37.9838, 23.7275),
            new Airport("Παρίσι", 48.8566, 2.3522),
            new Airport("Λονδίνο", 51.5074, -0.1278),
            new Airport("Μιλάνο", 45.4642, 9.1900),
            new Airport("Βρυξέλλες", 50.8503, 4.3517),
            new Airport("Βερολίνο", 52.5200, 13.4050),
            new Airport("Στοκχόλμη", 59.3293, 18.0686),
            new Airport("Όσλο", 59.9139, 10.7522),
            new Airport("Μαδρίτη", 40.4168, -3.7038),
            new Airport("Άμστερνταμ", 52.3676, 4.9041)
        };

        // Δημιουργία δισδιάστατου πίνακα String για αποθήκευση των δεδομένων
        String[][] airportArray = new String[airports.length][3];

        // Γέμισμα του πίνακα
        for (int i = 0; i < airports.length; i++) {
            airportArray[i][0] = airports[i].getName();                // Όνομα
            airportArray[i][1] = String.valueOf(airports[i].getLatitude());  // Γεωγραφικό Πλάτος
            airportArray[i][2] = String.valueOf(airports[i].getLongitude()); // Γεωγραφικό Μήκος
        }
        //!!!Εδω μπαίνει ο κώδικας interface για Airplane Details σε μια επαναληψη for για όσα αεροπλάνα εχουμε.
       // Αυτά τα 3 να σβηστουν οταν μπει το αντιστοιχο κομματι  System.out.println("Δώσε με την σειρά: Άυξοντα αριθμό αεροπλάνου, χωρητικότητα καυσίμων, το αεροδρόμιο που βρίσκεται και τις πτήσεις που μπορεί να κάνει:");
    insertedAirports[numberOfDestinations] int;
    for (i=0; i=numberOfDestinations; i++) {
       JFrame frame = new JFrame("Flight Destinations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select your Destinations", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] destinations = {
            "1. Αθήνα", "2. Παρίσι", "3. Λονδίνο", "4. Μιλάνο", "5. Βρυξέλλες",
            "6. Βερολίνο", "7. Στοκχόλμη", "8. Όσλο", "9. Μαδρίτη", "10. Άμστερνταμ"
        };
        JList<String> destinationList = new JList<>(destinations);
        destinationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(destinationList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton selectButton = new JButton("Show Selections");
        panel.add(selectButton, BorderLayout.SOUTH);

        JLabel selectionLabel = new JLabel("", JLabel.CENTER);
        selectionLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        panel.add(selectionLabel, BorderLayout.SOUTH);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDestination = destinationList.getSelectedValuesList();
                if (selectedDestination != null) {
                    JOptionPane.showMessageDialog(frame, "You selected: " + String.join(", ", selectedDestinations));
                    insertedAirports[i] = selectedDestination;
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a destination.");
                }
            }
        });

        frame.add(panel);

        frame.setVisible(true);
    }
    
    
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