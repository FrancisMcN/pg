/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg;

import ie.francis.pg.term.NonTerminal;
import ie.francis.pg.term.Term;
import java.util.ArrayList;
import java.util.List;

public class Production {

  private final NonTerminal nt;
  private final List<Term> terms;

  public Production(NonTerminal nt) {
    this.nt = nt;
    this.terms = new ArrayList<>();
  }

  public Production addTerm(Term term) {
    this.terms.add(term);
    return this;
  }

  public List<Term> terms() {
    return this.terms;
  }

  public NonTerminal nonTerminal() {
    return nt;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.terms.size(); i++) {
      Term term = this.terms.get(i);
      sb.append(term);
      if (i + 1 < this.terms.size()) {
        sb.append(" ");
      }
    }
    return sb.toString();
  }
}
