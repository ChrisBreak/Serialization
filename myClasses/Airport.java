package myClasses;
public class Airport {
  private Hangar[] hangars;
  private String location;

  public Airport(){
    
  }
  public Airport(Hangar[] hangars, String location) {
    this.hangars = hangars;
    this.location = location;
  }
  public void setHangars(Hangar[] hangars) {
    this.hangars = hangars;
  }
  public void setLocation(String location) {
    this.location = location;
  }
}
