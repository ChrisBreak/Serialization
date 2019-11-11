//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

import org.jdom2.*;
import java.lang.reflect.*;
import myClasses.*;
import java.util.List;

public class Deserializer {
  private Element root;
  private List<Element> children;
  private Object obj;

  public void deserialize(Document doc) {
    root = doc.getRootElement();
    children = root.getChildren();
    Element mainEl = children.get(0);
    obj = initObject(mainEl);
  }

  public Object getObject() {
    return obj;
  }

  private Object initObject(Element el) {
    String className = el.getAttributeValue("class");
    List<Element> classContent = el.getChildren();
    Object retObj = null;
    try {
      Class objClass = Class.forName(className);
      if (!objClass.isArray()) {
        retObj = objClass.newInstance();
        for (Element field : classContent) {
          Element value = field.getChild("value");
          Element reference = field.getChild("reference");
          Field classField = objClass.getDeclaredField(field.getAttributeValue("name"));
          classField.setAccessible(true);

          if ((value != null) && !(Modifier.isFinal(classField.getModifiers()) && Modifier.isStatic(classField.getModifiers()))) {
            String val = value.getText();
            Object fieldValue = parsePrimitive(classField.getType(), val);
            classField.set(retObj, fieldValue);
          }
          else if ((reference != null) && !(Modifier.isFinal(classField.getModifiers()) && Modifier.isStatic(classField.getModifiers()))) {
            String ref = reference.getText();
            for (Element child : children) {
              if (child.getAttributeValue("id").equals(ref)) {
                classField.set(retObj, initObject(child));
                break;
              }
            }
          }
        }
      }
      else {
        int arrayLength = Integer.parseInt(el.getAttributeValue("length"));
        Class classType = objClass.getComponentType();
        retObj = Array.newInstance(classType, arrayLength);
        if (classType.isPrimitive()) {
          for (int i = 0; i < arrayLength; i++) {
            Element classEl = classContent.get(i);
            Array.set(retObj, i, parsePrimitive(classType, classEl.getText()));
          }
        }
        else {
          for (int i = 0; i < arrayLength; i++) {
            Element classEl = classContent.get(i);
            String ref = classEl.getText();
            for (Element child : children) {
              if (child.getAttributeValue("id").equals(ref)) {
                Array.set(retObj, i, initObject(child));
                break;
              }
            }
          }
        }
      }
    }
    catch (ClassNotFoundException|InstantiationException|IllegalAccessException|NoSuchFieldException|IllegalArgumentException e) {
      e.printStackTrace();
    }
    return retObj;
  }

  private Object parsePrimitive(Class objClass, String value) {
    if (objClass == int.class) return Integer.parseInt(value);
    else if (objClass == boolean.class) return Boolean.parseBoolean(value);
    else if (objClass == char.class) return value.charAt(0);
    else if (objClass == float.class) return Float.parseFloat(value);
    else if (objClass == double.class) return Double.parseDouble(value);
    else if (objClass == long.class) return Long.parseLong(value);
    else if (objClass == byte.class) return Byte.parseByte(value);
    else if (objClass == short.class) return Short.parseShort(value);
    return value;
  }

}
