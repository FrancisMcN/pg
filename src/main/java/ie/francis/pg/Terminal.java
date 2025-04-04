/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

import java.util.Objects;

public class Terminal implements Term {

  private final String value;

  public Terminal(String value) {
    this.value = value;
  }

  @Override
  public String Stringify() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Terminal terminal = (Terminal) o;
    return Objects.equals(value, terminal.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
