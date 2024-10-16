/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.node;

import static ie.francis.pg.StringUtil.indent;

import java.util.ArrayList;
import java.util.List;

public class OptionalNode implements Node {
  private final List<Node> nodes;

  public OptionalNode() {
    this.nodes = new ArrayList<>();
  }

  public OptionalNode addNode(Node node) {
    this.nodes.add(node);
    return this;
  }

  @Override
  public String print(int depth) {

    StringBuilder ifStatements = new StringBuilder();
    for (int i = 0; i < nodes.size(); i++) {
      ifStatements.append(
          String.format(
              "if (%s) {", String.format("nextTokenIs(%s)", nodes.get(i).name().toUpperCase())));
      ifStatements.append("\n");

      // Include arbitrary code
      String code = nodes.get(i).code();
      StringBuilder codeToAdd = new StringBuilder();
      for (String line : code.split("\n")) {
        codeToAdd.append(indent(depth + 1));
        codeToAdd.append(line);
        codeToAdd.append("\n");
      }
      ifStatements.append(codeToAdd);
      ifStatements.append(indent(depth + 1));
      ifStatements.append("return;\n");
      ifStatements.append(indent(depth));
      ifStatements.append("}");
      if (i + 1 < nodes.size()) {
        ifStatements.append(" else ");
      }
    }
    return String.format("%s%s", indent(depth), ifStatements);
  }

  @Override
  public String code() {
    return "";
  }

  @Override
  public String name() {
    return "optional";
  }
}
