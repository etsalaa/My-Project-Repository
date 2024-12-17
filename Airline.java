import java.util.ArrayList;
import java.util.List;

public class Airline {
    // Ιδιότητες
    private List<Airports> airports;   
    private List<Airplane> airplanes;   
    private String airline;            

   
    public Airline(String airline) {
        this.airline = airline;
        this.airports = new ArrayList<>();
        this.airplanes = new ArrayList<>();
    }

   
    public void setNumberOfAirplanes(Airplane airplane) {
        airplanes.add(airplane);
    }

  
    public List<Airplane> getNumberOfAirplanes() {
        return airplanes;
    }

    
    public void setNumberOfDestinations(Airports airport) {
        airports.add(airport);
    }


    public List<Airports> getNumberOfDestinations() {
        return airports;
    }

   
    public void setWantedLocations(Airports airport) {
        airports.add(airport);
    }

   
    public List<Airports> getWantedLocations() {
        return airports;
    }

    @Override
    public String toString() {
        return "Airline: " + airline + "\n" +
               "Airplanes: " + airplanes + "\n" +
               "Airports: " + airports;
    }
}
