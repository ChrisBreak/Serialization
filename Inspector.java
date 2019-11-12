//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

import java.lang.reflect.*;
import java.util.ArrayList;

public class Inspector {
  private ArrayList<Object> objsDone = new ArrayList<Object>();

  public void inspect(Object obj, boolean recursive) {
      Class c = obj.getClass();
      inspectClass(c, obj, recursive, "");
  }

  private void inspectClass(Class c, Object obj, boolean recursive, String indent) {
    if (objsDone.contains(obj)){
      return;
    }
    objsDone.add(obj);
    System.out.println(indent + "CLASS: " + c.getName());
    if (c.isArray()) {
      inspectArray(c, obj, recursive, indent);
    } else {
      String insideIndent = indent + "  ";
      printFields(c, obj, recursive, insideIndent);
      System.out.println(indent + "======END OF CLASS " + c.getName() + "======");
    }
  }

  private void inspectArray(Class c, Object obj, boolean recursive, String indent) {
    String insideIndent = indent + "  ";
    for (int i = 0; i < Array.getLength(obj); i++) {
      System.out.println(insideIndent + "  VALUE: " + Array.get(obj, i));
      if (Array.get(obj, i) != null) {
        if (recursive && !(obj.getClass().getComponentType().isPrimitive())) {
          inspectClass(Array.get(obj, i).getClass(), Array.get(obj, i), recursive, insideIndent + "  ");
        }
      }
    }
  }

  private void printFields(Class c, Object obj, boolean recursive, String indent) {
    System.out.println(indent + "FIELDS: FROM: " + c.getName());
    Field[] fields = c.getDeclaredFields();
    if (fields.length > 0) {
      for (int i = 0; i < fields.length; i++) {
        fields[i].setAccessible(true);
        System.out.println(indent + "  FIELD NAME: " + fields[i].getName() + " FROM " + c.getName());
        System.out.println(indent + "  TYPE: " + fields[i].getType().getName());
        System.out.println(indent + "  MODIFIER: " + Modifier.toString(fields[i].getModifiers()));
        try {
          System.out.println(indent + "  VALUE: " + fields[i].get(obj));

          if (recursive && !(fields[i].getType().isPrimitive()) && (fields[i].get(obj) != null)) {
            inspectClass(fields[i].get(obj).getClass(), fields[i].get(obj), recursive, indent + "  ");
          }
        }
        catch (IllegalAccessException e) {
          e.printStackTrace(System.out);
          System.out.println("Error: " + e.getMessage());
        }
        System.out.println(indent + "  -----------------");
      }
    } else {
      System.out.println(indent + "  NONE");
    }
  }
}
