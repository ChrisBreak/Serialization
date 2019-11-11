package myClasses;
public class Pilot{
  private String name;
  private int[] ratings;
  private Airport base;

  public Pilot() {
    
  }
  public Pilot(String name, int[] ratings, Airport base) {
    this.name = name;
    this.ratings = ratings;
    this.base = base;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setRatings(int[] ratings) {
    this.ratings = ratings;
  }
}
