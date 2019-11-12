//class with array of primitives

package myClasses;
public class Pilot{
  private String name;
  private int age;
  private int[] ratings;

  public Pilot() {

  }
  public Pilot(String name, int age, int[] ratings) {
    this.name = name;
    this.age = age;
    this.ratings = ratings;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public void setRatings(int[] ratings) {
    this.ratings = ratings;
  }
}
