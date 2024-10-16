/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.node;

import static ie.francis.pg.StringUtil.indent;

import java.util.ArrayList;
import java.util.List;

public class NonTerminalNode implements Node {

  private final String name;
  private List<Node> body;

  public NonTerminalNode(String name) {
    this.name = name;
    this.body = new ArrayList<>();
  }

  public NonTerminalNode addNodeToBody(Node node) {
    this.body.add(node);
    return this;
  }

  @Override
  public String print(int depth) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < body.size(); i++) {
      Node node = body.get(i);
      sb.append(node.print(depth + 1));
      if (i + 1 < body.size()) {
        sb.append("\n");
      }
    }
    return String.format("%spublic void %s() {\n%s\n%s}", indent(depth), name, sb, indent(depth));
  }

  @Override
  public String code() {
    return "";
  }

  @Override
  public String name() {
    return name;
  }
}
