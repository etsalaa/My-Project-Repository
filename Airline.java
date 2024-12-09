import java.util.Scanner;

public class Airline {
    private int numberOfAirplanes;
    private int numberOfDestination;
    private int[][] locations;

   private Scanner scanner = new Scanner(System.in);
    
  public void setNumberOfAirplanes() {
        System.out.print("Emfanise arithmo aeroplanwn: ");
        this.numberOfAirplanes = scanner.nextInt();
      
        public int getNumberOfAirplanes() {
        return this.numberOfAirplanes;
            
            public void setNumberOfDestinations() {
        System.out.print("Emfanise ton arithmo twn proorismwn: ");
        this.numberOfDestinations = scanner.nextInt();

              public int getNumberOfDestinations() {
        return this.numberOfDestinations;
                  public void setLocations() {
        locations = new int[numberOfDestinations][3]; 
        System.out.println("Emfanise tis topothesies (gewgrafiko platos,, gewgrafiko mikos) kai sinolo episkepsewn :");
        for (int i = 0; i < numberOfDestinations; i++) {
            System.out.print("Topothesia " + (i + 1) + " (platos, mikos, apaitiseis ");
            locations[i][0] = scanner.nextInt();
            locations[i][1] = scanner.nextInt(); 
            locations[i][2] = scanner.nextInt(); 

         public int[][] getLocations() {
           return this.locations;
public void printAirlineInfo() {
        System.out.println("Arithmos aeroplanwn: " + this.numberOfAirplanes);
        System.out.println("Arithmos proorismwn: " + this.numberOfDestinations);
        System.out.println("Topothesia kai apaitiseis:");
        for (int i = 0; i < numberOfDestinations; i++) {
            System.out.println("Topothesia " + (i + 1) + ": Platos=" + locations[i][0] + 
                               ", Mikoa=" + locations[i][1] + 
                               ", Apaitiseis=" + locations[i][2]);
            
