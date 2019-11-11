package myClasses;
public class Airplane {
  private int seats;
  private boolean active;
  private Pilot pilot;

  public Airplane(){

  }
  public Airplane(int seats, boolean active, Pilot pilot) {
    this.seats = seats;
    this.active = active;
    this.pilot = pilot;
  }
  public void setSeats(int seats) {
    this.seats = seats;
  }
  public void setActive(boolean active) {
    this.active = active;
  }
  public void setPilot(Pilot pilot) {
    this.pilot = pilot;
  }
}
