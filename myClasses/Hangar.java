package myClasses;
public class Hangar {
  private int capacity;
  private Airplane[] airplanes;

  public Hangar() {
    
  }
  public Hangar(int capacity, Airplane[] airplanes) {
    this.capacity = capacity;
    this.airplanes = airplanes;
  }
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
  public void setAirplanes(Airplane[] airplanes) {
    this.airplanes = airplanes;
  }
}
