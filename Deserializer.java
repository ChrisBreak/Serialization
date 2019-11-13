//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

import org.jdom2.*;
import java.lang.reflect.*;
import myClasses.*;
import java.util.List;
import java.util.IdentityHashMap;

public class Deserializer {
  private Element root;
  private List<Element> children;
  private Object obj;
  private IdentityHashMap<String, Object> objsDone = new IdentityHashMap<String, Object>();

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
    String classId = el.getAttributeValue("id");
    if (objsDone.containsKey(classId)) {
      return objsDone.get(classId);
    }
    List<Element> classContent = el.getChildren();
    Object retObj = null;
    try {
      Class objClass = Class.forName(className);
      if (!objClass.isArray()) {
        retObj = objClass.newInstance();
        objsDone.put(classId, retObj);
        for (Element field : classContent) {
          Element value = field.getChild("value");
          Element reference = field.getChild("reference");
          Field classField = objClass.getDeclaredField(field.getAttributeValue("name"));
          classField.setAccessible(true);

          if ((value != null) && !(Modifier.isFinal(classField.getModifiers()) && Modifier.isStatic(classField.getModifiers()))) {
            String val = value.getText();
            Object fieldValue = PParser.parse(classField.getType(), val);
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
        retObj = initArray(el);
      }
    }
    catch (ClassNotFoundException|InstantiationException|IllegalAccessException|NoSuchFieldException|IllegalArgumentException e) {
      e.printStackTrace();
    }
    return retObj;
  }

  private Object initArray(Element el) throws ClassNotFoundException {
    String className = el.getAttributeValue("class");
    String classId = el.getAttributeValue("id");
    if (objsDone.containsKey(classId)) {
      return objsDone.get(classId);
    }
    List<Element> classContent = el.getChildren();
    Object retObj = null;
    Class objClass = Class.forName(className);

    int arrayLength = Integer.parseInt(el.getAttributeValue("length"));
    Class classType = objClass.getComponentType();
    retObj = Array.newInstance(classType, arrayLength);
    objsDone.put(classId, retObj);
    if (classType.isPrimitive()) {
      for (int i = 0; i < arrayLength; i++) {
        Element classEl = classContent.get(i);
        Array.set(retObj, i, PParser.parse(classType, classEl.getText()));
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
    return retObj;
  }

}
