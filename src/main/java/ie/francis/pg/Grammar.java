/*
 * (c) 2025 Francis McNamee
 * */
 
package ie.francis.pg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grammar {

  private final Map<NonTerminal, List<Rule>> rules;
  private Set<Term> eps;
  private Map<Term, Set<Term>> first;
  private Map<Term, Set<Term>> follow;
  private Map<Term, List<Set<Term>>> predict;
  private NonTerminal start;

  public Grammar() {
    this.rules = new LinkedHashMap<>();
  }

  public Grammar addProduction(NonTerminal nonterm, Rule rule) {
    this.rules.putIfAbsent(nonterm, new ArrayList<>());
    this.rules.get(nonterm).add(rule);
    if (this.start == null) {
      this.start = nonterm;
    }
    return this;
  }

  private Set<Term> eps() {
    Set<Term> eps = new HashSet<>();
    eps.add(new Terminal("ε"));

    // Initialise EPS with every non-terminal where N -> ε
    for (NonTerminal nonterm : this.rules.keySet()) {
      List<Rule> rules = this.rules.get(nonterm);
      for (Rule rule : rules) {
        if (rule.terms().size() == 1 && rule.terms().contains(new Terminal("ε"))) {
          eps.add(nonterm);
        }
      }
    }

    boolean changed;
    do {
      changed = false;
      // Loop through all non-terminals
      for (NonTerminal nonTerminal : this.rules.keySet()) {
        // Get all productions for each non-terminal
        List<Rule> rules = this.rules.get(nonTerminal);
        // Loop through all productions for each non-terminal
        for (Rule rule : rules) {
          // If all terms in a rule are in EPS, then add the corresponding non-terminal
          // to EPS
          if (eps.containsAll(rule.terms())) {
            if (!eps.contains(nonTerminal)) {
              eps.add(nonTerminal);
              changed = true;
            }
          }
        }
      }

    } while (changed);

    return eps;
  }

  private Map<Term, Set<Term>> first() {

    Map<Term, Set<Term>> first = new HashMap<>();
    Set<Term> eps = eps();

    // Initialise first sets
    // Create a set FIRST(x) = {x} for every terminal in the grammar
    for (NonTerminal nonTerminal : this.rules.keySet()) {
      for (Rule rule : this.rules.get(nonTerminal)) {
        for (Term term : rule.terms()) {
          // If string is all-lowercase then it must be a terminal
          if (term instanceof Terminal) {
            Set<Term> set = new HashSet<>();
            set.add(term);
            first.putIfAbsent(term, set);
          }
        }
      }
    }

    // Create a set FIRST(X) = {} for every non-terminal in the grammar
    for (NonTerminal nonTerminal : this.rules.keySet()) {
      first.putIfAbsent(nonTerminal, new HashSet<>());
    }

    boolean changed;
    do {
      changed = false;
      for (NonTerminal nonTerminal : this.rules.keySet()) {
        for (Rule production : this.rules.get(nonTerminal)) {
          for (Term term : production.terms()) {
            if (!first.get(nonTerminal).containsAll(first.get(term))) {
              first.get(nonTerminal).addAll(first.get(term));
              changed = true;
            }
            if (!eps.contains(term)) {
              break;
            }
          }
        }
      }

    } while (changed);

    return first;
  }

  private Map<Term, Set<Term>> follow() {

    Map<Term, Set<Term>> follow = new HashMap<>();
    Map<Term, Set<Term>> first = first();
    Set<Term> eps = eps();

    // Create a set FOLLOW(X) = {} for every non-terminal in the grammar
    for (NonTerminal nonTerminal : this.rules.keySet()) {
      follow.putIfAbsent(nonTerminal, new HashSet<>());
    }

    boolean changed;
    do {
      changed = false;
      for (NonTerminal nonTerminal : this.rules.keySet()) {
        for (Rule production : this.rules.get(nonTerminal)) {
          List<Term> terms = production.terms();
          for (int i = 0; i < terms.size() - 1; i++) {
            Term term = terms.get(i);
            // if Yi is a non-terminal then FOLLOW(Yi) := FOLLOW(Yi) ∪ first(Yi+1Yi+2⋯Yk )
            if (term instanceof NonTerminal) {
              for (int j = i + 1; j < terms.size(); j++) {
                if (!follow.get(term).containsAll(first.get(terms.get(j)))) {
                  follow.get(term).addAll(first.get(terms.get(j)));
                  changed = true;
                }
              }
            }
          }

          for (int i = terms.size() - 1; i >= 0; i--) {
            Term term = terms.get(i);
            if (term instanceof NonTerminal) {
              // if Yi is a non-terminal FOLLOW(Yi) := FOLLOW(Yi) ∪ FOLLOW(X)
              if (!follow.get(term).containsAll(follow.get(nonTerminal))) {
                follow.get(term).addAll(follow.get(nonTerminal));
                changed = true;
              }
            }
            if (!eps.contains(term)) {
              break;
            }
          }
        }
      }

    } while (changed);

    return follow;
  }

  private Map<Term, List<Set<Term>>> predict() {

    Set<Term> eps = eps();
    Map<Term, Set<Term>> first = first();
    Map<Term, Set<Term>> follow = follow();
    Map<Term, List<Set<Term>>> predict = new HashMap<>();

    // PREDICT(X→w ) := first(w ) if eps(w)=false , and first(w ) ∪ FOLLOW(X ) otherwise
    for (NonTerminal nonTerminal : this.rules.keySet()) {
      for (Rule production : this.rules.get(nonTerminal)) {
        List<Term> terms = production.terms();
        Set<Term> set = new HashSet<>();
        Term term = terms.get(0);
        if (!eps.contains(term)) {
          set.addAll(first.get(term));
        } else {
          set.addAll(first.get(term));
          set.addAll(follow.get(nonTerminal));
        }
        set.remove(new Terminal("ε"));
        predict.putIfAbsent(nonTerminal, new ArrayList<>());
        predict.get(nonTerminal).add(set);
      }
    }

    return predict;
  }

  public Map<Term, List<Set<Term>>> getPredict() {
    return predict;
  }

  public Set<Term> getEps() {
    return eps;
  }

  public Map<NonTerminal, List<Rule>> getRules() {
    return rules;
  }

  public void generate() {

    //        Set<Term> eps = eps();
    //        System.out.println(eps);
    //
    //        System.out.println("--- first ---");
    //        Map<Term, Set<Term>> first = first();
    //        for (Map.Entry<Term, Set<Term>> entry : first.entrySet()) {
    //            System.out.println(entry.getKey() + " " + entry.getValue());
    //        }
    //
    //        System.out.println("--- follow ---");
    //        Map<Term, Set<Term>> follow = follow();
    //        for (Map.Entry<Term, Set<Term>> entry : follow.entrySet()) {
    //            System.out.println(entry.getKey() + " " + entry.getValue());
    //        }
    //        System.out.println("--- predict ---");
    //        Map<Term, List<Set<Term>>> predict = predict();
    //        for (Map.Entry<Term, List<Set<Term>>> entry : predict.entrySet()) {
    //            for (Set<Term> rule : entry.getValue()) {
    //                System.out.println(entry.getKey() + " " + rule);
    //            }
    //        }

    this.eps = eps();
    this.first = first();
    this.follow = follow();
    this.predict = predict();
  }

  public NonTerminal getStart() {
    return start;
  }
}
