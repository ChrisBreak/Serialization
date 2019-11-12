//class with object variable

package myClasses;
public class Runway {
  private int length;
  private int width;
  private Airport airport;

  public Runway() {

  }
  public Runway(int length, int width, Airport airport) {
    this.length = length;
    this.width = width;
    this.airport = airport;
  }
  public void setLength(int length) {
    this.length = length;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  public void setAirport(Airport airport) {
    this.airport = airport;
  }
}
