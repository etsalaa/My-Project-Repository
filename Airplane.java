public class Airplane {
    private String airplane;
    private String location;
    private double fuelCapacity;
    public Airplane(String airplane, String location, double fuelCapacity ) {
        this.airplane = airplane;
        this.location = location;
        this.fuelCapacity = fuelCapacity;
    }
    public boolean needRefueling(double remainingFuel, double needFuel) {
        return remainingFuel < needFuel; 
    }
    
    public double refuel(double remainingFuel, double fuelCapacity) {
        while (remainingFuel < fuelCapacity) {
            remainingFuel += 1000; 
            if (remainingFuel > fuelCapacity) {
                remainingFuel = fuelCapacity; 
            }
        }
        return remainingFuel; 
    }
    
    public double fuelSpending(double remainingFuel, double fuelCapacity) {
        return fuelCapacity - remainingFuel; 
    }

    public String getAirplane() {
        return airplane;
    }
    public String getlocation() {
        return location;
    }
    public double getfuelCapacity() {
        return fuelCapacity;
    }
    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }
}
