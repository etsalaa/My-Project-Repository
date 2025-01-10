import java.util.ArrayList;
import java.util.List;

public class Airline {
    // Ιδιότητες
    private List<Airport2> airports;  
    private String airline;   
    private int numberOfAirplanes;  
    private int numberOfDest;       

   public Airline() { }

    public Airline(String airline) {
        this.airline = airline;
        this.airports = new ArrayList<>();
    }

   
    public void setNumberOfAirplanes(int numberOfAirplanes) {
        this.numberOfAirplanes = numberOfAirplanes;
    }

  
    public int getNumberOfAirplanes() {
        return this.numberOfAirplanes;
    }

    
    public void setNumberOfDestinations(int numberOfDest) {
        this.numberOfDest = numberOfDest;
    }


    public int getNumberOfDestinations() {
        return this.numberOfDest;
    }

   
    public void setWantedLocations(Airport2 airport) {
        airports.add(airport);
    }

   
    public List<Airport2> getWantedLocations() {
        return airports;
    }

    @Override
    public String toString() {
        return "Airline: " + airline + "\n" +
               "Airports: " + airports;
    }
}
