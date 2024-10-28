/*
 * (c) 2024 Francis McNamee
 * */
 
package ie.francis.pg.printer;

import ie.francis.pg.Production;
import ie.francis.pg.term.NonTerminal;
import ie.francis.pg.term.Term;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasePrinter {

  protected final StringBuilder cp;
  protected final Map<NonTerminal, List<Production>> productions;

  protected int depth = 0;

  public BasePrinter(Production... productions) {
    this.productions = new LinkedHashMap<>();
    for (Production production : productions) {
      if (!this.productions.containsKey(production.nonTerminal())) {
        this.productions.put(production.nonTerminal(), new ArrayList<>());
      }
      this.productions.get(production.nonTerminal()).add(production);
    }
    this.cp = new StringBuilder();
  }

  public Set<Term> follow(NonTerminal nt) {
    Set<Term> followSet = new HashSet<>();

    //        for (NonTerminal nonTerminal : this.nonTerminals) {
    //            for (Production production : nonTerminal.productions()) {
    //                List<Term> tempTerms = new ArrayList<>();
    //                for (Term term : production.terms()) {
    //                    tempTerms.add(term);
    //                }
    //                for (int i = 0; i < production.terms().size(); i++) {
    //                    List<Term> terms = tempTerms;
    //                    Term term = terms.get(i);
    //                    if (term instanceof Modifier) {
    //                        terms.remove(i);
    //                        terms.addAll(i, ((Modifier)term).expand());
    //                    }
    //                    if (terms.get(i) == nt && i + 1 < terms.size()) {
    //                        followSet.addAll(terms.get(i + 1).first());
    //                    }
    //                    else if (terms.get(i) == nt && i + 1 >= terms.size()) {
    //                        if (nt != nonTerminal) {
    //                            followSet.addAll(follow(nonTerminal));
    //                        }
    //                    }
    //                }
    //            }
    //        }

    return followSet;
  }

  public Set<String> first(Production production) {

    Set<String> firstSet = new HashSet<>();
    if (production.terms().size() > 0) {
      Term term = production.terms().get(0).expand().get(0);
      if (term instanceof NonTerminal) {
        firstSet.addAll(first((NonTerminal) term));
      } else {
        firstSet.add(term.toString());
      }
    }

    return firstSet;
  }

  public Set<String> first(NonTerminal nonTerminal) {
    Set<String> firstSet = new HashSet<>();

    List<Production> productions = this.productions.get(nonTerminal);
    for (Production production : productions) {
      firstSet.addAll(first(production));
    }

    return firstSet;
  }
}
