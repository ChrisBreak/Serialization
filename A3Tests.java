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

public class A3Tests {

  @Test
  public void testPParser() {
    assertEquals("\ntestPParser int", 24, PParser.parse(int.class, "24"));
    assertEquals("\ntestPParser boolean", true, PParser.parse(boolean.class, "true"));
    assertEquals("\ntestPParser char", 'C', PParser.parse(char.class, "C"));
    assertEquals("\ntestPParser String", "String", PParser.parse(String.class, "String"));
  }

  public static void main( String[] args ) {
      Result result = JUnitCore.runClasses( A3Tests.class );
      for (Failure failure : result.getFailures()) {
          System.out.println(failure.toString());
      }
  }
}
