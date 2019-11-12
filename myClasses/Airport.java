//class with array of objects

package myClasses;
public class Airport {
  private String location;
  private Airplane[] onSite;
  private Runway[] runways;

  public Airport(){

  }
  public Airport(String location, Airplane[] onSite, Runway[] runways) {
    this.location = location;
    this.onSite = onSite;
    this.runways = runways;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public void setOnsite(Airplane[] onSite) {
    this.onSite = onSite;
  }
  public void setRunways(Runway[] runways) {
    this.runways = runways;
  }
}
