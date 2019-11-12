//class with only primitive variables

package myClasses;
public class Airplane {
  private int id;
  private int seats;
  private boolean active;

  public Airplane(){

  }
  public Airplane(int id, int seats, boolean active) {
    this.id = id;
    this.seats = seats;
    this.active = active;
  }
  public void setId(int id) {
    this.id = id;
  }
  public void setSeats(int seats) {
    this.seats = seats;
  }
  public void setActive(boolean active) {
    this.active = active;
  }
}
