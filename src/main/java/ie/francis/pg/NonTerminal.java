/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

public class NonTerminal implements Term {

  private final String value;

  public NonTerminal(String value) {
    this.value = value;
  }

  @Override
  public String Stringify() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
