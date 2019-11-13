//A3Tests
//compile: javac -cp .:jdom2/*:junit/* *.java
//run: java -cp .:junit/* org.junit.runner.JUnitCore A3Tests


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import myClasses.Airplane;

public class A3Tests {
  private ByteArrayOutputStream testStream;
  private final PrintStream originalPrint = System.out;

  @Test
  public void testPParser() {
    assertEquals("\ntestPParser int", 24, PParser.parse(int.class, "24"));
    assertEquals("\ntestPParser boolean", true, PParser.parse(boolean.class, "true"));
    assertEquals("\ntestPParser char", 'C', PParser.parse(char.class, "C"));
    assertEquals("\ntestPParser String", "String", PParser.parse(String.class, "String"));
  }

  @Test
  public void testInspector() {
    testStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(testStream));
    Inspector inspector = new Inspector();
    Airplane airP = new Airplane(1234, 40, true);
    inspector.inspect(airP, true);
    assertEquals("\nAirplane class inspection", "CLASS: myClasses.Airplane\n"
      +"  FIELDS: FROM: myClasses.Airplane\n"
      +"    FIELD NAME: id FROM myClasses.Airplane\n"
      +"    TYPE: int\n"
      +"    MODIFIER: private\n"
      +"    VALUE: 1234\n"
      +"    -----------------\n"
      +"    FIELD NAME: seats FROM myClasses.Airplane\n"
      +"    TYPE: int\n"
      +"    MODIFIER: private\n"
      +"    VALUE: 40\n"
      +"    -----------------\n"
      +"    FIELD NAME: active FROM myClasses.Airplane\n"
      +"    TYPE: boolean\n"
      +"    MODIFIER: private\n"
      +"    VALUE: true\n"
      +"    -----------------\n"
      +"======END OF CLASS myClasses.Airplane======\n", testStream.toString());
    System.setOut(originalPrint);
  }

  public static void main( String[] args ) {
      Result result = JUnitCore.runClasses( A3Tests.class );
      for (Failure failure : result.getFailures()) {
          System.out.println(failure.toString());
      }
  }
}
