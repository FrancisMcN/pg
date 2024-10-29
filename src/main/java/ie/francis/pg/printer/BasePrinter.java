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

  public Set<String> predict(Production production) {
    Set<String> predict = first(production);

    // If non-terminal (X) can become empty string, then
    // predict(X) = first(X) U follow(X)
    if (canBecomeEpsilon(production.nonTerminal())) {
      predict.addAll(follow(production.nonTerminal()));
    }

    return predict;
  }

  public Set<String> predict(NonTerminal nonTerminal) {

    Set<String> predict = new HashSet<>();

    List<Production> productions = this.productions.get(nonTerminal);
    for (Production production : productions) {
      predict.addAll(predict(production));
    }

    return predict;
  }

  public boolean canBecomeEpsilon(NonTerminal nonTerminal) {
    Set<NonTerminal> eps = new HashSet<>();
    List<Production> productionList = productions.get(nonTerminal);
    for (Production production : productionList) {
      if (production.terms().size() == 1) {
        if (production.terms().get(0).toString().equals("Ɛ")) {
          eps.add(nonTerminal);
        }
      }
    }
    return eps.contains(nonTerminal);
  }

  public Set<String> follow(NonTerminal nonTerminal) {
    Set<String> followSet = new HashSet<>();

    for (Map.Entry<NonTerminal, List<Production>> entry : productions.entrySet()) {

      List<Production> productionList = entry.getValue();
      for (Production production : productionList) {
        List<Term> terms = production.terms();
        List<Term> expanded = new ArrayList<>();
        for (Term term : terms) {
          expanded.addAll(term.expand());
        }
        for (int i = 0; i < expanded.size(); i++) {
          if (expanded.get(i) == nonTerminal) {
            if (i + 1 < expanded.size()) {
              Term following = expanded.get(i + 1);
              if (following instanceof NonTerminal) {
                // Add first(X) when X is a non-terminal and follows nonTerminal
                followSet.addAll(first((NonTerminal) following));
              } else {
                // Add X when X is a terminal and follows nonTerminal
                followSet.add(following.toString());
              }
            } else if (nonTerminal != production.nonTerminal()) {
              // Add follow(X) when X is a non-terminal is the right-most term in a production
              // for nonTerminal
              followSet.addAll(follow(production.nonTerminal()));
            }
          }
        }
      }
    }
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
