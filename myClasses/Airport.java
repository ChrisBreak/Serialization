//class with array of objects

package myClasses;
public class Airport {
  private String location;
  private Airplane[] onSite;

  public Airport(){

  }
  public Airport(String location, Airplane[] onSite) {
    this.location = location;
    this.onSite = onSite;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public void setOnsite(Airplane[] onSite) {
    this.onSite = onSite;
  }
}
