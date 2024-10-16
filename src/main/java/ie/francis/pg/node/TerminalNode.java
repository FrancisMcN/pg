/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.node;

public class TerminalNode implements Node {

  private final String terminal;
  private String code;

  public TerminalNode(String terminal) {
    this.terminal = terminal;
  }

  public TerminalNode setCode(String code) {
    this.code = code;
    return this;
  }

  @Override
  public String print(int depth) {
    return String.format("%s", terminal);
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public String name() {
    return this.terminal;
  }
}
