/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

import java.util.List;

public class Production {

  private final NonTerminal nonterm;
  private final List<Term> rule;

  public Production(NonTerminal nonterm, List<Term> rule) {
    this.nonterm = nonterm;
    this.rule = rule;
  }

  public NonTerminal getNonterm() {
    return nonterm;
  }
}
