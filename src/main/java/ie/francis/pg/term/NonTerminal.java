/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NonTerminal implements Term {

  private final String name;

  public NonTerminal(String name) {
    this.name = name;
  }

  @Override
  public List<Term> expand() {
    List<Term> terms = new ArrayList<>();
    terms.add(this);
    return terms;
  }

  //    @Override
  public Set<Term> first() {
    return new HashSet<>();
  }

  public String name() {
    return name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
