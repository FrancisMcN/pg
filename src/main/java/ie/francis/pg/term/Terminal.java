/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Terminal implements Term {

  private final String value;

  public Terminal(String name) {
    this.value = name;
  }

  public String value() {
    return value;
  }

  @Override
  public List<Term> expand() {
    List<Term> terms = new ArrayList<>();
    terms.add(this);
    return terms;
  }

  @Override
  public Set<Term> first() {
    return new HashSet<>(List.of(this));
  }

  @Override
  public String toString() {
    return String.format("%s", value);
  }
}
