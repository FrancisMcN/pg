/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.printer;

public class StringUtil {

  public static String capitalise(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1);
  }

  public static String indent(int count, char character) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < count; i++) {
      sb.append(character);
    }
    return sb.toString();
  }

  public static String indent(int count) {
    return indent(count, '\t');
  }

  public static String indentLines(String code, int depth) {
    StringBuilder lines = new StringBuilder();
    for (String line : code.split("\n")) {
      lines.append(indent(depth));
      lines.append(line);
      lines.append("\n");
    }
    return lines.toString();
  }
}
