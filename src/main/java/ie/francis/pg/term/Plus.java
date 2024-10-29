/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.term;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Plus implements Term {

  private final List<Term> terms;

  public Plus() {
    this.terms = new ArrayList<>();
  }

  public Plus addTerm(Term term) {
    this.terms.add(term);
    return this;
  }

  @Override
  public Set<Term> first() {
    Set<Term> first = new HashSet<>();
    for (Term term : this.terms) {
      first.addAll(term.first());
    }
    return first;
  }

  public List<Term> terms() {
    return terms;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (this.terms.size() > 1) {
      sb.append("(");
    }
    for (int i = 0; i < this.terms.size(); i++) {
      sb.append(this.terms.get(i));
      if (i + 1 < this.terms.size()) {
        sb.append(" ");
      }
    }
    if (this.terms.size() > 1) {
      sb.append(")");
    }
    sb.append("+");
    return sb.toString();
  }

  @Override
  public List<Term> expand() {
    List<Term> expandedTerms = new ArrayList<>();
    for (Term term : this.terms) {
      expandedTerms.addAll(term.expand());
    }
    return expandedTerms;
  }
}
