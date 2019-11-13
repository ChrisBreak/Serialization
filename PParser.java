//Cristhian Sotelo-Plaza
//CPSC501 FALL 2019
//A3

public class PParser {
  public static Object parse(Class objClass, String value) {
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
