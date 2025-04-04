/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

import java.util.ArrayList;
import java.util.List;

public class Rule {

  private final List<Term> terms;

  public Rule() {
    this.terms = new ArrayList<>();
  }

  public Rule addTerm(Term term) {
    this.terms.add(term);
    return this;
  }

  public Rule addTerm(String term) {
    this.terms.add(new Terminal(term));
    return this;
  }

  public List<Term> terms() {
    return this.terms;
  }
}
